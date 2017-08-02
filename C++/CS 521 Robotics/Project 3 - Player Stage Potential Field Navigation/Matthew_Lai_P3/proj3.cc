/*
	Project 2 Move to Point
	Author: Matthew Lai
	CS 521 Project 2
*/

#include <iostream>
#include <libplayerc++/playerc++.h>
#include <iostream>
#include <fstream>
#include <cmath>

using namespace std;
using namespace PlayerCc;

// parameters
const double MAXSPEED = .5;		// Maximum Speed
const double MINSPEED = 0.1;		// Minimum Speed
const double MAXTURN = 1;		// Turn Speed
const double PI = atan(1)*4;
const double WPMINDIST = 0.15;		// Minimum dist to WP to be considered reached

struct Waypoint {
  bool nextWaypoint;
  double x;
  double y;
};

struct Vect {
  double x;
  double y;
};

// ACT module that simple sends the robot server commands through
//  the Position2dProxy. The parameters throttle and wheel are given
//  as throttle % and direction % (pos ccw, neg cw) and uses the above
//  constant values as the robot's maximum movement parameters
void ACT(double throttle, double wheel, Position2dProxy &pp)
{
  double speed = throttle * (MAXSPEED);
  double turn = wheel * MAXTURN;
  pp.SetSpeed(speed, turn);
}

// Calculates a vector's magnitude
double VectorMagnitude(Vect v)
{
   return sqrt(pow(v.x, 2)+pow(v.y, 2));
}

// Normalizes a vector
Vect Normalize(Vect v)
{
   Vect r = {v.x/VectorMagnitude(v), v.y/VectorMagnitude(v)};
   return r;
}

// Calculates the angle between two vectors
double VectorAngle(Vect a, Vect b)
{
    return acos((a.x*b.x + a.y*b.y)
		/(VectorMagnitude(a) * VectorMagnitude(b)));
}

// Calculates the unit vector towards the goalpoint
Vect GoalVector(double x, double y, Waypoint wp)
{
    Vect targetVector;
    targetVector.x = wp.x-x;
    targetVector.y = wp.y-y;
    targetVector = Normalize(targetVector);
    return targetVector;
}

// Converts Degrees to Radians
double D2R(double degree)
{
    return (degree/180.0) * PI;
}

