package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class TBBrushStroke {

    int m_brushIndex;
    color m_brushColor;
    float m_brushSize;
    UInt32 m_reserver1;
    UInt32 m_reserver2;
    UInt32 m_strokeFlags;
    ArrayList<TBControlPoint> m_controlPoints;

    TBBrushStroke() {
    	//
    }

    public TBBrushStroke(BinaryReader reader) {
        m_brushIndex = reader.ReadInt32();
        m_brushColor = reader.ReadColor();
        m_brushSize = reader.ReadFloat();
        m_reserver1 = reader.ReadUInt32();
        m_reserver2 = reader.ReadUInt32();
        m_strokeFlags = reader.ReadUInt32();
        int controlPointCount = reader.ReadInt32();
        m_controlPoints = new ArrayList<TBControlPoint>();
        for (int pointIndex = 0; pointIndex < controlPointCount; ++pointIndex) {
            m_controlPoints.Add(new TBControlPoint(reader));
        }
    }

    public void Write(BinaryWriter writer) {
        writer.Write(m_brushIndex);
        writer.Write(m_brushColor);
        writer.Write(m_brushSize);
        writer.Write(m_reserver1);
        writer.Write(m_reserver2);
        writer.Write(m_strokeFlags);
        writer.Write((int) m_controlPoints.Count);
        foreach (var controlPoint in m_controlPoints) {
            controlPoint.Write(writer);
        }
    }

    public TBBrushStroke Clone() {
        TBBrushStroke clone = new TBBrushStroke();
        clone.m_brushIndex = m_brushIndex;
        clone.m_brushColor = m_brushColor;
        clone.m_brushSize = m_brushSize;
        clone.m_reserver1 = m_reserver1;
        clone.m_reserver2 = m_reserver2;
        clone.m_strokeFlags = m_strokeFlags;
        ArrayList<TBControlPoint> controlPoints = new ArrayList<TBControlPoint>(m_controlPoints.Count);
        foreach (var controlPoint in m_controlPoints) {
            controlPoints.Add(controlPoint.Clone());
        }
        clone.m_controlPoints = controlPoints;
        return clone;
    }

    public int brushIndex() {
        return m_brushIndex;
    }

    public color brushColor {
        get { return m_brushColor; }
        set { m_brushColor = value; }
    }

    public float brushSize {
        get { return m_brushSize; }
        set { m_brushSize = value; }
    }

    public ArrayList<TBControlPoint> controlPoints() {
        return m_controlPoints;
    }
    
    // - - - - BOUNDS - - - -
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
    // - - - - ...... - - - -

    // - - - - TRANSFORM - - - -
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
    // - - - - ......... - - - -

}

