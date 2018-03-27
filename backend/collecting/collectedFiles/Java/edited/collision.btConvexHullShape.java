

package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btConvexHullShape extends btPolyhedralConvexAabbCachingShape {
	private long swigCPtr;
	
	protected btConvexHullShape(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, CollisionJNI.btConvexHullShape_SWIGUpcast(cPtr), cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	
	public btConvexHullShape(long cPtr, boolean cMemoryOwn) {
		this("btConvexHullShape", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(CollisionJNI.btConvexHullShape_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
	}
	
	public static long getCPtr(btConvexHullShape obj) {
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
				CollisionJNI.delete_btConvexHullShape(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public long operatorNew(long sizeInBytes) {
    return CollisionJNI.btConvexHullShape_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDelete(long ptr) {
    CollisionJNI.btConvexHullShape_operatorDelete__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNew(long arg0, long ptr) {
    return CollisionJNI.btConvexHullShape_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDelete(long arg0, long arg1) {
    CollisionJNI.btConvexHullShape_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public long operatorNewArray(long sizeInBytes) {
    return CollisionJNI.btConvexHullShape_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDeleteArray(long ptr) {
    CollisionJNI.btConvexHullShape_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNewArray(long arg0, long ptr) {
    return CollisionJNI.btConvexHullShape_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDeleteArray(long arg0, long arg1) {
    CollisionJNI.btConvexHullShape_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  static private long SwigConstructbtConvexHullShape(java.nio.FloatBuffer points, int numPoints, int stride) {
    assert points.isDirect() : "Buffer must be allocated direct.";
    return CollisionJNI.new_btConvexHullShape__SWIG_0(points, numPoints, stride);
  }

  public btConvexHullShape(java.nio.FloatBuffer points, int numPoints, int stride) {
    this(btConvexHullShape.SwigConstructbtConvexHullShape(points, numPoints, stride), true);
  }

  static private long SwigConstructbtConvexHullShape(java.nio.FloatBuffer points, int numPoints) {
    assert points.isDirect() : "Buffer must be allocated direct.";
    return CollisionJNI.new_btConvexHullShape__SWIG_1(points, numPoints);
  }

  public btConvexHullShape(java.nio.FloatBuffer points, int numPoints) {
    this(btConvexHullShape.SwigConstructbtConvexHullShape(points, numPoints), true);
  }

  static private long SwigConstructbtConvexHullShape(java.nio.FloatBuffer points) {
    assert points.isDirect() : "Buffer must be allocated direct.";
    return CollisionJNI.new_btConvexHullShape__SWIG_2(points);
  }

  public btConvexHullShape(java.nio.FloatBuffer points) {
    this(btConvexHullShape.SwigConstructbtConvexHullShape(points), true);
  }

  public btConvexHullShape() {
    this(CollisionJNI.new_btConvexHullShape__SWIG_3(), true);
  }

  public void addPoint(Vector3 point, boolean recalculateLocalAabb) {
    CollisionJNI.btConvexHullShape_addPoint__SWIG_0(swigCPtr, this, point, recalculateLocalAabb);
  }

  public void addPoint(Vector3 point) {
    CollisionJNI.btConvexHullShape_addPoint__SWIG_1(swigCPtr, this, point);
  }

  public btVector3 getUnscaledPoints() {
    long cPtr = CollisionJNI.btConvexHullShape_getUnscaledPoints(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public btVector3 getUnscaledPointsConst() {
    long cPtr = CollisionJNI.btConvexHullShape_getUnscaledPointsConst(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public btVector3 getPoints() {
    long cPtr = CollisionJNI.btConvexHullShape_getPoints(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void optimizeConvexHull() {
    CollisionJNI.btConvexHullShape_optimizeConvexHull(swigCPtr, this);
  }

  public Vector3 getScaledPoint(int i) {
	return CollisionJNI.btConvexHullShape_getScaledPoint(swigCPtr, this, i);
}

  public int getNumPoints() {
    return CollisionJNI.btConvexHullShape_getNumPoints(swigCPtr, this);
  }

  public btConvexHullShape(btShapeHull hull) {
    this(CollisionJNI.new_btConvexHullShape__SWIG_4(btShapeHull.getCPtr(hull), hull), true);
  }

}
