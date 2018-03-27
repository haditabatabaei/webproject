

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

public class Extras {

	
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

  public static MultiBodyTree CreateMultiBodyTree(MultiBodyTreeCreator creator) {
    long cPtr = ExtrasJNI.CreateMultiBodyTree(MultiBodyTreeCreator.getCPtr(creator), creator);
    return (cPtr == 0) ? null : new MultiBodyTree(cPtr, false);
  }

  public static void randomInit() {
    ExtrasJNI.randomInit__SWIG_0();
  }

  public static void randomInit(long seed) {
    ExtrasJNI.randomInit__SWIG_1(seed);
  }

  public static int randomInt(int low, int high) {
    return ExtrasJNI.randomInt(low, high);
  }

  public static float randomFloat(float low, float high) {
    return ExtrasJNI.randomFloat(low, high);
  }

  public static float randomMass() {
    return ExtrasJNI.randomMass();
  }

  public static SWIGTYPE_p_vec3 randomInertiaPrincipal() {
    return new SWIGTYPE_p_vec3(ExtrasJNI.randomInertiaPrincipal(), true);
  }

  public static SWIGTYPE_p_mat33 randomInertiaMatrix() {
    return new SWIGTYPE_p_mat33(ExtrasJNI.randomInertiaMatrix(), true);
  }

  public static SWIGTYPE_p_vec3 randomAxis() {
    return new SWIGTYPE_p_vec3(ExtrasJNI.randomAxis(), true);
  }

  public static int writeGraphvizDotFile(MultiBodyTree tree, MultiBodyNameMap map, String filename) {
    return ExtrasJNI.writeGraphvizDotFile(MultiBodyTree.getCPtr(tree), tree, MultiBodyNameMap.getCPtr(map), map, filename);
  }

  public static int compareInverseAndForwardDynamics(SWIGTYPE_p_vecx q, SWIGTYPE_p_vecx u, SWIGTYPE_p_vecx dot_u, Vector3 gravity, boolean verbose, btMultiBody btmb, MultiBodyTree id_tree, java.nio.DoubleBuffer pos_error, java.nio.DoubleBuffer acc_error) {
    assert pos_error.isDirect() : "Buffer must be allocated direct.";
    assert acc_error.isDirect() : "Buffer must be allocated direct.";
    {
      return ExtrasJNI.compareInverseAndForwardDynamics(SWIGTYPE_p_vecx.getCPtr(q), SWIGTYPE_p_vecx.getCPtr(u), SWIGTYPE_p_vecx.getCPtr(dot_u), gravity, verbose, btMultiBody.getCPtr(btmb), btmb, MultiBodyTree.getCPtr(id_tree), id_tree, pos_error, acc_error);
    }
  }

}
