import peasy.PeasyCam;
import tiltProcessing.*;

PeasyCam cam;
TiltLoader tiltLoader;
String url = "sketch.tilt";

void setup() {
  size(800, 800, P3D);
  
  cam = new PeasyCam(this, 400);

  tiltLoader = new TiltLoader(this, url);
  
  println(tiltLoader.numStrokes);
}

void draw() {
  background(127);
  pushMatrix();
  scale(10);
  for (StrokeGeometry strokeGeo : tiltLoader.strokes) {
    stroke(strokeGeo.brushColor);
    noFill();
    beginShape(TRIANGLE_STRIP);
    for (PVector point : strokeGeo.positions) {
      vertex(point.x, point.y, point.z);
    }
    endShape();
  }
  popMatrix();
}
