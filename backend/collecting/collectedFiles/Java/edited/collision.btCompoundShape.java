

package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btCompoundShape extends btCollisionShape {
	private long swigCPtr;
	
	protected btCompoundShape(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, CollisionJNI.btCompoundShape_SWIGUpcast(cPtr), cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	
	public btCompoundShape(long cPtr, boolean cMemoryOwn) {
		this("btCompoundShape", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(CollisionJNI.btCompoundShape_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
	}
	
	public static long getCPtr(btCompoundShape obj) {
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
				CollisionJNI.delete_btCompoundShape(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

	protected Array<btCollisionShape> children = new Array<btCollisionShape>();
	
	public void addChildShape(Matrix4 localTransform, btCollisionShape shape) {
		internalAddChildShape(localTransform, shape);
		children.add(shape);
		shape.obtain();
	}
	
	public void removeChildShape(btCollisionShape shape) {
		internalRemoveChildShape(shape);
		final int idx = children.indexOf(shape, false);
		if (idx >= 0)
			children.removeIndex(idx).release();
	}
	
	public void removeChildShapeByIndex(int index) {
		internalRemoveChildShapeByIndex(index);
		children.removeIndex(index).release();
	}
	
	public btCollisionShape getChildShape(int index) {
		return children.get(index);
	}
	
	@Override
	public void dispose() {
		for (btCollisionShape child : children)
			child.release();
		children.clear();
		super.dispose();
	}

  public long operatorNew(long sizeInBytes) {
    return CollisionJNI.btCompoundShape_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDelete(long ptr) {
    CollisionJNI.btCompoundShape_operatorDelete__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNew(long arg0, long ptr) {
    return CollisionJNI.btCompoundShape_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDelete(long arg0, long arg1) {
    CollisionJNI.btCompoundShape_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public long operatorNewArray(long sizeInBytes) {
    return CollisionJNI.btCompoundShape_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDeleteArray(long ptr) {
    CollisionJNI.btCompoundShape_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNewArray(long arg0, long ptr) {
    return CollisionJNI.btCompoundShape_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDeleteArray(long arg0, long arg1) {
    CollisionJNI.btCompoundShape_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public btCompoundShape(boolean enableDynamicAabbTree, int initialChildCapacity) {
    this(CollisionJNI.new_btCompoundShape__SWIG_0(enableDynamicAabbTree, initialChildCapacity), true);
  }

  public btCompoundShape(boolean enableDynamicAabbTree) {
    this(CollisionJNI.new_btCompoundShape__SWIG_1(enableDynamicAabbTree), true);
  }

  public btCompoundShape() {
    this(CollisionJNI.new_btCompoundShape__SWIG_2(), true);
  }

  private void internalAddChildShape(Matrix4 localTransform, btCollisionShape shape) {
    CollisionJNI.btCompoundShape_internalAddChildShape(swigCPtr, this, localTransform, btCollisionShape.getCPtr(shape), shape);
  }

  private void internalRemoveChildShape(btCollisionShape shape) {
    CollisionJNI.btCompoundShape_internalRemoveChildShape(swigCPtr, this, btCollisionShape.getCPtr(shape), shape);
  }

  private void internalRemoveChildShapeByIndex(int childShapeindex) {
    CollisionJNI.btCompoundShape_internalRemoveChildShapeByIndex(swigCPtr, this, childShapeindex);
  }

  public int getNumChildShapes() {
    return CollisionJNI.btCompoundShape_getNumChildShapes(swigCPtr, this);
  }

  public Matrix4 getChildTransform(int index) {
	return CollisionJNI.btCompoundShape_getChildTransform(swigCPtr, this, index);
}

  public Matrix4 getChildTransformConst(int index) {
	return CollisionJNI.btCompoundShape_getChildTransformConst(swigCPtr, this, index);
}

  public void updateChildTransform(int childIndex, Matrix4 newChildTransform, boolean shouldRecalculateLocalAabb) {
    CollisionJNI.btCompoundShape_updateChildTransform__SWIG_0(swigCPtr, this, childIndex, newChildTransform, shouldRecalculateLocalAabb);
  }

  public void updateChildTransform(int childIndex, Matrix4 newChildTransform) {
    CollisionJNI.btCompoundShape_updateChildTransform__SWIG_1(swigCPtr, this, childIndex, newChildTransform);
  }

  public btCompoundShapeChild getChildList() {
    long cPtr = CollisionJNI.btCompoundShape_getChildList(swigCPtr, this);
    return (cPtr == 0) ? null : new btCompoundShapeChild(cPtr, false);
  }

  public void recalculateLocalAabb() {
    CollisionJNI.btCompoundShape_recalculateLocalAabb(swigCPtr, this);
  }

  public btDbvt getDynamicAabbTreeConst() {
    long cPtr = CollisionJNI.btCompoundShape_getDynamicAabbTreeConst(swigCPtr, this);
    return (cPtr == 0) ? null : new btDbvt(cPtr, false);
  }

  public btDbvt getDynamicAabbTree() {
    long cPtr = CollisionJNI.btCompoundShape_getDynamicAabbTree(swigCPtr, this);
    return (cPtr == 0) ? null : new btDbvt(cPtr, false);
  }

  public void createAabbTreeFromChildren() {
    CollisionJNI.btCompoundShape_createAabbTreeFromChildren(swigCPtr, this);
  }

  public void calculatePrincipalAxisTransform(java.nio.FloatBuffer masses, Matrix4 principal, Vector3 inertia) {
    assert masses.isDirect() : "Buffer must be allocated direct.";
    {
      CollisionJNI.btCompoundShape_calculatePrincipalAxisTransform(swigCPtr, this, masses, principal, inertia);
    }
  }

  public int getUpdateRevision() {
    return CollisionJNI.btCompoundShape_getUpdateRevision(swigCPtr, this);
  }

}