// Main program. Receives a robot and a target waypoint and attempts to guide
//  the robot to the waypoint.
void Pilot(PlayerClient &robot, Waypoint wp)
{
   cout << "Pilot Starting\n";
   Position2dProxy pp(&robot,0);
   RangerProxy rp(&robot,0);

   cout << "Heading to waypoint (" << wp.x << ", " << wp.y <<")\n";
   bool atWP = false;

   while(!atWP)
   {
      robot.Read();

      double yaw = pp.GetYaw();
      double x = pp.GetXPos();
      double y = pp.GetYPos();
      Vect goalVect = Normalize(GoalVector(x, y, wp));
// Determine unit vector to goal. The base vector to perform additions to.
      Vect moveVect = goalVect;

// Determine obstacles and adds the various calculated potential field vectors to movement vector.
      bool obstacle = false;
      double minRange = 8;
      int minIndex = -1;
      double closeCount = 0;
      double maxSensorRange = rp.GetMaxRange();

// Determines unit vector of the robot's heading.
      Vect faceVector = {cos(yaw), sin(yaw)};

// Scan thorough Ranger readings and determine obstacles and the appropriate vector fields for each one
      for(int i = 0; i < 180; i++)
      {
	  if(rp[i] < 1)
	  closeCount++;

          if(!obstacle && rp[i] != 0) obstacle = true;

// Calculating for currently detected obstacle
          if(obstacle)
          {
// If Object edge is detected or ranger scan is complete perform the following actions
	      if(rp[i] == 0 || i == 179)
	      {
// Perform Vector addition for calculated Vector field for detected object
  // Calculate Tangential Vector based on robot heading and closes point
	 	  double obstacleAngle = D2R(minIndex-90)+yaw;
		  int sign1 = 1;
		  if(minIndex-90 < 0) sign1 = -1;
		  int sign2 = -sign1;
		  Vect tangentialVector = { sign1 * sin(obstacleAngle), sign2 * cos(obstacleAngle) };
		  tangentialVector = Normalize(tangentialVector);

  // Multiply tangentialVector by a scalar determined by the distance from the object
  		  double tScalar = (0.5-minRange)/.005;
		  if(minRange > 0.5) tScalar = 0;
		  tangentialVector.x = tangentialVector.x * tScalar;
		  tangentialVector.y = tangentialVector.y * tScalar;

  // Add Tangential field to movement vector according to unit vector tangent to obstacle heading
		  moveVect.x = moveVect.x + tangentialVector.x;
		  moveVect.y = moveVect.y + tangentialVector.y;

  // Calculate repulsion vector and multiply by scalar according to distance from object. Disallows
  //  robot from moving through walls.
		  double rScalar = (0.20-minRange)/0.0005;
		  if(minRange > 0.20) 
		      rScalar = 0;

		  Vect repulsionVector = { -cos(obstacleAngle), -sin(obstacleAngle) };
		  repulsionVector = Normalize(repulsionVector);
		  repulsionVector.x = repulsionVector.x * rScalar;
		  repulsionVector.y = repulsionVector.y * rScalar;
		  
  // Add Repulsion field to movement vector according to distance from obstacle
		  moveVect.x = moveVect.x + repulsionVector.x;
		  moveVect.y = moveVect.y + repulsionVector.y;

// If the robot is in a corner, turn 135 degrees Random way way and continue on
		  if(closeCount > 150)
		  {
			cout << "Escape Corner" << endl;
		      	int escape = (rand() % 2) * 2 - 1;
			double oldYaw = yaw;
			double newYaw = yaw;
			while(abs(oldYaw - newYaw) < D2R(135))
			{
			    robot.Read();
			    newYaw = pp.GetYaw();
			    pp.SetSpeed(0, escape);
			}
		  }

// Reset variables after clearing the object
                  obstacle = false;
                  minRange = 8;
		  minIndex = -1;
		  closeCount = 0;
                  continue;
              }

// Tracks the closest point to detected object
              if(rp[i] < minRange) 
	      {
	          minRange = rp[i];
	          minIndex = i;
	      }
          }
      }

// Calculate Speed and turn Angle based on calculated move vector and distance to GoalPoint. Add Random Noise. Normalize Vector.
    Vect randomVect = {rand(), rand()};
    randomVect = Normalize(randomVect);
    
    moveVect.x += randomVect.x/100;
    moveVect.y += randomVect.y/100;

    moveVect = Normalize(moveVect);

// Determine distance to Goal Waypoint
    double dist2WP = sqrt(pow(wp.x-x,2)+pow(wp.y-y,2));

// Dot product to determine angle difference between robot heading vector and move vector.
    double turnAngle = acos(faceVector.x*moveVect.x + faceVector.y*moveVect.y);

// Determine robot speed and robot turn direction/speed
    double speed = (pow(PI-turnAngle,2.5)/pow(PI,2.5));
    if(dist2WP < 2) speed = speed * sqrt(dist2WP/2);
    if(dist2WP < 1 && turnAngle > PI/10) speed = speed * 0.1;

    double angle = 1;
    if(faceVector.x*moveVect.y-faceVector.y*moveVect.x < 0) angle = -1;

    if(turnAngle < (PI/2)) angle = angle * (sqrt(turnAngle)/sqrt(PI));
    if(turnAngle < (PI/12)) angle = angle/10;

// Attractor potential field increases as robot approaches goal point.
    if(dist2WP < 3 && dist2WP != 0) 
    {
	moveVect.x += (3/dist2WP) * goalVect.x;
	moveVect.y += (3/dist2WP) * goalVect.y;
	moveVect = Normalize(moveVect);
    }

    cout << "Move Vector: <" << moveVect.x << ", " << moveVect.y << "> \t";
    cout << "Speed: " << speed << "\tAngle: " << angle;
    cout <<"\tDistance from WP: " << dist2WP << "\tTurn to Target Vector: " << turnAngle << endl;
//    cout << "Face Vector: <" << faceVector.x << ", " << faceVector.y << ">" << endl;

// Inform the ACT module the speed and turn percentages the robot should be using to reach
//  the current objective waypoint
      ACT(speed,angle,pp);

// If the robot is within a certain distance of the current objective waypoint, then the
//  waypoint is reached and the program is finished
      if(dist2WP < WPMINDIST) atWP = true;
   }
   cout << "Waypoint reached\n";
   cout << "Program Finished\n";
}

int main(int argc, char *argv[])
{
  if(argc != 3)
  {
    cout << "command: ./proj3 x y" << endl;
    return 0;
  }
  Waypoint target;
  target.x = atof(argv[1]);
  target.y = atof(argv[2]);
  cout << argv[1] << ", " << argv[2] << endl;
  PlayerClient robot("localhost");

  Pilot(robot, target);
}





