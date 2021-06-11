package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class TBBrushStroke { //partial

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
    
}

