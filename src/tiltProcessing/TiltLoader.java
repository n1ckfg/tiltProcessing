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

    /*
    for (int i = 0; i < numStrokes; i++) {

      const brush_index = data.getInt32( offset, true );

      const brush_color = [
        data.getFloat32( offset + 4, true ),
        data.getFloat32( offset + 8, true ),
        data.getFloat32( offset + 12, true ),
        data.getFloat32( offset + 16, true )
      ];
      const brush_size = data.getFloat32( offset + 20, true );
      const stroke_mask = data.getUint32( offset + 24, true );
      const controlpoint_mask = data.getUint32( offset + 28, true );

      let offset_stroke_mask = 0;
      let offset_controlpoint_mask = 0;

      for ( let j = 0; j < 4; j ++ ) {

        // TOFIX: I don't understand these masks yet

        const byte = 1 << j;
        if ( ( stroke_mask & byte ) > 0 ) offset_stroke_mask += 4;
        if ( ( controlpoint_mask & byte ) > 0 ) offset_controlpoint_mask += 4;

      }

      // console.log( { brush_index, brush_color, brush_size, stroke_mask, controlpoint_mask } );
      // console.log( offset_stroke_mask, offset_controlpoint_mask );

      offset = offset + 28 + offset_stroke_mask + 4; // TOFIX: This is wrong

      const num_control_points = data.getInt32( offset, true );

      // console.log( { num_control_points } );

      const positions = new Float32Array( num_control_points * 3 );
      const quaternions = new Float32Array( num_control_points * 4 );

      offset = offset + 4;

      for ( let j = 0, k = 0; j < positions.length; j += 3, k += 4 ) {

        positions[ j + 0 ] = data.getFloat32( offset + 0, true );
        positions[ j + 1 ] = data.getFloat32( offset + 4, true );
        positions[ j + 2 ] = data.getFloat32( offset + 8, true );

        quaternions[ k + 0 ] = data.getFloat32( offset + 12, true );
        quaternions[ k + 1 ] = data.getFloat32( offset + 16, true );
        quaternions[ k + 2 ] = data.getFloat32( offset + 20, true );
        quaternions[ k + 3 ] = data.getFloat32( offset + 24, true );

        offset = offset + 28 + offset_controlpoint_mask; // TOFIX: This is wrong

      }

      if ( brush_index in brushes === false ) {

        brushes[ brush_index ] = [];

      }

      brushes[ brush_index ].push( [ positions, quaternions, brush_size, brush_color ] );

    }
    */
  }

  private void buildTilt() {
    // TODO
  }

  private int getInt(byte[] _bytes, int _offset) {
    byte[] intBytes = { _bytes[_offset], _bytes[_offset+1], _bytes[_offset+2], _bytes[_offset+3] };
    return asInt(intBytes);
  }

  private float getFloat(byte[] _bytes, int _offset) {
    byte[] floatBytes = { _bytes[_offset], _bytes[_offset+1], _bytes[_offset+2], _bytes[_offset+3] };
    return asFloat(floatBytes);
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
