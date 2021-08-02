package tiltProcessing;

import java.io.*;
import java.nio.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import processing.core.*;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class TiltLoader {

	PApplet parent;
	public String url;
	public byte[] bytes;
  public JSONObject json;
  public int numStrokes;

  private ZipFile zipFile;
  private ArrayList<String> fileNames;

	public TiltLoader(PApplet _parent, String _url) {
    parent = _parent;
    url = _url;
    read(url);
  }

  public void read(String _url) {
    // A tilt zipfile should contain three items: thumbnail.png, data.sketch, metadata.json
    try {
      url = getFilePath(_url);
      zipFile = new ZipFile(url);
      
      fileNames = new ArrayList<String>();
      final Enumeration<? extends ZipEntry> entries = zipFile.entries();
      while (entries.hasMoreElements()) {
        fileNames.add(entries.nextElement().getName());
      }

      json = parent.parseJSONObject(readEntryAsString("metadata.json"));
  		bytes = readEntryAsBytes("data.sketch");
      
      parseTilt();

      zipFile.close();
    } catch (Exception e) {
      parent.println(e);
    }
  }

  private void parseTilt() {
    numStrokes = getInt(bytes, 16);

    int offset = 20;

    for (int i = 0; i < numStrokes; i++) {
      int brushIndex = getInt(bytes, offset);

      float[] brushColor = { getFloat(bytes, offset + 4), getFloat(bytes, offset + 8), getFloat(bytes, offset + 12), getFloat(bytes, offset + 16) };

      float brushSize = getFloat(bytes, offset + 20);
      int strokeMask = getUInt(bytes, offset + 24);
      int controlPointMask = getUInt(bytes, offset + 28);

      int offsetStrokeMask = 0;
      int offsetControlPointMask = 0;

      for (int j = 0; j < 4; j++) {
        byte b = (byte) (1 << j);
        if ((strokeMask & b) > 0) offsetStrokeMask += 4;
        if ((controlPointMask & b) > 0) offsetControlPointMask += 4;
      }

      parent.println("1. " + brushIndex + ", [" + brushColor[0] + ", " + brushColor[1] + ", " + brushColor[2] + ", " + brushColor[3] + "]," + brushSize);
      parent.println("2. " + offsetStrokeMask + "," + offsetControlPointMask + "," + strokeMask + "," + controlPointMask);

      offset = offset + 28 + offsetStrokeMask + 4; // TOFIX: This is wrong

      int numControlPoints = getInt(bytes, offset);

      parent.println("3. " + numControlPoints);

      float[] positions = new float[numControlPoints * 3];

      offset = offset + 4;

      for (int j = 0; j < positions.length; j += 3) {
        positions[j + 0] = getFloat(bytes, offset + 0);
        positions[j + 1] = getFloat(bytes, offset + 4);
        positions[j + 2] = getFloat(bytes, offset + 8);

        offset = offset + 28 + offsetControlPointMask; // TOFIX: This is wrong
      }

      /*
      if ( brushIndex in brushes === false ) {
        brushes[ brushIndex ] = [];
      }

      brushes[ brushIndex ].push( [ positions, quaternions, brushSize, brushColor ] );
      */
    }
  }

  private void buildTilt() {
    // TODO
  }

  private int getUInt(byte[] _bytes, int _offset) {
    byte[] uintBytes = { _bytes[_offset], _bytes[_offset+1], _bytes[_offset+2], _bytes[_offset+3] };
    return asUInt(uintBytes);
  }

  private int getInt(byte[] _bytes, int _offset) {
    byte[] intBytes = { _bytes[_offset], _bytes[_offset+1], _bytes[_offset+2], _bytes[_offset+3] };
    return asInt(intBytes);
  }

  private float getFloat(byte[] _bytes, int _offset) {
    byte[] floatBytes = { _bytes[_offset], _bytes[_offset+1], _bytes[_offset+2], _bytes[_offset+3] };
    return asFloat(floatBytes);
  }

  private int asUInt(byte[] _bytes) {
    int i = asInt(_bytes);
    long unsigned = i & 0xffffffffL;
    return (int) unsigned;
  }

  private int asInt(byte[] _bytes) {
    return (_bytes[0] & 0xFF) 
           | ((_bytes[1] & 0xFF) << 8) 
           | ((_bytes[2] & 0xFF) << 16) 
           | ((_bytes[3] & 0xFF) << 24);
  }

  private float asFloat(byte[] _bytes) {
    return Float.intBitsToFloat(asInt(_bytes));
  }

  private byte[] readEntryAsBytes(String entry) {
    try {
      InputStream stream = zipFile.getInputStream(zipFile.getEntry(entry));
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();

      int nRead;
      byte[] data = new byte[4];

      while ((nRead = stream.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      }

      buffer.flush();
      return buffer.toByteArray();
    } catch (Exception e) {
      parent.println(e);
      return null;
    }
  }

  private String readEntryAsString(String entry) {
    try {
      InputStream stream = zipFile.getInputStream(zipFile.getEntry(entry));
      String newLine = System.getProperty("line.separator");
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      StringBuilder result = new StringBuilder();
      boolean flag = false;
      for (String line; (line = reader.readLine()) != null; ) {
        result.append(flag? newLine: "").append(line);
        flag = true;
      }
      return result.toString();
    } catch (Exception e) {
      parent.println(e);
      return null;
    }
  }

  private String getFilePath(String fileName) {
    String returns = parent.dataPath(fileName);
    return returns;
  }

  private float remap(float s, float a1, float a2, float b1, float b2) {
      return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
  }

  private boolean hitDetect3D(PVector p1, PVector p2, float s) { 
    if (PVector.dist(p1, p2) < s) {
      return true;
    } else {
      return false;
    }
  }
 
  private float rounder(float _val, float _places){
    _val *= parent.pow(10, _places);
    _val = parent.round(_val);
    _val /= parent.pow(10, _places);
    return _val;
  }

}
