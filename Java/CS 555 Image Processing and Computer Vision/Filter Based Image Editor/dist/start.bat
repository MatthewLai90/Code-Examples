set Arch=x64
if %PROCESSOR_ARCHITECTURE% == "x86" (
    if not defined PROCESSOR_ARCHITEW6432 set Arch=x86
)

if %Arch% == x64 java -Djava.library.path=lib/x64/ -jar FilterImageEditor_Project.jar
if %Arch% == x86 java -Djava.library.path=lib/x86/ -jar FilterImageEditor_Project.jar