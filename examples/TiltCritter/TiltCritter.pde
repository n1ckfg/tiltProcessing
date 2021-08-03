import peasy.PeasyCam;
import tiltProcessing.*;

PeasyCam cam;
TiltLoader tl;

void setup() {
  size(800, 800, P3D);
  
  cam = new PeasyCam(this, 400);

  tl = new TiltLoader(this, "Untitled_2.tilt");

  println(tiltLoader.numStrokes);
}

void draw() {
  background(0);
  for (TiltStroke ts : tl.strokes) {
    noFill();
    stroke(ts.brushColor);
    beginShape();
    for (PVector p : ts.positions) {
      vertex(p.x, p.y, p.z);
    }
    endShape();
  }
}
