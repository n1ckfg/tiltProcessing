package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class Utils {

  PApplet parent;

  public ArrayList<Stroke> strokes;
  
  public Utils(PApplet _parent) { 
    parent = _parent;
    strokes = new ArrayList<Stroke>();
  }
  
  public void run() {
    for (int i=0; i<strokes.size(); i++) {
      strokes.get(i).run();
    }
  }
  
  public void run(PGraphics g) {
    for (int i=0; i<strokes.size(); i++) {
      strokes.get(i).run(g);
    }
  }
}


