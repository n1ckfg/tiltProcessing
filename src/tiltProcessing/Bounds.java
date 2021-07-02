package tiltProcessing;

import processing.core.*;

// https://docs.unity3d.com/ScriptReference/Bounds.html
// minimal port of Unity's C# Bounds class

public class Bounds {

	public PVector center;
	public PVector extents;

	public Bounds(PVector _center, PVector _extents) {
		center = _center;
		extents = _extents;
	}

}