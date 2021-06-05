package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class Stroke {
  
  PApplet parent;

  public PShape s;
  public ArrayList<ControlPoint> points;
  public int col;
  public float globalScale;
  public PVector globalOffset;
    
  public Stroke(PApplet _parent, ArrayList<ControlPoint> _p, int _c) {
    parent = _parent;
    init(_p, _c);
  }

  public void init(ArrayList<ControlPoint> _p, int _c) {
    col = parent.color(255);
    globalScale = 1;
    globalOffset = new PVector(0,0,0);
    setColor(_c);
    setPoints(_p);
  }

  public void run() {
    parent.pushMatrix();
    parent.scale(globalScale, globalScale, globalScale);
    parent.shape(s);
    parent.popMatrix();
  }
  
  public void run(PGraphics g) {
    g.pushMatrix();
    g.scale(globalScale, globalScale, globalScale);
    g.shape(s);
    g.popMatrix();
  }
  
  int getColor() {
    return s.getStroke(0);
  }
  
  public void setColor(int _c) {
    col = _c;
  }
  
  ArrayList<PVector> getPoints() {
    ArrayList<PVector> points = new ArrayList<PVector>();
    for (int i=0; i<s.getVertexCount(); i++) {
      points.add(s.getVertex(i));
    }
    return points;
  }
  
  public void setPoints(ArrayList<ControlPoint> _p) {
    s = parent.createShape();
    s.beginShape();
    s.noFill();
    s.stroke(col);
    s.strokeWeight(2);
    for (int i=0; i<_p.size(); i++) {
      PVector pt = _p.get(i).co;
      s.vertex(pt.z, -pt.y, pt.x);
    }
    s.endShape();
    points = _p;
  }

}
