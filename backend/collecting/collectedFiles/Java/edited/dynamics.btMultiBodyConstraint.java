

package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btMultiBodyConstraint extends BulletBase {
	private long swigCPtr;
	
	protected btMultiBodyConstraint(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public btMultiBodyConstraint(long cPtr, boolean cMemoryOwn) {
		this("btMultiBodyConstraint", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(btMultiBodyConstraint obj) {
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
				DynamicsJNI.delete_btMultiBodyConstraint(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public long operatorNew(long sizeInBytes) {
    return DynamicsJNI.btMultiBodyConstraint_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDelete(long ptr) {
    DynamicsJNI.btMultiBodyConstraint_operatorDelete__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNew(long arg0, long ptr) {
    return DynamicsJNI.btMultiBodyConstraint_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDelete(long arg0, long arg1) {
    DynamicsJNI.btMultiBodyConstraint_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public long operatorNewArray(long sizeInBytes) {
    return DynamicsJNI.btMultiBodyConstraint_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDeleteArray(long ptr) {
    DynamicsJNI.btMultiBodyConstraint_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNewArray(long arg0, long ptr) {
    return DynamicsJNI.btMultiBodyConstraint_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDeleteArray(long arg0, long arg1) {
    DynamicsJNI.btMultiBodyConstraint_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public void updateJacobianSizes() {
    DynamicsJNI.btMultiBodyConstraint_updateJacobianSizes(swigCPtr, this);
  }

  public void allocateJacobiansMultiDof() {
    DynamicsJNI.btMultiBodyConstraint_allocateJacobiansMultiDof(swigCPtr, this);
  }

  public void setFrameInB(Matrix3 frameInB) {
    DynamicsJNI.btMultiBodyConstraint_setFrameInB(swigCPtr, this, frameInB);
  }

  public void setPivotInB(Vector3 pivotInB) {
    DynamicsJNI.btMultiBodyConstraint_setPivotInB(swigCPtr, this, pivotInB);
  }

  public void finalizeMultiDof() {
    DynamicsJNI.btMultiBodyConstraint_finalizeMultiDof(swigCPtr, this);
  }

  public int getIslandIdA() {
    return DynamicsJNI.btMultiBodyConstraint_getIslandIdA(swigCPtr, this);
  }

  public int getIslandIdB() {
    return DynamicsJNI.btMultiBodyConstraint_getIslandIdB(swigCPtr, this);
  }

  public void createConstraintRows(SWIGTYPE_p_btAlignedObjectArrayT_btMultiBodySolverConstraint_t constraintRows, btMultiBodyJacobianData data, btContactSolverInfo infoGlobal) {
    DynamicsJNI.btMultiBodyConstraint_createConstraintRows(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btMultiBodySolverConstraint_t.getCPtr(constraintRows), btMultiBodyJacobianData.getCPtr(data), data, btContactSolverInfo.getCPtr(infoGlobal), infoGlobal);
  }

  public int getNumRows() {
    return DynamicsJNI.btMultiBodyConstraint_getNumRows(swigCPtr, this);
  }

  public btMultiBody getMultiBodyA() {
    long cPtr = DynamicsJNI.btMultiBodyConstraint_getMultiBodyA(swigCPtr, this);
    return (cPtr == 0) ? null : new btMultiBody(cPtr, false);
  }

  public btMultiBody getMultiBodyB() {
    long cPtr = DynamicsJNI.btMultiBodyConstraint_getMultiBodyB(swigCPtr, this);
    return (cPtr == 0) ? null : new btMultiBody(cPtr, false);
  }

  public void internalSetAppliedImpulse(int dof, float appliedImpulse) {
    DynamicsJNI.btMultiBodyConstraint_internalSetAppliedImpulse(swigCPtr, this, dof, appliedImpulse);
  }

  public float getAppliedImpulse(int dof) {
    return DynamicsJNI.btMultiBodyConstraint_getAppliedImpulse(swigCPtr, this, dof);
  }

  public float getPosition(int row) {
    return DynamicsJNI.btMultiBodyConstraint_getPosition(swigCPtr, this, row);
  }

  public void setPosition(int row, float pos) {
    DynamicsJNI.btMultiBodyConstraint_setPosition(swigCPtr, this, row, pos);
  }

  public boolean isUnilateral() {
    return DynamicsJNI.btMultiBodyConstraint_isUnilateral(swigCPtr, this);
  }

  public java.nio.FloatBuffer jacobianA(int row) {
    return DynamicsJNI.btMultiBodyConstraint_jacobianA(swigCPtr, this, row);
}

  public java.nio.FloatBuffer jacobianAConst(int row) {
    return DynamicsJNI.btMultiBodyConstraint_jacobianAConst(swigCPtr, this, row);
}

  public java.nio.FloatBuffer jacobianB(int row) {
    return DynamicsJNI.btMultiBodyConstraint_jacobianB(swigCPtr, this, row);
}

  public java.nio.FloatBuffer jacobianBConst(int row) {
    return DynamicsJNI.btMultiBodyConstraint_jacobianBConst(swigCPtr, this, row);
}

  public float getMaxAppliedImpulse() {
    return DynamicsJNI.btMultiBodyConstraint_getMaxAppliedImpulse(swigCPtr, this);
  }

  public void setMaxAppliedImpulse(float maxImp) {
    DynamicsJNI.btMultiBodyConstraint_setMaxAppliedImpulse(swigCPtr, this, maxImp);
  }

  public void debugDraw(btIDebugDraw drawer) {
    DynamicsJNI.btMultiBodyConstraint_debugDraw(swigCPtr, this, btIDebugDraw.getCPtr(drawer), drawer);
  }

  public void setGearRatio(float ratio) {
    DynamicsJNI.btMultiBodyConstraint_setGearRatio(swigCPtr, this, ratio);
  }

  public void setGearAuxLink(int gearAuxLink) {
    DynamicsJNI.btMultiBodyConstraint_setGearAuxLink(swigCPtr, this, gearAuxLink);
  }

  public void setRelativePositionTarget(float relPosTarget) {
    DynamicsJNI.btMultiBodyConstraint_setRelativePositionTarget(swigCPtr, this, relPosTarget);
  }

  public void setErp(float erp) {
    DynamicsJNI.btMultiBodyConstraint_setErp(swigCPtr, this, erp);
  }

}
