#ifndef __proj4map_h_INCLUDED__
#define __proj4map_h_INCLUDED__

#include <iostream>
#include <fstream>
#include <string>

class Map
{
  public:
//  float gridMap[1000][1000];
    float **gridMap;
    float **grownMap;

    Map(std::string name, double scale);
//    ~Map();
    void inputMap(std::string mapName);

    int mapwidth, mapheight;
    char inputLine1[80], nextChar;
    int maxVal;

  private:
    int width, height;
    double scale_map;

};

void outputMap(Map &m, std::string name);
void outputGrownMap(Map &m, std::string name);
void outputMap(int height, int width, float** map, Map &m, std::string name);

#endif
