package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class StrokeGeometry {

	PApplet parent;
  public ArrayList<PVector> positions;
  public float brush_size;
  public int brush_color; 

  public StrokeGeometry(PApplet _parent, ArrayList<PVector> _positions, float _brush_size, int _brush_color) {
    parent = _parent;
    positions = _positions;
    brush_size = _brush_size;
    brush_color = _brush_color;
  }

}
