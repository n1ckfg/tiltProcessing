import peasy.PeasyCam;
import tiltProcessing.*;

PeasyCam cam;
TiltLoader tl;
PShape shp;

void setup() {
  size(800, 600, P3D);
  noCursor();
  
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
  
  bloomSetup();
}

void draw() {
  background(0);

  tex.beginDraw();
  tex.background(0);
  tex.pushMatrix();
  tex.translate(10, 80, 0);
  tex.rotateX(radians(90));
  tex.rotateY(radians(90));
  tex.rotateZ(radians(90));
  tex.shape(shp);
  tex.popMatrix();
  tex.endDraw();
   
  cam.getState().apply(tex);
  
  cam.beginHUD();
  bloomDraw();
  cam.endHUD();
  
  surface.setTitle(""+frameRate);
}
