

package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btCollisionWorldImporter extends BulletBase {
	private long swigCPtr;
	
	protected btCollisionWorldImporter(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, cPtr, cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	 
	public btCollisionWorldImporter(long cPtr, boolean cMemoryOwn) {
		this("btCollisionWorldImporter", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(swigCPtr = cPtr, cMemoryOwn);
	}
	
	public static long getCPtr(btCollisionWorldImporter obj) {
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
				CollisionJNI.delete_btCollisionWorldImporter(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public btCollisionWorldImporter(btCollisionWorld world) {
    this(CollisionJNI.new_btCollisionWorldImporter(btCollisionWorld.getCPtr(world), world), true);
  }

  public boolean convertAllObjects(btBulletSerializedArrays arrays) {
    return CollisionJNI.btCollisionWorldImporter_convertAllObjects(swigCPtr, this, btBulletSerializedArrays.getCPtr(arrays), arrays);
  }

  public void deleteAllData() {
    CollisionJNI.btCollisionWorldImporter_deleteAllData(swigCPtr, this);
  }

  public void setVerboseMode(int verboseMode) {
    CollisionJNI.btCollisionWorldImporter_setVerboseMode(swigCPtr, this, verboseMode);
  }

  public int getVerboseMode() {
    return CollisionJNI.btCollisionWorldImporter_getVerboseMode(swigCPtr, this);
  }

  public int getNumCollisionShapes() {
    return CollisionJNI.btCollisionWorldImporter_getNumCollisionShapes(swigCPtr, this);
  }

  public btCollisionShape getCollisionShapeByIndex(int index) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_getCollisionShapeByIndex(swigCPtr, this, index);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public int getNumRigidBodies() {
    return CollisionJNI.btCollisionWorldImporter_getNumRigidBodies(swigCPtr, this);
  }

  public btCollisionObject getRigidBodyByIndex(int index) {
	return btCollisionObject.getInstance(CollisionJNI.btCollisionWorldImporter_getRigidBodyByIndex(swigCPtr, this, index), false);
}

  public int getNumBvhs() {
    return CollisionJNI.btCollisionWorldImporter_getNumBvhs(swigCPtr, this);
  }

  public btOptimizedBvh getBvhByIndex(int index) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_getBvhByIndex(swigCPtr, this, index);
    return (cPtr == 0) ? null : new btOptimizedBvh(cPtr, false);
  }

  public int getNumTriangleInfoMaps() {
    return CollisionJNI.btCollisionWorldImporter_getNumTriangleInfoMaps(swigCPtr, this);
  }

  public btTriangleInfoMap getTriangleInfoMapByIndex(int index) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_getTriangleInfoMapByIndex(swigCPtr, this, index);
    return (cPtr == 0) ? null : new btTriangleInfoMap(cPtr, false);
  }

  public btCollisionShape getCollisionShapeByName(String name) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_getCollisionShapeByName(swigCPtr, this, name);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionObject getCollisionObjectByName(String name) {
	return btCollisionObject.getInstance(CollisionJNI.btCollisionWorldImporter_getCollisionObjectByName(swigCPtr, this, name), false);
}

  public String getNameForPointer(long ptr) {
    return CollisionJNI.btCollisionWorldImporter_getNameForPointer(swigCPtr, this, ptr);
  }

  public btCollisionObject createCollisionObject(Matrix4 startTransform, btCollisionShape shape, String bodyName) {
	return btCollisionObject.getInstance(CollisionJNI.btCollisionWorldImporter_createCollisionObject(swigCPtr, this, startTransform, btCollisionShape.getCPtr(shape), shape, bodyName), false);
}

  public btCollisionShape createPlaneShape(Vector3 planeNormal, float planeConstant) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createPlaneShape(swigCPtr, this, planeNormal, planeConstant);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createBoxShape(Vector3 halfExtents) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createBoxShape(swigCPtr, this, halfExtents);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createSphereShape(float radius) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createSphereShape(swigCPtr, this, radius);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createCapsuleShapeX(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createCapsuleShapeX(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createCapsuleShapeY(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createCapsuleShapeY(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createCapsuleShapeZ(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createCapsuleShapeZ(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createCylinderShapeX(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createCylinderShapeX(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createCylinderShapeY(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createCylinderShapeY(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createCylinderShapeZ(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createCylinderShapeZ(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createConeShapeX(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createConeShapeX(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createConeShapeY(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createConeShapeY(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btCollisionShape createConeShapeZ(float radius, float height) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createConeShapeZ(swigCPtr, this, radius, height);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btTriangleIndexVertexArray createTriangleMeshContainer() {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createTriangleMeshContainer(swigCPtr, this);
    return (cPtr == 0) ? null : new btTriangleIndexVertexArray(cPtr, false);
  }

  public btBvhTriangleMeshShape createBvhTriangleMeshShape(btStridingMeshInterface trimesh, btOptimizedBvh bvh) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createBvhTriangleMeshShape(swigCPtr, this, btStridingMeshInterface.getCPtr(trimesh), trimesh, btOptimizedBvh.getCPtr(bvh), bvh);
    return (cPtr == 0) ? null : new btBvhTriangleMeshShape(cPtr, false);
  }

  public btCollisionShape createConvexTriangleMeshShape(btStridingMeshInterface trimesh) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createConvexTriangleMeshShape(swigCPtr, this, btStridingMeshInterface.getCPtr(trimesh), trimesh);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public btStridingMeshInterfaceData createStridingMeshInterfaceData(btStridingMeshInterfaceData interfaceData) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createStridingMeshInterfaceData(swigCPtr, this, btStridingMeshInterfaceData.getCPtr(interfaceData), interfaceData);
    return (cPtr == 0) ? null : new btStridingMeshInterfaceData(cPtr, false);
  }

  public btConvexHullShape createConvexHullShape() {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createConvexHullShape(swigCPtr, this);
    return (cPtr == 0) ? null : new btConvexHullShape(cPtr, false);
  }

  public btCompoundShape createCompoundShape() {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createCompoundShape(swigCPtr, this);
    return (cPtr == 0) ? null : new btCompoundShape(cPtr, false);
  }

  public btScaledBvhTriangleMeshShape createScaledTrangleMeshShape(btBvhTriangleMeshShape meshShape, Vector3 localScalingbtBvhTriangleMeshShape) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createScaledTrangleMeshShape(swigCPtr, this, btBvhTriangleMeshShape.getCPtr(meshShape), meshShape, localScalingbtBvhTriangleMeshShape);
    return (cPtr == 0) ? null : new btScaledBvhTriangleMeshShape(cPtr, false);
  }

  public btMultiSphereShape createMultiSphereShape(btVector3 positions, java.nio.FloatBuffer radi, int numSpheres) {
    assert radi.isDirect() : "Buffer must be allocated direct.";
    {
      long cPtr = CollisionJNI.btCollisionWorldImporter_createMultiSphereShape(swigCPtr, this, btVector3.getCPtr(positions), positions, radi, numSpheres);
      return (cPtr == 0) ? null : new btMultiSphereShape(cPtr, false);
    }
  }

  public btTriangleIndexVertexArray createMeshInterface(btStridingMeshInterfaceData meshData) {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createMeshInterface(swigCPtr, this, btStridingMeshInterfaceData.getCPtr(meshData), meshData);
    return (cPtr == 0) ? null : new btTriangleIndexVertexArray(cPtr, false);
  }

  public btOptimizedBvh createOptimizedBvh() {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createOptimizedBvh(swigCPtr, this);
    return (cPtr == 0) ? null : new btOptimizedBvh(cPtr, false);
  }

  public btTriangleInfoMap createTriangleInfoMap() {
    long cPtr = CollisionJNI.btCollisionWorldImporter_createTriangleInfoMap(swigCPtr, this);
    return (cPtr == 0) ? null : new btTriangleInfoMap(cPtr, false);
  }

}
