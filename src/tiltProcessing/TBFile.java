package tiltProcessing;

import processing.core.*;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.io.*;
import java.nio.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.ArrayList;

//using Ionic.Zip;
//using uint16 = System.UInt16;
//using uint32 = System.UInt32;
//using int32 = System.int;

public class TBFile {

    static readonly String kFileSketchData  = "data.sketch";
    static readonly String kFileMetadata    = "metadata.json";
    static readonly String kFileThumbnail   = "thumbnail.png";

    private static readonly uint SKETCH_SENTINEL = 3312887245u;
    private static readonly int SKETCH_VERSION = 5;

    TBHeader m_header;
    TBBrushStrokes m_brushStrokes;
    String m_metadata;
    byte[] m_thumbnailBytes;

    TBFile() {
    	//
    }

    public TBFile(String path) {
        using (FileStream stream = File.OpenRead(path)) {
            using (BinaryReader reader = new BinaryReader(stream)) {
                Read(reader);
            }
        }
    }

    public TBFile(Stream stream) {
        using (BinaryReader reader = new BinaryReader(stream)) {
            Read(reader);
        }
    }

    void Read(BinaryReader reader) {
        m_header = new TBHeader(reader);

        byte[] bytes = new byte[reader.BaseStream.Length - reader.BaseStream.Position];
        reader.Read(bytes, 0, bytes.Length);
        using (MemoryStream stream = new MemoryStream(bytes)) {
            using (ZipFile zipFile = ZipFile.Read(stream)) {
                String tempDir = GetTempDirectory(".tilt-in");
                foreach (var entry in zipFile.Entries) {
                    entry.ExtractToFile(tempDir);
                }

                try {
                    m_thumbnailBytes = ReadThumbnailBytes(Path.Combine(tempDir, kFileThumbnail));
                    m_brushStrokes = ReadBrushStrokes(Path.Combine(tempDir, kFileSketchData));
                    m_metadata = ReadMetadata(Path.Combine(tempDir, kFileMetadata));
                } finally {
                    Directory.Delete(tempDir, true);
                }
            }
        }
    }

    //#region Read

    static TBBrushStrokes ReadBrushStrokes(String path) {
        using (Stream stream = File.OpenRead(path)) {
            using (BinaryReader reader = new BinaryReader(stream)) {
                return new TBBrushStrokes(reader);
            }
        }
    }

    static String ReadMetadata(String path) {
        return File.ReadAllText(path);
    }

    static byte[] ReadThumbnailBytes(String path) {
        return File.ReadAllBytes(path);
    }

    //#endregion

    //#region Write

    public void Write(String path) {
        String tempDir = GetTempDirectory(".tilt-out");
        try {
            WriteToTempDir(tempDir);

            using (FileStream stream = File.OpenWrite(path)) {
                using (BinaryWriter writer = new BinaryWriter(stream)) {
                    m_header.Write(writer);

                    using (ZipFile zipFile = new ZipFile()) {
                        String[] files = {
                            kFileThumbnail,
                            kFileSketchData,
                            kFileMetadata
                        };
                        
                        foreach (var file in files) {
                            var entry = zipFile.AddFile(Path.Combine(tempDir, file), "/");
                            entry.CompressionMethod = CompressionMethod.None;
                        }

                        zipFile.Save(writer.BaseStream);
                    }
                }
            }
        } finally {
            if (Directory.Exists(tempDir)) {
                Directory.Delete(tempDir, true);
            }
        }
    }

    void WriteToTempDir(String tempDir) {
        String sketchFile  = Path.Combine(tempDir, "data.sketch");
        using (FileStream stream = File.OpenWrite(sketchFile)) {
            using (BinaryWriter writer = new BinaryWriter(stream)) {
                m_brushStrokes.Write(writer);
            }
        }

        String metadataFile = Path.Combine(tempDir, "metadata.json");
        File.WriteAllText(metadataFile, m_metadata);

        String thumbnailFile = Path.Combine(tempDir, "thumbnail.png");
        File.WriteAllBytes(thumbnailFile, m_thumbnailBytes);
    }

    //#endregion

    //#region Helpers

    static String GetTempDirectory(String name, bool createIsNotExists = true) {
        String tempDir = Path.Combine(Path.GetTempPath(), name);
        if (Directory.Exists(tempDir)) {
            Directory.Delete(tempDir, true);
        }

        if (createIsNotExists) {
            Directory.CreateDirectory(tempDir);
        }

        return tempDir;
    }

    //#endregion

    //#region Clonable

    public TBFile Clone() {
        TBFile clone = new TBFile();
        clone.m_header = m_header.Clone();
        clone.m_brushStrokes = m_brushStrokes.Clone();
        clone.m_metadata = m_metadata;
        clone.m_thumbnailBytes = new byte[m_thumbnailBytes.Length];
        Array.Copy(m_thumbnailBytes, clone.m_thumbnailBytes, m_thumbnailBytes.Length);
        return clone;
    }

    //#endregion

    public TBBrushStrokes brushStrokes {
        get { return m_brushStrokes; }
        set { m_brushStrokes = value; }
    }

}

