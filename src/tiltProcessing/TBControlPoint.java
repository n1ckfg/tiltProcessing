package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class TBControlPoint {

    TBControlPoint() {
    	//
    }

    public TBControlPoint(BinaryReader reader) {
        this.position = reader.ReadVector3();
        this.orientation = reader.ReadQuaternion();
        this.pressure = reader.ReadFloat();
        this.timestamp = reader.ReadUInt32(); 
    }

    public void Write(BinaryWriter writer) {
        writer.Write(this.position);
        writer.Write(this.orientation);
        writer.Write(this.pressure);
        writer.Write(this.timestamp);
    }

    public TBControlPoint Clone() {
        TBControlPoint clone = new TBControlPoint();
        clone.orientation = orientation;
        clone.position = position;
        clone.pressure = pressure;
        clone.timestamp = timestamp;
        return clone;
    }

    public override string ToString() {
        return string.Format("Position={0} orientation={1} Pressure={2} Timestamp={3}", position, orientation.eulerAngles, pressure, timestamp);
    }

    public Quaternion orientation { get; set; }
    public Vector3 position { get; set; }
    public float pressure { get; set; }
    public UInt32 timestamp { get; set; }

    public Vector3 tangent {
        get { return pressure * (orientation * Vector3.up); }
    }

    public Vector3 normal {
        get { return orientation * Vector3.forward; }
    }
    
}
