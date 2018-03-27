

package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btQuantizedBvh extends BulletBase {
	private long swigCPtr;
	
	protected btQuantizedBvh(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public btQuantizedBvh(long cPtr, boolean cMemoryOwn) {
		this("btQuantizedBvh", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(btQuantizedBvh obj) {
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
				CollisionJNI.delete_btQuantizedBvh(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public long operatorNew(long sizeInBytes) {
    return CollisionJNI.btQuantizedBvh_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDelete(long ptr) {
    CollisionJNI.btQuantizedBvh_operatorDelete__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNew(long arg0, long ptr) {
    return CollisionJNI.btQuantizedBvh_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDelete(long arg0, long arg1) {
    CollisionJNI.btQuantizedBvh_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public long operatorNewArray(long sizeInBytes) {
    return CollisionJNI.btQuantizedBvh_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDeleteArray(long ptr) {
    CollisionJNI.btQuantizedBvh_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNewArray(long arg0, long ptr) {
    return CollisionJNI.btQuantizedBvh_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDeleteArray(long arg0, long arg1) {
    CollisionJNI.btQuantizedBvh_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public btQuantizedBvh() {
    this(CollisionJNI.new_btQuantizedBvh(), true);
  }

  public void setQuantizationValues(Vector3 bvhAabbMin, Vector3 bvhAabbMax, float quantizationMargin) {
    CollisionJNI.btQuantizedBvh_setQuantizationValues__SWIG_0(swigCPtr, this, bvhAabbMin, bvhAabbMax, quantizationMargin);
  }

  public void setQuantizationValues(Vector3 bvhAabbMin, Vector3 bvhAabbMax) {
    CollisionJNI.btQuantizedBvh_setQuantizationValues__SWIG_1(swigCPtr, this, bvhAabbMin, bvhAabbMax);
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t getLeafNodeArray() {
    return new SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t(CollisionJNI.btQuantizedBvh_getLeafNodeArray(swigCPtr, this), false);
  }

  public void buildInternal() {
    CollisionJNI.btQuantizedBvh_buildInternal(swigCPtr, this);
  }

  public void reportAabbOverlappingNodex(btNodeOverlapCallback nodeCallback, Vector3 aabbMin, Vector3 aabbMax) {
    CollisionJNI.btQuantizedBvh_reportAabbOverlappingNodex(swigCPtr, this, btNodeOverlapCallback.getCPtr(nodeCallback), nodeCallback, aabbMin, aabbMax);
  }

  public void reportRayOverlappingNodex(btNodeOverlapCallback nodeCallback, Vector3 raySource, Vector3 rayTarget) {
    CollisionJNI.btQuantizedBvh_reportRayOverlappingNodex(swigCPtr, this, btNodeOverlapCallback.getCPtr(nodeCallback), nodeCallback, raySource, rayTarget);
  }

  public void reportBoxCastOverlappingNodex(btNodeOverlapCallback nodeCallback, Vector3 raySource, Vector3 rayTarget, Vector3 aabbMin, Vector3 aabbMax) {
    CollisionJNI.btQuantizedBvh_reportBoxCastOverlappingNodex(swigCPtr, this, btNodeOverlapCallback.getCPtr(nodeCallback), nodeCallback, raySource, rayTarget, aabbMin, aabbMax);
  }

  public void quantize(java.nio.IntBuffer out, Vector3 point, int isMax) {
    assert out.isDirect() : "Buffer must be allocated direct.";
    {
      CollisionJNI.btQuantizedBvh_quantize(swigCPtr, this, out, point, isMax);
    }
  }

  public void quantizeWithClamp(java.nio.IntBuffer out, Vector3 point2, int isMax) {
    assert out.isDirect() : "Buffer must be allocated direct.";
    {
      CollisionJNI.btQuantizedBvh_quantizeWithClamp(swigCPtr, this, out, point2, isMax);
    }
  }

  public Vector3 unQuantize(java.nio.IntBuffer vecIn) {
    assert vecIn.isDirect() : "Buffer must be allocated direct.";
    {
	return CollisionJNI.btQuantizedBvh_unQuantize(swigCPtr, this, vecIn);
}
  }

  public void setTraversalMode(int traversalMode) {
    CollisionJNI.btQuantizedBvh_setTraversalMode(swigCPtr, this, traversalMode);
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t getQuantizedNodeArray() {
    return new SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t(CollisionJNI.btQuantizedBvh_getQuantizedNodeArray(swigCPtr, this), false);
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btBvhSubtreeInfo_t getSubtreeInfoArray() {
    return new SWIGTYPE_p_btAlignedObjectArrayT_btBvhSubtreeInfo_t(CollisionJNI.btQuantizedBvh_getSubtreeInfoArray(swigCPtr, this), false);
  }

  public long calculateSerializeBufferSize() {
    return CollisionJNI.btQuantizedBvh_calculateSerializeBufferSize(swigCPtr, this);
  }

  public boolean serialize(long o_alignedDataBuffer, long i_dataBufferSize, boolean i_swapEndian) {
    return CollisionJNI.btQuantizedBvh_serialize__SWIG_0(swigCPtr, this, o_alignedDataBuffer, i_dataBufferSize, i_swapEndian);
  }

  public static btQuantizedBvh deSerializeInPlace(long i_alignedDataBuffer, long i_dataBufferSize, boolean i_swapEndian) {
    long cPtr = CollisionJNI.btQuantizedBvh_deSerializeInPlace(i_alignedDataBuffer, i_dataBufferSize, i_swapEndian);
    return (cPtr == 0) ? null : new btQuantizedBvh(cPtr, false);
  }

  public static long getAlignmentSerializationPadding() {
    return CollisionJNI.btQuantizedBvh_getAlignmentSerializationPadding();
  }

  public int calculateSerializeBufferSizeNew() {
    return CollisionJNI.btQuantizedBvh_calculateSerializeBufferSizeNew(swigCPtr, this);
  }

  public String serialize(long dataBuffer, btSerializer serializer) {
    return CollisionJNI.btQuantizedBvh_serialize__SWIG_1(swigCPtr, this, dataBuffer, btSerializer.getCPtr(serializer), serializer);
  }

  public void deSerializeFloat(btQuantizedBvhFloatData quantizedBvhFloatData) {
    CollisionJNI.btQuantizedBvh_deSerializeFloat(swigCPtr, this, btQuantizedBvhFloatData.getCPtr(quantizedBvhFloatData), quantizedBvhFloatData);
  }

  public void deSerializeDouble(btQuantizedBvhDoubleData quantizedBvhDoubleData) {
    CollisionJNI.btQuantizedBvh_deSerializeDouble(swigCPtr, this, btQuantizedBvhDoubleData.getCPtr(quantizedBvhDoubleData), quantizedBvhDoubleData);
  }

  public boolean isQuantized() {
    return CollisionJNI.btQuantizedBvh_isQuantized(swigCPtr, this);
  }

  public final static class btTraversalMode {
    public final static int TRAVERSAL_STACKLESS = 0;
    public final static int TRAVERSAL_STACKLESS_CACHE_FRIENDLY = TRAVERSAL_STACKLESS + 1;
    public final static int TRAVERSAL_RECURSIVE = TRAVERSAL_STACKLESS_CACHE_FRIENDLY + 1;
  }

}
