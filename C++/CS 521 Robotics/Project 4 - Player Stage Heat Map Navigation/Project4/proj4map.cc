#include "proj4map.h"
#include <cmath>

using namespace std;

/*************************************************************************
 *                                                                        *
 * Function inputMap converts the input map information into an           *
 * initial occupancy grid, which is stored in gridMap.                    *
 *                        
 * (Here, the "output" parameter says whether to print out the scaled     *
 *  map; output = 1 ==> yes, print;  output = 0 ==> no, don't print.)     *
 *************************************************************************/

Map::Map(string name, double scale)
{
  scale_map = scale;
  inputMap(name);
}
/*
Map::~Map()
{
  for(int i = 0; i < mapheight; i++) delete[] gridMap[i];
  delete[] gridMap;
}*/

void Map::inputMap(string mapName)
{
//    ifstream inFile("hospital_section.pnm");
    ifstream inFile(mapName.c_str());
    inFile.getline(inputLine1,80);
    inFile >> width >> height >> maxVal;
    cout << "Width = " << width << ", Height = " << height << endl;
    cout << "Scaled to: " << endl;
    cout << "Width = " << round((width+scale_map)/scale_map) << ", Height = " << round((height+scale_map)/scale_map) << endl;

    int i, j, m, n;

    mapwidth = round((width+scale_map)/scale_map);
    mapheight = round((height+scale_map)/scale_map);
    
    gridMap = new float*[mapheight];
    grownMap = new float*[mapheight];
    for(i = 0; i < mapheight; i++) 
    {
        gridMap[i] = new float[mapwidth];
        grownMap[i] = new float[mapwidth];
    }

    // Initialize map to 0's, meaning all free space 

    for (m=0; m<mapheight; m++)
        for (n=0; n<mapwidth; n++)   
        {
            gridMap[m][n] = 0.0;
            grownMap[m][n] = 0.0;
        }
//cout << "READING" <<endl;
    // Read in map; 
    for (i=0; i<height; i++)    
        for (j=0; j<width; j++) {
	  inFile >> nextChar;
//if(!nextChar) cout << i << " " << j << endl;
	  if (!nextChar)
          {
	    gridMap[(int)floor(i/scale_map)][(int)floor(j/scale_map)] = 1.0;
            grownMap[(int)floor(i/scale_map)][(int)floor(j/scale_map)] = 1.0;
          }
	}
    cout << "Map input complete.\n";
}

void outputMap(Map &m, string name)
{
//    cout << "Outputing" << endl;
    ofstream outFile(name.c_str());
//    cout << "Printing Header" << endl;
    outFile << m.inputLine1 << endl;
    outFile << m.mapwidth << " " << m.mapheight << endl
	    << m.maxVal << endl;

    cout << "Printing characters" << endl;
    for (int i=0; i<m.mapheight; i++)
    for (int j=0; j<m.mapwidth; j++) {
	if (m.gridMap[i][j] == 1.0)
	  outFile << (char) 0;
	if (m.gridMap[i][j] == 0.0)
	  outFile << (char) -1;
        if (m.gridMap[i][j] != 1.0 && m.gridMap[i][j] != 0.0)
          outFile << (char) 128;

    }
    cout << "Scaled map output to file. " + name + "\n";
}

void outputGrownMap(Map &m, string name)
{
//    cout << "Outputing" << endl;
    ofstream outFile(name.c_str());
//    cout << "Printing Header" << endl;
    outFile << m.inputLine1 << endl;
    outFile << m.mapwidth << " " << m.mapheight << endl
	    << m.maxVal << endl;

    cout << "Printing characters" << endl;
    for (int i=0; i<m.mapheight; i++)
    for (int j=0; j<m.mapwidth; j++) {
	if (m.grownMap[i][j] == 1.0)
	  outFile << (char) 0;
	if (m.grownMap[i][j] == 0.0)
	  outFile << (char) -1;
        if (m.grownMap[i][j] != 1.0 && m.grownMap[i][j] != 0.0)
          outFile << (char) 128;

    }
    cout << "Scaled map output to file. " + name + "\n";
}

void outputMap(int height, int width, float** map, Map &m, string name)
{
//    cout << "Outputing" << endl;
    ofstream outFile(name.c_str());
//    cout << "Printing Header" << endl;
    outFile << m.inputLine1 << endl;
    outFile << width << " " << height << endl
	    << m.maxVal << endl;

    for (int i=0; i<height; i++)
    for (int j=0; j<width; j++) {
	if (map[i][j] == 1.0 || map[i][j] == 2000000.0 )
	  outFile << (char) 0;
	if (map[i][j] == 0.0)
	  outFile << (char) -1;
        if (map[i][j] != 1.0 && map[i][j] != 0.0 && map[i][j] != 2000000.0)
          outFile << (char) 128;

    }
    cout << "Map output to file. " + name + "\n";
}

/*int main(int argc, char *argv[])
{
  Map map("hospital_section.pnm", 1);

//  inputMap(0);  // Here, '1' means to print out the scaled map to a file;
                // If you don't want the printout, pass a parameter of '0'.

//  for (int i = 0; i < height/SCALE_MAP; i++)
//    for(int j = 0; j < width/SCALE_MAP; j++)
//      if(i < height/SCALE_MAP/2) gridMap[i][j] = 5;
  outputMap(map, "scale_map_2.pnm");
  return 0;
}
*/
