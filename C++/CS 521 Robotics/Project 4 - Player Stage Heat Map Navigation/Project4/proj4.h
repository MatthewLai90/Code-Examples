/*
	Project 2 Move to Point
	Author: Matthew Lai
	CS 521 Project 2
*/
#ifndef __proj4_h_INCLUDED__
#define __proj4_h_INCLUDED__

#include <iostream>
#include <libplayerc++/playerc++.h>
#include <iostream>
#include <fstream>
#include <cmath>
#include "proj4map.h"

using namespace std;
using namespace PlayerCc;

// parameters

//class Navigator {
//  int[][] map;
//};

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
void ACT(double throttle, double wheel, Position2dProxy &pp);

// Calculates a vector's magnitude
double VectorMagnitude(Vect v);

// Normalizes a vector
Vect Normalize(Vect v);

// Calculates the angle between two vectors
double VectorAngle(Vect a, Vect b);

// Calculates the unit vector towards the goalpoint
Vect GoalVector(double x, double y, Waypoint wp);

// Converts Degrees to Radians
double D2R(double degree);
// Main program. Receives a robot and a target waypoint and attempts to guide
//  the robot to the waypoint.
void Pilot(PlayerClient &robot, Waypoint wp);


#endif
