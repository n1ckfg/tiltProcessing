package tiltProcessing;

import processing.core.*;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.util.ArrayList;

//using Ionic.Zip;

public static class TBExtensions {

    #region BinaryReader

    public static string ReadString(this BinaryReader reader, int length) {
        char[] chars = new char[length];
        reader.Read(chars, 0, chars.Length);
        return new string(chars, 0, chars.Length);
    }

    public static Color ReadColor(this BinaryReader reader) {
        float r = reader.ReadFloat();
        float g = reader.ReadFloat();
        float b = reader.ReadFloat();
        float a = reader.ReadFloat();

        return new Color(r, g, b, a);
    }

    public static Vector3 ReadVector3(this BinaryReader reader) {
        float x = reader.ReadFloat();
        float y = reader.ReadFloat();
        float z = reader.ReadFloat();

        return new Vector3(x, y, z);
    }

    public static Quaternion ReadQuaternion(this BinaryReader reader) {
        float x = reader.ReadFloat();
        float y = reader.ReadFloat();
        float z = reader.ReadFloat();
        float w = reader.ReadFloat();

        return new Quaternion(x, y, z, w);
    }

    public static float ReadFloat(this BinaryReader reader) {
        return reader.ReadSingle();
    }

    public static void Skip(this BinaryReader reader, long size) {
        reader.BaseStream.Position += size;
    }

    #endregion

    #region Binarywriter

    public static void Write(this BinaryWriter writer, string value, int length) {
        char[] chars = value.ToCharArray(0, length);
        writer.Write(chars, 0, chars.Length);
    }

    public static void Write(this BinaryWriter writer, Color value) {
        writer.Write(value.r);
        writer.Write(value.g);
        writer.Write(value.b);
        writer.Write(value.a);
    }

    public static void Write(this BinaryWriter writer, Vector3 value) {
        writer.Write(value.x);
        writer.Write(value.y);
        writer.Write(value.z);
    }

    public static void Write(this BinaryWriter writer, Quaternion value) {
        writer.Write(value.x);
        writer.Write(value.y);
        writer.Write(value.z);
        writer.Write(value.w);
    }

    #endregion

    #region ZipEntry

    public static string ExtractToFile(this ZipEntry entry, string baseDir) {
        string path = Path.Combine(baseDir, entry.FileName);
        using (FileStream stream = File.OpenWrite(path)) {
            entry.Extract(stream);
        }
        return path;
    }

    #endregion
}


