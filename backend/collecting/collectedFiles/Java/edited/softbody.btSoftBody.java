

package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;

public class btSoftBody extends btCollisionObject {
	private long swigCPtr;
	
	protected btSoftBody(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, SoftbodyJNI.btSoftBody_SWIGUpcast(cPtr), cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	
	public btSoftBody(long cPtr, boolean cMemoryOwn) {
		this("btSoftBody", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(SoftbodyJNI.btSoftBody_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
	}
	
	public static long getCPtr(btSoftBody obj) {
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
				SoftbodyJNI.delete_btSoftBody(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public void setCollisionDisabledObjects(btCollisionObjectConstArray value) {
    SoftbodyJNI.btSoftBody_collisionDisabledObjects_set(swigCPtr, this, btCollisionObjectConstArray.getCPtr(value), value);
  }

  public btCollisionObjectConstArray getCollisionDisabledObjects() {
    long cPtr = SoftbodyJNI.btSoftBody_collisionDisabledObjects_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btCollisionObjectConstArray(cPtr, false);
  }

  public void setSoftBodySolver(btSoftBodySolver value) {
    SoftbodyJNI.btSoftBody_softBodySolver_set(swigCPtr, this, btSoftBodySolver.getCPtr(value), value);
  }

  public btSoftBodySolver getSoftBodySolver() {
    long cPtr = SoftbodyJNI.btSoftBody_softBodySolver_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSoftBodySolver(cPtr, false);
  }

  static public class eAeroModel extends BulletBase {
  	private long swigCPtr;
  	
  	protected eAeroModel(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public eAeroModel(long cPtr, boolean cMemoryOwn) {
  		this("eAeroModel", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(eAeroModel obj) {
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
  				SoftbodyJNI.delete_btSoftBody_eAeroModel(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public eAeroModel() {
      this(SoftbodyJNI.new_btSoftBody_eAeroModel(), true);
    }
  
    public final static class EnumFlagType {
      public final static int V_Point = 0;
      public final static int V_TwoSided = V_Point + 1;
      public final static int V_TwoSidedLiftDrag = V_TwoSided + 1;
      public final static int V_OneSided = V_TwoSidedLiftDrag + 1;
      public final static int F_TwoSided = V_OneSided + 1;
      public final static int F_TwoSidedLiftDrag = F_TwoSided + 1;
      public final static int F_OneSided = F_TwoSidedLiftDrag + 1;
      public final static int END = F_OneSided + 1;
    }
  
  }

  static public class eVSolver extends BulletBase {
  	private long swigCPtr;
  	
  	protected eVSolver(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public eVSolver(long cPtr, boolean cMemoryOwn) {
  		this("eVSolver", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(eVSolver obj) {
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
  				SoftbodyJNI.delete_btSoftBody_eVSolver(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public eVSolver() {
      this(SoftbodyJNI.new_btSoftBody_eVSolver(), true);
    }
  
    public final static class EnumFlagType {
      public final static int Linear = 0;
      public final static int END = Linear + 1;
    }
  
  }

  static public class ePSolver extends BulletBase {
  	private long swigCPtr;
  	
  	protected ePSolver(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public ePSolver(long cPtr, boolean cMemoryOwn) {
  		this("ePSolver", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(ePSolver obj) {
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
  				SoftbodyJNI.delete_btSoftBody_ePSolver(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public ePSolver() {
      this(SoftbodyJNI.new_btSoftBody_ePSolver(), true);
    }
  
    public final static class EnumFlagType {
      public final static int Linear = 0;
      public final static int Anchors = Linear + 1;
      public final static int RContacts = Anchors + 1;
      public final static int SContacts = RContacts + 1;
      public final static int END = SContacts + 1;
    }
  
  }

  static public class eSolverPresets extends BulletBase {
  	private long swigCPtr;
  	
  	protected eSolverPresets(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public eSolverPresets(long cPtr, boolean cMemoryOwn) {
  		this("eSolverPresets", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(eSolverPresets obj) {
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
  				SoftbodyJNI.delete_btSoftBody_eSolverPresets(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public eSolverPresets() {
      this(SoftbodyJNI.new_btSoftBody_eSolverPresets(), true);
    }
  
    public final static class EnumFlagType {
      public final static int Positions = 0;
      public final static int Velocities = Positions + 1;
      public final static int Default = Positions;
      public final static int END = Default + 1;
    }
  
  }

  static public class eFeature extends BulletBase {
  	private long swigCPtr;
  	
  	protected eFeature(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public eFeature(long cPtr, boolean cMemoryOwn) {
  		this("eFeature", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(eFeature obj) {
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
  				SoftbodyJNI.delete_btSoftBody_eFeature(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public eFeature() {
      this(SoftbodyJNI.new_btSoftBody_eFeature(), true);
    }
  
    public final static class EnumFlagType {
      public final static int None = 0;
      public final static int Node = None + 1;
      public final static int Link = Node + 1;
      public final static int Face = Link + 1;
      public final static int Tetra = Face + 1;
      public final static int END = Tetra + 1;
    }
  
  }

  static public class fCollision extends BulletBase {
  	private long swigCPtr;
  	
  	protected fCollision(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public fCollision(long cPtr, boolean cMemoryOwn) {
  		this("fCollision", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(fCollision obj) {
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
  				SoftbodyJNI.delete_btSoftBody_fCollision(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public fCollision() {
      this(SoftbodyJNI.new_btSoftBody_fCollision(), true);
    }
  
    public final static class EnumFlagType {
      public final static int RVSmask = 0x000f;
      public final static int SDF_RS = 0x0001;
      public final static int CL_RS = 0x0002;
      public final static int SVSmask = 0x0030;
      public final static int VF_SS = 0x0010;
      public final static int CL_SS = 0x0020;
      public final static int CL_SELF = 0x0040;
      public final static int Default = SDF_RS;
      public final static int END = Default + 1;
    }
  
  }

  static public class fMaterial extends BulletBase {
  	private long swigCPtr;
  	
  	protected fMaterial(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public fMaterial(long cPtr, boolean cMemoryOwn) {
  		this("fMaterial", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(fMaterial obj) {
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
  				SoftbodyJNI.delete_btSoftBody_fMaterial(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public fMaterial() {
      this(SoftbodyJNI.new_btSoftBody_fMaterial(), true);
    }
  
    public final static class EnumFlagType {
      public final static int DebugDraw = 0x0001;
      public final static int Default = DebugDraw;
      public final static int END = Default + 1;
    }
  
  }

  static public class sRayCast extends BulletBase {
  	private long swigCPtr;
  	
  	protected sRayCast(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public sRayCast(long cPtr, boolean cMemoryOwn) {
  		this("sRayCast", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(sRayCast obj) {
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
  				SoftbodyJNI.delete_btSoftBody_sRayCast(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setBody(btSoftBody value) {
      SoftbodyJNI.btSoftBody_sRayCast_body_set(swigCPtr, this, btSoftBody.getCPtr(value), value);
    }
  
    public btSoftBody getBody() {
      long cPtr = SoftbodyJNI.btSoftBody_sRayCast_body_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody(cPtr, false);
    }
  
    public void setFeature(int value) {
      SoftbodyJNI.btSoftBody_sRayCast_feature_set(swigCPtr, this, value);
    }
  
    public int getFeature() {
      return SoftbodyJNI.btSoftBody_sRayCast_feature_get(swigCPtr, this);
    }
  
    public void setIndex(int value) {
      SoftbodyJNI.btSoftBody_sRayCast_index_set(swigCPtr, this, value);
    }
  
    public int getIndex() {
      return SoftbodyJNI.btSoftBody_sRayCast_index_get(swigCPtr, this);
    }
  
    public void setFraction(float value) {
      SoftbodyJNI.btSoftBody_sRayCast_fraction_set(swigCPtr, this, value);
    }
  
    public float getFraction() {
      return SoftbodyJNI.btSoftBody_sRayCast_fraction_get(swigCPtr, this);
    }
  
    public sRayCast() {
      this(SoftbodyJNI.new_btSoftBody_sRayCast(), true);
    }
  
  }

  static public class ImplicitFn extends BulletBase {
  	private long swigCPtr;
  	
  	protected ImplicitFn(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public ImplicitFn(long cPtr, boolean cMemoryOwn) {
  		this("ImplicitFn", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(ImplicitFn obj) {
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
  				SoftbodyJNI.delete_btSoftBody_ImplicitFn(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public float Eval(Vector3 x) {
      return SoftbodyJNI.btSoftBody_ImplicitFn_Eval(swigCPtr, this, x);
    }
  
  }

  static public class sCti extends BulletBase {
  	private long swigCPtr;
  	
  	protected sCti(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public sCti(long cPtr, boolean cMemoryOwn) {
  		this("sCti", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(sCti obj) {
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
  				SoftbodyJNI.delete_btSoftBody_sCti(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setColObj(btCollisionObject value) {
      SoftbodyJNI.btSoftBody_sCti_colObj_set(swigCPtr, this, btCollisionObject.getCPtr(value), value);
    }
  
    public btCollisionObject getColObj() {
  	return btCollisionObject.getInstance(SoftbodyJNI.btSoftBody_sCti_colObj_get(swigCPtr, this), false);
  }
  
    public void setNormal(btVector3 value) {
      SoftbodyJNI.btSoftBody_sCti_normal_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getNormal() {
      long cPtr = SoftbodyJNI.btSoftBody_sCti_normal_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setOffset(float value) {
      SoftbodyJNI.btSoftBody_sCti_offset_set(swigCPtr, this, value);
    }
  
    public float getOffset() {
      return SoftbodyJNI.btSoftBody_sCti_offset_get(swigCPtr, this);
    }
  
    public sCti() {
      this(SoftbodyJNI.new_btSoftBody_sCti(), true);
    }
  
  }

  static public class sMedium extends BulletBase {
  	private long swigCPtr;
  	
  	protected sMedium(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public sMedium(long cPtr, boolean cMemoryOwn) {
  		this("sMedium", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(sMedium obj) {
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
  				SoftbodyJNI.delete_btSoftBody_sMedium(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setVelocity(btVector3 value) {
      SoftbodyJNI.btSoftBody_sMedium_velocity_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getVelocity() {
      long cPtr = SoftbodyJNI.btSoftBody_sMedium_velocity_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setPressure(float value) {
      SoftbodyJNI.btSoftBody_sMedium_pressure_set(swigCPtr, this, value);
    }
  
    public float getPressure() {
      return SoftbodyJNI.btSoftBody_sMedium_pressure_get(swigCPtr, this);
    }
  
    public void setDensity(float value) {
      SoftbodyJNI.btSoftBody_sMedium_density_set(swigCPtr, this, value);
    }
  
    public float getDensity() {
      return SoftbodyJNI.btSoftBody_sMedium_density_get(swigCPtr, this);
    }
  
    public sMedium() {
      this(SoftbodyJNI.new_btSoftBody_sMedium(), true);
    }
  
  }

  static public class Element extends BulletBase {
  	private long swigCPtr;
  	
  	protected Element(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Element(long cPtr, boolean cMemoryOwn) {
  		this("Element", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Element obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Element(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setTag(long value) {
      SoftbodyJNI.btSoftBody_Element_tag_set(swigCPtr, this, value);
    }
  
    public long getTag() {
      return SoftbodyJNI.btSoftBody_Element_tag_get(swigCPtr, this);
    }
  
    public Element() {
      this(SoftbodyJNI.new_btSoftBody_Element(), true);
    }
  
  }

  static public class Material extends btSoftBody.Element {
  	private long swigCPtr;
  	
  	protected Material(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_Material_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public Material(long cPtr, boolean cMemoryOwn) {
  		this("Material", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_Material_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(Material obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Material(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setKLST(float value) {
      SoftbodyJNI.btSoftBody_Material_kLST_set(swigCPtr, this, value);
    }
  
    public float getKLST() {
      return SoftbodyJNI.btSoftBody_Material_kLST_get(swigCPtr, this);
    }
  
    public void setKAST(float value) {
      SoftbodyJNI.btSoftBody_Material_kAST_set(swigCPtr, this, value);
    }
  
    public float getKAST() {
      return SoftbodyJNI.btSoftBody_Material_kAST_get(swigCPtr, this);
    }
  
    public void setKVST(float value) {
      SoftbodyJNI.btSoftBody_Material_kVST_set(swigCPtr, this, value);
    }
  
    public float getKVST() {
      return SoftbodyJNI.btSoftBody_Material_kVST_get(swigCPtr, this);
    }
  
    public void setFlags(int value) {
      SoftbodyJNI.btSoftBody_Material_flags_set(swigCPtr, this, value);
    }
  
    public int getFlags() {
      return SoftbodyJNI.btSoftBody_Material_flags_get(swigCPtr, this);
    }
  
    public Material() {
      this(SoftbodyJNI.new_btSoftBody_Material(), true);
    }
  
  }

  static public class Feature extends btSoftBody.Element {
  	private long swigCPtr;
  	
  	protected Feature(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_Feature_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public Feature(long cPtr, boolean cMemoryOwn) {
  		this("Feature", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_Feature_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(Feature obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Feature(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setMaterial(btSoftBody.Material value) {
      SoftbodyJNI.btSoftBody_Feature_material_set(swigCPtr, this, btSoftBody.Material.getCPtr(value), value);
    }
  
    public btSoftBody.Material getMaterial() {
      long cPtr = SoftbodyJNI.btSoftBody_Feature_material_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Material(cPtr, false);
    }
  
    public Feature() {
      this(SoftbodyJNI.new_btSoftBody_Feature(), true);
    }
  
  }

  static public class Node extends btSoftBody.Feature {
  	private long swigCPtr;
  	
  	protected Node(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_Node_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public Node(long cPtr, boolean cMemoryOwn) {
  		this("Node", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_Node_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(Node obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Node(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setX(btVector3 value) {
      SoftbodyJNI.btSoftBody_Node_x_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getX() {
      long cPtr = SoftbodyJNI.btSoftBody_Node_x_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setQ(btVector3 value) {
      SoftbodyJNI.btSoftBody_Node_q_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getQ() {
      long cPtr = SoftbodyJNI.btSoftBody_Node_q_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setV(btVector3 value) {
      SoftbodyJNI.btSoftBody_Node_v_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getV() {
      long cPtr = SoftbodyJNI.btSoftBody_Node_v_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setF(btVector3 value) {
      SoftbodyJNI.btSoftBody_Node_f_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getF() {
      long cPtr = SoftbodyJNI.btSoftBody_Node_f_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setN(btVector3 value) {
      SoftbodyJNI.btSoftBody_Node_n_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getN() {
      long cPtr = SoftbodyJNI.btSoftBody_Node_n_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setIm(float value) {
      SoftbodyJNI.btSoftBody_Node_im_set(swigCPtr, this, value);
    }
  
    public float getIm() {
      return SoftbodyJNI.btSoftBody_Node_im_get(swigCPtr, this);
    }
  
    public void setArea(float value) {
      SoftbodyJNI.btSoftBody_Node_area_set(swigCPtr, this, value);
    }
  
    public float getArea() {
      return SoftbodyJNI.btSoftBody_Node_area_get(swigCPtr, this);
    }
  
    public void setLeaf(btDbvtNode value) {
      SoftbodyJNI.btSoftBody_Node_leaf_set(swigCPtr, this, btDbvtNode.getCPtr(value), value);
    }
  
    public btDbvtNode getLeaf() {
  	return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Node_leaf_get(swigCPtr, this), false);
  }
  
    public void setBattach(int value) {
      SoftbodyJNI.btSoftBody_Node_battach_set(swigCPtr, this, value);
    }
  
    public int getBattach() {
      return SoftbodyJNI.btSoftBody_Node_battach_get(swigCPtr, this);
    }
  
    public Node() {
      this(SoftbodyJNI.new_btSoftBody_Node(), true);
    }
  
  }

  static public class Link extends btSoftBody.Feature {
  	private long swigCPtr;
  	
  	protected Link(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_Link_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public Link(long cPtr, boolean cMemoryOwn) {
  		this("Link", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_Link_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(Link obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Link(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setC3(btVector3 value) {
      SoftbodyJNI.btSoftBody_Link_c3_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getC3() {
      long cPtr = SoftbodyJNI.btSoftBody_Link_c3_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setN(SWIGTYPE_p_p_btSoftBody__Node value) {
      SoftbodyJNI.btSoftBody_Link_n_set(swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(value));
    }
  
    public SWIGTYPE_p_p_btSoftBody__Node getN() {
      long cPtr = SoftbodyJNI.btSoftBody_Link_n_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_p_btSoftBody__Node(cPtr, false);
    }
  
    public void setRl(float value) {
      SoftbodyJNI.btSoftBody_Link_rl_set(swigCPtr, this, value);
    }
  
    public float getRl() {
      return SoftbodyJNI.btSoftBody_Link_rl_get(swigCPtr, this);
    }
  
    public void setBbending(int value) {
      SoftbodyJNI.btSoftBody_Link_bbending_set(swigCPtr, this, value);
    }
  
    public int getBbending() {
      return SoftbodyJNI.btSoftBody_Link_bbending_get(swigCPtr, this);
    }
  
    public void setC0(float value) {
      SoftbodyJNI.btSoftBody_Link_c0_set(swigCPtr, this, value);
    }
  
    public float getC0() {
      return SoftbodyJNI.btSoftBody_Link_c0_get(swigCPtr, this);
    }
  
    public void setC1(float value) {
      SoftbodyJNI.btSoftBody_Link_c1_set(swigCPtr, this, value);
    }
  
    public float getC1() {
      return SoftbodyJNI.btSoftBody_Link_c1_get(swigCPtr, this);
    }
  
    public void setC2(float value) {
      SoftbodyJNI.btSoftBody_Link_c2_set(swigCPtr, this, value);
    }
  
    public float getC2() {
      return SoftbodyJNI.btSoftBody_Link_c2_get(swigCPtr, this);
    }
  
    public long operatorNew(long sizeInBytes) {
      return SoftbodyJNI.btSoftBody_Link_operatorNew__SWIG_0(swigCPtr, this, sizeInBytes);
    }
  
    public void operatorDelete(long ptr) {
      SoftbodyJNI.btSoftBody_Link_operatorDelete__SWIG_0(swigCPtr, this, ptr);
    }
  
    public long operatorNew(long arg0, long ptr) {
      return SoftbodyJNI.btSoftBody_Link_operatorNew__SWIG_1(swigCPtr, this, arg0, ptr);
    }
  
    public void operatorDelete(long arg0, long arg1) {
      SoftbodyJNI.btSoftBody_Link_operatorDelete__SWIG_1(swigCPtr, this, arg0, arg1);
    }
  
    public long operatorNewArray(long sizeInBytes) {
      return SoftbodyJNI.btSoftBody_Link_operatorNewArray__SWIG_0(swigCPtr, this, sizeInBytes);
    }
  
    public void operatorDeleteArray(long ptr) {
      SoftbodyJNI.btSoftBody_Link_operatorDeleteArray__SWIG_0(swigCPtr, this, ptr);
    }
  
    public long operatorNewArray(long arg0, long ptr) {
      return SoftbodyJNI.btSoftBody_Link_operatorNewArray__SWIG_1(swigCPtr, this, arg0, ptr);
    }
  
    public void operatorDeleteArray(long arg0, long arg1) {
      SoftbodyJNI.btSoftBody_Link_operatorDeleteArray__SWIG_1(swigCPtr, this, arg0, arg1);
    }
  
    public Link() {
      this(SoftbodyJNI.new_btSoftBody_Link(), true);
    }
  
  }

  static public class Face extends btSoftBody.Feature {
  	private long swigCPtr;
  	
  	protected Face(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_Face_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public Face(long cPtr, boolean cMemoryOwn) {
  		this("Face", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_Face_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(Face obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Face(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setN(SWIGTYPE_p_p_btSoftBody__Node value) {
      SoftbodyJNI.btSoftBody_Face_n_set(swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(value));
    }
  
    public SWIGTYPE_p_p_btSoftBody__Node getN() {
      long cPtr = SoftbodyJNI.btSoftBody_Face_n_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_p_btSoftBody__Node(cPtr, false);
    }
  
    public void setNormal(btVector3 value) {
      SoftbodyJNI.btSoftBody_Face_normal_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getNormal() {
      long cPtr = SoftbodyJNI.btSoftBody_Face_normal_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setRa(float value) {
      SoftbodyJNI.btSoftBody_Face_ra_set(swigCPtr, this, value);
    }
  
    public float getRa() {
      return SoftbodyJNI.btSoftBody_Face_ra_get(swigCPtr, this);
    }
  
    public void setLeaf(btDbvtNode value) {
      SoftbodyJNI.btSoftBody_Face_leaf_set(swigCPtr, this, btDbvtNode.getCPtr(value), value);
    }
  
    public btDbvtNode getLeaf() {
  	return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Face_leaf_get(swigCPtr, this), false);
  }
  
    public Face() {
      this(SoftbodyJNI.new_btSoftBody_Face(), true);
    }
  
  }

  static public class Tetra extends btSoftBody.Feature {
  	private long swigCPtr;
  	
  	protected Tetra(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_Tetra_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public Tetra(long cPtr, boolean cMemoryOwn) {
  		this("Tetra", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_Tetra_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(Tetra obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Tetra(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setN(SWIGTYPE_p_p_btSoftBody__Node value) {
      SoftbodyJNI.btSoftBody_Tetra_n_set(swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(value));
    }
  
    public SWIGTYPE_p_p_btSoftBody__Node getN() {
      long cPtr = SoftbodyJNI.btSoftBody_Tetra_n_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_p_btSoftBody__Node(cPtr, false);
    }
  
    public void setRv(float value) {
      SoftbodyJNI.btSoftBody_Tetra_rv_set(swigCPtr, this, value);
    }
  
    public float getRv() {
      return SoftbodyJNI.btSoftBody_Tetra_rv_get(swigCPtr, this);
    }
  
    public void setLeaf(btDbvtNode value) {
      SoftbodyJNI.btSoftBody_Tetra_leaf_set(swigCPtr, this, btDbvtNode.getCPtr(value), value);
    }
  
    public btDbvtNode getLeaf() {
  	return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Tetra_leaf_get(swigCPtr, this), false);
  }
  
    public void setC0(btVector3 value) {
      SoftbodyJNI.btSoftBody_Tetra_c0_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getC0() {
      long cPtr = SoftbodyJNI.btSoftBody_Tetra_c0_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setC1(float value) {
      SoftbodyJNI.btSoftBody_Tetra_c1_set(swigCPtr, this, value);
    }
  
    public float getC1() {
      return SoftbodyJNI.btSoftBody_Tetra_c1_get(swigCPtr, this);
    }
  
    public void setC2(float value) {
      SoftbodyJNI.btSoftBody_Tetra_c2_set(swigCPtr, this, value);
    }
  
    public float getC2() {
      return SoftbodyJNI.btSoftBody_Tetra_c2_get(swigCPtr, this);
    }
  
    public Tetra() {
      this(SoftbodyJNI.new_btSoftBody_Tetra(), true);
    }
  
  }

  static public class RContact extends BulletBase {
  	private long swigCPtr;
  	
  	protected RContact(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public RContact(long cPtr, boolean cMemoryOwn) {
  		this("RContact", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(RContact obj) {
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
  				SoftbodyJNI.delete_btSoftBody_RContact(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setCti(btSoftBody.sCti value) {
      SoftbodyJNI.btSoftBody_RContact_cti_set(swigCPtr, this, btSoftBody.sCti.getCPtr(value), value);
    }
  
    public btSoftBody.sCti getCti() {
      long cPtr = SoftbodyJNI.btSoftBody_RContact_cti_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.sCti(cPtr, false);
    }
  
    public void setNode(btSoftBody.Node value) {
      SoftbodyJNI.btSoftBody_RContact_node_set(swigCPtr, this, btSoftBody.Node.getCPtr(value), value);
    }
  
    public btSoftBody.Node getNode() {
      long cPtr = SoftbodyJNI.btSoftBody_RContact_node_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Node(cPtr, false);
    }
  
    public void setC0(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_RContact_c0_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getC0() {
      long cPtr = SoftbodyJNI.btSoftBody_RContact_c0_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public void setC1(btVector3 value) {
      SoftbodyJNI.btSoftBody_RContact_c1_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getC1() {
      long cPtr = SoftbodyJNI.btSoftBody_RContact_c1_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setC2(float value) {
      SoftbodyJNI.btSoftBody_RContact_c2_set(swigCPtr, this, value);
    }
  
    public float getC2() {
      return SoftbodyJNI.btSoftBody_RContact_c2_get(swigCPtr, this);
    }
  
    public void setC3(float value) {
      SoftbodyJNI.btSoftBody_RContact_c3_set(swigCPtr, this, value);
    }
  
    public float getC3() {
      return SoftbodyJNI.btSoftBody_RContact_c3_get(swigCPtr, this);
    }
  
    public void setC4(float value) {
      SoftbodyJNI.btSoftBody_RContact_c4_set(swigCPtr, this, value);
    }
  
    public float getC4() {
      return SoftbodyJNI.btSoftBody_RContact_c4_get(swigCPtr, this);
    }
  
    public RContact() {
      this(SoftbodyJNI.new_btSoftBody_RContact(), true);
    }
  
  }

  static public class SContact extends BulletBase {
  	private long swigCPtr;
  	
  	protected SContact(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public SContact(long cPtr, boolean cMemoryOwn) {
  		this("SContact", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(SContact obj) {
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
  				SoftbodyJNI.delete_btSoftBody_SContact(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setNode(btSoftBody.Node value) {
      SoftbodyJNI.btSoftBody_SContact_node_set(swigCPtr, this, btSoftBody.Node.getCPtr(value), value);
    }
  
    public btSoftBody.Node getNode() {
      long cPtr = SoftbodyJNI.btSoftBody_SContact_node_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Node(cPtr, false);
    }
  
    public void setFace(btSoftBody.Face value) {
      SoftbodyJNI.btSoftBody_SContact_face_set(swigCPtr, this, btSoftBody.Face.getCPtr(value), value);
    }
  
    public btSoftBody.Face getFace() {
      long cPtr = SoftbodyJNI.btSoftBody_SContact_face_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Face(cPtr, false);
    }
  
    public void setWeights(btVector3 value) {
      SoftbodyJNI.btSoftBody_SContact_weights_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getWeights() {
      long cPtr = SoftbodyJNI.btSoftBody_SContact_weights_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setNormal(btVector3 value) {
      SoftbodyJNI.btSoftBody_SContact_normal_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getNormal() {
      long cPtr = SoftbodyJNI.btSoftBody_SContact_normal_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setMargin(float value) {
      SoftbodyJNI.btSoftBody_SContact_margin_set(swigCPtr, this, value);
    }
  
    public float getMargin() {
      return SoftbodyJNI.btSoftBody_SContact_margin_get(swigCPtr, this);
    }
  
    public void setFriction(float value) {
      SoftbodyJNI.btSoftBody_SContact_friction_set(swigCPtr, this, value);
    }
  
    public float getFriction() {
      return SoftbodyJNI.btSoftBody_SContact_friction_get(swigCPtr, this);
    }
  
    public void setCfm(float[] value) {
      SoftbodyJNI.btSoftBody_SContact_cfm_set(swigCPtr, this, value);
    }
  
    public float[] getCfm() {
      return SoftbodyJNI.btSoftBody_SContact_cfm_get(swigCPtr, this);
  }
  
    public SContact() {
      this(SoftbodyJNI.new_btSoftBody_SContact(), true);
    }
  
  }

  static public class Anchor extends BulletBase {
  	private long swigCPtr;
  	
  	protected Anchor(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Anchor(long cPtr, boolean cMemoryOwn) {
  		this("Anchor", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Anchor obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Anchor(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setNode(btSoftBody.Node value) {
      SoftbodyJNI.btSoftBody_Anchor_node_set(swigCPtr, this, btSoftBody.Node.getCPtr(value), value);
    }
  
    public btSoftBody.Node getNode() {
      long cPtr = SoftbodyJNI.btSoftBody_Anchor_node_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Node(cPtr, false);
    }
  
    public void setLocal(btVector3 value) {
      SoftbodyJNI.btSoftBody_Anchor_local_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getLocal() {
      long cPtr = SoftbodyJNI.btSoftBody_Anchor_local_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setBody(btRigidBody value) {
      SoftbodyJNI.btSoftBody_Anchor_body_set(swigCPtr, this, btRigidBody.getCPtr(value), value);
    }
  
    public btRigidBody getBody() {
  	return btRigidBody.getInstance(SoftbodyJNI.btSoftBody_Anchor_body_get(swigCPtr, this), false);
  }
  
    public void setInfluence(float value) {
      SoftbodyJNI.btSoftBody_Anchor_influence_set(swigCPtr, this, value);
    }
  
    public float getInfluence() {
      return SoftbodyJNI.btSoftBody_Anchor_influence_get(swigCPtr, this);
    }
  
    public void setC0(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_Anchor_c0_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getC0() {
      long cPtr = SoftbodyJNI.btSoftBody_Anchor_c0_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public void setC1(btVector3 value) {
      SoftbodyJNI.btSoftBody_Anchor_c1_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getC1() {
      long cPtr = SoftbodyJNI.btSoftBody_Anchor_c1_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setC2(float value) {
      SoftbodyJNI.btSoftBody_Anchor_c2_set(swigCPtr, this, value);
    }
  
    public float getC2() {
      return SoftbodyJNI.btSoftBody_Anchor_c2_get(swigCPtr, this);
    }
  
    public Anchor() {
      this(SoftbodyJNI.new_btSoftBody_Anchor(), true);
    }
  
  }

  static public class Note extends btSoftBody.Element {
  	private long swigCPtr;
  	
  	protected Note(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_Note_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public Note(long cPtr, boolean cMemoryOwn) {
  		this("Note", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_Note_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(Note obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Note(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public String getText() {
      return SoftbodyJNI.btSoftBody_Note_text_get(swigCPtr, this);
    }
  
    public void setOffset(btVector3 value) {
      SoftbodyJNI.btSoftBody_Note_offset_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getOffset() {
      long cPtr = SoftbodyJNI.btSoftBody_Note_offset_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setRank(int value) {
      SoftbodyJNI.btSoftBody_Note_rank_set(swigCPtr, this, value);
    }
  
    public int getRank() {
      return SoftbodyJNI.btSoftBody_Note_rank_get(swigCPtr, this);
    }
  
    public void setNodes(SWIGTYPE_p_p_btSoftBody__Node value) {
      SoftbodyJNI.btSoftBody_Note_nodes_set(swigCPtr, this, SWIGTYPE_p_p_btSoftBody__Node.getCPtr(value));
    }
  
    public SWIGTYPE_p_p_btSoftBody__Node getNodes() {
      long cPtr = SoftbodyJNI.btSoftBody_Note_nodes_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_p_btSoftBody__Node(cPtr, false);
    }
  
    public void setCoords(float[] value) {
      SoftbodyJNI.btSoftBody_Note_coords_set(swigCPtr, this, value);
    }
  
    public float[] getCoords() {
      return SoftbodyJNI.btSoftBody_Note_coords_get(swigCPtr, this);
  }
  
    public Note() {
      this(SoftbodyJNI.new_btSoftBody_Note(), true);
    }
  
  }

  static public class Pose extends BulletBase {
  	private long swigCPtr;
  	
  	protected Pose(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Pose(long cPtr, boolean cMemoryOwn) {
  		this("Pose", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Pose obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Pose(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setBvolume(boolean value) {
      SoftbodyJNI.btSoftBody_Pose_bvolume_set(swigCPtr, this, value);
    }
  
    public boolean getBvolume() {
      return SoftbodyJNI.btSoftBody_Pose_bvolume_get(swigCPtr, this);
    }
  
    public void setBframe(boolean value) {
      SoftbodyJNI.btSoftBody_Pose_bframe_set(swigCPtr, this, value);
    }
  
    public boolean getBframe() {
      return SoftbodyJNI.btSoftBody_Pose_bframe_get(swigCPtr, this);
    }
  
    public void setVolume(float value) {
      SoftbodyJNI.btSoftBody_Pose_volume_set(swigCPtr, this, value);
    }
  
    public float getVolume() {
      return SoftbodyJNI.btSoftBody_Pose_volume_get(swigCPtr, this);
    }
  
    public void setPos(btVector3Array value) {
      SoftbodyJNI.btSoftBody_Pose_pos_set(swigCPtr, this, btVector3Array.getCPtr(value), value);
    }
  
    public btVector3Array getPos() {
      long cPtr = SoftbodyJNI.btSoftBody_Pose_pos_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3Array(cPtr, false);
    }
  
    public void setWgh(btScalarArray value) {
      SoftbodyJNI.btSoftBody_Pose_wgh_set(swigCPtr, this, btScalarArray.getCPtr(value), value);
    }
  
    public btScalarArray getWgh() {
      long cPtr = SoftbodyJNI.btSoftBody_Pose_wgh_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btScalarArray(cPtr, false);
    }
  
    public void setCom(btVector3 value) {
      SoftbodyJNI.btSoftBody_Pose_com_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getCom() {
      long cPtr = SoftbodyJNI.btSoftBody_Pose_com_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setRot(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_Pose_rot_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getRot() {
      long cPtr = SoftbodyJNI.btSoftBody_Pose_rot_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public void setScl(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_Pose_scl_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getScl() {
      long cPtr = SoftbodyJNI.btSoftBody_Pose_scl_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public void setAqq(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_Pose_aqq_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getAqq() {
      long cPtr = SoftbodyJNI.btSoftBody_Pose_aqq_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public Pose() {
      this(SoftbodyJNI.new_btSoftBody_Pose(), true);
    }
  
  }

  static public class Cluster extends BulletBase {
  	private long swigCPtr;
  	
  	protected Cluster(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Cluster(long cPtr, boolean cMemoryOwn) {
  		this("Cluster", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Cluster obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Cluster(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setMasses(btScalarArray value) {
      SoftbodyJNI.btSoftBody_Cluster_masses_set(swigCPtr, this, btScalarArray.getCPtr(value), value);
    }
  
    public btScalarArray getMasses() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_masses_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btScalarArray(cPtr, false);
    }
  
    public void setNodes(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t value) {
      SoftbodyJNI.btSoftBody_Cluster_nodes_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t.getCPtr(value));
    }
  
    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t getNodes() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_nodes_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_p_t(cPtr, false);
    }
  
    public void setFramerefs(btVector3Array value) {
      SoftbodyJNI.btSoftBody_Cluster_framerefs_set(swigCPtr, this, btVector3Array.getCPtr(value), value);
    }
  
    public btVector3Array getFramerefs() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_framerefs_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3Array(cPtr, false);
    }
  
    public void setFramexform(btTransform value) {
      SoftbodyJNI.btSoftBody_Cluster_framexform_set(swigCPtr, this, btTransform.getCPtr(value), value);
    }
  
    public btTransform getFramexform() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_framexform_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btTransform(cPtr, false);
    }
  
    public void setIdmass(float value) {
      SoftbodyJNI.btSoftBody_Cluster_idmass_set(swigCPtr, this, value);
    }
  
    public float getIdmass() {
      return SoftbodyJNI.btSoftBody_Cluster_idmass_get(swigCPtr, this);
    }
  
    public void setImass(float value) {
      SoftbodyJNI.btSoftBody_Cluster_imass_set(swigCPtr, this, value);
    }
  
    public float getImass() {
      return SoftbodyJNI.btSoftBody_Cluster_imass_get(swigCPtr, this);
    }
  
    public void setLocii(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_Cluster_locii_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getLocii() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_locii_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public void setInvwi(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_Cluster_invwi_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getInvwi() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_invwi_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public void setCom(btVector3 value) {
      SoftbodyJNI.btSoftBody_Cluster_com_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getCom() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_com_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setVimpulses(btVector3 value) {
      SoftbodyJNI.btSoftBody_Cluster_vimpulses_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getVimpulses() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_vimpulses_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setDimpulses(btVector3 value) {
      SoftbodyJNI.btSoftBody_Cluster_dimpulses_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getDimpulses() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_dimpulses_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setNvimpulses(int value) {
      SoftbodyJNI.btSoftBody_Cluster_nvimpulses_set(swigCPtr, this, value);
    }
  
    public int getNvimpulses() {
      return SoftbodyJNI.btSoftBody_Cluster_nvimpulses_get(swigCPtr, this);
    }
  
    public void setNdimpulses(int value) {
      SoftbodyJNI.btSoftBody_Cluster_ndimpulses_set(swigCPtr, this, value);
    }
  
    public int getNdimpulses() {
      return SoftbodyJNI.btSoftBody_Cluster_ndimpulses_get(swigCPtr, this);
    }
  
    public void setLv(btVector3 value) {
      SoftbodyJNI.btSoftBody_Cluster_lv_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getLv() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_lv_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setAv(btVector3 value) {
      SoftbodyJNI.btSoftBody_Cluster_av_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getAv() {
      long cPtr = SoftbodyJNI.btSoftBody_Cluster_av_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setLeaf(btDbvtNode value) {
      SoftbodyJNI.btSoftBody_Cluster_leaf_set(swigCPtr, this, btDbvtNode.getCPtr(value), value);
    }
  
    public btDbvtNode getLeaf() {
  	return btDbvtNode.internalTemp(SoftbodyJNI.btSoftBody_Cluster_leaf_get(swigCPtr, this), false);
  }
  
    public void setNdamping(float value) {
      SoftbodyJNI.btSoftBody_Cluster_ndamping_set(swigCPtr, this, value);
    }
  
    public float getNdamping() {
      return SoftbodyJNI.btSoftBody_Cluster_ndamping_get(swigCPtr, this);
    }
  
    public void setLdamping(float value) {
      SoftbodyJNI.btSoftBody_Cluster_ldamping_set(swigCPtr, this, value);
    }
  
    public float getLdamping() {
      return SoftbodyJNI.btSoftBody_Cluster_ldamping_get(swigCPtr, this);
    }
  
    public void setAdamping(float value) {
      SoftbodyJNI.btSoftBody_Cluster_adamping_set(swigCPtr, this, value);
    }
  
    public float getAdamping() {
      return SoftbodyJNI.btSoftBody_Cluster_adamping_get(swigCPtr, this);
    }
  
    public void setMatching(float value) {
      SoftbodyJNI.btSoftBody_Cluster_matching_set(swigCPtr, this, value);
    }
  
    public float getMatching() {
      return SoftbodyJNI.btSoftBody_Cluster_matching_get(swigCPtr, this);
    }
  
    public void setMaxSelfCollisionImpulse(float value) {
      SoftbodyJNI.btSoftBody_Cluster_maxSelfCollisionImpulse_set(swigCPtr, this, value);
    }
  
    public float getMaxSelfCollisionImpulse() {
      return SoftbodyJNI.btSoftBody_Cluster_maxSelfCollisionImpulse_get(swigCPtr, this);
    }
  
    public void setSelfCollisionImpulseFactor(float value) {
      SoftbodyJNI.btSoftBody_Cluster_selfCollisionImpulseFactor_set(swigCPtr, this, value);
    }
  
    public float getSelfCollisionImpulseFactor() {
      return SoftbodyJNI.btSoftBody_Cluster_selfCollisionImpulseFactor_get(swigCPtr, this);
    }
  
    public void setContainsAnchor(boolean value) {
      SoftbodyJNI.btSoftBody_Cluster_containsAnchor_set(swigCPtr, this, value);
    }
  
    public boolean getContainsAnchor() {
      return SoftbodyJNI.btSoftBody_Cluster_containsAnchor_get(swigCPtr, this);
    }
  
    public void setCollide(boolean value) {
      SoftbodyJNI.btSoftBody_Cluster_collide_set(swigCPtr, this, value);
    }
  
    public boolean getCollide() {
      return SoftbodyJNI.btSoftBody_Cluster_collide_get(swigCPtr, this);
    }
  
    public void setClusterIndex(int value) {
      SoftbodyJNI.btSoftBody_Cluster_clusterIndex_set(swigCPtr, this, value);
    }
  
    public int getClusterIndex() {
      return SoftbodyJNI.btSoftBody_Cluster_clusterIndex_get(swigCPtr, this);
    }
  
    public Cluster() {
      this(SoftbodyJNI.new_btSoftBody_Cluster(), true);
    }
  
  }

  static public class Impulse extends BulletBase {
  	private long swigCPtr;
  	
  	protected Impulse(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Impulse(long cPtr, boolean cMemoryOwn) {
  		this("Impulse", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Impulse obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Impulse(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setVelocity(btVector3 value) {
      SoftbodyJNI.btSoftBody_Impulse_velocity_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getVelocity() {
      long cPtr = SoftbodyJNI.btSoftBody_Impulse_velocity_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setDrift(btVector3 value) {
      SoftbodyJNI.btSoftBody_Impulse_drift_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getDrift() {
      long cPtr = SoftbodyJNI.btSoftBody_Impulse_drift_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setAsVelocity(int value) {
      SoftbodyJNI.btSoftBody_Impulse_asVelocity_set(swigCPtr, this, value);
    }
  
    public int getAsVelocity() {
      return SoftbodyJNI.btSoftBody_Impulse_asVelocity_get(swigCPtr, this);
    }
  
    public void setAsDrift(int value) {
      SoftbodyJNI.btSoftBody_Impulse_asDrift_set(swigCPtr, this, value);
    }
  
    public int getAsDrift() {
      return SoftbodyJNI.btSoftBody_Impulse_asDrift_get(swigCPtr, this);
    }
  
    public Impulse() {
      this(SoftbodyJNI.new_btSoftBody_Impulse(), true);
    }
  
    public btSoftBody.Impulse operatorSubtraction() {
      return new btSoftBody.Impulse(SoftbodyJNI.btSoftBody_Impulse_operatorSubtraction(swigCPtr, this), true);
    }
  
    public btSoftBody.Impulse operatorMultiplication(float x) {
      return new btSoftBody.Impulse(SoftbodyJNI.btSoftBody_Impulse_operatorMultiplication(swigCPtr, this, x), true);
    }
  
  }

  static public class Body extends BulletBase {
  	private long swigCPtr;
  	
  	protected Body(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Body(long cPtr, boolean cMemoryOwn) {
  		this("Body", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Body obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Body(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setSoft(btSoftBody.Cluster value) {
      SoftbodyJNI.btSoftBody_Body_soft_set(swigCPtr, this, btSoftBody.Cluster.getCPtr(value), value);
    }
  
    public btSoftBody.Cluster getSoft() {
      long cPtr = SoftbodyJNI.btSoftBody_Body_soft_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Cluster(cPtr, false);
    }
  
    public void setRigid(btRigidBody value) {
      SoftbodyJNI.btSoftBody_Body_rigid_set(swigCPtr, this, btRigidBody.getCPtr(value), value);
    }
  
    public btRigidBody getRigid() {
  	return btRigidBody.getInstance(SoftbodyJNI.btSoftBody_Body_rigid_get(swigCPtr, this), false);
  }
  
    public void setCollisionObject(btCollisionObject value) {
      SoftbodyJNI.btSoftBody_Body_collisionObject_set(swigCPtr, this, btCollisionObject.getCPtr(value), value);
    }
  
    public btCollisionObject getCollisionObject() {
  	return btCollisionObject.getInstance(SoftbodyJNI.btSoftBody_Body_collisionObject_get(swigCPtr, this), false);
  }
  
    public Body() {
      this(SoftbodyJNI.new_btSoftBody_Body__SWIG_0(), true);
    }
  
    public Body(btSoftBody.Cluster p) {
      this(SoftbodyJNI.new_btSoftBody_Body__SWIG_1(btSoftBody.Cluster.getCPtr(p), p), true);
    }
  
    public Body(btCollisionObject colObj) {
      this(SoftbodyJNI.new_btSoftBody_Body__SWIG_2(btCollisionObject.getCPtr(colObj), colObj), true);
    }
  
    public void activate() {
      SoftbodyJNI.btSoftBody_Body_activate(swigCPtr, this);
    }
  
    public Matrix3 invWorldInertia() {
  	return SoftbodyJNI.btSoftBody_Body_invWorldInertia(swigCPtr, this);
  }
  
    public float invMass() {
      return SoftbodyJNI.btSoftBody_Body_invMass(swigCPtr, this);
    }
  
    public Matrix4 xform() {
  	return SoftbodyJNI.btSoftBody_Body_xform(swigCPtr, this);
  }
  
    public Vector3 linearVelocity() {
  	return SoftbodyJNI.btSoftBody_Body_linearVelocity(swigCPtr, this);
  }
  
    public Vector3 angularVelocity(Vector3 rpos) {
  	return SoftbodyJNI.btSoftBody_Body_angularVelocity__SWIG_0(swigCPtr, this, rpos);
  }
  
    public Vector3 angularVelocity() {
  	return SoftbodyJNI.btSoftBody_Body_angularVelocity__SWIG_1(swigCPtr, this);
  }
  
    public Vector3 velocity(Vector3 rpos) {
  	return SoftbodyJNI.btSoftBody_Body_velocity(swigCPtr, this, rpos);
  }
  
    public void applyVImpulse(Vector3 impulse, Vector3 rpos) {
      SoftbodyJNI.btSoftBody_Body_applyVImpulse(swigCPtr, this, impulse, rpos);
    }
  
    public void applyDImpulse(Vector3 impulse, Vector3 rpos) {
      SoftbodyJNI.btSoftBody_Body_applyDImpulse(swigCPtr, this, impulse, rpos);
    }
  
    public void applyImpulse(btSoftBody.Impulse impulse, Vector3 rpos) {
      SoftbodyJNI.btSoftBody_Body_applyImpulse(swigCPtr, this, btSoftBody.Impulse.getCPtr(impulse), impulse, rpos);
    }
  
    public void applyVAImpulse(Vector3 impulse) {
      SoftbodyJNI.btSoftBody_Body_applyVAImpulse(swigCPtr, this, impulse);
    }
  
    public void applyDAImpulse(Vector3 impulse) {
      SoftbodyJNI.btSoftBody_Body_applyDAImpulse(swigCPtr, this, impulse);
    }
  
    public void applyAImpulse(btSoftBody.Impulse impulse) {
      SoftbodyJNI.btSoftBody_Body_applyAImpulse(swigCPtr, this, btSoftBody.Impulse.getCPtr(impulse), impulse);
    }
  
    public void applyDCImpulse(Vector3 impulse) {
      SoftbodyJNI.btSoftBody_Body_applyDCImpulse(swigCPtr, this, impulse);
    }
  
  }

  static public class Joint extends BulletBase {
  	private long swigCPtr;
  	
  	protected Joint(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Joint(long cPtr, boolean cMemoryOwn) {
  		this("Joint", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Joint obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Joint(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
      static public class eType extends BulletBase {
      	private long swigCPtr;
      	
      	protected eType(final String className, long cPtr, boolean cMemoryOwn) {
      		super(className, cPtr, cMemoryOwn);
      		swigCPtr = cPtr;
      	}
      	
      	 
      	public eType(long cPtr, boolean cMemoryOwn) {
      		this("eType", cPtr, cMemoryOwn);
      		construct();
      	}
      	
      	@Override
      	protected void reset(long cPtr, boolean cMemoryOwn) {
      		if (!destroyed)
      			destroy();
      		super.reset(swigCPtr = cPtr, cMemoryOwn);
      	}
      	
      	public static long getCPtr(eType obj) {
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
      				SoftbodyJNI.delete_btSoftBody_Joint_eType(swigCPtr);
      			}
      			swigCPtr = 0;
      		}
      		super.delete();
      	}
      
        public eType() {
          this(SoftbodyJNI.new_btSoftBody_Joint_eType(), true);
        }
      
        public final static class EnumFlagType {
          public final static int Linear = 0;
          public final static int Angular = Linear + 1;
          public final static int Contact = Angular + 1;
        }
      
      }
  
      static public class Specs extends BulletBase {
      	private long swigCPtr;
      	
      	protected Specs(final String className, long cPtr, boolean cMemoryOwn) {
      		super(className, cPtr, cMemoryOwn);
      		swigCPtr = cPtr;
      	}
      	
      	 
      	public Specs(long cPtr, boolean cMemoryOwn) {
      		this("Specs", cPtr, cMemoryOwn);
      		construct();
      	}
      	
      	@Override
      	protected void reset(long cPtr, boolean cMemoryOwn) {
      		if (!destroyed)
      			destroy();
      		super.reset(swigCPtr = cPtr, cMemoryOwn);
      	}
      	
      	public static long getCPtr(Specs obj) {
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
      				SoftbodyJNI.delete_btSoftBody_Joint_Specs(swigCPtr);
      			}
      			swigCPtr = 0;
      		}
      		super.delete();
      	}
      
        public Specs() {
          this(SoftbodyJNI.new_btSoftBody_Joint_Specs(), true);
        }
      
        public void setErp(float value) {
          SoftbodyJNI.btSoftBody_Joint_Specs_erp_set(swigCPtr, this, value);
        }
      
        public float getErp() {
          return SoftbodyJNI.btSoftBody_Joint_Specs_erp_get(swigCPtr, this);
        }
      
        public void setCfm(float value) {
          SoftbodyJNI.btSoftBody_Joint_Specs_cfm_set(swigCPtr, this, value);
        }
      
        public float getCfm() {
          return SoftbodyJNI.btSoftBody_Joint_Specs_cfm_get(swigCPtr, this);
        }
      
        public void setSplit(float value) {
          SoftbodyJNI.btSoftBody_Joint_Specs_split_set(swigCPtr, this, value);
        }
      
        public float getSplit() {
          return SoftbodyJNI.btSoftBody_Joint_Specs_split_get(swigCPtr, this);
        }
      
      }
  
    public void setBodies(btSoftBody.Body value) {
      SoftbodyJNI.btSoftBody_Joint_bodies_set(swigCPtr, this, btSoftBody.Body.getCPtr(value), value);
    }
  
    public btSoftBody.Body getBodies() {
      long cPtr = SoftbodyJNI.btSoftBody_Joint_bodies_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Body(cPtr, false);
    }
  
    public void setRefs(btVector3 value) {
      SoftbodyJNI.btSoftBody_Joint_refs_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getRefs() {
      long cPtr = SoftbodyJNI.btSoftBody_Joint_refs_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setCfm(float value) {
      SoftbodyJNI.btSoftBody_Joint_cfm_set(swigCPtr, this, value);
    }
  
    public float getCfm() {
      return SoftbodyJNI.btSoftBody_Joint_cfm_get(swigCPtr, this);
    }
  
    public void setErp(float value) {
      SoftbodyJNI.btSoftBody_Joint_erp_set(swigCPtr, this, value);
    }
  
    public float getErp() {
      return SoftbodyJNI.btSoftBody_Joint_erp_get(swigCPtr, this);
    }
  
    public void setSplit(float value) {
      SoftbodyJNI.btSoftBody_Joint_split_set(swigCPtr, this, value);
    }
  
    public float getSplit() {
      return SoftbodyJNI.btSoftBody_Joint_split_get(swigCPtr, this);
    }
  
    public void setDrift(btVector3 value) {
      SoftbodyJNI.btSoftBody_Joint_drift_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getDrift() {
      long cPtr = SoftbodyJNI.btSoftBody_Joint_drift_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setSdrift(btVector3 value) {
      SoftbodyJNI.btSoftBody_Joint_sdrift_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getSdrift() {
      long cPtr = SoftbodyJNI.btSoftBody_Joint_sdrift_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setMassmatrix(btMatrix3x3 value) {
      SoftbodyJNI.btSoftBody_Joint_massmatrix_set(swigCPtr, this, btMatrix3x3.getCPtr(value), value);
    }
  
    public btMatrix3x3 getMassmatrix() {
      long cPtr = SoftbodyJNI.btSoftBody_Joint_massmatrix_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btMatrix3x3(cPtr, false);
    }
  
    public void setDelete(boolean value) {
      SoftbodyJNI.btSoftBody_Joint_delete_set(swigCPtr, this, value);
    }
  
    public boolean getDelete() {
      return SoftbodyJNI.btSoftBody_Joint_delete_get(swigCPtr, this);
    }
  
    public void Prepare(float dt, int iterations) {
      SoftbodyJNI.btSoftBody_Joint_Prepare(swigCPtr, this, dt, iterations);
    }
  
    public void Solve(float dt, float sor) {
      SoftbodyJNI.btSoftBody_Joint_Solve(swigCPtr, this, dt, sor);
    }
  
    public void Terminate(float dt) {
      SoftbodyJNI.btSoftBody_Joint_Terminate(swigCPtr, this, dt);
    }
  
    public int Type() {
      return SoftbodyJNI.btSoftBody_Joint_Type(swigCPtr, this);
    }
  
  }

  static public class LJoint extends btSoftBody.Joint {
  	private long swigCPtr;
  	
  	protected LJoint(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_LJoint_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public LJoint(long cPtr, boolean cMemoryOwn) {
  		this("LJoint", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_LJoint_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(LJoint obj) {
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
  				SoftbodyJNI.delete_btSoftBody_LJoint(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
      static public class Specs extends btSoftBody.Joint.Specs {
      	private long swigCPtr;
      	
      	protected Specs(final String className, long cPtr, boolean cMemoryOwn) {
      		super(className, SoftbodyJNI.btSoftBody_LJoint_Specs_SWIGUpcast(cPtr), cMemoryOwn);
      		swigCPtr = cPtr;
      	}
      	
      	
      	public Specs(long cPtr, boolean cMemoryOwn) {
      		this("Specs", cPtr, cMemoryOwn);
      		construct();
      	}
      	
      	@Override
      	protected void reset(long cPtr, boolean cMemoryOwn) {
      		if (!destroyed)
      			destroy();
      		super.reset(SoftbodyJNI.btSoftBody_LJoint_Specs_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
      	}
      	
      	public static long getCPtr(Specs obj) {
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
      				SoftbodyJNI.delete_btSoftBody_LJoint_Specs(swigCPtr);
      			}
      			swigCPtr = 0;
      		}
      		super.delete();
      	}
      
        public void setPosition(btVector3 value) {
          SoftbodyJNI.btSoftBody_LJoint_Specs_position_set(swigCPtr, this, btVector3.getCPtr(value), value);
        }
      
        public btVector3 getPosition() {
          long cPtr = SoftbodyJNI.btSoftBody_LJoint_Specs_position_get(swigCPtr, this);
          return (cPtr == 0) ? null : new btVector3(cPtr, false);
        }
      
        public Specs() {
          this(SoftbodyJNI.new_btSoftBody_LJoint_Specs(), true);
        }
      
      }
  
    public void setRpos(btVector3 value) {
      SoftbodyJNI.btSoftBody_LJoint_rpos_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getRpos() {
      long cPtr = SoftbodyJNI.btSoftBody_LJoint_rpos_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public LJoint() {
      this(SoftbodyJNI.new_btSoftBody_LJoint(), true);
    }
  
  }

  static public class AJoint extends btSoftBody.Joint {
  	private long swigCPtr;
  	
  	protected AJoint(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_AJoint_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public AJoint(long cPtr, boolean cMemoryOwn) {
  		this("AJoint", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_AJoint_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(AJoint obj) {
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
  				SoftbodyJNI.delete_btSoftBody_AJoint(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
      static public class IControl extends BulletBase {
      	private long swigCPtr;
      	
      	protected IControl(final String className, long cPtr, boolean cMemoryOwn) {
      		super(className, cPtr, cMemoryOwn);
      		swigCPtr = cPtr;
      	}
      	
      	 
      	public IControl(long cPtr, boolean cMemoryOwn) {
      		this("IControl", cPtr, cMemoryOwn);
      		construct();
      	}
      	
      	@Override
      	protected void reset(long cPtr, boolean cMemoryOwn) {
      		if (!destroyed)
      			destroy();
      		super.reset(swigCPtr = cPtr, cMemoryOwn);
      	}
      	
      	public static long getCPtr(IControl obj) {
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
      				SoftbodyJNI.delete_btSoftBody_AJoint_IControl(swigCPtr);
      			}
      			swigCPtr = 0;
      		}
      		super.delete();
      	}
      
        public void Prepare(btSoftBody.AJoint arg0) {
          SoftbodyJNI.btSoftBody_AJoint_IControl_Prepare(swigCPtr, this, btSoftBody.AJoint.getCPtr(arg0), arg0);
        }
      
        public float Speed(btSoftBody.AJoint arg0, float current) {
          return SoftbodyJNI.btSoftBody_AJoint_IControl_Speed(swigCPtr, this, btSoftBody.AJoint.getCPtr(arg0), arg0, current);
        }
      
        public static btSoftBody.AJoint.IControl Default() {
          long cPtr = SoftbodyJNI.btSoftBody_AJoint_IControl_Default();
          return (cPtr == 0) ? null : new btSoftBody.AJoint.IControl(cPtr, false);
        }
      
        public IControl() {
          this(SoftbodyJNI.new_btSoftBody_AJoint_IControl(), true);
        }
      
      }
  
      static public class Specs extends btSoftBody.Joint.Specs {
      	private long swigCPtr;
      	
      	protected Specs(final String className, long cPtr, boolean cMemoryOwn) {
      		super(className, SoftbodyJNI.btSoftBody_AJoint_Specs_SWIGUpcast(cPtr), cMemoryOwn);
      		swigCPtr = cPtr;
      	}
      	
      	
      	public Specs(long cPtr, boolean cMemoryOwn) {
      		this("Specs", cPtr, cMemoryOwn);
      		construct();
      	}
      	
      	@Override
      	protected void reset(long cPtr, boolean cMemoryOwn) {
      		if (!destroyed)
      			destroy();
      		super.reset(SoftbodyJNI.btSoftBody_AJoint_Specs_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
      	}
      	
      	public static long getCPtr(Specs obj) {
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
      				SoftbodyJNI.delete_btSoftBody_AJoint_Specs(swigCPtr);
      			}
      			swigCPtr = 0;
      		}
      		super.delete();
      	}
      
        public Specs() {
          this(SoftbodyJNI.new_btSoftBody_AJoint_Specs(), true);
        }
      
        public void setAxis(btVector3 value) {
          SoftbodyJNI.btSoftBody_AJoint_Specs_axis_set(swigCPtr, this, btVector3.getCPtr(value), value);
        }
      
        public btVector3 getAxis() {
          long cPtr = SoftbodyJNI.btSoftBody_AJoint_Specs_axis_get(swigCPtr, this);
          return (cPtr == 0) ? null : new btVector3(cPtr, false);
        }
      
        public void setIcontrol(btSoftBody.AJoint.IControl value) {
          SoftbodyJNI.btSoftBody_AJoint_Specs_icontrol_set(swigCPtr, this, btSoftBody.AJoint.IControl.getCPtr(value), value);
        }
      
        public btSoftBody.AJoint.IControl getIcontrol() {
          long cPtr = SoftbodyJNI.btSoftBody_AJoint_Specs_icontrol_get(swigCPtr, this);
          return (cPtr == 0) ? null : new btSoftBody.AJoint.IControl(cPtr, false);
        }
      
      }
  
    public void setAxis(btVector3 value) {
      SoftbodyJNI.btSoftBody_AJoint_axis_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getAxis() {
      long cPtr = SoftbodyJNI.btSoftBody_AJoint_axis_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setIcontrol(btSoftBody.AJoint.IControl value) {
      SoftbodyJNI.btSoftBody_AJoint_icontrol_set(swigCPtr, this, btSoftBody.AJoint.IControl.getCPtr(value), value);
    }
  
    public btSoftBody.AJoint.IControl getIcontrol() {
      long cPtr = SoftbodyJNI.btSoftBody_AJoint_icontrol_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.AJoint.IControl(cPtr, false);
    }
  
    public AJoint() {
      this(SoftbodyJNI.new_btSoftBody_AJoint(), true);
    }
  
  }

  static public class CJoint extends btSoftBody.Joint {
  	private long swigCPtr;
  	
  	protected CJoint(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_CJoint_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public CJoint(long cPtr, boolean cMemoryOwn) {
  		this("CJoint", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_CJoint_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(CJoint obj) {
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
  				SoftbodyJNI.delete_btSoftBody_CJoint(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setLife(int value) {
      SoftbodyJNI.btSoftBody_CJoint_life_set(swigCPtr, this, value);
    }
  
    public int getLife() {
      return SoftbodyJNI.btSoftBody_CJoint_life_get(swigCPtr, this);
    }
  
    public void setMaxlife(int value) {
      SoftbodyJNI.btSoftBody_CJoint_maxlife_set(swigCPtr, this, value);
    }
  
    public int getMaxlife() {
      return SoftbodyJNI.btSoftBody_CJoint_maxlife_get(swigCPtr, this);
    }
  
    public void setRpos(btVector3 value) {
      SoftbodyJNI.btSoftBody_CJoint_rpos_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getRpos() {
      long cPtr = SoftbodyJNI.btSoftBody_CJoint_rpos_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setNormal(btVector3 value) {
      SoftbodyJNI.btSoftBody_CJoint_normal_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getNormal() {
      long cPtr = SoftbodyJNI.btSoftBody_CJoint_normal_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setFriction(float value) {
      SoftbodyJNI.btSoftBody_CJoint_friction_set(swigCPtr, this, value);
    }
  
    public float getFriction() {
      return SoftbodyJNI.btSoftBody_CJoint_friction_get(swigCPtr, this);
    }
  
    public CJoint() {
      this(SoftbodyJNI.new_btSoftBody_CJoint(), true);
    }
  
  }

  static public class Config extends BulletBase {
  	private long swigCPtr;
  	
  	protected Config(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public Config(long cPtr, boolean cMemoryOwn) {
  		this("Config", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(Config obj) {
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
  				SoftbodyJNI.delete_btSoftBody_Config(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setAeromodel(int value) {
      SoftbodyJNI.btSoftBody_Config_aeromodel_set(swigCPtr, this, value);
    }
  
    public int getAeromodel() {
      return SoftbodyJNI.btSoftBody_Config_aeromodel_get(swigCPtr, this);
    }
  
    public void setKVCF(float value) {
      SoftbodyJNI.btSoftBody_Config_kVCF_set(swigCPtr, this, value);
    }
  
    public float getKVCF() {
      return SoftbodyJNI.btSoftBody_Config_kVCF_get(swigCPtr, this);
    }
  
    public void setKDP(float value) {
      SoftbodyJNI.btSoftBody_Config_kDP_set(swigCPtr, this, value);
    }
  
    public float getKDP() {
      return SoftbodyJNI.btSoftBody_Config_kDP_get(swigCPtr, this);
    }
  
    public void setKDG(float value) {
      SoftbodyJNI.btSoftBody_Config_kDG_set(swigCPtr, this, value);
    }
  
    public float getKDG() {
      return SoftbodyJNI.btSoftBody_Config_kDG_get(swigCPtr, this);
    }
  
    public void setKLF(float value) {
      SoftbodyJNI.btSoftBody_Config_kLF_set(swigCPtr, this, value);
    }
  
    public float getKLF() {
      return SoftbodyJNI.btSoftBody_Config_kLF_get(swigCPtr, this);
    }
  
    public void setKPR(float value) {
      SoftbodyJNI.btSoftBody_Config_kPR_set(swigCPtr, this, value);
    }
  
    public float getKPR() {
      return SoftbodyJNI.btSoftBody_Config_kPR_get(swigCPtr, this);
    }
  
    public void setKVC(float value) {
      SoftbodyJNI.btSoftBody_Config_kVC_set(swigCPtr, this, value);
    }
  
    public float getKVC() {
      return SoftbodyJNI.btSoftBody_Config_kVC_get(swigCPtr, this);
    }
  
    public void setKDF(float value) {
      SoftbodyJNI.btSoftBody_Config_kDF_set(swigCPtr, this, value);
    }
  
    public float getKDF() {
      return SoftbodyJNI.btSoftBody_Config_kDF_get(swigCPtr, this);
    }
  
    public void setKMT(float value) {
      SoftbodyJNI.btSoftBody_Config_kMT_set(swigCPtr, this, value);
    }
  
    public float getKMT() {
      return SoftbodyJNI.btSoftBody_Config_kMT_get(swigCPtr, this);
    }
  
    public void setKCHR(float value) {
      SoftbodyJNI.btSoftBody_Config_kCHR_set(swigCPtr, this, value);
    }
  
    public float getKCHR() {
      return SoftbodyJNI.btSoftBody_Config_kCHR_get(swigCPtr, this);
    }
  
    public void setKKHR(float value) {
      SoftbodyJNI.btSoftBody_Config_kKHR_set(swigCPtr, this, value);
    }
  
    public float getKKHR() {
      return SoftbodyJNI.btSoftBody_Config_kKHR_get(swigCPtr, this);
    }
  
    public void setKSHR(float value) {
      SoftbodyJNI.btSoftBody_Config_kSHR_set(swigCPtr, this, value);
    }
  
    public float getKSHR() {
      return SoftbodyJNI.btSoftBody_Config_kSHR_get(swigCPtr, this);
    }
  
    public void setKAHR(float value) {
      SoftbodyJNI.btSoftBody_Config_kAHR_set(swigCPtr, this, value);
    }
  
    public float getKAHR() {
      return SoftbodyJNI.btSoftBody_Config_kAHR_get(swigCPtr, this);
    }
  
    public void setKSRHR_CL(float value) {
      SoftbodyJNI.btSoftBody_Config_kSRHR_CL_set(swigCPtr, this, value);
    }
  
    public float getKSRHR_CL() {
      return SoftbodyJNI.btSoftBody_Config_kSRHR_CL_get(swigCPtr, this);
    }
  
    public void setKSKHR_CL(float value) {
      SoftbodyJNI.btSoftBody_Config_kSKHR_CL_set(swigCPtr, this, value);
    }
  
    public float getKSKHR_CL() {
      return SoftbodyJNI.btSoftBody_Config_kSKHR_CL_get(swigCPtr, this);
    }
  
    public void setKSSHR_CL(float value) {
      SoftbodyJNI.btSoftBody_Config_kSSHR_CL_set(swigCPtr, this, value);
    }
  
    public float getKSSHR_CL() {
      return SoftbodyJNI.btSoftBody_Config_kSSHR_CL_get(swigCPtr, this);
    }
  
    public void setKSR_SPLT_CL(float value) {
      SoftbodyJNI.btSoftBody_Config_kSR_SPLT_CL_set(swigCPtr, this, value);
    }
  
    public float getKSR_SPLT_CL() {
      return SoftbodyJNI.btSoftBody_Config_kSR_SPLT_CL_get(swigCPtr, this);
    }
  
    public void setKSK_SPLT_CL(float value) {
      SoftbodyJNI.btSoftBody_Config_kSK_SPLT_CL_set(swigCPtr, this, value);
    }
  
    public float getKSK_SPLT_CL() {
      return SoftbodyJNI.btSoftBody_Config_kSK_SPLT_CL_get(swigCPtr, this);
    }
  
    public void setKSS_SPLT_CL(float value) {
      SoftbodyJNI.btSoftBody_Config_kSS_SPLT_CL_set(swigCPtr, this, value);
    }
  
    public float getKSS_SPLT_CL() {
      return SoftbodyJNI.btSoftBody_Config_kSS_SPLT_CL_get(swigCPtr, this);
    }
  
    public void setMaxvolume(float value) {
      SoftbodyJNI.btSoftBody_Config_maxvolume_set(swigCPtr, this, value);
    }
  
    public float getMaxvolume() {
      return SoftbodyJNI.btSoftBody_Config_maxvolume_get(swigCPtr, this);
    }
  
    public void setTimescale(float value) {
      SoftbodyJNI.btSoftBody_Config_timescale_set(swigCPtr, this, value);
    }
  
    public float getTimescale() {
      return SoftbodyJNI.btSoftBody_Config_timescale_get(swigCPtr, this);
    }
  
    public void setViterations(int value) {
      SoftbodyJNI.btSoftBody_Config_viterations_set(swigCPtr, this, value);
    }
  
    public int getViterations() {
      return SoftbodyJNI.btSoftBody_Config_viterations_get(swigCPtr, this);
    }
  
    public void setPiterations(int value) {
      SoftbodyJNI.btSoftBody_Config_piterations_set(swigCPtr, this, value);
    }
  
    public int getPiterations() {
      return SoftbodyJNI.btSoftBody_Config_piterations_get(swigCPtr, this);
    }
  
    public void setDiterations(int value) {
      SoftbodyJNI.btSoftBody_Config_diterations_set(swigCPtr, this, value);
    }
  
    public int getDiterations() {
      return SoftbodyJNI.btSoftBody_Config_diterations_get(swigCPtr, this);
    }
  
    public void setCiterations(int value) {
      SoftbodyJNI.btSoftBody_Config_citerations_set(swigCPtr, this, value);
    }
  
    public int getCiterations() {
      return SoftbodyJNI.btSoftBody_Config_citerations_get(swigCPtr, this);
    }
  
    public void setCollisions(int value) {
      SoftbodyJNI.btSoftBody_Config_collisions_set(swigCPtr, this, value);
    }
  
    public int getCollisions() {
      return SoftbodyJNI.btSoftBody_Config_collisions_get(swigCPtr, this);
    }
  
    public void setVsequence(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t value) {
      SoftbodyJNI.btSoftBody_Config_vsequence_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t.getCPtr(value));
    }
  
    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t getVsequence() {
      long cPtr = SoftbodyJNI.btSoftBody_Config_vsequence_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__eVSolver____t(cPtr, false);
    }
  
    public void setPsequence(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t value) {
      SoftbodyJNI.btSoftBody_Config_psequence_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t.getCPtr(value));
    }
  
    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t getPsequence() {
      long cPtr = SoftbodyJNI.btSoftBody_Config_psequence_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t(cPtr, false);
    }
  
    public void setDsequence(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t value) {
      SoftbodyJNI.btSoftBody_Config_dsequence_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t.getCPtr(value));
    }
  
    public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t getDsequence() {
      long cPtr = SoftbodyJNI.btSoftBody_Config_dsequence_get(swigCPtr, this);
      return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__ePSolver____t(cPtr, false);
    }
  
    public Config() {
      this(SoftbodyJNI.new_btSoftBody_Config(), true);
    }
  
  }

  static public class SolverState extends BulletBase {
  	private long swigCPtr;
  	
  	protected SolverState(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, cPtr, cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	 
  	public SolverState(long cPtr, boolean cMemoryOwn) {
  		this("SolverState", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(swigCPtr = cPtr, cMemoryOwn);
  	}
  	
  	public static long getCPtr(SolverState obj) {
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
  				SoftbodyJNI.delete_btSoftBody_SolverState(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setSdt(float value) {
      SoftbodyJNI.btSoftBody_SolverState_sdt_set(swigCPtr, this, value);
    }
  
    public float getSdt() {
      return SoftbodyJNI.btSoftBody_SolverState_sdt_get(swigCPtr, this);
    }
  
    public void setIsdt(float value) {
      SoftbodyJNI.btSoftBody_SolverState_isdt_set(swigCPtr, this, value);
    }
  
    public float getIsdt() {
      return SoftbodyJNI.btSoftBody_SolverState_isdt_get(swigCPtr, this);
    }
  
    public void setVelmrg(float value) {
      SoftbodyJNI.btSoftBody_SolverState_velmrg_set(swigCPtr, this, value);
    }
  
    public float getVelmrg() {
      return SoftbodyJNI.btSoftBody_SolverState_velmrg_get(swigCPtr, this);
    }
  
    public void setRadmrg(float value) {
      SoftbodyJNI.btSoftBody_SolverState_radmrg_set(swigCPtr, this, value);
    }
  
    public float getRadmrg() {
      return SoftbodyJNI.btSoftBody_SolverState_radmrg_get(swigCPtr, this);
    }
  
    public void setUpdmrg(float value) {
      SoftbodyJNI.btSoftBody_SolverState_updmrg_set(swigCPtr, this, value);
    }
  
    public float getUpdmrg() {
      return SoftbodyJNI.btSoftBody_SolverState_updmrg_get(swigCPtr, this);
    }
  
    public SolverState() {
      this(SoftbodyJNI.new_btSoftBody_SolverState(), true);
    }
  
  }

  static public class RayFromToCaster extends ICollide {
  	private long swigCPtr;
  	
  	protected RayFromToCaster(final String className, long cPtr, boolean cMemoryOwn) {
  		super(className, SoftbodyJNI.btSoftBody_RayFromToCaster_SWIGUpcast(cPtr), cMemoryOwn);
  		swigCPtr = cPtr;
  	}
  	
  	
  	public RayFromToCaster(long cPtr, boolean cMemoryOwn) {
  		this("RayFromToCaster", cPtr, cMemoryOwn);
  		construct();
  	}
  	
  	@Override
  	protected void reset(long cPtr, boolean cMemoryOwn) {
  		if (!destroyed)
  			destroy();
  		super.reset(SoftbodyJNI.btSoftBody_RayFromToCaster_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
  	}
  	
  	public static long getCPtr(RayFromToCaster obj) {
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
  				SoftbodyJNI.delete_btSoftBody_RayFromToCaster(swigCPtr);
  			}
  			swigCPtr = 0;
  		}
  		super.delete();
  	}
  
    public void setRayFrom(btVector3 value) {
      SoftbodyJNI.btSoftBody_RayFromToCaster_rayFrom_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getRayFrom() {
      long cPtr = SoftbodyJNI.btSoftBody_RayFromToCaster_rayFrom_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setRayTo(btVector3 value) {
      SoftbodyJNI.btSoftBody_RayFromToCaster_rayTo_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getRayTo() {
      long cPtr = SoftbodyJNI.btSoftBody_RayFromToCaster_rayTo_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setRayNormalizedDirection(btVector3 value) {
      SoftbodyJNI.btSoftBody_RayFromToCaster_rayNormalizedDirection_set(swigCPtr, this, btVector3.getCPtr(value), value);
    }
  
    public btVector3 getRayNormalizedDirection() {
      long cPtr = SoftbodyJNI.btSoftBody_RayFromToCaster_rayNormalizedDirection_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btVector3(cPtr, false);
    }
  
    public void setMint(float value) {
      SoftbodyJNI.btSoftBody_RayFromToCaster_mint_set(swigCPtr, this, value);
    }
  
    public float getMint() {
      return SoftbodyJNI.btSoftBody_RayFromToCaster_mint_get(swigCPtr, this);
    }
  
    public void setFace(btSoftBody.Face value) {
      SoftbodyJNI.btSoftBody_RayFromToCaster_face_set(swigCPtr, this, btSoftBody.Face.getCPtr(value), value);
    }
  
    public btSoftBody.Face getFace() {
      long cPtr = SoftbodyJNI.btSoftBody_RayFromToCaster_face_get(swigCPtr, this);
      return (cPtr == 0) ? null : new btSoftBody.Face(cPtr, false);
    }
  
    public void setTests(int value) {
      SoftbodyJNI.btSoftBody_RayFromToCaster_tests_set(swigCPtr, this, value);
    }
  
    public int getTests() {
      return SoftbodyJNI.btSoftBody_RayFromToCaster_tests_get(swigCPtr, this);
    }
  
    public RayFromToCaster(Vector3 rayFrom, Vector3 rayTo, float mxt) {
      this(SoftbodyJNI.new_btSoftBody_RayFromToCaster(rayFrom, rayTo, mxt), true);
    }
  
    public static float rayFromToTriangle(Vector3 rayFrom, Vector3 rayTo, Vector3 rayNormalizedDirection, Vector3 a, Vector3 b, Vector3 c, float maxt) {
      return SoftbodyJNI.btSoftBody_RayFromToCaster_rayFromToTriangle__SWIG_0(rayFrom, rayTo, rayNormalizedDirection, a, b, c, maxt);
    }
  
    public static float rayFromToTriangle(Vector3 rayFrom, Vector3 rayTo, Vector3 rayNormalizedDirection, Vector3 a, Vector3 b, Vector3 c) {
      return SoftbodyJNI.btSoftBody_RayFromToCaster_rayFromToTriangle__SWIG_1(rayFrom, rayTo, rayNormalizedDirection, a, b, c);
    }
  
  }

  public void setCfg(btSoftBody.Config value) {
    SoftbodyJNI.btSoftBody_cfg_set(swigCPtr, this, btSoftBody.Config.getCPtr(value), value);
  }

  public btSoftBody.Config getCfg() {
    long cPtr = SoftbodyJNI.btSoftBody_cfg_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSoftBody.Config(cPtr, false);
  }

  public void setSst(btSoftBody.SolverState value) {
    SoftbodyJNI.btSoftBody_sst_set(swigCPtr, this, btSoftBody.SolverState.getCPtr(value), value);
  }

  public btSoftBody.SolverState getSst() {
    long cPtr = SoftbodyJNI.btSoftBody_sst_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSoftBody.SolverState(cPtr, false);
  }

  public void setPose(btSoftBody.Pose value) {
    SoftbodyJNI.btSoftBody_pose_set(swigCPtr, this, btSoftBody.Pose.getCPtr(value), value);
  }

  public btSoftBody.Pose getPose() {
    long cPtr = SoftbodyJNI.btSoftBody_pose_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSoftBody.Pose(cPtr, false);
  }

  public void setTag(long value) {
    SoftbodyJNI.btSoftBody_tag_set(swigCPtr, this, value);
  }

  public long getTag() {
    return SoftbodyJNI.btSoftBody_tag_get(swigCPtr, this);
  }

  public void setWorldInfo(btSoftBodyWorldInfo value) {
    SoftbodyJNI.btSoftBody_worldInfo_set(swigCPtr, this, btSoftBodyWorldInfo.getCPtr(value), value);
  }

  public btSoftBodyWorldInfo getWorldInfo() {
    long cPtr = SoftbodyJNI.btSoftBody_worldInfo_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btSoftBodyWorldInfo(cPtr, false);
  }

  public void setNotes(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t value) {
    SoftbodyJNI.btSoftBody_notes_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t getNotes() {
    long cPtr = SoftbodyJNI.btSoftBody_notes_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Note_t(cPtr, false);
  }

  public void setNodes(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t value) {
    SoftbodyJNI.btSoftBody_nodes_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t getNodes() {
    long cPtr = SoftbodyJNI.btSoftBody_nodes_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Node_t(cPtr, false);
  }

  public void setLinks(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t value) {
    SoftbodyJNI.btSoftBody_links_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t getLinks() {
    long cPtr = SoftbodyJNI.btSoftBody_links_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Link_t(cPtr, false);
  }

  public void setFaces(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t value) {
    SoftbodyJNI.btSoftBody_faces_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t getFaces() {
    long cPtr = SoftbodyJNI.btSoftBody_faces_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Face_t(cPtr, false);
  }

  public void setTetras(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t value) {
    SoftbodyJNI.btSoftBody_tetras_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t getTetras() {
    long cPtr = SoftbodyJNI.btSoftBody_tetras_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Tetra_t(cPtr, false);
  }

  public void setAnchors(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t value) {
    SoftbodyJNI.btSoftBody_anchors_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t getAnchors() {
    long cPtr = SoftbodyJNI.btSoftBody_anchors_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Anchor_t(cPtr, false);
  }

  public void setRcontacts(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t value) {
    SoftbodyJNI.btSoftBody_rcontacts_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t getRcontacts() {
    long cPtr = SoftbodyJNI.btSoftBody_rcontacts_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__RContact_t(cPtr, false);
  }

  public void setScontacts(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t value) {
    SoftbodyJNI.btSoftBody_scontacts_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t getScontacts() {
    long cPtr = SoftbodyJNI.btSoftBody_scontacts_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__SContact_t(cPtr, false);
  }

  public void setJoints(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t value) {
    SoftbodyJNI.btSoftBody_joints_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t getJoints() {
    long cPtr = SoftbodyJNI.btSoftBody_joints_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Joint_p_t(cPtr, false);
  }

  public void setMaterials(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t value) {
    SoftbodyJNI.btSoftBody_materials_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t getMaterials() {
    long cPtr = SoftbodyJNI.btSoftBody_materials_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Material_p_t(cPtr, false);
  }

  public void setTimeacc(float value) {
    SoftbodyJNI.btSoftBody_timeacc_set(swigCPtr, this, value);
  }

  public float getTimeacc() {
    return SoftbodyJNI.btSoftBody_timeacc_get(swigCPtr, this);
  }

  public void setBounds(btVector3 value) {
    SoftbodyJNI.btSoftBody_bounds_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getBounds() {
    long cPtr = SoftbodyJNI.btSoftBody_bounds_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setBUpdateRtCst(boolean value) {
    SoftbodyJNI.btSoftBody_bUpdateRtCst_set(swigCPtr, this, value);
  }

  public boolean getBUpdateRtCst() {
    return SoftbodyJNI.btSoftBody_bUpdateRtCst_get(swigCPtr, this);
  }

  public void setNdbvt(btDbvt value) {
    SoftbodyJNI.btSoftBody_ndbvt_set(swigCPtr, this, btDbvt.getCPtr(value), value);
  }

  public btDbvt getNdbvt() {
    long cPtr = SoftbodyJNI.btSoftBody_ndbvt_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btDbvt(cPtr, false);
  }

  public void setFdbvt(btDbvt value) {
    SoftbodyJNI.btSoftBody_fdbvt_set(swigCPtr, this, btDbvt.getCPtr(value), value);
  }

  public btDbvt getFdbvt() {
    long cPtr = SoftbodyJNI.btSoftBody_fdbvt_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btDbvt(cPtr, false);
  }

  public void setCdbvt(btDbvt value) {
    SoftbodyJNI.btSoftBody_cdbvt_set(swigCPtr, this, btDbvt.getCPtr(value), value);
  }

  public btDbvt getCdbvt() {
    long cPtr = SoftbodyJNI.btSoftBody_cdbvt_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btDbvt(cPtr, false);
  }

  public void setClusters(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t value) {
    SoftbodyJNI.btSoftBody_clusters_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t getClusters() {
    long cPtr = SoftbodyJNI.btSoftBody_clusters_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody__Cluster_p_t(cPtr, false);
  }

  public void setClusterConnectivity(SWIGTYPE_p_btAlignedObjectArrayT_bool_t value) {
    SoftbodyJNI.btSoftBody_clusterConnectivity_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_bool_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_bool_t getClusterConnectivity() {
    long cPtr = SoftbodyJNI.btSoftBody_clusterConnectivity_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_bool_t(cPtr, false);
  }

  public void setInitialWorldTransform(btTransform value) {
    SoftbodyJNI.btSoftBody_initialWorldTransform_set(swigCPtr, this, btTransform.getCPtr(value), value);
  }

  public btTransform getInitialWorldTransform() {
    long cPtr = SoftbodyJNI.btSoftBody_initialWorldTransform_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btTransform(cPtr, false);
  }

  public void setWindVelocity(btVector3 value) {
    SoftbodyJNI.btSoftBody_windVelocity_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getWindVelocity() {
    long cPtr = SoftbodyJNI.btSoftBody_windVelocity_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setRestLengthScale(float value) {
    SoftbodyJNI.btSoftBody_restLengthScale_set(swigCPtr, this, value);
  }

  public float getRestLengthScale() {
    return SoftbodyJNI.btSoftBody_restLengthScale_get(swigCPtr, this);
  }

  static private long SwigConstructbtSoftBody(btSoftBodyWorldInfo worldInfo, int node_count, btVector3 x, java.nio.FloatBuffer m) {
    assert m.isDirect() : "Buffer must be allocated direct.";
    return SoftbodyJNI.new_btSoftBody__SWIG_0(btSoftBodyWorldInfo.getCPtr(worldInfo), worldInfo, node_count, btVector3.getCPtr(x), x, m);
  }

  public btSoftBody(btSoftBodyWorldInfo worldInfo, int node_count, btVector3 x, java.nio.FloatBuffer m) {
    this(btSoftBody.SwigConstructbtSoftBody(worldInfo, node_count, x, m), true);
  }

  public btSoftBody(btSoftBodyWorldInfo worldInfo) {
    this(SoftbodyJNI.new_btSoftBody__SWIG_1(btSoftBodyWorldInfo.getCPtr(worldInfo), worldInfo), true);
  }

  public void initDefaults() {
    SoftbodyJNI.btSoftBody_initDefaults(swigCPtr, this);
  }

  public void setUserIndexMapping(SWIGTYPE_p_btAlignedObjectArrayT_int_t value) {
    SoftbodyJNI.btSoftBody_userIndexMapping_set(swigCPtr, this, SWIGTYPE_p_btAlignedObjectArrayT_int_t.getCPtr(value));
  }

  public SWIGTYPE_p_btAlignedObjectArrayT_int_t getUserIndexMapping() {
    long cPtr = SoftbodyJNI.btSoftBody_userIndexMapping_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_btAlignedObjectArrayT_int_t(cPtr, false);
  }

  public boolean checkLink(int node0, int node1) {
    return SoftbodyJNI.btSoftBody_checkLink__SWIG_0(swigCPtr, this, node0, node1);
  }

  public boolean checkLink(btSoftBody.Node node0, btSoftBody.Node node1) {
    return SoftbodyJNI.btSoftBody_checkLink__SWIG_1(swigCPtr, this, btSoftBody.Node.getCPtr(node0), node0, btSoftBody.Node.getCPtr(node1), node1);
  }

  public boolean checkFace(int node0, int node1, int node2) {
    return SoftbodyJNI.btSoftBody_checkFace(swigCPtr, this, node0, node1, node2);
  }

  public btSoftBody.Material appendMaterial() {
    long cPtr = SoftbodyJNI.btSoftBody_appendMaterial(swigCPtr, this);
    return (cPtr == 0) ? null : new btSoftBody.Material(cPtr, false);
  }

  public void appendNote(String text, Vector3 o, btVector4 c, btSoftBody.Node n0, btSoftBody.Node n1, btSoftBody.Node n2, btSoftBody.Node n3) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_0(swigCPtr, this, text, o, btVector4.getCPtr(c), c, btSoftBody.Node.getCPtr(n0), n0, btSoftBody.Node.getCPtr(n1), n1, btSoftBody.Node.getCPtr(n2), n2, btSoftBody.Node.getCPtr(n3), n3);
  }

  public void appendNote(String text, Vector3 o, btVector4 c, btSoftBody.Node n0, btSoftBody.Node n1, btSoftBody.Node n2) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_1(swigCPtr, this, text, o, btVector4.getCPtr(c), c, btSoftBody.Node.getCPtr(n0), n0, btSoftBody.Node.getCPtr(n1), n1, btSoftBody.Node.getCPtr(n2), n2);
  }

  public void appendNote(String text, Vector3 o, btVector4 c, btSoftBody.Node n0, btSoftBody.Node n1) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_2(swigCPtr, this, text, o, btVector4.getCPtr(c), c, btSoftBody.Node.getCPtr(n0), n0, btSoftBody.Node.getCPtr(n1), n1);
  }

  public void appendNote(String text, Vector3 o, btVector4 c, btSoftBody.Node n0) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_3(swigCPtr, this, text, o, btVector4.getCPtr(c), c, btSoftBody.Node.getCPtr(n0), n0);
  }

  public void appendNote(String text, Vector3 o, btVector4 c) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_4(swigCPtr, this, text, o, btVector4.getCPtr(c), c);
  }

  public void appendNote(String text, Vector3 o) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_5(swigCPtr, this, text, o);
  }

  public void appendNote(String text, Vector3 o, btSoftBody.Node feature) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_6(swigCPtr, this, text, o, btSoftBody.Node.getCPtr(feature), feature);
  }

  public void appendNote(String text, Vector3 o, btSoftBody.Link feature) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_7(swigCPtr, this, text, o, btSoftBody.Link.getCPtr(feature), feature);
  }

  public void appendNote(String text, Vector3 o, btSoftBody.Face feature) {
    SoftbodyJNI.btSoftBody_appendNote__SWIG_8(swigCPtr, this, text, o, btSoftBody.Face.getCPtr(feature), feature);
  }

  public void appendNode(Vector3 x, float m) {
    SoftbodyJNI.btSoftBody_appendNode(swigCPtr, this, x, m);
  }

  public void appendLink(int model, btSoftBody.Material mat) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_0(swigCPtr, this, model, btSoftBody.Material.getCPtr(mat), mat);
  }

  public void appendLink(int model) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_1(swigCPtr, this, model);
  }

  public void appendLink() {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_2(swigCPtr, this);
  }

  public void appendLink(int node0, int node1, btSoftBody.Material mat, boolean bcheckexist) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_3(swigCPtr, this, node0, node1, btSoftBody.Material.getCPtr(mat), mat, bcheckexist);
  }

  public void appendLink(int node0, int node1, btSoftBody.Material mat) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_4(swigCPtr, this, node0, node1, btSoftBody.Material.getCPtr(mat), mat);
  }

  public void appendLink(int node0, int node1) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_5(swigCPtr, this, node0, node1);
  }

  public void appendLink(btSoftBody.Node node0, btSoftBody.Node node1, btSoftBody.Material mat, boolean bcheckexist) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_6(swigCPtr, this, btSoftBody.Node.getCPtr(node0), node0, btSoftBody.Node.getCPtr(node1), node1, btSoftBody.Material.getCPtr(mat), mat, bcheckexist);
  }

  public void appendLink(btSoftBody.Node node0, btSoftBody.Node node1, btSoftBody.Material mat) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_7(swigCPtr, this, btSoftBody.Node.getCPtr(node0), node0, btSoftBody.Node.getCPtr(node1), node1, btSoftBody.Material.getCPtr(mat), mat);
  }

  public void appendLink(btSoftBody.Node node0, btSoftBody.Node node1) {
    SoftbodyJNI.btSoftBody_appendLink__SWIG_8(swigCPtr, this, btSoftBody.Node.getCPtr(node0), node0, btSoftBody.Node.getCPtr(node1), node1);
  }

  public void appendFace(int model, btSoftBody.Material mat) {
    SoftbodyJNI.btSoftBody_appendFace__SWIG_0(swigCPtr, this, model, btSoftBody.Material.getCPtr(mat), mat);
  }

  public void appendFace(int model) {
    SoftbodyJNI.btSoftBody_appendFace__SWIG_1(swigCPtr, this, model);
  }

  public void appendFace() {
    SoftbodyJNI.btSoftBody_appendFace__SWIG_2(swigCPtr, this);
  }

  public void appendFace(int node0, int node1, int node2, btSoftBody.Material mat) {
    SoftbodyJNI.btSoftBody_appendFace__SWIG_3(swigCPtr, this, node0, node1, node2, btSoftBody.Material.getCPtr(mat), mat);
  }

  public void appendFace(int node0, int node1, int node2) {
    SoftbodyJNI.btSoftBody_appendFace__SWIG_4(swigCPtr, this, node0, node1, node2);
  }

  public void appendTetra(int model, btSoftBody.Material mat) {
    SoftbodyJNI.btSoftBody_appendTetra__SWIG_0(swigCPtr, this, model, btSoftBody.Material.getCPtr(mat), mat);
  }

  public void appendTetra(int node0, int node1, int node2, int node3, btSoftBody.Material mat) {
    SoftbodyJNI.btSoftBody_appendTetra__SWIG_1(swigCPtr, this, node0, node1, node2, node3, btSoftBody.Material.getCPtr(mat), mat);
  }

  public void appendTetra(int node0, int node1, int node2, int node3) {
    SoftbodyJNI.btSoftBody_appendTetra__SWIG_2(swigCPtr, this, node0, node1, node2, node3);
  }

  public void appendAnchor(int node, btRigidBody body, boolean disableCollisionBetweenLinkedBodies, float influence) {
    SoftbodyJNI.btSoftBody_appendAnchor__SWIG_0(swigCPtr, this, node, btRigidBody.getCPtr(body), body, disableCollisionBetweenLinkedBodies, influence);
  }

  public void appendAnchor(int node, btRigidBody body, boolean disableCollisionBetweenLinkedBodies) {
    SoftbodyJNI.btSoftBody_appendAnchor__SWIG_1(swigCPtr, this, node, btRigidBody.getCPtr(body), body, disableCollisionBetweenLinkedBodies);
  }

  public void appendAnchor(int node, btRigidBody body) {
    SoftbodyJNI.btSoftBody_appendAnchor__SWIG_2(swigCPtr, this, node, btRigidBody.getCPtr(body), body);
  }

  public void appendAnchor(int node, btRigidBody body, Vector3 localPivot, boolean disableCollisionBetweenLinkedBodies, float influence) {
    SoftbodyJNI.btSoftBody_appendAnchor__SWIG_3(swigCPtr, this, node, btRigidBody.getCPtr(body), body, localPivot, disableCollisionBetweenLinkedBodies, influence);
  }

  public void appendAnchor(int node, btRigidBody body, Vector3 localPivot, boolean disableCollisionBetweenLinkedBodies) {
    SoftbodyJNI.btSoftBody_appendAnchor__SWIG_4(swigCPtr, this, node, btRigidBody.getCPtr(body), body, localPivot, disableCollisionBetweenLinkedBodies);
  }

  public void appendAnchor(int node, btRigidBody body, Vector3 localPivot) {
    SoftbodyJNI.btSoftBody_appendAnchor__SWIG_5(swigCPtr, this, node, btRigidBody.getCPtr(body), body, localPivot);
  }

  public void appendLinearJoint(btSoftBody.LJoint.Specs specs, btSoftBody.Cluster body0, btSoftBody.Body body1) {
    SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_0(swigCPtr, this, btSoftBody.LJoint.Specs.getCPtr(specs), specs, btSoftBody.Cluster.getCPtr(body0), body0, btSoftBody.Body.getCPtr(body1), body1);
  }

  public void appendLinearJoint(btSoftBody.LJoint.Specs specs, btSoftBody.Body body) {
    SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_1(swigCPtr, this, btSoftBody.LJoint.Specs.getCPtr(specs), specs, btSoftBody.Body.getCPtr(body), body);
  }

  public void appendLinearJoint(btSoftBody.LJoint.Specs specs) {
    SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_2(swigCPtr, this, btSoftBody.LJoint.Specs.getCPtr(specs), specs);
  }

  public void appendLinearJoint(btSoftBody.LJoint.Specs specs, btSoftBody body) {
    SoftbodyJNI.btSoftBody_appendLinearJoint__SWIG_3(swigCPtr, this, btSoftBody.LJoint.Specs.getCPtr(specs), specs, btSoftBody.getCPtr(body), body);
  }

  public void appendAngularJoint(btSoftBody.AJoint.Specs specs, btSoftBody.Cluster body0, btSoftBody.Body body1) {
    SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_0(swigCPtr, this, btSoftBody.AJoint.Specs.getCPtr(specs), specs, btSoftBody.Cluster.getCPtr(body0), body0, btSoftBody.Body.getCPtr(body1), body1);
  }

  public void appendAngularJoint(btSoftBody.AJoint.Specs specs, btSoftBody.Body body) {
    SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_1(swigCPtr, this, btSoftBody.AJoint.Specs.getCPtr(specs), specs, btSoftBody.Body.getCPtr(body), body);
  }

  public void appendAngularJoint(btSoftBody.AJoint.Specs specs) {
    SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_2(swigCPtr, this, btSoftBody.AJoint.Specs.getCPtr(specs), specs);
  }

  public void appendAngularJoint(btSoftBody.AJoint.Specs specs, btSoftBody body) {
    SoftbodyJNI.btSoftBody_appendAngularJoint__SWIG_3(swigCPtr, this, btSoftBody.AJoint.Specs.getCPtr(specs), specs, btSoftBody.getCPtr(body), body);
  }

  public void addForce(Vector3 force) {
    SoftbodyJNI.btSoftBody_addForce__SWIG_0(swigCPtr, this, force);
  }

  public void addForce(Vector3 force, int node) {
    SoftbodyJNI.btSoftBody_addForce__SWIG_1(swigCPtr, this, force, node);
  }

  public void addAeroForceToNode(Vector3 windVelocity, int nodeIndex) {
    SoftbodyJNI.btSoftBody_addAeroForceToNode(swigCPtr, this, windVelocity, nodeIndex);
  }

  public void addAeroForceToFace(Vector3 windVelocity, int faceIndex) {
    SoftbodyJNI.btSoftBody_addAeroForceToFace(swigCPtr, this, windVelocity, faceIndex);
  }

  public void addVelocity(Vector3 velocity) {
    SoftbodyJNI.btSoftBody_addVelocity__SWIG_0(swigCPtr, this, velocity);
  }

  public void setVelocity(Vector3 velocity) {
    SoftbodyJNI.btSoftBody_setVelocity(swigCPtr, this, velocity);
  }

  public void addVelocity(Vector3 velocity, int node) {
    SoftbodyJNI.btSoftBody_addVelocity__SWIG_1(swigCPtr, this, velocity, node);
  }

  public void setMass(int node, float mass) {
    SoftbodyJNI.btSoftBody_setMass(swigCPtr, this, node, mass);
  }

  public float getMass(int node) {
    return SoftbodyJNI.btSoftBody_getMass(swigCPtr, this, node);
  }

  public float getTotalMass() {
    return SoftbodyJNI.btSoftBody_getTotalMass(swigCPtr, this);
  }

  public void setTotalMass(float mass, boolean fromfaces) {
    SoftbodyJNI.btSoftBody_setTotalMass__SWIG_0(swigCPtr, this, mass, fromfaces);
  }

  public void setTotalMass(float mass) {
    SoftbodyJNI.btSoftBody_setTotalMass__SWIG_1(swigCPtr, this, mass);
  }

  public void setTotalDensity(float density) {
    SoftbodyJNI.btSoftBody_setTotalDensity(swigCPtr, this, density);
  }

  public void setVolumeMass(float mass) {
    SoftbodyJNI.btSoftBody_setVolumeMass(swigCPtr, this, mass);
  }

  public void setVolumeDensity(float density) {
    SoftbodyJNI.btSoftBody_setVolumeDensity(swigCPtr, this, density);
  }

  public void transform(Matrix4 trs) {
    SoftbodyJNI.btSoftBody_transform(swigCPtr, this, trs);
  }

  public void translate(Vector3 trs) {
    SoftbodyJNI.btSoftBody_translate(swigCPtr, this, trs);
  }

  public void rotate(Quaternion rot) {
    SoftbodyJNI.btSoftBody_rotate(swigCPtr, this, rot);
  }

  public void scale(Vector3 scl) {
    SoftbodyJNI.btSoftBody_scale(swigCPtr, this, scl);
  }

  public void setPose(boolean bvolume, boolean bframe) {
    SoftbodyJNI.btSoftBody_setPose(swigCPtr, this, bvolume, bframe);
  }

  public void resetLinkRestLengths() {
    SoftbodyJNI.btSoftBody_resetLinkRestLengths(swigCPtr, this);
  }

  public float getVolume() {
    return SoftbodyJNI.btSoftBody_getVolume(swigCPtr, this);
  }

  public int clusterCount() {
    return SoftbodyJNI.btSoftBody_clusterCount(swigCPtr, this);
  }

  public static Vector3 clusterCom(btSoftBody.Cluster cluster) {
	return SoftbodyJNI.btSoftBody_clusterCom__SWIG_0(btSoftBody.Cluster.getCPtr(cluster), cluster);
}

  public Vector3 clusterCom(int cluster) {
	return SoftbodyJNI.btSoftBody_clusterCom__SWIG_1(swigCPtr, this, cluster);
}

  public static Vector3 clusterVelocity(btSoftBody.Cluster cluster, Vector3 rpos) {
	return SoftbodyJNI.btSoftBody_clusterVelocity(btSoftBody.Cluster.getCPtr(cluster), cluster, rpos);
}

  public static void clusterVImpulse(btSoftBody.Cluster cluster, Vector3 rpos, Vector3 impulse) {
    SoftbodyJNI.btSoftBody_clusterVImpulse(btSoftBody.Cluster.getCPtr(cluster), cluster, rpos, impulse);
  }

  public static void clusterDImpulse(btSoftBody.Cluster cluster, Vector3 rpos, Vector3 impulse) {
    SoftbodyJNI.btSoftBody_clusterDImpulse(btSoftBody.Cluster.getCPtr(cluster), cluster, rpos, impulse);
  }

  public static void clusterImpulse(btSoftBody.Cluster cluster, Vector3 rpos, btSoftBody.Impulse impulse) {
    SoftbodyJNI.btSoftBody_clusterImpulse(btSoftBody.Cluster.getCPtr(cluster), cluster, rpos, btSoftBody.Impulse.getCPtr(impulse), impulse);
  }

  public static void clusterVAImpulse(btSoftBody.Cluster cluster, Vector3 impulse) {
    SoftbodyJNI.btSoftBody_clusterVAImpulse(btSoftBody.Cluster.getCPtr(cluster), cluster, impulse);
  }

  public static void clusterDAImpulse(btSoftBody.Cluster cluster, Vector3 impulse) {
    SoftbodyJNI.btSoftBody_clusterDAImpulse(btSoftBody.Cluster.getCPtr(cluster), cluster, impulse);
  }

  public static void clusterAImpulse(btSoftBody.Cluster cluster, btSoftBody.Impulse impulse) {
    SoftbodyJNI.btSoftBody_clusterAImpulse(btSoftBody.Cluster.getCPtr(cluster), cluster, btSoftBody.Impulse.getCPtr(impulse), impulse);
  }

  public static void clusterDCImpulse(btSoftBody.Cluster cluster, Vector3 impulse) {
    SoftbodyJNI.btSoftBody_clusterDCImpulse(btSoftBody.Cluster.getCPtr(cluster), cluster, impulse);
  }

  public int generateBendingConstraints(int distance, btSoftBody.Material mat) {
    return SoftbodyJNI.btSoftBody_generateBendingConstraints__SWIG_0(swigCPtr, this, distance, btSoftBody.Material.getCPtr(mat), mat);
  }

  public int generateBendingConstraints(int distance) {
    return SoftbodyJNI.btSoftBody_generateBendingConstraints__SWIG_1(swigCPtr, this, distance);
  }

  public void randomizeConstraints() {
    SoftbodyJNI.btSoftBody_randomizeConstraints(swigCPtr, this);
  }

  public void releaseCluster(int index) {
    SoftbodyJNI.btSoftBody_releaseCluster(swigCPtr, this, index);
  }

  public void releaseClusters() {
    SoftbodyJNI.btSoftBody_releaseClusters(swigCPtr, this);
  }

  public int generateClusters(int k, int maxiterations) {
    return SoftbodyJNI.btSoftBody_generateClusters__SWIG_0(swigCPtr, this, k, maxiterations);
  }

  public int generateClusters(int k) {
    return SoftbodyJNI.btSoftBody_generateClusters__SWIG_1(swigCPtr, this, k);
  }

  public void refine(btSoftBody.ImplicitFn ifn, float accurary, boolean cut) {
    SoftbodyJNI.btSoftBody_refine(swigCPtr, this, btSoftBody.ImplicitFn.getCPtr(ifn), ifn, accurary, cut);
  }

  public boolean cutLink(int node0, int node1, float position) {
    return SoftbodyJNI.btSoftBody_cutLink__SWIG_0(swigCPtr, this, node0, node1, position);
  }

  public boolean cutLink(btSoftBody.Node node0, btSoftBody.Node node1, float position) {
    return SoftbodyJNI.btSoftBody_cutLink__SWIG_1(swigCPtr, this, btSoftBody.Node.getCPtr(node0), node0, btSoftBody.Node.getCPtr(node1), node1, position);
  }

  public boolean rayTest(Vector3 rayFrom, Vector3 rayTo, btSoftBody.sRayCast results) {
    return SoftbodyJNI.btSoftBody_rayTest__SWIG_0(swigCPtr, this, rayFrom, rayTo, btSoftBody.sRayCast.getCPtr(results), results);
  }

  public void setSolver(int preset) {
    SoftbodyJNI.btSoftBody_setSolver(swigCPtr, this, preset);
  }

  public void predictMotion(float dt) {
    SoftbodyJNI.btSoftBody_predictMotion(swigCPtr, this, dt);
  }

  public void solveConstraints() {
    SoftbodyJNI.btSoftBody_solveConstraints(swigCPtr, this);
  }

  public void staticSolve(int iterations) {
    SoftbodyJNI.btSoftBody_staticSolve(swigCPtr, this, iterations);
  }

  public static void solveCommonConstraints(SWIGTYPE_p_p_btSoftBody bodies, int count, int iterations) {
    SoftbodyJNI.btSoftBody_solveCommonConstraints(SWIGTYPE_p_p_btSoftBody.getCPtr(bodies), count, iterations);
  }

  public static void solveClusters(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t bodies) {
    SoftbodyJNI.btSoftBody_solveClusters__SWIG_0(SWIGTYPE_p_btAlignedObjectArrayT_btSoftBody_p_t.getCPtr(bodies));
  }

  public void integrateMotion() {
    SoftbodyJNI.btSoftBody_integrateMotion(swigCPtr, this);
  }

  public void defaultCollisionHandler(btCollisionObjectWrapper pcoWrap) {
    SoftbodyJNI.btSoftBody_defaultCollisionHandler__SWIG_0(swigCPtr, this, btCollisionObjectWrapper.getCPtr(pcoWrap), pcoWrap);
  }

  public void defaultCollisionHandler(btSoftBody psb) {
    SoftbodyJNI.btSoftBody_defaultCollisionHandler__SWIG_1(swigCPtr, this, btSoftBody.getCPtr(psb), psb);
  }

  public void setWindVelocity(Vector3 velocity) {
    SoftbodyJNI.btSoftBody_setWindVelocity(swigCPtr, this, velocity);
  }

  public static btSoftBody upcastConstBtCollisionObject(btCollisionObject colObj) {
    long cPtr = SoftbodyJNI.btSoftBody_upcastConstBtCollisionObject(btCollisionObject.getCPtr(colObj), colObj);
    return (cPtr == 0) ? null : new btSoftBody(cPtr, false);
  }

  public static btSoftBody upcast(btCollisionObject colObj) {
    long cPtr = SoftbodyJNI.btSoftBody_upcast(btCollisionObject.getCPtr(colObj), colObj);
    return (cPtr == 0) ? null : new btSoftBody(cPtr, false);
  }

  public void getAabb(Vector3 aabbMin, Vector3 aabbMax) {
    SoftbodyJNI.btSoftBody_getAabb(swigCPtr, this, aabbMin, aabbMax);
  }

  public void pointersToIndices() {
    SoftbodyJNI.btSoftBody_pointersToIndices(swigCPtr, this);
  }

  public void indicesToPointers(java.nio.IntBuffer map) {
    assert map.isDirect() : "Buffer must be allocated direct.";
    {
      SoftbodyJNI.btSoftBody_indicesToPointers__SWIG_0(swigCPtr, this, map);
    }
  }

  public void indicesToPointers() {
    SoftbodyJNI.btSoftBody_indicesToPointers__SWIG_1(swigCPtr, this);
  }

  public int rayTest(Vector3 rayFrom, Vector3 rayTo, SWIGTYPE_p_float mint, SWIGTYPE_p_btSoftBody__eFeature___ feature, SWIGTYPE_p_int index, boolean bcountonly) {
    return SoftbodyJNI.btSoftBody_rayTest__SWIG_1(swigCPtr, this, rayFrom, rayTo, SWIGTYPE_p_float.getCPtr(mint), SWIGTYPE_p_btSoftBody__eFeature___.getCPtr(feature), SWIGTYPE_p_int.getCPtr(index), bcountonly);
  }

  public void initializeFaceTree() {
    SoftbodyJNI.btSoftBody_initializeFaceTree(swigCPtr, this);
  }

  public Vector3 evaluateCom() {
	return SoftbodyJNI.btSoftBody_evaluateCom(swigCPtr, this);
}

  public boolean checkContact(btCollisionObjectWrapper colObjWrap, Vector3 x, float margin, btSoftBody.sCti cti) {
    return SoftbodyJNI.btSoftBody_checkContact(swigCPtr, this, btCollisionObjectWrapper.getCPtr(colObjWrap), colObjWrap, x, margin, btSoftBody.sCti.getCPtr(cti), cti);
  }

  public void updateNormals() {
    SoftbodyJNI.btSoftBody_updateNormals(swigCPtr, this);
  }

  public void updateBounds() {
    SoftbodyJNI.btSoftBody_updateBounds(swigCPtr, this);
  }

  public void updatePose() {
    SoftbodyJNI.btSoftBody_updatePose(swigCPtr, this);
  }

  public void updateConstants() {
    SoftbodyJNI.btSoftBody_updateConstants(swigCPtr, this);
  }

  public void updateLinkConstants() {
    SoftbodyJNI.btSoftBody_updateLinkConstants(swigCPtr, this);
  }

  public void updateArea(boolean averageArea) {
    SoftbodyJNI.btSoftBody_updateArea__SWIG_0(swigCPtr, this, averageArea);
  }

  public void updateArea() {
    SoftbodyJNI.btSoftBody_updateArea__SWIG_1(swigCPtr, this);
  }

  public void initializeClusters() {
    SoftbodyJNI.btSoftBody_initializeClusters(swigCPtr, this);
  }

  public void updateClusters() {
    SoftbodyJNI.btSoftBody_updateClusters(swigCPtr, this);
  }

  public void cleanupClusters() {
    SoftbodyJNI.btSoftBody_cleanupClusters(swigCPtr, this);
  }

  public void prepareClusters(int iterations) {
    SoftbodyJNI.btSoftBody_prepareClusters(swigCPtr, this, iterations);
  }

  public void solveClusters(float sor) {
    SoftbodyJNI.btSoftBody_solveClusters__SWIG_1(swigCPtr, this, sor);
  }

  public void applyClusters(boolean drift) {
    SoftbodyJNI.btSoftBody_applyClusters(swigCPtr, this, drift);
  }

  public void dampClusters() {
    SoftbodyJNI.btSoftBody_dampClusters(swigCPtr, this);
  }

  public void applyForces() {
    SoftbodyJNI.btSoftBody_applyForces(swigCPtr, this);
  }

  public static void PSolve_Anchors(btSoftBody psb, float kst, float ti) {
    SoftbodyJNI.btSoftBody_PSolve_Anchors(btSoftBody.getCPtr(psb), psb, kst, ti);
  }

  public static void PSolve_RContacts(btSoftBody psb, float kst, float ti) {
    SoftbodyJNI.btSoftBody_PSolve_RContacts(btSoftBody.getCPtr(psb), psb, kst, ti);
  }

  public static void PSolve_SContacts(btSoftBody psb, float arg1, float ti) {
    SoftbodyJNI.btSoftBody_PSolve_SContacts(btSoftBody.getCPtr(psb), psb, arg1, ti);
  }

  public static void PSolve_Links(btSoftBody psb, float kst, float ti) {
    SoftbodyJNI.btSoftBody_PSolve_Links(btSoftBody.getCPtr(psb), psb, kst, ti);
  }

  public static void VSolve_Links(btSoftBody psb, float kst) {
    SoftbodyJNI.btSoftBody_VSolve_Links(btSoftBody.getCPtr(psb), psb, kst);
  }

  public static SWIGTYPE_p_f_p_btSoftBody_float_float__void getSolver(int solver) {
    long cPtr = SoftbodyJNI.btSoftBody_getSolver__SWIG_0(solver);
    return (cPtr == 0) ? null : new SWIGTYPE_p_f_p_btSoftBody_float_float__void(cPtr, false);
  }

  static private long SwigConstructbtSoftBody(btSoftBodyWorldInfo worldInfo, java.nio.FloatBuffer vertices, int vertexSize, int posOffset, int normalOffset, java.nio.ShortBuffer indices, int indexOffset, int numVertices, java.nio.ShortBuffer indexMap, int indexMapOffset) {
    assert vertices.isDirect() : "Buffer must be allocated direct.";
    assert indices.isDirect() : "Buffer must be allocated direct.";
    assert indexMap.isDirect() : "Buffer must be allocated direct.";
    return SoftbodyJNI.new_btSoftBody__SWIG_2(btSoftBodyWorldInfo.getCPtr(worldInfo), worldInfo, vertices, vertexSize, posOffset, normalOffset, indices, indexOffset, numVertices, indexMap, indexMapOffset);
  }

  public btSoftBody(btSoftBodyWorldInfo worldInfo, java.nio.FloatBuffer vertices, int vertexSize, int posOffset, int normalOffset, java.nio.ShortBuffer indices, int indexOffset, int numVertices, java.nio.ShortBuffer indexMap, int indexMapOffset) {
    this(btSoftBody.SwigConstructbtSoftBody(worldInfo, vertices, vertexSize, posOffset, normalOffset, indices, indexOffset, numVertices, indexMap, indexMapOffset), true);
  }

  public int getNodeCount() {
    return SoftbodyJNI.btSoftBody_getNodeCount(swigCPtr, this);
  }

  public btSoftBody.Node getNode(int idx) {
    long cPtr = SoftbodyJNI.btSoftBody_getNode(swigCPtr, this, idx);
    return (cPtr == 0) ? null : new btSoftBody.Node(cPtr, false);
  }

  public int getLinkCount() {
    return SoftbodyJNI.btSoftBody_getLinkCount(swigCPtr, this);
  }

  public btSoftBody.Link getLink(int idx) {
    long cPtr = SoftbodyJNI.btSoftBody_getLink(swigCPtr, this, idx);
    return (cPtr == 0) ? null : new btSoftBody.Link(cPtr, false);
  }

  public void getVertices(java.nio.FloatBuffer buffer, int vertexCount, int vertexSize, int posOffset) {
    assert buffer.isDirect() : "Buffer must be allocated direct.";
    {
      SoftbodyJNI.btSoftBody_getVertices__SWIG_0(swigCPtr, this, buffer, vertexCount, vertexSize, posOffset);
    }
  }

  public void getVertices(java.nio.FloatBuffer vertices, int vertexSize, int posOffset, java.nio.ShortBuffer indices, int indexOffset, int numVertices, java.nio.ShortBuffer indexMap, int indexMapOffset) {
    assert vertices.isDirect() : "Buffer must be allocated direct.";
    assert indices.isDirect() : "Buffer must be allocated direct.";
    assert indexMap.isDirect() : "Buffer must be allocated direct.";
    {
      SoftbodyJNI.btSoftBody_getVertices__SWIG_1(swigCPtr, this, vertices, vertexSize, posOffset, indices, indexOffset, numVertices, indexMap, indexMapOffset);
    }
  }

  public void getVertices(java.nio.FloatBuffer vertices, int vertexSize, int posOffset, int normalOffset, java.nio.ShortBuffer indices, int indexOffset, int numVertices, java.nio.ShortBuffer indexMap, int indexMapOffset) {
    assert vertices.isDirect() : "Buffer must be allocated direct.";
    assert indices.isDirect() : "Buffer must be allocated direct.";
    assert indexMap.isDirect() : "Buffer must be allocated direct.";
    {
      SoftbodyJNI.btSoftBody_getVertices__SWIG_2(swigCPtr, this, vertices, vertexSize, posOffset, normalOffset, indices, indexOffset, numVertices, indexMap, indexMapOffset);
    }
  }

  public int getFaceCount() {
    return SoftbodyJNI.btSoftBody_getFaceCount(swigCPtr, this);
  }

  public btSoftBody.Face getFace(int idx) {
    long cPtr = SoftbodyJNI.btSoftBody_getFace(swigCPtr, this, idx);
    return (cPtr == 0) ? null : new btSoftBody.Face(cPtr, false);
  }

  public void getIndices(java.nio.ShortBuffer buffer, int triangleCount) {
    assert buffer.isDirect() : "Buffer must be allocated direct.";
    {
      SoftbodyJNI.btSoftBody_getIndices(swigCPtr, this, buffer, triangleCount);
    }
  }

  public void setConfig_kVCF(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kVCF(swigCPtr, this, v);
  }

  public void setConfig_kDP(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kDP(swigCPtr, this, v);
  }

  public void setConfig_kDG(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kDG(swigCPtr, this, v);
  }

  public void setConfig_kLF(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kLF(swigCPtr, this, v);
  }

  public void setConfig_kPR(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kPR(swigCPtr, this, v);
  }

  public void setConfig_kVC(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kVC(swigCPtr, this, v);
  }

  public void setConfig_kDF(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kDF(swigCPtr, this, v);
  }

  public void setConfig_kMT(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kMT(swigCPtr, this, v);
  }

  public void setConfig_kCHR(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kCHR(swigCPtr, this, v);
  }

  public void setConfig_kKHR(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kKHR(swigCPtr, this, v);
  }

  public void setConfig_kSHR(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kSHR(swigCPtr, this, v);
  }

  public void setConfig_kAHR(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kAHR(swigCPtr, this, v);
  }

  public void setConfig_kSRHR_CL(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kSRHR_CL(swigCPtr, this, v);
  }

  public void setConfig_kSKHR_CL(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kSKHR_CL(swigCPtr, this, v);
  }

  public void setConfig_kSSHR_CL(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kSSHR_CL(swigCPtr, this, v);
  }

  public void setConfig_kSR_SPLT_CL(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kSR_SPLT_CL(swigCPtr, this, v);
  }

  public void setConfig_kSK_SPLT_CL(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kSK_SPLT_CL(swigCPtr, this, v);
  }

  public void setConfig_kSS_SPLT_CL(float v) {
    SoftbodyJNI.btSoftBody_setConfig_kSS_SPLT_CL(swigCPtr, this, v);
  }

  public void setConfig_maxvolume(float v) {
    SoftbodyJNI.btSoftBody_setConfig_maxvolume(swigCPtr, this, v);
  }

  public void setConfig_timescale(float v) {
    SoftbodyJNI.btSoftBody_setConfig_timescale(swigCPtr, this, v);
  }

  public void setConfig_viterations(int v) {
    SoftbodyJNI.btSoftBody_setConfig_viterations(swigCPtr, this, v);
  }

  public void setConfig_piterations(int v) {
    SoftbodyJNI.btSoftBody_setConfig_piterations(swigCPtr, this, v);
  }

  public void setConfig_diterations(int v) {
    SoftbodyJNI.btSoftBody_setConfig_diterations(swigCPtr, this, v);
  }

  public void setConfig_citerations(int v) {
    SoftbodyJNI.btSoftBody_setConfig_citerations(swigCPtr, this, v);
  }

  public void setConfig_collisions(int v) {
    SoftbodyJNI.btSoftBody_setConfig_collisions(swigCPtr, this, v);
  }

}
