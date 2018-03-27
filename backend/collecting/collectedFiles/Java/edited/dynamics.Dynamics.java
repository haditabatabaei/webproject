

package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class Dynamics implements DynamicsConstants {

	
	public final static Vector3 staticVector3 = new Vector3();
	
	public final static com.badlogic.gdx.utils.Pool<Vector3> poolVector3 = new com.badlogic.gdx.utils.Pool<Vector3>() {
		@Override
		protected Vector3 newObject() {
			return new Vector3();
		}
	};


	
	public final static Quaternion staticQuaternion = new Quaternion();
	
	public final static com.badlogic.gdx.utils.Pool<Quaternion> poolQuaternion = new com.badlogic.gdx.utils.Pool<Quaternion>() {
		@Override
		protected Quaternion newObject() {
			return new Quaternion();
		}
	};


	
	public final static Matrix3 staticMatrix3 = new Matrix3();
	
	public final static com.badlogic.gdx.utils.Pool<Matrix3> poolMatrix3 = new com.badlogic.gdx.utils.Pool<Matrix3>() {
		@Override
		protected Matrix3 newObject() {
			return new Matrix3();
		}
	};


	
	public final static Matrix4 staticMatrix4 = new Matrix4();
	
	public final static com.badlogic.gdx.utils.Pool<Matrix4> poolMatrix4 = new com.badlogic.gdx.utils.Pool<Matrix4>() {
		@Override
		protected Matrix4 newObject() {
			return new Matrix4();
		}
	};

  public static void setGDeactivationTime(float value) {
    DynamicsJNI.gDeactivationTime_set(value);
  }

  public static float getGDeactivationTime() {
    return DynamicsJNI.gDeactivationTime_get();
  }

  public static void setGDisableDeactivation(boolean value) {
    DynamicsJNI.gDisableDeactivation_set(value);
  }

  public static boolean getGDisableDeactivation() {
    return DynamicsJNI.gDisableDeactivation_get();
  }

  public static float btAdjustAngleToLimits(float angleInRadians, float angleLowerLimitInRadians, float angleUpperLimitInRadians) {
    return DynamicsJNI.btAdjustAngleToLimits(angleInRadians, angleLowerLimitInRadians, angleUpperLimitInRadians);
  }

  public static void InternalTickCallback_CB(btDynamicsWorld world, float timeStep) {
    DynamicsJNI.InternalTickCallback_CB(btDynamicsWorld.getCPtr(world), world, timeStep);
  }

  public static float resolveSingleCollision(btRigidBody body1, btCollisionObject colObj2, Vector3 contactPositionWorld, Vector3 contactNormalOnB, btContactSolverInfo solverInfo, float distance) {
    return DynamicsJNI.resolveSingleCollision(btRigidBody.getCPtr(body1), body1, btCollisionObject.getCPtr(colObj2), colObj2, contactPositionWorld, contactNormalOnB, btContactSolverInfo.getCPtr(solverInfo), solverInfo, distance);
  }

  public static void resolveSingleBilateral(btRigidBody body1, Vector3 pos1, btRigidBody body2, Vector3 pos2, float distance, Vector3 normal, SWIGTYPE_p_float impulse, float timeStep) {
    DynamicsJNI.resolveSingleBilateral(btRigidBody.getCPtr(body1), body1, pos1, btRigidBody.getCPtr(body2), body2, pos2, distance, normal, SWIGTYPE_p_float.getCPtr(impulse), timeStep);
  }

  public static boolean btSolveDantzigLCP(int n, java.nio.FloatBuffer A, java.nio.FloatBuffer x, java.nio.FloatBuffer b, java.nio.FloatBuffer w, int nub, java.nio.FloatBuffer lo, java.nio.FloatBuffer hi, java.nio.IntBuffer findex, btDantzigScratchMemory scratch) {
    assert A.isDirect() : "Buffer must be allocated direct.";
    assert x.isDirect() : "Buffer must be allocated direct.";
    assert b.isDirect() : "Buffer must be allocated direct.";
    assert w.isDirect() : "Buffer must be allocated direct.";
    assert lo.isDirect() : "Buffer must be allocated direct.";
    assert hi.isDirect() : "Buffer must be allocated direct.";
    assert findex.isDirect() : "Buffer must be allocated direct.";
    {
      return DynamicsJNI.btSolveDantzigLCP(n, A, x, b, w, nub, lo, hi, findex, btDantzigScratchMemory.getCPtr(scratch), scratch);
    }
  }

}
