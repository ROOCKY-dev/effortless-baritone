/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.ByteBufAllocator
 *  io.netty.util.ByteProcessor
 *  io.netty.util.internal.ObjectUtil
 *  io.netty.util.internal.StringUtil
 */
package dev.huskuraft.effortless.api.networking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

class WrappedByteBuf
extends ByteBuf {
    protected final ByteBuf source;

    protected WrappedByteBuf(ByteBuf source) {
        this.source = (ByteBuf)ObjectUtil.checkNotNull((Object)source, (String)"source");
    }

    public final boolean hasMemoryAddress() {
        return this.source.hasMemoryAddress();
    }

    public boolean isContiguous() {
        return this.source.isContiguous();
    }

    public final long memoryAddress() {
        return this.source.memoryAddress();
    }

    public final int capacity() {
        return this.source.capacity();
    }

    public ByteBuf capacity(int newCapacity) {
        this.source.capacity(newCapacity);
        return this;
    }

    public final int maxCapacity() {
        return this.source.maxCapacity();
    }

    public final ByteBufAllocator alloc() {
        return this.source.alloc();
    }

    public final ByteOrder order() {
        return this.source.order();
    }

    public ByteBuf order(ByteOrder endianness) {
        return this.source.order(endianness);
    }

    public final ByteBuf unwrap() {
        return this.source;
    }

    public ByteBuf asReadOnly() {
        return this.source.asReadOnly();
    }

    public boolean isReadOnly() {
        return this.source.isReadOnly();
    }

    public final boolean isDirect() {
        return this.source.isDirect();
    }

    public final int readerIndex() {
        return this.source.readerIndex();
    }

    public final ByteBuf readerIndex(int readerIndex) {
        this.source.readerIndex(readerIndex);
        return this;
    }

    public final int writerIndex() {
        return this.source.writerIndex();
    }

    public final ByteBuf writerIndex(int writerIndex) {
        this.source.writerIndex(writerIndex);
        return this;
    }

    public ByteBuf setIndex(int readerIndex, int writerIndex) {
        this.source.setIndex(readerIndex, writerIndex);
        return this;
    }

    public final int readableBytes() {
        return this.source.readableBytes();
    }

    public final int writableBytes() {
        return this.source.writableBytes();
    }

    public final int maxWritableBytes() {
        return this.source.maxWritableBytes();
    }

    public int maxFastWritableBytes() {
        return this.source.maxFastWritableBytes();
    }

    public final boolean isReadable() {
        return this.source.isReadable();
    }

    public final boolean isWritable() {
        return this.source.isWritable();
    }

    public final ByteBuf clear() {
        this.source.clear();
        return this;
    }

    public final ByteBuf markReaderIndex() {
        this.source.markReaderIndex();
        return this;
    }

    public final ByteBuf resetReaderIndex() {
        this.source.resetReaderIndex();
        return this;
    }

    public final ByteBuf markWriterIndex() {
        this.source.markWriterIndex();
        return this;
    }

    public final ByteBuf resetWriterIndex() {
        this.source.resetWriterIndex();
        return this;
    }

    public ByteBuf discardReadBytes() {
        this.source.discardReadBytes();
        return this;
    }

    public ByteBuf discardSomeReadBytes() {
        this.source.discardSomeReadBytes();
        return this;
    }

    public ByteBuf ensureWritable(int minWritableBytes) {
        this.source.ensureWritable(minWritableBytes);
        return this;
    }

    public int ensureWritable(int minWritableBytes, boolean force) {
        return this.source.ensureWritable(minWritableBytes, force);
    }

    public boolean getBoolean(int index) {
        return this.source.getBoolean(index);
    }

    public byte getByte(int index) {
        return this.source.getByte(index);
    }

    public short getUnsignedByte(int index) {
        return this.source.getUnsignedByte(index);
    }

    public short getShort(int index) {
        return this.source.getShort(index);
    }

    public short getShortLE(int index) {
        return this.source.getShortLE(index);
    }

    public int getUnsignedShort(int index) {
        return this.source.getUnsignedShort(index);
    }

    public int getUnsignedShortLE(int index) {
        return this.source.getUnsignedShortLE(index);
    }

    public int getMedium(int index) {
        return this.source.getMedium(index);
    }

    public int getMediumLE(int index) {
        return this.source.getMediumLE(index);
    }

    public int getUnsignedMedium(int index) {
        return this.source.getUnsignedMedium(index);
    }

    public int getUnsignedMediumLE(int index) {
        return this.source.getUnsignedMediumLE(index);
    }

    public int getInt(int index) {
        return this.source.getInt(index);
    }

    public int getIntLE(int index) {
        return this.source.getIntLE(index);
    }

    public long getUnsignedInt(int index) {
        return this.source.getUnsignedInt(index);
    }

    public long getUnsignedIntLE(int index) {
        return this.source.getUnsignedIntLE(index);
    }

    public long getLong(int index) {
        return this.source.getLong(index);
    }

    public long getLongLE(int index) {
        return this.source.getLongLE(index);
    }

    public char getChar(int index) {
        return this.source.getChar(index);
    }

    public float getFloat(int index) {
        return this.source.getFloat(index);
    }

    public double getDouble(int index) {
        return this.source.getDouble(index);
    }

    public ByteBuf getBytes(int index, ByteBuf dst) {
        this.source.getBytes(index, dst);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int length) {
        this.source.getBytes(index, dst, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        this.source.getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst) {
        this.source.getBytes(index, dst);
        return this;
    }

    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        this.source.getBytes(index, dst, dstIndex, length);
        return this;
    }

    public ByteBuf getBytes(int index, ByteBuffer dst) {
        this.source.getBytes(index, dst);
        return this;
    }

    public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
        this.source.getBytes(index, out, length);
        return this;
    }

    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
        return this.source.getBytes(index, out, length);
    }

    public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
        return this.source.getBytes(index, out, position, length);
    }

    public CharSequence getCharSequence(int index, int length, Charset charset) {
        return this.source.getCharSequence(index, length, charset);
    }

    public ByteBuf setBoolean(int index, boolean value) {
        this.source.setBoolean(index, value);
        return this;
    }

    public ByteBuf setByte(int index, int value) {
        this.source.setByte(index, value);
        return this;
    }

    public ByteBuf setShort(int index, int value) {
        this.source.setShort(index, value);
        return this;
    }

    public ByteBuf setShortLE(int index, int value) {
        this.source.setShortLE(index, value);
        return this;
    }

    public ByteBuf setMedium(int index, int value) {
        this.source.setMedium(index, value);
        return this;
    }

    public ByteBuf setMediumLE(int index, int value) {
        this.source.setMediumLE(index, value);
        return this;
    }

    public ByteBuf setInt(int index, int value) {
        this.source.setInt(index, value);
        return this;
    }

    public ByteBuf setIntLE(int index, int value) {
        this.source.setIntLE(index, value);
        return this;
    }

    public ByteBuf setLong(int index, long value) {
        this.source.setLong(index, value);
        return this;
    }

    public ByteBuf setLongLE(int index, long value) {
        this.source.setLongLE(index, value);
        return this;
    }

    public ByteBuf setChar(int index, int value) {
        this.source.setChar(index, value);
        return this;
    }

    public ByteBuf setFloat(int index, float value) {
        this.source.setFloat(index, value);
        return this;
    }

    public ByteBuf setDouble(int index, double value) {
        this.source.setDouble(index, value);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuf src) {
        this.source.setBytes(index, src);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuf src, int length) {
        this.source.setBytes(index, src, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        this.source.setBytes(index, src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, byte[] src) {
        this.source.setBytes(index, src);
        return this;
    }

    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        this.source.setBytes(index, src, srcIndex, length);
        return this;
    }

    public ByteBuf setBytes(int index, ByteBuffer src) {
        this.source.setBytes(index, src);
        return this;
    }

    public int setBytes(int index, InputStream in, int length) throws IOException {
        return this.source.setBytes(index, in, length);
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
        return this.source.setBytes(index, in, length);
    }

    public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
        return this.source.setBytes(index, in, position, length);
    }

    public ByteBuf setZero(int index, int length) {
        this.source.setZero(index, length);
        return this;
    }

    public int setCharSequence(int index, CharSequence sequence, Charset charset) {
        return this.source.setCharSequence(index, sequence, charset);
    }

    public boolean readBoolean() {
        return this.source.readBoolean();
    }

    public byte readByte() {
        return this.source.readByte();
    }

    public short readUnsignedByte() {
        return this.source.readUnsignedByte();
    }

    public short readShort() {
        return this.source.readShort();
    }

    public short readShortLE() {
        return this.source.readShortLE();
    }

    public int readUnsignedShort() {
        return this.source.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.source.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.source.readMedium();
    }

    public int readMediumLE() {
        return this.source.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.source.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.source.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.source.readInt();
    }

    public int readIntLE() {
        return this.source.readIntLE();
    }

    public long readUnsignedInt() {
        return this.source.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.source.readUnsignedIntLE();
    }

    public long readLong() {
        return this.source.readLong();
    }

    public long readLongLE() {
        return this.source.readLongLE();
    }

    public char readChar() {
        return this.source.readChar();
    }

    public float readFloat() {
        return this.source.readFloat();
    }

    public double readDouble() {
        return this.source.readDouble();
    }

    public ByteBuf readBytes(int length) {
        return this.source.readBytes(length);
    }

    public ByteBuf readSlice(int length) {
        return this.source.readSlice(length);
    }

    public ByteBuf readRetainedSlice(int length) {
        return this.source.readRetainedSlice(length);
    }

    public ByteBuf readBytes(ByteBuf dst) {
        this.source.readBytes(dst);
        return this;
    }

    public ByteBuf readBytes(ByteBuf dst, int length) {
        this.source.readBytes(dst, length);
        return this;
    }

    public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
        this.source.readBytes(dst, dstIndex, length);
        return this;
    }

    public ByteBuf readBytes(byte[] dst) {
        this.source.readBytes(dst);
        return this;
    }

    public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
        this.source.readBytes(dst, dstIndex, length);
        return this;
    }

    public ByteBuf readBytes(ByteBuffer dst) {
        this.source.readBytes(dst);
        return this;
    }

    public ByteBuf readBytes(OutputStream out, int length) throws IOException {
        this.source.readBytes(out, length);
        return this;
    }

    public int readBytes(GatheringByteChannel out, int length) throws IOException {
        return this.source.readBytes(out, length);
    }

    public int readBytes(FileChannel out, long position, int length) throws IOException {
        return this.source.readBytes(out, position, length);
    }

    public CharSequence readCharSequence(int length, Charset charset) {
        return this.source.readCharSequence(length, charset);
    }

    public ByteBuf skipBytes(int length) {
        this.source.skipBytes(length);
        return this;
    }

    public ByteBuf writeBoolean(boolean value) {
        this.source.writeBoolean(value);
        return this;
    }

    public ByteBuf writeByte(int value) {
        this.source.writeByte(value);
        return this;
    }

    public ByteBuf writeShort(int value) {
        this.source.writeShort(value);
        return this;
    }

    public ByteBuf writeShortLE(int value) {
        this.source.writeShortLE(value);
        return this;
    }

    public ByteBuf writeMedium(int value) {
        this.source.writeMedium(value);
        return this;
    }

    public ByteBuf writeMediumLE(int value) {
        this.source.writeMediumLE(value);
        return this;
    }

    public ByteBuf writeInt(int value) {
        this.source.writeInt(value);
        return this;
    }

    public ByteBuf writeIntLE(int value) {
        this.source.writeIntLE(value);
        return this;
    }

    public ByteBuf writeLong(long value) {
        this.source.writeLong(value);
        return this;
    }

    public ByteBuf writeLongLE(long value) {
        this.source.writeLongLE(value);
        return this;
    }

    public ByteBuf writeChar(int value) {
        this.source.writeChar(value);
        return this;
    }

    public ByteBuf writeFloat(float value) {
        this.source.writeFloat(value);
        return this;
    }

    public ByteBuf writeDouble(double value) {
        this.source.writeDouble(value);
        return this;
    }

    public ByteBuf writeBytes(ByteBuf src) {
        this.source.writeBytes(src);
        return this;
    }

    public ByteBuf writeBytes(ByteBuf src, int length) {
        this.source.writeBytes(src, length);
        return this;
    }

    public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
        this.source.writeBytes(src, srcIndex, length);
        return this;
    }

    public ByteBuf writeBytes(byte[] src) {
        this.source.writeBytes(src);
        return this;
    }

    public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
        this.source.writeBytes(src, srcIndex, length);
        return this;
    }

    public ByteBuf writeBytes(ByteBuffer src) {
        this.source.writeBytes(src);
        return this;
    }

    public int writeBytes(InputStream in, int length) throws IOException {
        return this.source.writeBytes(in, length);
    }

    public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
        return this.source.writeBytes(in, length);
    }

    public int writeBytes(FileChannel in, long position, int length) throws IOException {
        return this.source.writeBytes(in, position, length);
    }

    public ByteBuf writeZero(int length) {
        this.source.writeZero(length);
        return this;
    }

    public int writeCharSequence(CharSequence sequence, Charset charset) {
        return this.source.writeCharSequence(sequence, charset);
    }

    public int indexOf(int fromIndex, int toIndex, byte value) {
        return this.source.indexOf(fromIndex, toIndex, value);
    }

    public int bytesBefore(byte value) {
        return this.source.bytesBefore(value);
    }

    public int bytesBefore(int length, byte value) {
        return this.source.bytesBefore(length, value);
    }

    public int bytesBefore(int index, int length, byte value) {
        return this.source.bytesBefore(index, length, value);
    }

    public int forEachByte(ByteProcessor processor) {
        return this.source.forEachByte(processor);
    }

    public int forEachByte(int index, int length, ByteProcessor processor) {
        return this.source.forEachByte(index, length, processor);
    }

    public int forEachByteDesc(ByteProcessor processor) {
        return this.source.forEachByteDesc(processor);
    }

    public int forEachByteDesc(int index, int length, ByteProcessor processor) {
        return this.source.forEachByteDesc(index, length, processor);
    }

    public ByteBuf copy() {
        return this.source.copy();
    }

    public ByteBuf copy(int index, int length) {
        return this.source.copy(index, length);
    }

    public ByteBuf slice() {
        return this.source.slice();
    }

    public ByteBuf retainedSlice() {
        return this.source.retainedSlice();
    }

    public ByteBuf slice(int index, int length) {
        return this.source.slice(index, length);
    }

    public ByteBuf retainedSlice(int index, int length) {
        return this.source.retainedSlice(index, length);
    }

    public ByteBuf duplicate() {
        return this.source.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.source.retainedDuplicate();
    }

    public int nioBufferCount() {
        return this.source.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.source.nioBuffer();
    }

    public ByteBuffer nioBuffer(int index, int length) {
        return this.source.nioBuffer(index, length);
    }

    public ByteBuffer[] nioBuffers() {
        return this.source.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int index, int length) {
        return this.source.nioBuffers(index, length);
    }

    public ByteBuffer internalNioBuffer(int index, int length) {
        return this.source.internalNioBuffer(index, length);
    }

    public boolean hasArray() {
        return this.source.hasArray();
    }

    public byte[] array() {
        return this.source.array();
    }

    public int arrayOffset() {
        return this.source.arrayOffset();
    }

    public String toString(Charset charset) {
        return this.source.toString(charset);
    }

    public String toString(int index, int length, Charset charset) {
        return this.source.toString(index, length, charset);
    }

    public int hashCode() {
        return this.source.hashCode();
    }

    public boolean equals(Object obj) {
        return this.source.equals(obj);
    }

    public int compareTo(ByteBuf buffer) {
        return this.source.compareTo(buffer);
    }

    public String toString() {
        return StringUtil.simpleClassName((Object)((Object)this)) + "(" + this.source.toString() + ")";
    }

    public ByteBuf retain(int increment) {
        this.source.retain(increment);
        return this;
    }

    public ByteBuf retain() {
        this.source.retain();
        return this;
    }

    public ByteBuf touch() {
        this.source.touch();
        return this;
    }

    public ByteBuf touch(Object hint) {
        this.source.touch(hint);
        return this;
    }

    public final boolean isReadable(int size) {
        return this.source.isReadable(size);
    }

    public final boolean isWritable(int size) {
        return this.source.isWritable(size);
    }

    public final int refCnt() {
        return this.source.refCnt();
    }

    public boolean release() {
        return this.source.release();
    }

    public boolean release(int decrement) {
        return this.source.release(decrement);
    }
}
