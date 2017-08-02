
#include "proj4navigate.h"
#include <cmath>
#include <queue>
#include <cstdlib>
#include <set>
#include <string>

struct GridPoint {
	int i;
	int j;
	int cellValue;
	GridPoint() { }
	GridPoint(int Ii, int Ij, int IcellValue) {
		i = Ii;
		j = Ij;
		cellValue = IcellValue;
	}
	bool operator<(const GridPoint& rhs) const { return (i*100000+j < rhs.i*100000+rhs.j); }
	bool operator>(const GridPoint& rhs) const { return (i*100000+j > rhs.i*100000+rhs.j); }
	bool operator==(const GridPoint& rhs) const { return (i*100000+j == rhs.i*100000+rhs.j); }
};

void Navigate(Map m, Waypoint& start, Waypoint& target, vector<Waypoint> &results, int robotSize)
{
	using namespace std;

	int ROBOT_SIZE = robotSize; // grow by this much
	int growthSize = ceil(ROBOT_SIZE/2);
	float **newGrid = new float*[m.mapheight];
	for(int i = 0; i < m.mapheight; i++)
	newGrid[i] = new float[m.mapwidth];
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// grow map
	cout << "\nGrowing Map" << endl;
	for(int i = 0; i < m.mapheight; i++)
	for(int j = 0; j < m.mapwidth; j++)
	if(m.gridMap[i][j] == 1)
	for(int k = -growthSize; k <= growthSize; k++)
	for(int l = -growthSize; l <= growthSize; l++)
	if(i+k >=0 && i+k < m.mapheight && j+l >=0 && j+l < m.mapwidth)
	newGrid[i+k][j+l] = 1;

	outputMap(m.mapheight, m.mapwidth, newGrid, m, "1-GrownMap.pnm");
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Generate Heat Map
	bool heatMapsConnected = false;

	GridPoint originGridPoint;
	originGridPoint.j = (10 * start.x)+200;
	originGridPoint.i = (-10* start.y)+82;

	GridPoint targetGridPoint;
	targetGridPoint.j = (10 * target.x)+200;
	targetGridPoint.i = (-10* target.y)+82;    


	// Origin Heat Map Variables
	int transferNumber1 = 1;    
	queue<GridPoint> pointQueue1;
	pointQueue1.push(originGridPoint);
	int count1 = 1;

	set<GridPoint> in1;
	in1.insert(originGridPoint);

	// Target Heat Map Variables
	int transferNumber2 = 1000001;
	queue<GridPoint> pointQueue2;
	pointQueue2.push(targetGridPoint);
	int count2 = 1;

	set<GridPoint> in2;
	in2.insert(targetGridPoint);
	// Continue until heat maps are complete
	while(!heatMapsConnected)
	{
		transferNumber1++;
		transferNumber2--;
		int tempcount = 0;
		// Heat Map Robot - Origin
		while(!pointQueue1.empty() && count1 != 0)
		{
			// Retrieve next point to visit
			count1 = count1-1;
			GridPoint gP = pointQueue1.front();
			pointQueue1.pop();
			newGrid[gP.i][gP.j] = transferNumber1;
			for(int a = -1; a <= 1; a++)
			for(int b = -1; b <= 1; b++)
			if(gP.i+a >= 0 && gP.i+a < m.mapheight && 
					gP.j+b >= 0 && gP.j+b < m.mapwidth)
			{ 
				// Remember next points if not yet visited
				GridPoint tempGP;
				tempGP.i = gP.i+a;
				tempGP.j = gP.j+b;
				if(in1.find(tempGP) == in1.end())
				{
					if(newGrid[gP.i+a][gP.j+b] == 0)
					{
						GridPoint newPoint;
						newPoint.i = gP.i + a;
						newPoint.j = gP.j + b;
						pointQueue1.push(newPoint);
						in1.insert(newPoint);
						tempcount++;
					}


					// Exit Condition (heat maps connected)
					else if(newGrid[gP.i+a][gP.j+b] > transferNumber1 + 1)
					heatMapsConnected = true;
				}
			}
		}
		count1 = tempcount;
		tempcount = 0;
		// Heat Map Robot - Target
		while(!pointQueue2.empty() && count2 != 0)
		{
			// Retrieve next point to visit
			count2 = count2-1;
			GridPoint gP = pointQueue2.front();
			pointQueue2.pop();
			newGrid[gP.i][gP.j] = transferNumber2;
			for(int a = -1; a <= 1; a++)
			for(int b = -1; b <= 1; b++)
			if(gP.i+a >= 0 && gP.i+a < m.mapheight && 
					gP.j+b >= 0 && gP.j+b < m.mapwidth)
			{
				// Remember next points if not yet visited
				GridPoint tempGP;
				tempGP.i = gP.i+a;
				tempGP.j = gP.j+b;
				if(in2.find(tempGP) == in2.end()) 
				{
					if(newGrid[gP.i+a][gP.j+b] == 0)
					{
						GridPoint newPoint;
						newPoint.i = gP.i + a;
						newPoint.j = gP.j + b;
						pointQueue2.push(newPoint);
						in2.insert(newPoint);
						tempcount++;
					}

					// Exit Condition (heat maps connected)
					else if(newGrid[gP.i+a][gP.j+b] != 1 && newGrid[gP.i+a][gP.j+b] < transferNumber2 - 1)
					heatMapsConnected = true;
				}
			}
		}
		count2 = tempcount;
		
		// Cannot Reach Condition
		if((pointQueue1.empty() || pointQueue2.empty()) && !heatMapsConnected)
		{
			cout << "\nRobot Cannot Reach This Target" << endl;
			
			outputMap(m.mapheight, m.mapwidth, newGrid, m, "UnreachedHeatMap.pnm");
			if(robotSize-1 >= 1)
			Navigate(m, start, target, results, robotSize-1);
			exit( EXIT_FAILURE );
		}
	}
	
	outputMap(m.mapheight, m.mapwidth, newGrid, m, "2-CompletedHeatMap.pnm");

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Generate List of GridPoints from Heat Map
	// Start at Robot Origin

	bool atTarget = false;
	bool transfered = false;
	int nextValue = 2;

	GridPoint startGS(originGridPoint.i, originGridPoint.j, 2);

	vector<GridPoint> gridPath;

	set<GridPoint> doNotVisit;
	gridPath.push_back(startGS);
	srand(time(NULL));
	int count = 0;
	while(!atTarget) {
		nextValue++;

		// At Each [Current Point] (gridPath.back()), pick random 8-connected direction that is the [Next Value] that is NOT on the doNotVisit list
		// Add cell to GridPoint List	(gridPath.push_back(nextPoint))
		// Mark cell on newGrid as 1 (newGrid[nextPoint.i][nextPoint.j] = 1)

		bool nextCellSelected = false;				//	1	2	3
													//	8	0	4
		vector<int> selection;						//	7	6	5
		for(int i = 0; i < 8; i++) selection.push_back(i);
		
		GridPoint currentPoint = gridPath.back();
		while(!selection.empty() && !nextCellSelected)
		{
			// Get Random Point
			int select = (rand() % selection.size());
			GridPoint potential(currentPoint.i, currentPoint.j, nextValue);
			switch(selection.at(select)) {
			case 1: potential.i = potential.i - 1; potential.j = potential.j + 1; break;
			case 2:                                potential.j = potential.j + 1; break;
			case 3: potential.i = potential.i + 1; potential.j = potential.j + 1; break;
			case 4: potential.i = potential.i + 1;                                break; 
			case 5: potential.i = potential.i + 1; potential.j = potential.j - 1; break;
			case 6:                                potential.j = potential.j - 1; break;
			case 7: potential.i = potential.i - 1; potential.j = potential.j - 1; break;
			case 0: potential.i = potential.i - 1;                                break;
			}
			selection.erase(selection.begin()+select);
			if(potential.i >= 0 && potential.i < m.mapheight && potential.j >= 0 && potential.j < m.mapwidth)
			{
				// Determine if moment of transfer
				if(!transfered && newGrid[potential.i][potential.j] > transferNumber1 && newGrid[potential.i][potential.j] < 1000000) 
				{
					nextValue = newGrid[potential.i][potential.j];
					transfered = true;
					nextCellSelected = true;
					potential.cellValue = nextValue;
					gridPath.push_back(potential);
					newGrid[potential.i][potential.j] = 2000000;
				}
				// Determine if next valid point
				else if(newGrid[potential.i][potential.j] == nextValue && doNotVisit.find(potential) == doNotVisit.end()) 
				{
					nextCellSelected = true;
					gridPath.push_back(potential);
					newGrid[potential.i][potential.j] = 2000000;
					if(potential.cellValue == 1000000) atTarget = true;
				}
			}
			
		}
		// If no cell is the [Next Value] then backtrack using GridPointList
		// Add [Current Point] to do not return set (doNotVisit.insert())
		// Remove [Current Point] from GridPoint List (gridPath.pop_back())
		// Mark [Current Point] on newGrid as original value (newGrid[currentPoint.i][currentPoint.j] = currentPoint.cellValue)
		// reduce nextValue to previous point (add or subtract)
		
		if(!nextCellSelected)
		{
			doNotVisit.insert(currentPoint);
			gridPath.pop_back();
			newGrid[currentPoint.i][currentPoint.j] = currentPoint.cellValue;
			
			nextValue = gridPath.back().cellValue;
		}
	}/*
cout << "Debugging Waypoints: " << endl;
for(int i = 0; i < gridPath.size(); i++)
	{
		Waypoint wp;
	wp.x = (((double)gridPath[i].j-200.0))/10.0;
	wp.y = ((82-(double)gridPath[i].i))/10.0;
	cout << wp.x << ", " << wp.y << endl;
	}
	sleep(5);*/
	outputMap(m.mapheight, m.mapwidth, newGrid, m, "3-CompletedPathHeatMap.pnm");
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Iteratively Smooth List of GridPoints

	int p1 = 0;
	int p2 = 1;
	int p3 = 2;

	bool smoothed = (gridPath.size() < 3);
	cout << "# " << gridPath.size() << endl;
	while(!smoothed) {

		// Determine if Center point is Removable
		// If p1 is left  of p3 (p1.x < p3.x) then check square left  of p3 (p3.x-1, p3.y). If wall, then middle is NOT removable.
		// If p1 is right of p3 (p1.x > p3.x) then check square right of p3 (p3.x+1, p3.y). If wall, then middle is NOT removable.
		// If p1 is above of p3 (p1.y < p3.y) then check square above of p3 (p3.x, p3.y-1). If wall, then middle is NOT removable.
		// If p1 is below of p3 (p1.y > p3.y) then check square below of p3 (p3.x, p3.y+1). If wall, then middle is NOT removable.
		bool removable = (!(gridPath[p1].i < gridPath[p3].i && newGrid[gridPath[p3].i - 1][gridPath[p3].j] == 1) && 
		!(gridPath[p1].i > gridPath[p3].i && newGrid[gridPath[p3].i + 1][gridPath[p3].j] == 1) &&
		!(gridPath[p1].j < gridPath[p3].j && newGrid[gridPath[p3].i][gridPath[p3].j - 1] == 1) &&
		!(gridPath[p1].j > gridPath[p3].j && newGrid[gridPath[p3].i][gridPath[p3].j + 1] == 1));
		// IF Middle Removable then remove P2
		if(removable) gridPath.erase(gridPath.begin()+p2);
		// IF Middle is NOT Removable then increment 1, 2, 3 
		else
		{
			p1++;
			p2++;
			p3++;
		}
		smoothed = (3 > gridPath.size() || p3 >= gridPath.size());
	}
	cout << "# " << gridPath.size() << endl;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Produce Waypoints
	//    cout << "The robot will move through these points." << endl;
	for(int i = 0; i < gridPath.size(); i++)
	{
		Waypoint wp;
		wp.x = (((double)gridPath[i].j-200.0))/10.0;
		wp.y = ((82-(double)gridPath[i].i))/10.0;
		results.push_back(wp);
		//	cout << "--- " << wp.x << ", " << wp.y << endl;
	}
	//    sleep(5);

	// Delete created arrays
	for(int i = 0; i < m.mapheight; i++)
	delete[] newGrid[i];
	delete[] newGrid;
}



























