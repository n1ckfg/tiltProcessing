package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public partial class TBBrushStroke {

    public Vector3 startPosition {
        get { return controlPoints[0].position; }
    }

    public Vector3 endPosition {
        get { return controlPoints[controlPoints.Count - 1].position; }
    }

    public void Translate(float dx, float dy, float dz) {
        Translate(new Vector3(dx, dy, dz));
    }

    public void Translate(Vector3 offset) {
        foreach (var point in controlPoints) {
            point.position += offset;
        }
    }
    
}
