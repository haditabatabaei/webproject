
package com.alibaba.dubbo.common.serialize.fastjson;

import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;


public class FastJsonObjectOutput implements ObjectOutput {

    private final PrintWriter writer;

    public FastJsonObjectOutput(OutputStream out) {
        this(new OutputStreamWriter(out));
    }

    public FastJsonObjectOutput(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void writeBool(boolean v) throws IOException {
        writeObject(v);
    }

    public void writeByte(byte v) throws IOException {
        writeObject(v);
    }

    public void writeShort(short v) throws IOException {
        writeObject(v);
    }

    public void writeInt(int v) throws IOException {
        writeObject(v);
    }

    public void writeLong(long v) throws IOException {
        writeObject(v);
    }

    public void writeFloat(float v) throws IOException {
        writeObject(v);
    }

    public void writeDouble(double v) throws IOException {
        writeObject(v);
    }

    public void writeUTF(String v) throws IOException {
        writeObject(v);
    }

    public void writeBytes(byte[] b) throws IOException {
        writer.println(new String(b));
    }

    public void writeBytes(byte[] b, int off, int len) throws IOException {
        writer.println(new String(b, off, len));
    }

    public void writeObject(Object obj) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.write(obj);
        out.writeTo(writer);
        out.close();         writer.println();
        writer.flush();
    }

    public void flushBuffer() throws IOException {
        writer.flush();
    }

}