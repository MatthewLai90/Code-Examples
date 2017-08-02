
To run the project on Windows, simply run the start.bat file located in the 'dist' folder


On any other operating system, you must first have compiled OpenCV 3.0.0 for your OS with the proper configurations to compile a Java library:


1. The following website has instructions on how to compile the OpenCV software to include Java bindings. It is nearly the same as compiling it regularly since Java support is built in:

http://docs.opencv.org/doc/tutorials/introduction/desktop_java/java_dev_intro.html

Simply make sure to have Java, CMake, and Apache Ant installed then run the commands as instructed from the above website, ensuring that the build process lists java as one of the modules that will be built. 


2. Copy the ./opencv-3.0.0/build/bin/opencv-300.jar file located in the resulting build folder to the dist/lib/ folder of my project, replacing the .jar file that is already there.


3. Run the following command from the terminal, replacing the path with the folder containing the Java OpenCV library file "libopencv_java300.so". This is normally "./opencv-3.0.0/build/lib/":

java -Djava.library.path=/path/to/lib/ -jar dist/FilterImageEditor_CV.jar


