import tiltProcessing.*;

TiltLoader tiltLoader;
String url = "sketch.tilt";

void setup() {
  size(800, 800, P3D);
  
  tiltLoader = new TiltLoader(this, url);
  
  println(tiltLoader.numStrokes);
}

void draw() {
  //
}
