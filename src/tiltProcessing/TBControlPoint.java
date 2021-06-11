package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class TBControlPoint {

	public PVector position;
	public Quaternion orientation;
	public float pressure;
	public UInt32 timestamp;

    TBControlPoint() {
    	//
    }

    public TBControlPoint(BinaryReader reader) {
        position = reader.ReadVector3();
        orientation = reader.ReadQuaternion();
        pressure = reader.ReadFloat();
        timestamp = reader.ReadUInt32(); 
    }

    public void Write(BinaryWriter writer) {
        writer.Write(position);
        writer.Write(orientation);
        writer.Write(pressure);
        writer.Write(timestamp);
    }

    public TBControlPoint Clone() {
        TBControlPoint clone = new TBControlPoint();
        clone.orientation = orientation;
        clone.position = position;
        clone.pressure = pressure;
        clone.timestamp = timestamp;
        return clone;
    }

    public override String ToString() {
        return String.Format("Position={0} orientation={1} Pressure={2} Timestamp={3}", position, orientation.eulerAngles, pressure, timestamp);
    }

    public PVector tangent() {
        return pressure * (orientation * PVector.up);
    }

    public PVector normal() {
        return orientation * PVector.forward;
    }
    
}
