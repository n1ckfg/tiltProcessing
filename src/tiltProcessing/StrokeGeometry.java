package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class StrokeGeometry {

	PApplet parent;
  public ArrayList<PVector> positions;
  public float brushSize;
  public int brushColor; 

  public StrokeGeometry(PApplet _parent, ArrayList<PVector> _positions, float _brushSize, int _brushColor) {
    parent = _parent;
    positions = _positions;
    brushSize = _brushSize;
    brushColor = _brushColor;
  }

}
