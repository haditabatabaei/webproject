

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

public class User2InternalIndex extends BulletBase {
	private long swigCPtr;
	
	protected User2InternalIndex(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public User2InternalIndex(long cPtr, boolean cMemoryOwn) {
		this("User2InternalIndex", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(User2InternalIndex obj) {
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
				ExtrasJNI.delete_User2InternalIndex(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public User2InternalIndex() {
    this(ExtrasJNI.new_User2InternalIndex(), true);
  }

  public void addBody(int body, int parent) {
    ExtrasJNI.User2InternalIndex_addBody(swigCPtr, this, body, parent);
  }

  public int buildMapping() {
    return ExtrasJNI.User2InternalIndex_buildMapping(swigCPtr, this);
  }

  public int user2internal(int user, java.nio.IntBuffer internal) {
    assert internal.isDirect() : "Buffer must be allocated direct.";
    {
      return ExtrasJNI.User2InternalIndex_user2internal(swigCPtr, this, user, internal);
    }
  }

  public int internal2user(int internal, java.nio.IntBuffer user) {
    assert user.isDirect() : "Buffer must be allocated direct.";
    {
      return ExtrasJNI.User2InternalIndex_internal2user(swigCPtr, this, internal, user);
    }
  }

}
