

package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btHeightfieldTerrainShape extends btConcaveShape {
	private long swigCPtr;
	
	protected btHeightfieldTerrainShape(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, CollisionJNI.btHeightfieldTerrainShape_SWIGUpcast(cPtr), cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	
	public btHeightfieldTerrainShape(long cPtr, boolean cMemoryOwn) {
		this("btHeightfieldTerrainShape", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(CollisionJNI.btHeightfieldTerrainShape_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
	}
	
	public static long getCPtr(btHeightfieldTerrainShape obj) {
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
				CollisionJNI.delete_btHeightfieldTerrainShape(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public long operatorNew(long sizeInBytes) {
    return CollisionJNI.btHeightfieldTerrainShape_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDelete(long ptr) {
    CollisionJNI.btHeightfieldTerrainShape_operatorDelete__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNew(long arg0, long ptr) {
    return CollisionJNI.btHeightfieldTerrainShape_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDelete(long arg0, long arg1) {
    CollisionJNI.btHeightfieldTerrainShape_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public long operatorNewArray(long sizeInBytes) {
    return CollisionJNI.btHeightfieldTerrainShape_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDeleteArray(long ptr) {
    CollisionJNI.btHeightfieldTerrainShape_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNewArray(long arg0, long ptr) {
    return CollisionJNI.btHeightfieldTerrainShape_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDeleteArray(long arg0, long arg1) {
    CollisionJNI.btHeightfieldTerrainShape_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public void setUseDiamondSubdivision(boolean useDiamondSubdivision) {
    CollisionJNI.btHeightfieldTerrainShape_setUseDiamondSubdivision__SWIG_0(swigCPtr, this, useDiamondSubdivision);
  }

  public void setUseDiamondSubdivision() {
    CollisionJNI.btHeightfieldTerrainShape_setUseDiamondSubdivision__SWIG_1(swigCPtr, this);
  }

  public void setUseZigzagSubdivision(boolean useZigzagSubdivision) {
    CollisionJNI.btHeightfieldTerrainShape_setUseZigzagSubdivision__SWIG_0(swigCPtr, this, useZigzagSubdivision);
  }

  public void setUseZigzagSubdivision() {
    CollisionJNI.btHeightfieldTerrainShape_setUseZigzagSubdivision__SWIG_1(swigCPtr, this);
  }

  static private long SwigConstructbtHeightfieldTerrainShape(int heightStickWidth, int heightStickLength, java.nio.FloatBuffer heightfieldData, float heightScale, float minHeight, float maxHeight, int upAxis, boolean flipQuadEdges) {
    assert heightfieldData.isDirect() : "Buffer must be allocated direct.";
    return CollisionJNI.new_btHeightfieldTerrainShape__SWIG_0(heightStickWidth, heightStickLength, heightfieldData, heightScale, minHeight, maxHeight, upAxis, flipQuadEdges);
  }

  public btHeightfieldTerrainShape(int heightStickWidth, int heightStickLength, java.nio.FloatBuffer heightfieldData, float heightScale, float minHeight, float maxHeight, int upAxis, boolean flipQuadEdges) {
    this(btHeightfieldTerrainShape.SwigConstructbtHeightfieldTerrainShape(heightStickWidth, heightStickLength, heightfieldData, heightScale, minHeight, maxHeight, upAxis, flipQuadEdges), true);
  }

  static private long SwigConstructbtHeightfieldTerrainShape(int heightStickWidth, int heightStickLength, java.nio.ShortBuffer heightfieldData, float heightScale, float minHeight, float maxHeight, int upAxis, boolean flipQuadEdges) {
    assert heightfieldData.isDirect() : "Buffer must be allocated direct.";
    return CollisionJNI.new_btHeightfieldTerrainShape__SWIG_1(heightStickWidth, heightStickLength, heightfieldData, heightScale, minHeight, maxHeight, upAxis, flipQuadEdges);
  }

  public btHeightfieldTerrainShape(int heightStickWidth, int heightStickLength, java.nio.ShortBuffer heightfieldData, float heightScale, float minHeight, float maxHeight, int upAxis, boolean flipQuadEdges) {
    this(btHeightfieldTerrainShape.SwigConstructbtHeightfieldTerrainShape(heightStickWidth, heightStickLength, heightfieldData, heightScale, minHeight, maxHeight, upAxis, flipQuadEdges), true);
  }

}
