import peasy.PeasyCam;
import tiltProcessing.*;

PeasyCam cam;
TiltLoader tl;
PShape shp;

void setup() {
  size(800, 600, P3D);
  
  cam = new PeasyCam(this, 250);
  float fov = PI/3.0;
  float cameraZ = (height/2.0) / tan(fov/2.0);
  perspective(fov, float(width)/float(height), cameraZ/100.0, cameraZ*100.0);
  
  tl = new TiltLoader(this, "Untitled_2.tilt");
  shp = createShape(GROUP);
  
  for (TiltStroke ts : tl.strokes) {
    PShape sShp = createShape();
    
    sShp.beginShape();
    sShp.stroke(ts.brushColor);
    sShp.strokeWeight(ts.brushSize * 30);
    sShp.noFill();
    for (PVector p : ts.positions) {
      p.mult(10);
      sShp.vertex(p.x, p.y, p.z);
    }
    sShp.endShape();
    shp.addChild(sShp);
  }  
}

void draw() {
  background(0);
  
  pushMatrix();
  translate(10, 80, 0);
  rotateX(radians(90));
  rotateY(radians(90));
  rotateZ(radians(90));
  shape(shp);
  popMatrix();
  
  surface.setTitle(""+frameRate);
}
