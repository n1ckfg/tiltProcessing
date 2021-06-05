package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class Sketch {

  PApplet parent;

  public ArrayList<LatkFrame> frames;
  public int currentFrame;
  public String name;
  
  public Sketch(PApplet _parent) { 
    parent = _parent;
    frames = new ArrayList<LatkFrame>();
    currentFrame = 0;
    name = "P5layer";
  }
  
  public void run() {
    frames.get(currentFrame).run();
  }
  
  public void run(PGraphics g) {
    frames.get(currentFrame).run(g);
  }
  
  public void nextFrame() {
    currentFrame++;
    if (currentFrame > frames.size()-1) currentFrame = 0;
  }
}

