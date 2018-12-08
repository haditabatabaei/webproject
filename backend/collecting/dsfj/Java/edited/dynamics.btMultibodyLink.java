

package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btMultibodyLink extends BulletBase {
	private long swigCPtr;
	
	protected btMultibodyLink(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public btMultibodyLink(long cPtr, boolean cMemoryOwn) {
		this("btMultibodyLink", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(btMultibodyLink obj) {
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
				DynamicsJNI.delete_btMultibodyLink(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public long operatorNew(long sizeInBytes) {
    return DynamicsJNI.btMultibodyLink_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDelete(long ptr) {
    DynamicsJNI.btMultibodyLink_operatorDelete__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNew(long arg0, long ptr) {
    return DynamicsJNI.btMultibodyLink_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDelete(long arg0, long arg1) {
    DynamicsJNI.btMultibodyLink_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public long operatorNewArray(long sizeInBytes) {
    return DynamicsJNI.btMultibodyLink_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
  }

  public void operatorDeleteArray(long ptr) {
    DynamicsJNI.btMultibodyLink_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
  }

  public long operatorNewArray(long arg0, long ptr) {
    return DynamicsJNI.btMultibodyLink_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
  }

  public void operatorDeleteArray(long arg0, long arg1) {
    DynamicsJNI.btMultibodyLink_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
  }

  public void setMass(float value) {
    DynamicsJNI.btMultibodyLink_mass_set(swigCPtr, this, value);
  }

  public float getMass() {
    return DynamicsJNI.btMultibodyLink_mass_get(swigCPtr, this);
  }

  public void setInertiaLocal(btVector3 value) {
    DynamicsJNI.btMultibodyLink_inertiaLocal_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getInertiaLocal() {
    long cPtr = DynamicsJNI.btMultibodyLink_inertiaLocal_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setParent(int value) {
    DynamicsJNI.btMultibodyLink_parent_set(swigCPtr, this, value);
  }

  public int getParent() {
    return DynamicsJNI.btMultibodyLink_parent_get(swigCPtr, this);
  }

  public void setZeroRotParentToThis(btQuaternion value) {
    DynamicsJNI.btMultibodyLink_zeroRotParentToThis_set(swigCPtr, this, btQuaternion.getCPtr(value), value);
  }

  public btQuaternion getZeroRotParentToThis() {
    long cPtr = DynamicsJNI.btMultibodyLink_zeroRotParentToThis_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btQuaternion(cPtr, false);
  }

  public void setDVector(btVector3 value) {
    DynamicsJNI.btMultibodyLink_dVector_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getDVector() {
    long cPtr = DynamicsJNI.btMultibodyLink_dVector_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setEVector(btVector3 value) {
    DynamicsJNI.btMultibodyLink_eVector_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getEVector() {
    long cPtr = DynamicsJNI.btMultibodyLink_eVector_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setAbsFrameTotVelocity(btSpatialMotionVector value) {
    DynamicsJNI.btMultibodyLink_absFrameTotVelocity_set(swigCPtr, this, btSpatialMotionVector.getCPtr(value), value);
  }

  public btSpatialMotionVector getAbsFrameTotVelocity() {
    long cPtr = DynamicsJNI.btMultibodyLink_absFrameTotVelocity_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSpatialMotionVector(cPtr, false);
  }

  public void setAbsFrameLocVelocity(btSpatialMotionVector value) {
    DynamicsJNI.btMultibodyLink_absFrameLocVelocity_set(swigCPtr, this, btSpatialMotionVector.getCPtr(value), value);
  }

  public btSpatialMotionVector getAbsFrameLocVelocity() {
    long cPtr = DynamicsJNI.btMultibodyLink_absFrameLocVelocity_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSpatialMotionVector(cPtr, false);
  }

  public void setAxes(btSpatialMotionVector value) {
    DynamicsJNI.btMultibodyLink_axes_set(swigCPtr, this, btSpatialMotionVector.getCPtr(value), value);
  }

  public btSpatialMotionVector getAxes() {
    long cPtr = DynamicsJNI.btMultibodyLink_axes_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSpatialMotionVector(cPtr, false);
  }

  public void setAxisTop(int dof, Vector3 axis) {
    DynamicsJNI.btMultibodyLink_setAxisTop__SWIG_0(swigCPtr, this, dof, axis);
  }

  public void setAxisBottom(int dof, Vector3 axis) {
    DynamicsJNI.btMultibodyLink_setAxisBottom__SWIG_0(swigCPtr, this, dof, axis);
  }

  public void setAxisTop(int dof, float x, float y, float z) {
    DynamicsJNI.btMultibodyLink_setAxisTop__SWIG_1(swigCPtr, this, dof, x, y, z);
  }

  public void setAxisBottom(int dof, float x, float y, float z) {
    DynamicsJNI.btMultibodyLink_setAxisBottom__SWIG_1(swigCPtr, this, dof, x, y, z);
  }

  public Vector3 getAxisTop(int dof) {
	return DynamicsJNI.btMultibodyLink_getAxisTop(swigCPtr, this, dof);
}

  public Vector3 getAxisBottom(int dof) {
	return DynamicsJNI.btMultibodyLink_getAxisBottom(swigCPtr, this, dof);
}

  public void setDofOffset(int value) {
    DynamicsJNI.btMultibodyLink_dofOffset_set(swigCPtr, this, value);
  }

  public int getDofOffset() {
    return DynamicsJNI.btMultibodyLink_dofOffset_get(swigCPtr, this);
  }

  public void setCfgOffset(int value) {
    DynamicsJNI.btMultibodyLink_cfgOffset_set(swigCPtr, this, value);
  }

  public int getCfgOffset() {
    return DynamicsJNI.btMultibodyLink_cfgOffset_get(swigCPtr, this);
  }

  public void setCachedRotParentToThis(btQuaternion value) {
    DynamicsJNI.btMultibodyLink_cachedRotParentToThis_set(swigCPtr, this, btQuaternion.getCPtr(value), value);
  }

  public btQuaternion getCachedRotParentToThis() {
    long cPtr = DynamicsJNI.btMultibodyLink_cachedRotParentToThis_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btQuaternion(cPtr, false);
  }

  public void setCachedRVector(btVector3 value) {
    DynamicsJNI.btMultibodyLink_cachedRVector_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getCachedRVector() {
    long cPtr = DynamicsJNI.btMultibodyLink_cachedRVector_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setAppliedForce(btVector3 value) {
    DynamicsJNI.btMultibodyLink_appliedForce_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getAppliedForce() {
    long cPtr = DynamicsJNI.btMultibodyLink_appliedForce_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setAppliedTorque(btVector3 value) {
    DynamicsJNI.btMultibodyLink_appliedTorque_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getAppliedTorque() {
    long cPtr = DynamicsJNI.btMultibodyLink_appliedTorque_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setAppliedConstraintForce(btVector3 value) {
    DynamicsJNI.btMultibodyLink_appliedConstraintForce_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getAppliedConstraintForce() {
    long cPtr = DynamicsJNI.btMultibodyLink_appliedConstraintForce_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setAppliedConstraintTorque(btVector3 value) {
    DynamicsJNI.btMultibodyLink_appliedConstraintTorque_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getAppliedConstraintTorque() {
    long cPtr = DynamicsJNI.btMultibodyLink_appliedConstraintTorque_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setJointPos(float[] value) {
    DynamicsJNI.btMultibodyLink_jointPos_set(swigCPtr, this, value);
  }

  public float[] getJointPos() {
    return DynamicsJNI.btMultibodyLink_jointPos_get(swigCPtr, this);
}

  public void setJointTorque(float[] value) {
    DynamicsJNI.btMultibodyLink_jointTorque_set(swigCPtr, this, value);
  }

  public float[] getJointTorque() {
    return DynamicsJNI.btMultibodyLink_jointTorque_get(swigCPtr, this);
}

  public void setCollider(btMultiBodyLinkCollider value) {
    DynamicsJNI.btMultibodyLink_collider_set(swigCPtr, this, btMultiBodyLinkCollider.getCPtr(value), value);
  }

  public btMultiBodyLinkCollider getCollider() {
    long cPtr = DynamicsJNI.btMultibodyLink_collider_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btMultiBodyLinkCollider(cPtr, false);
  }

  public void setFlags(int value) {
    DynamicsJNI.btMultibodyLink_flags_set(swigCPtr, this, value);
  }

  public int getFlags() {
    return DynamicsJNI.btMultibodyLink_flags_get(swigCPtr, this);
  }

  public void setDofCount(int value) {
    DynamicsJNI.btMultibodyLink_dofCount_set(swigCPtr, this, value);
  }

  public int getDofCount() {
    return DynamicsJNI.btMultibodyLink_dofCount_get(swigCPtr, this);
  }

  public void setPosVarCount(int value) {
    DynamicsJNI.btMultibodyLink_posVarCount_set(swigCPtr, this, value);
  }

  public int getPosVarCount() {
    return DynamicsJNI.btMultibodyLink_posVarCount_get(swigCPtr, this);
  }

  public void setJointType(int value) {
    DynamicsJNI.btMultibodyLink_jointType_set(swigCPtr, this, value);
  }

  public int getJointType() {
    return DynamicsJNI.btMultibodyLink_jointType_get(swigCPtr, this);
  }

  public void setJointFeedback(btMultiBodyJointFeedback value) {
    DynamicsJNI.btMultibodyLink_jointFeedback_set(swigCPtr, this, btMultiBodyJointFeedback.getCPtr(value), value);
  }

  public btMultiBodyJointFeedback getJointFeedback() {
    long cPtr = DynamicsJNI.btMultibodyLink_jointFeedback_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btMultiBodyJointFeedback(cPtr, false);
  }

  public void setCachedWorldTransform(btTransform value) {
    DynamicsJNI.btMultibodyLink_cachedWorldTransform_set(swigCPtr, this, btTransform.getCPtr(value), value);
  }

  public btTransform getCachedWorldTransform() {
    long cPtr = DynamicsJNI.btMultibodyLink_cachedWorldTransform_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btTransform(cPtr, false);
  }

  public String getLinkName() {
    return DynamicsJNI.btMultibodyLink_linkName_get(swigCPtr, this);
  }

  public String getJointName() {
    return DynamicsJNI.btMultibodyLink_jointName_get(swigCPtr, this);
  }

  public void setUserPtr(long value) {
    DynamicsJNI.btMultibodyLink_userPtr_set(swigCPtr, this, value);
  }

  public long getUserPtr() {
    return DynamicsJNI.btMultibodyLink_userPtr_get(swigCPtr, this);
  }

  public void setJointDamping(float value) {
    DynamicsJNI.btMultibodyLink_jointDamping_set(swigCPtr, this, value);
  }

  public float getJointDamping() {
    return DynamicsJNI.btMultibodyLink_jointDamping_get(swigCPtr, this);
  }

  public void setJointFriction(float value) {
    DynamicsJNI.btMultibodyLink_jointFriction_set(swigCPtr, this, value);
  }

  public float getJointFriction() {
    return DynamicsJNI.btMultibodyLink_jointFriction_get(swigCPtr, this);
  }

  public void setJointLowerLimit(float value) {
    DynamicsJNI.btMultibodyLink_jointLowerLimit_set(swigCPtr, this, value);
  }

  public float getJointLowerLimit() {
    return DynamicsJNI.btMultibodyLink_jointLowerLimit_get(swigCPtr, this);
  }

  public void setJointUpperLimit(float value) {
    DynamicsJNI.btMultibodyLink_jointUpperLimit_set(swigCPtr, this, value);
  }

  public float getJointUpperLimit() {
    return DynamicsJNI.btMultibodyLink_jointUpperLimit_get(swigCPtr, this);
  }

  public void setJointMaxForce(float value) {
    DynamicsJNI.btMultibodyLink_jointMaxForce_set(swigCPtr, this, value);
  }

  public float getJointMaxForce() {
    return DynamicsJNI.btMultibodyLink_jointMaxForce_get(swigCPtr, this);
  }

  public void setJointMaxVelocity(float value) {
    DynamicsJNI.btMultibodyLink_jointMaxVelocity_set(swigCPtr, this, value);
  }

  public float getJointMaxVelocity() {
    return DynamicsJNI.btMultibodyLink_jointMaxVelocity_get(swigCPtr, this);
  }

  public btMultibodyLink() {
    this(DynamicsJNI.new_btMultibodyLink(), true);
  }

  public void updateCacheMultiDof(java.nio.FloatBuffer pq) {
    assert pq.isDirect() : "Buffer must be allocated direct.";
    {
      DynamicsJNI.btMultibodyLink_updateCacheMultiDof__SWIG_0(swigCPtr, this, pq);
    }
  }

  public void updateCacheMultiDof() {
    DynamicsJNI.btMultibodyLink_updateCacheMultiDof__SWIG_1(swigCPtr, this);
  }

  public final static class eFeatherstoneJointType {
    public final static int eRevolute = 0;
    public final static int ePrismatic = 1;
    public final static int eSpherical = 2;
    public final static int ePlanar = 3;
    public final static int eFixed = 4;
    public final static int eInvalid = eFixed + 1;
  }

}