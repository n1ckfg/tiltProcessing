package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class Quaternion {

	public float w,x,y,z;
	public float[][] matrix;
	
	public Quaternion() {
		w = 1;
		x = 0;
		y = 0;
		z = 0;	
	}
	
	public Quaternion(float _w, float _x, float _y, float _z) {
		w = _w;
		x = _x;
		y = _y;
		z = _z;
	}
	
	public void rotateAxisX(float angle) {
		rotateAxis(angle, new PVector(1, 0, 0));
	}
	
	public void rotateAxisY(float angle) {
		rotateAxis(angle, new PVector(0, 1, 0));
	} 
	
	public void rotateAxisZ(float angle) {
		rotateAxis(angle, new PVector(0, 0, 1));
	}

	// https://answers.unity.com/questions/1209461/problem-using-quaternionangleaxis-around-transform.html	
	public void rotateAxis(float angle, PVector p) {
		Quaternion q = new Quaternion(w, x, y, z).mult(calculateRotation(angle, p));
		w = q.w;
		x = q.x;
		y = q.y;
		z = q.z;
	}	

	// https://stackoverflow.com/questions/4436764/rotating-a-quaternion-on-1-axis
	// https://github.com/jdf/peasycam/blob/master/src/peasy/org/apache/commons/math/geometry/Rotation.java#L20
	public Quaternion calculateRotation(float angle, PVector p) {
		float _w, _x, _y, _z;
		
		float omega, s, c;

		s = sqrt(p.x*p.x + p.y*p.y + p.z*p.z);

		if (abs(s) > Float.MIN_VALUE) {
			c = 1.0/s;

			p.x *= c;
			p.y *= c;
			p.z *= c;

			omega = -0.5f * angle;
			s = (float)sin(omega);

			_w = (float)cos(omega);
			_x = s*p.x;
			_y = s*p.y;
			_z = s*p.z;
		} else {
			_w = 1.0f;
			_x = _y = 0.0f;
			_z = 0.0f;
		}
		
		return new Quaternion(_w, _x, _y, _z).normalize();	
	}

	public Quaternion reciprocal() {
		float _w, _x, _y, _z;
		
		float norm = sqrt(w*w + x*x + y*y + z*z);
		if (norm == 0.0) norm = 1.0;

		float recip = 1.0 / norm;

		_w =	w * recip;
		_x = -x * recip;
		_y = -y * recip;
		_z = -z * recip;

		return new Quaternion(_w, _x, _y, _z);	
	}
	
	// http://home.apache.org/~luc/commons-math-3.6-RC2-site/jacoco/org.apache.commons.math3.complex/Quaternion.java.html
	public Quaternion normalize() {
		float _w, _x, _y, _z;
		
		float norm = sqrt(w*w + x*x + y*y + z*z);
		if (norm == 0.0) {
			_w = 1.0; 
			_x = _y = _z = 0.0;
		} else {
			float recip = 1.0/norm;

			_w = w * recip;
			_x = x * recip;
			_y = y * recip;
			_z = z * recip;
		}
		
		return new Quaternion(_w, _x, _y, _z);	
	}

	public Quaternion add(Quaternion q) {
		float _w, _x, _y, _z;
		
		_w = w + q.w;
		_x = x + q.x;
		_y = y + q.y;
		_z = z + q.z;
		
		return new Quaternion(_w, _x, _y, _z);
	}
	
	public Quaternion mult(Quaternion q) {
		float _w, _x, _y, _z;
		
		_w = w*q.w - (x*q.x + y*q.y + z*q.z);
		_x = w*q.x + q.w*x + y*q.z - z*q.y;
		_y = w*q.y + q.w*y + z*q.x - x*q.z;
		_z = w*q.z + q.w*z + x*q.y - y*q.x;
		
		return new Quaternion(_w, _x, _y, _z);	
	}
	
	public Quaternion identity() {	
		return new Quaternion(1, 0, 0, 0);	
	}
	
	public Quaternion conjugate() {
		float _w, _x, _y, _z;
		
		_w = w;
		_x = -x;
		_y = -y;
		_z = -z;
		
		return new Quaternion(_w, _x, _y, _z);	
	}
	
	public Quaternion slerp(Quaternion a, Quaternion b, float t) {
		float _w, _x, _y, _z;

		float omega, cosom, sinom, sclp, sclq;
		cosom = a.x*b.x + a.y*b.y + a.z*b.z + a.w*b.w;

		if ((1.0f+cosom) > Float.MIN_VALUE) {
			if ((1.0f-cosom) > Float.MIN_VALUE) {
				omega = acos(cosom);
				sinom = sin(omega);
				sclp = sin((1.0f-t)*omega) / sinom;
				sclq = sin(t*omega) / sinom;
			} else {
				sclp = 1.0f - t;
				sclq = t;
			}

			_w = sclp*a.w + sclq*b.w;
			_x = sclp*a.x + sclq*b.x;
			_y = sclp*a.y + sclq*b.y;
			_z = sclp*a.z + sclq*b.z;
		} else {
			_w = a.z;
			_x =-a.y;
			_y = a.x;
			_z =-a.w;

			sclp = sin((1.0f-t) * PI * 0.5);
			sclq = sin(t * PI * 0.5);

			_x = sclp*a.x + sclq*b.x;
			_y = sclp*a.y + sclq*b.y;
			_z = sclp*a.z + sclq*b.z;
		}

		return new Quaternion(_w, _x, _y, _z);	
	}

	public Quaternion exp() {			
		float _w, _x, _y, _z;
		
		float Mul;
		float Length = sqrt(x*x + y*y + z*z);

		if (Length > 1.0e-4) {
			Mul = sin(Length)/Length;
		} else {
			Mul = 1.0;
		}
		
		_w = cos(Length);

		_x = x * Mul;
		_y = y * Mul;
		_z = z * Mul; 

		return new Quaternion(_w, _x, _y, _z);
	}

	public Quaternion log() {
		float _w, _x, _y, _z;
		
		float Length;
		Length = sqrt(x*x + y*y + z*z);
		Length = atan(Length/w);

		_w = 0.0;
		_x = x * Length;
		_y = y * Length;
		_z = z * Length;

		return new Quaternion(_w, _x, _y, _z);
	}
	
	public void debug() {
		println("w: " + w + ", x: " + x + ", y: " + y + ", z: " + z);
	}

	// https://commons.apache.org/proper/commons-math/javadocs/api-2.2/src-html/org/apache/commons/math/geometry/Rotation.html#line.837	
	public float[][] calculateMatrix() {
		// products
		float q0q0	= w * w;
		float q0q1	= w * x;
		float q0q2	= w * y;
		float q0q3	= w * z;
		float q1q1	= x * x;
		float q1q2	= x * y;
		float q1q3	= x * z;
		float q2q2	= y * y;
		float q2q3	= y * z;
		float q3q3	= z * z;
	
		// create the matrix
		matrix = new float[3][];
		matrix[0] = new float[3];
		matrix[1] = new float[3];
		matrix[2] = new float[3];
	
		matrix[0][0] = 2.0 * (q0q0 + q1q1) - 1.0;
		matrix[1][0] = 2.0 * (q1q2 - q0q3);
		matrix[2][0] = 2.0 * (q1q3 + q0q2);
	
		matrix[0][1] = 2.0 * (q1q2 + q0q3);
		matrix[1][1] = 2.0 * (q0q0 + q2q2) - 1.0;
		matrix[2][1] = 2.0 * (q2q3 - q0q1);
	
		matrix[0][2] = 2.0 * (q1q3 - q0q2);
		matrix[1][2] = 2.0 * (q2q3 + q0q1);
		matrix[2][2] = 2.0 * (q0q0 + q3q3) - 1.0;
	
		return matrix;
	}
	
	public void useMatrix() {
		applyMatrix(matrix[0][0], matrix[0][1], matrix[0][2], 0,
		matrix[1][0], matrix[1][1], matrix[1][2], 0,
		matrix[2][0], matrix[2][1], matrix[2][2], 0,
		0, 0, 0, 1);
	}
	
	public void run() {
		calculateMatrix();
		useMatrix();
	}
	 
}