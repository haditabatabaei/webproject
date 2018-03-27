

package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btDefaultSerializer extends btSerializer {
	private long swigCPtr;
	
	protected btDefaultSerializer(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, LinearMathJNI.btDefaultSerializer_SWIGUpcast(cPtr), cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	
	public btDefaultSerializer(long cPtr, boolean cMemoryOwn) {
		this("btDefaultSerializer", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(LinearMathJNI.btDefaultSerializer_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
	}
	
	public static long getCPtr(btDefaultSerializer obj) {
		return (obj == null) ? 0 : obj.swigCPtr;
	}

	@Override
	protected void finalize() throws Throwable {
		if (!destroyed)
			destroy();
		super.finalize();
	}

  @Override protected synchronized void delete() {
		if (swigCPtr != 0) {
			if (swigCMemOwn) {
				swigCMemOwn = false;
				LinearMathJNI.delete_btDefaultSerializer(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public SWIGTYPE_p_btHashMapT_btHashPtr_void_p_t getSkipPointers() {
    long cPtr = LinearMathJNI.btDefaultSerializer_skipPointers_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btHashMapT_btHashPtr_void_p_t(cPtr, false);
  }

  static private long SwigConstructbtDefaultSerializer(int totalSize, java.nio.ByteBuffer buffer) {
    assert buffer.isDirect() : "Buffer must be allocated direct.";
    return LinearMathJNI.new_btDefaultSerializer__SWIG_0(totalSize, buffer);
  }

  public btDefaultSerializer(int totalSize, java.nio.ByteBuffer buffer) {
    this(btDefaultSerializer.SwigConstructbtDefaultSerializer(totalSize, buffer), true);
  }

  public btDefaultSerializer(int totalSize) {
    this(LinearMathJNI.new_btDefaultSerializer__SWIG_1(totalSize), true);
  }

  public btDefaultSerializer() {
    this(LinearMathJNI.new_btDefaultSerializer__SWIG_2(), true);
  }

  public static int getMemoryDnaSizeInBytes() {
    return LinearMathJNI.btDefaultSerializer_getMemoryDnaSizeInBytes();
  }

  public static String getMemoryDna() {
    return LinearMathJNI.btDefaultSerializer_getMemoryDna();
  }

  public void insertHeader() {
    LinearMathJNI.btDefaultSerializer_insertHeader(swigCPtr, this);
  }

  public void writeHeader(java.nio.ByteBuffer buffer) {
    assert buffer.isDirect() : "Buffer must be allocated direct.";
    {
      LinearMathJNI.btDefaultSerializer_writeHeader(swigCPtr, this, buffer);
    }
  }

  public java.nio.ByteBuffer internalAlloc(long size) {
    return LinearMathJNI.btDefaultSerializer_internalAlloc(swigCPtr, this, size);
}

}
