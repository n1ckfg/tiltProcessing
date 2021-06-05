package tiltProcessing;

import processing.core.*;

public class ControlPoint {

  PApplet parent;

  public PVector co;
  public float pressure;
  public float strength;
  public int vertex_color;

  public ControlPoint(PApplet _parent) {
    parent = _parent;
    co = new PVector(0,0,0);
    pressure = 1.0f;
    strength = 1.0f;
    vertex_color = parent.color(0,0,0,0);
  }
  
  public ControlPoint(PApplet _parent, PVector _co) {
    parent = _parent;
    co = _co;
    pressure = 1.0f;
    strength = 1.0f;
    vertex_color = parent.color(0,0,0,0);
  }
  
  public ControlPoint(PApplet _parent, PVector _co, int _vertex_color) {
    parent = _parent;
    co = _co;
    pressure = 1.0f;
    strength = 1.0f;
    vertex_color = _vertex_color;
  }
  
  public ControlPoint(PApplet _parent, PVector _co, float _pressure, float _strength) {
    parent = _parent;
    co = _co;
    pressure = _pressure;
    strength = _strength;
    vertex_color = parent.color(0,0,0,0);
  } 
 
  public ControlPoint(PApplet _parent, PVector _co, float _pressure, float _strength, int _vertex_color) {
    parent = _parent;
    co = _co;
    pressure = _pressure;
    strength = _strength;
    vertex_color = _vertex_color;
  }
  
}
