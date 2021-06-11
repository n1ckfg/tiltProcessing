package tiltProcessing;

import processing.core.*;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.util.ArrayList;

//using Ionic.Zip;

public static class TBExtensions {

    //#region BinaryReader

    public static String ReadString(BinaryReader reader, int length) {
        char[] chars = new char[length];
        reader.Read(chars, 0, chars.Length);
        return new String(chars, 0, chars.Length);
    }

    public static color ReadColor(BinaryReader reader) {
        float r = reader.ReadFloat();
        float g = reader.ReadFloat();
        float b = reader.ReadFloat();
        float a = reader.ReadFloat();

        return new color(r, g, b, a);
    }

    public static PVector ReadVector3(BinaryReader reader) {
        float x = reader.ReadFloat();
        float y = reader.ReadFloat();
        float z = reader.ReadFloat();

        return new PVector(x, y, z);
    }

    public static Quaternion ReadQuaternion(BinaryReader reader) {
        float x = reader.ReadFloat();
        float y = reader.ReadFloat();
        float z = reader.ReadFloat();
        float w = reader.ReadFloat();

        return new Quaternion(x, y, z, w);
    }

    public static float ReadFloat(BinaryReader reader) {
        return reader.ReadSingle();
    }

    public static void Skip(BinaryReader reader, long size) {
        reader.BaseStream.Position += size;
    }

    //#endregion

    //#region Binarywriter

    public static void Write(BinaryWriter writer, String value, int length) {
        char[] chars = value.ToCharArray(0, length);
        writer.Write(chars, 0, chars.Length);
    }

    public static void Write(BinaryWriter writer, color value) {
        writer.Write(red(value));
        writer.Write(green(value));
        writer.Write(blue(value));
        writer.Write(alpha(value));
    }

    public static void Write(BinaryWriter writer, PVector value) {
        writer.Write(value.x);
        writer.Write(value.y);
        writer.Write(value.z);
    }

    public static void Write(BinaryWriter writer, Quaternion value) {
        writer.Write(value.x);
        writer.Write(value.y);
        writer.Write(value.z);
        writer.Write(value.w);
    }

    //#endregion

    //#region ZipEntry

    public static String ExtractToFile(ZipEntry entry, String baseDir) {
        String path = Path.Combine(baseDir, entry.FileName);
        using (FileStream stream = File.OpenWrite(path)) {
            entry.Extract(stream);
        }
        return path;
    }

    //#endregion
}


