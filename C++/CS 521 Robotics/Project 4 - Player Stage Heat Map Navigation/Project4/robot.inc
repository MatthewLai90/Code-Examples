
#MattBot
#Matthew Lai
#26 April 2015

define mattbot position
(
  color "DarkGreen"
  drive "diff"
  gui_nose 1                  	# Draw a nose on the robot so we can see which way it points
  obstacle_return 1           	# Can hit things.
  ranger_return 0.5            	# reflects sonar beams
  blob_return 1               	# Seen by blobfinders  
  fiducial_return 1           	# Seen as "1" fiducial finders

  fancysicklaser( pose [ 0 0 0 0 ] )

  mass 15.0 

  localization "gps"         	  
  size [ .3 .3 .25 ]
  
  # TL
  block(points 4 point[0] [ 0.0 0.0 ] point[1] [ 0.1 0.0 ] point[2] [ 0.1 0.1 ] point[3] [ 0.0 0.1 ] z [ 0.0 0.05 ])
  # TC
  block(points 4 point[0] [ 0.1 0.0 ] point[1] [ 0.2 0.0 ] point[2] [ 0.2 0.1 ] point[3] [ 0.1 0.1 ] z [ 0.0 0.05 ])
  # TR
  block(points 4 point[0] [ 0.2 0.0 ] point[1] [ 0.3 0.09 ] point[2] [ 0.3 0.1 ] point[3] [ 0.2 0.1 ] z [ 0.0 0.1 ])

  # BL
  block(points 4 point[0] [ 0.0 0.2 ] point[1] [ 0.1 0.2 ] point[2] [ 0.1 0.3 ] point[3] [ 0.0 0.3 ] z [ 0.0 0.05 ])
  # BC
  block(points 4 point[0] [ 0.1 0.2 ] point[1] [ 0.2 0.2 ] point[2] [ 0.2 0.3 ] point[3] [ 0.1 0.3 ] z [ 0.0 0.05 ])
  # BR
  block(points 4 point[0] [ 0.2 0.2 ] point[1] [ 0.3 0.2 ] point[2] [ 0.3 0.21 ] point[3] [ 0.2 0.3 ] z [ 0.00 0.1 ])

  # ML
  block(points 4 point[0] [ 0.0 0.1 ] point[1] [ 0.1 0.1 ] point[2] [ 0.1 0.2 ] point[3] [ 0.0 0.2 ] z [ 0.0 0.05 ])
  # MR
  block(points 4 point[0] [ 0.2 0.1 ] point[1] [ 0.3 0.1 ] point[2] [ 0.3 0.2 ] point[3] [ 0.2 0.2 ] z [ 0.0 0.1 ])

  #head
  block(points 4 point[0] [ 0.11 0.11 ] point[1] [ 0.19 0.11 ] point[2] [ 0.19 0.19 ] point[3] [ 0.11 0.19 ] z [ 0.2 0.25 ])
  #neck
  block(points 4 point[0] [ 0.1 0.13 ] point[1] [ 0.11 0.13 ] point[2] [ 0.11 0.16 ] point[3] [ 0.1 0.16 ] z [ 0.0 0.25 ])
)
