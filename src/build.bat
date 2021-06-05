@echo off

cd %cd%

mkdir build\tiltProcessing
javac -cp "C:\Program Files\processing\core\library\core.jar" tiltProcessing\*.java
move /y tiltProcessing\*.class build\tiltProcessing\
cd build
jar cvfm ..\tiltProcessing.jar manifest.txt tiltProcessing\*.class
cd ..
move tiltProcessing.jar ..\library\

@pause