

package com.badlogic.gdx.physics.bullet.extras;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.inversedynamics.MultiBodyTree;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btContactSolverInfo;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

public class MultiBodyTreeCreator extends BulletBase {
	private long swigCPtr;
	
	protected MultiBodyTreeCreator(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public MultiBodyTreeCreator(long cPtr, boolean cMemoryOwn) {
		this("MultiBodyTreeCreator", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(MultiBodyTreeCreator obj) {
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
				ExtrasJNI.delete_MultiBodyTreeCreator(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public int getNumBodies(java.nio.IntBuffer num_bodies) {
    assert num_bodies.isDirect() : "Buffer must be allocated direct.";
    {
      return ExtrasJNI.MultiBodyTreeCreator_getNumBodies(swigCPtr, this, num_bodies);
    }
  }

  public int getBody(int body_index, java.nio.IntBuffer parent_index, SWIGTYPE_p_btInverseDynamicsBullet3__JointType joint_type, SWIGTYPE_p_vec3 parent_r_parent_body_ref, SWIGTYPE_p_mat33 body_T_parent_ref, SWIGTYPE_p_vec3 body_axis_of_motion, java.nio.FloatBuffer mass, SWIGTYPE_p_vec3 body_r_body_com, SWIGTYPE_p_mat33 body_I_body, java.nio.IntBuffer user_int, SWIGTYPE_p_p_void user_ptr) {
    assert parent_index.isDirect() : "Buffer must be allocated direct.";
    assert mass.isDirect() : "Buffer must be allocated direct.";
    assert user_int.isDirect() : "Buffer must be allocated direct.";
    {
      return ExtrasJNI.MultiBodyTreeCreator_getBody(swigCPtr, this, body_index, parent_index, SWIGTYPE_p_btInverseDynamicsBullet3__JointType.getCPtr(joint_type), SWIGTYPE_p_vec3.getCPtr(parent_r_parent_body_ref), SWIGTYPE_p_mat33.getCPtr(body_T_parent_ref), SWIGTYPE_p_vec3.getCPtr(body_axis_of_motion), mass, SWIGTYPE_p_vec3.getCPtr(body_r_body_com), SWIGTYPE_p_mat33.getCPtr(body_I_body), user_int, SWIGTYPE_p_p_void.getCPtr(user_ptr));
    }
  }

  public SWIGTYPE_p_MultiBodyNameMap getNameMap() {
    long cPtr = ExtrasJNI.MultiBodyTreeCreator_getNameMap(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_MultiBodyNameMap(cPtr, false);
  }

}
