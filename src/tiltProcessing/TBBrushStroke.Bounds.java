package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class TBBrushStroke { //partial

    final float kMinValue = -10000; //const
    final float kMaxValue = 10000; //const

    public Bounds bounds() {
        PVector min = new PVector(kMaxValue, kMaxValue, kMaxValue);
        PVector max = new PVector(kMinValue, kMinValue, kMinValue);

        foreach (var point in controlPoints) {
            min.x = Mathf.Min(min.x, point.position.x);
            min.y = Mathf.Min(min.y, point.position.y);
            min.z = Mathf.Min(min.z, point.position.z);

            max.x = Mathf.Min(max.x, point.position.x);
            max.y = Mathf.Min(max.y, point.position.y);
            max.z = Mathf.Min(max.z, point.position.z);
        }

        PVector center = 0.5f * (min + max);
        PVector size = max - min;

        return new Bounds(center, size);
    }

}
