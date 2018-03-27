

package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btClock extends BulletBase {
	private long swigCPtr;
	
	protected btClock(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public btClock(long cPtr, boolean cMemoryOwn) {
		this("btClock", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(btClock obj) {
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
				LinearMathJNI.delete_btClock(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public btClock() {
    this(LinearMathJNI.new_btClock__SWIG_0(), true);
  }

  public btClock(btClock other) {
    this(LinearMathJNI.new_btClock__SWIG_1(btClock.getCPtr(other), other), true);
  }

  public btClock operatorAssignment(btClock other) {
    return new btClock(LinearMathJNI.btClock_operatorAssignment(swigCPtr, this, btClock.getCPtr(other), other), false);
  }

  public void reset() {
    LinearMathJNI.btClock_reset(swigCPtr, this);
  }

  public java.math.BigInteger getTimeMilliseconds() {
    return LinearMathJNI.btClock_getTimeMilliseconds(swigCPtr, this);
  }

  public java.math.BigInteger getTimeMicroseconds() {
    return LinearMathJNI.btClock_getTimeMicroseconds(swigCPtr, this);
  }

  public java.math.BigInteger getTimeNanoseconds() {
    return LinearMathJNI.btClock_getTimeNanoseconds(swigCPtr, this);
  }

  public float getTimeSeconds() {
    return LinearMathJNI.btClock_getTimeSeconds(swigCPtr, this);
  }

}
