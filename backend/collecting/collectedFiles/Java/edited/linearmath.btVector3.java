

package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btVector3 extends BulletBase {
	private long swigCPtr;
	
	protected btVector3(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public btVector3(long cPtr, boolean cMemoryOwn) {
		this("btVector3", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(btVector3 obj) {
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
				LinearMathJNI.delete_btVector3(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public long operatorNew(long sizeInBytes) {
    return LinearMathJNI.btVector3_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDelete(long ptr) {
    LinearMathJNI.btVector3_operatorDelete__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNew(long arg0, long ptr) {
    return LinearMathJNI.btVector3_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDelete(long arg0, long arg1) {
    LinearMathJNI.btVector3_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public long operatorNewArray(long sizeInBytes) {
    return LinearMathJNI.btVector3_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDeleteArray(long ptr) {
    LinearMathJNI.btVector3_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNewArray(long arg0, long ptr) {
    return LinearMathJNI.btVector3_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDeleteArray(long arg0, long arg1) {
    LinearMathJNI.btVector3_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public void setFloats(float[] value) {
    LinearMathJNI.btVector3_floats_set(swigCPtr, this, value);
  }

  public float[] getFloats() {
    return LinearMathJNI.btVector3_floats_get(swigCPtr, this);
  }

  public btVector3() {
    this(LinearMathJNI.new_btVector3__SWIG_0(), true);
  }

  public btVector3(float _x, float _y, float _z) {
    this(LinearMathJNI.new_btVector3__SWIG_1(_x, _y, _z), true);
  }

  public Vector3 operatorAdditionAssignment(Vector3 v) {
	return LinearMathJNI.btVector3_operatorAdditionAssignment(swigCPtr, this, v);
}

  public Vector3 operatorSubtractionAssignment(Vector3 v) {
	return LinearMathJNI.btVector3_operatorSubtractionAssignment(swigCPtr, this, v);
}

  public Vector3 operatorMultiplicationAssignment(float s) {
	return LinearMathJNI.btVector3_operatorMultiplicationAssignment__SWIG_0(swigCPtr, this, s);
}

  public Vector3 operatorDivisionAssignment(float s) {
	return LinearMathJNI.btVector3_operatorDivisionAssignment(swigCPtr, this, s);
}

  public float dot(Vector3 v) {
    return LinearMathJNI.btVector3_dot(swigCPtr, this, v);
  }

  public float length2() {
    return LinearMathJNI.btVector3_length2(swigCPtr, this);
  }

  public float length() {
    return LinearMathJNI.btVector3_length(swigCPtr, this);
  }

  public float norm() {
    return LinearMathJNI.btVector3_norm(swigCPtr, this);
  }

  public float safeNorm() {
    return LinearMathJNI.btVector3_safeNorm(swigCPtr, this);
  }

  public float distance2(Vector3 v) {
    return LinearMathJNI.btVector3_distance2(swigCPtr, this, v);
  }

  public float distance(Vector3 v) {
    return LinearMathJNI.btVector3_distance(swigCPtr, this, v);
  }

  public Vector3 safeNormalize() {
	return LinearMathJNI.btVector3_safeNormalize(swigCPtr, this);
}

  public Vector3 normalize() {
	return LinearMathJNI.btVector3_normalize(swigCPtr, this);
}

  public Vector3 normalized() {
	return LinearMathJNI.btVector3_normalized(swigCPtr, this);
}

  public Vector3 rotate(Vector3 wAxis, float angle) {
	return LinearMathJNI.btVector3_rotate(swigCPtr, this, wAxis, angle);
}

  public float angle(Vector3 v) {
    return LinearMathJNI.btVector3_angle(swigCPtr, this, v);
  }

  public Vector3 absolute() {
	return LinearMathJNI.btVector3_absolute(swigCPtr, this);
}

  public Vector3 cross(Vector3 v) {
	return LinearMathJNI.btVector3_cross(swigCPtr, this, v);
}

  public float triple(Vector3 v1, Vector3 v2) {
    return LinearMathJNI.btVector3_triple(swigCPtr, this, v1, v2);
  }

  public int minAxis() {
    return LinearMathJNI.btVector3_minAxis(swigCPtr, this);
  }

  public int maxAxis() {
    return LinearMathJNI.btVector3_maxAxis(swigCPtr, this);
  }

  public int furthestAxis() {
    return LinearMathJNI.btVector3_furthestAxis(swigCPtr, this);
  }

  public int closestAxis() {
    return LinearMathJNI.btVector3_closestAxis(swigCPtr, this);
  }

  public void setInterpolate3(Vector3 v0, Vector3 v1, float rt) {
    LinearMathJNI.btVector3_setInterpolate3(swigCPtr, this, v0, v1, rt);
  }

  public Vector3 lerp(Vector3 v, float t) {
	return LinearMathJNI.btVector3_lerp(swigCPtr, this, v, t);
}

  public Vector3 operatorMultiplicationAssignment(Vector3 v) {
	return LinearMathJNI.btVector3_operatorMultiplicationAssignment__SWIG_1(swigCPtr, this, v);
}

  public float getX() {
    return LinearMathJNI.btVector3_getX(swigCPtr, this);
  }

  public float getY() {
    return LinearMathJNI.btVector3_getY(swigCPtr, this);
  }

  public float getZ() {
    return LinearMathJNI.btVector3_getZ(swigCPtr, this);
  }

  public void setX(float _x) {
    LinearMathJNI.btVector3_setX(swigCPtr, this, _x);
  }

  public void setY(float _y) {
    LinearMathJNI.btVector3_setY(swigCPtr, this, _y);
  }

  public void setZ(float _z) {
    LinearMathJNI.btVector3_setZ(swigCPtr, this, _z);
  }

  public void setW(float _w) {
    LinearMathJNI.btVector3_setW(swigCPtr, this, _w);
  }

  public float x() {
    return LinearMathJNI.btVector3_x(swigCPtr, this);
  }

  public float y() {
    return LinearMathJNI.btVector3_y(swigCPtr, this);
  }

  public float z() {
    return LinearMathJNI.btVector3_z(swigCPtr, this);
  }

  public float w() {
    return LinearMathJNI.btVector3_w(swigCPtr, this);
  }

  public java.nio.FloatBuffer operatorbtScalarPtr() {
    return LinearMathJNI.btVector3_operatorbtScalarPtr(swigCPtr, this);
}

  public java.nio.FloatBuffer operatorbtConstScalarPtr() {
    return LinearMathJNI.btVector3_operatorbtConstScalarPtr(swigCPtr, this);
}

  public boolean operatorEqualTo(Vector3 other) {
    return LinearMathJNI.btVector3_operatorEqualTo(swigCPtr, this, other);
  }

  public boolean operatorNotEqualTo(Vector3 other) {
    return LinearMathJNI.btVector3_operatorNotEqualTo(swigCPtr, this, other);
  }

  public void setMax(Vector3 other) {
    LinearMathJNI.btVector3_setMax(swigCPtr, this, other);
  }

  public void setMin(Vector3 other) {
    LinearMathJNI.btVector3_setMin(swigCPtr, this, other);
  }

  public void setValue(float _x, float _y, float _z) {
    LinearMathJNI.btVector3_setValue(swigCPtr, this, _x, _y, _z);
  }

  public void getSkewSymmetricMatrix(btVector3 v0, btVector3 v1, btVector3 v2) {
    LinearMathJNI.btVector3_getSkewSymmetricMatrix(swigCPtr, this, btVector3.getCPtr(v0), v0, btVector3.getCPtr(v1), v1, btVector3.getCPtr(v2), v2);
  }

  public void setZero() {
    LinearMathJNI.btVector3_setZero(swigCPtr, this);
  }

  public boolean isZero() {
    return LinearMathJNI.btVector3_isZero(swigCPtr, this);
  }

  public boolean fuzzyZero() {
    return LinearMathJNI.btVector3_fuzzyZero(swigCPtr, this);
  }

  public void serialize(btVector3FloatData dataOut) {
    LinearMathJNI.btVector3_serialize(swigCPtr, this, btVector3FloatData.getCPtr(dataOut), dataOut);
  }

  public void deSerialize(btVector3FloatData dataIn) {
    LinearMathJNI.btVector3_deSerialize(swigCPtr, this, btVector3FloatData.getCPtr(dataIn), dataIn);
  }

  public void serializeFloat(btVector3FloatData dataOut) {
    LinearMathJNI.btVector3_serializeFloat(swigCPtr, this, btVector3FloatData.getCPtr(dataOut), dataOut);
  }

  public void deSerializeFloat(btVector3FloatData dataIn) {
    LinearMathJNI.btVector3_deSerializeFloat(swigCPtr, this, btVector3FloatData.getCPtr(dataIn), dataIn);
  }

  public void serializeDouble(btVector3DoubleData dataOut) {
    LinearMathJNI.btVector3_serializeDouble(swigCPtr, this, btVector3DoubleData.getCPtr(dataOut), dataOut);
  }

  public void deSerializeDouble(btVector3DoubleData dataIn) {
    LinearMathJNI.btVector3_deSerializeDouble(swigCPtr, this, btVector3DoubleData.getCPtr(dataIn), dataIn);
  }

  public int maxDot(btVector3 array, int array_count, SWIGTYPE_p_float dotOut) {
    return LinearMathJNI.btVector3_maxDot(swigCPtr, this, btVector3.getCPtr(array), array, array_count, SWIGTYPE_p_float.getCPtr(dotOut));
  }

  public int minDot(btVector3 array, int array_count, SWIGTYPE_p_float dotOut) {
    return LinearMathJNI.btVector3_minDot(swigCPtr, this, btVector3.getCPtr(array), array, array_count, SWIGTYPE_p_float.getCPtr(dotOut));
  }

  public Vector3 dot3(Vector3 v0, Vector3 v1, Vector3 v2) {
	return LinearMathJNI.btVector3_dot3(swigCPtr, this, v0, v1, v2);
}

}
