package tiltProcessing;

import processing.core.*;
import java.util.ArrayList;

public class TBHeader {

    static readonly string SKETCH_SENTINEL = "tilT";

    string m_sentinel;

    UInt16 m_headerSize;
    UInt16 m_headerVersion;
    UInt32 m_reserved1;
    UInt32 m_reserved2;

    TBHeader() {
    	//
    }

    public TBHeader(BinaryReader reader) {
        m_sentinel = reader.ReadString(4);
        if (m_sentinel != SKETCH_SENTINEL) {
            throw new Exception("Wrong sentinel: " + m_sentinel);
        }

        m_headerSize = reader.ReadUInt16();
        m_headerVersion = reader.ReadUInt16();
        m_reserved1 = reader.ReadUInt32();
        m_reserved2 = reader.ReadUInt32();
    }

    public void Write(BinaryWriter writer) {
        writer.Write(m_sentinel, 4);
        writer.Write(m_headerSize);
        writer.Write(m_headerVersion);
        writer.Write(m_reserved1);
        writer.Write(m_reserved2);
    }

    public TBHeader Clone() {
        TBHeader clone = new TBHeader();
        clone.m_sentinel = m_sentinel;
        clone.m_headerSize = m_headerSize;
        clone.m_headerVersion = m_headerVersion;
        clone.m_reserved1 = m_reserved1;
        clone.m_reserved2 = m_reserved2;
        return clone;
    }

}


