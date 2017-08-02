

#ifndef __proj4nav_h_INCLUDED__
#define __proj4nav_h_INCLUDED__


#include "proj4.h"
#include "proj4map.h"
void Navigate(Map m, Waypoint& start, Waypoint& target, std::vector<Waypoint> &results, int robotSize);

#endif
