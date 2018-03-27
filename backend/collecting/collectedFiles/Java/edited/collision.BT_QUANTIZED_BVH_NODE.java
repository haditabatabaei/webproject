

package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class BT_QUANTIZED_BVH_NODE extends BulletBase {
	private long swigCPtr;
	
	protected BT_QUANTIZED_BVH_NODE(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public BT_QUANTIZED_BVH_NODE(long cPtr, boolean cMemoryOwn) {
		this("BT_QUANTIZED_BVH_NODE", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(BT_QUANTIZED_BVH_NODE obj) {
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
				CollisionJNI.delete_BT_QUANTIZED_BVH_NODE(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public void setQuantizedAabbMin(int[] value) {
    CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMin_set(swigCPtr, this, value);
  }

  public int[] getQuantizedAabbMin() {
    return CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMin_get(swigCPtr, this);
}

  public void setQuantizedAabbMax(int[] value) {
    CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMax_set(swigCPtr, this, value);
  }

  public int[] getQuantizedAabbMax() {
    return CollisionJNI.BT_QUANTIZED_BVH_NODE_quantizedAabbMax_get(swigCPtr, this);
}

  public void setEscapeIndexOrDataIndex(int value) {
    CollisionJNI.BT_QUANTIZED_BVH_NODE_escapeIndexOrDataIndex_set(swigCPtr, this, value);
  }

  public int getEscapeIndexOrDataIndex() {
    return CollisionJNI.BT_QUANTIZED_BVH_NODE_escapeIndexOrDataIndex_get(swigCPtr, this);
  }

  public BT_QUANTIZED_BVH_NODE() {
    this(CollisionJNI.new_BT_QUANTIZED_BVH_NODE(), true);
  }

  public boolean isLeafNode() {
    return CollisionJNI.BT_QUANTIZED_BVH_NODE_isLeafNode(swigCPtr, this);
  }

  public int getEscapeIndex() {
    return CollisionJNI.BT_QUANTIZED_BVH_NODE_getEscapeIndex(swigCPtr, this);
  }

  public void setEscapeIndex(int index) {
    CollisionJNI.BT_QUANTIZED_BVH_NODE_setEscapeIndex(swigCPtr, this, index);
  }

  public int getDataIndex() {
    return CollisionJNI.BT_QUANTIZED_BVH_NODE_getDataIndex(swigCPtr, this);
  }

  public void setDataIndex(int index) {
    CollisionJNI.BT_QUANTIZED_BVH_NODE_setDataIndex(swigCPtr, this, index);
  }

  public boolean testQuantizedBoxOverlapp(java.nio.IntBuffer quantizedMin, java.nio.IntBuffer quantizedMax) {
    assert quantizedMin.isDirect() : "Buffer must be allocated direct.";
    assert quantizedMax.isDirect() : "Buffer must be allocated direct.";
    {
      return CollisionJNI.BT_QUANTIZED_BVH_NODE_testQuantizedBoxOverlapp(swigCPtr, this, quantizedMin, quantizedMax);
    }
  }

}
