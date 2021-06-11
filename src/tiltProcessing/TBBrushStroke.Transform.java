package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class TBBrushStroke { //partial

    public PVector startPosition() {
        return controlPoints[0].position;
    }

    public PVector endPosition() {
        return controlPoints[controlPoints.Count - 1].position;
    }

    public void Translate(float dx, float dy, float dz) {
        Translate(new PVector(dx, dy, dz));
    }

    public void Translate(PVector offset) {
        foreach (var point in controlPoints) {
            point.position += offset;
        }
    }
    
}
