import peasy.PeasyCam;
import tiltProcessing.*;

PeasyCam cam;
TiltLoader tl;

void setup() {
  size(800, 800, P3D);
  
  cam = new PeasyCam(this, 400);

  tl = new TiltLoader(this, "sketch.tilt");
  
  println(tl.numStrokes);
}

void draw() {
  background(127);
  pushMatrix();
  scale(10);
  for (TiltStroke ts : tl.strokes) {
    stroke(ts.brushColor);
    noFill();
    beginShape(TRIANGLE_STRIP);
    for (PVector p : ts.positions) {
      vertex(p.x, p.y, p.z);
    }
    endShape();
  }
  popMatrix();
}
