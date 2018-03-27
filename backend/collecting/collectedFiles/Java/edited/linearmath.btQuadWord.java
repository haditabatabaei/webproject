

package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btQuadWord extends BulletBase {
	private long swigCPtr;
	
	protected btQuadWord(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public btQuadWord(long cPtr, boolean cMemoryOwn) {
		this("btQuadWord", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(btQuadWord obj) {
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
				LinearMathJNI.delete_btQuadWord(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public float getX() {
    return LinearMathJNI.btQuadWord_getX(swigCPtr, this);
  }

  public float getY() {
    return LinearMathJNI.btQuadWord_getY(swigCPtr, this);
  }

  public float getZ() {
    return LinearMathJNI.btQuadWord_getZ(swigCPtr, this);
  }

  public void setX(float _x) {
    LinearMathJNI.btQuadWord_setX(swigCPtr, this, _x);
  }

  public void setY(float _y) {
    LinearMathJNI.btQuadWord_setY(swigCPtr, this, _y);
  }

  public void setZ(float _z) {
    LinearMathJNI.btQuadWord_setZ(swigCPtr, this, _z);
  }

  public void setW(float _w) {
    LinearMathJNI.btQuadWord_setW(swigCPtr, this, _w);
  }

  public float x() {
    return LinearMathJNI.btQuadWord_x(swigCPtr, this);
  }

  public float y() {
    return LinearMathJNI.btQuadWord_y(swigCPtr, this);
  }

  public float z() {
    return LinearMathJNI.btQuadWord_z(swigCPtr, this);
  }

  public float w() {
    return LinearMathJNI.btQuadWord_w(swigCPtr, this);
  }

  public java.nio.FloatBuffer operatorbtScalarPtr() {
    return LinearMathJNI.btQuadWord_operatorbtScalarPtr(swigCPtr, this);
}

  public java.nio.FloatBuffer operatorbtConstScalarPtr() {
    return LinearMathJNI.btQuadWord_operatorbtConstScalarPtr(swigCPtr, this);
}

  public boolean operatorEqualTo(btQuadWord other) {
    return LinearMathJNI.btQuadWord_operatorEqualTo(swigCPtr, this, btQuadWord.getCPtr(other), other);
  }

  public boolean operatorNotEqualTo(btQuadWord other) {
    return LinearMathJNI.btQuadWord_operatorNotEqualTo(swigCPtr, this, btQuadWord.getCPtr(other), other);
  }

  public void setValue(float _x, float _y, float _z) {
    LinearMathJNI.btQuadWord_setValue__SWIG_0(swigCPtr, this, _x, _y, _z);
  }

  public void setValue(float _x, float _y, float _z, float _w) {
    LinearMathJNI.btQuadWord_setValue__SWIG_1(swigCPtr, this, _x, _y, _z, _w);
  }

  public btQuadWord() {
    this(LinearMathJNI.new_btQuadWord__SWIG_0(), true);
  }

  public btQuadWord(float _x, float _y, float _z) {
    this(LinearMathJNI.new_btQuadWord__SWIG_1(_x, _y, _z), true);
  }

  public btQuadWord(float _x, float _y, float _z, float _w) {
    this(LinearMathJNI.new_btQuadWord__SWIG_2(_x, _y, _z, _w), true);
  }

  public void setMax(btQuadWord other) {
    LinearMathJNI.btQuadWord_setMax(swigCPtr, this, btQuadWord.getCPtr(other), other);
  }

  public void setMin(btQuadWord other) {
    LinearMathJNI.btQuadWord_setMin(swigCPtr, this, btQuadWord.getCPtr(other), other);
  }

}
