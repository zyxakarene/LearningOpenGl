package zyx.opengl.models.loading.meshes;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.ISubMeshBuilder;
import zyx.opengl.models.implementations.LoadableWorldModelVO;

import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.loading.meshes.fallback.FakeMesh;
import zyx.utils.PrintBuilder;
import zyx.utils.cheats.Print;

public class ZafLoader
{

	public static LoadableWorldModelVO loadMeshFrom(ResourceDataInputStream in, String id)
	{
		PrintBuilder builder = new PrintBuilder();
		try
		{
			builder.append("==== Parsing mesh data from byte count:", in.available(), "====");
			builder.append("↳ Id", id);

			ZafObject obj = new ZafObject();
			obj.read(in, builder);

			PhysBox phys = createPhysBox(obj.subMeshes[0].physInformation);

			builder.append("========");
			Print.out(builder);

			LoadableWorldModelVO vo = new LoadableWorldModelVO(obj.subMeshCount);
			for (int i = 0; i < obj.subMeshCount; i++)
			{
				SubMeshObject subMesh = obj.subMeshes[i];
				ISubMeshBuilder meshBuilder = vo.getSubMeshBuilder(i);
				
				meshBuilder.setBoneCount(subMesh.boneCount);
				meshBuilder.setVertexData(subMesh.vertexData, subMesh.elementData);
				meshBuilder.setPhysBox(phys);
				meshBuilder.setTextureIds(subMesh.diffuseTexture, subMesh.normalTexture, subMesh.specularTexture);
				meshBuilder.setMaterialData(subMesh.materialInformation);
			}
			
			vo.setSkeletonId(obj.skeletonId);
			vo.setRadius(obj.radiusCenter, obj.radius);
			
			return vo;
			
		}
		catch (Exception e)
		{
			builder.append("==== [ERROR] Failed to parse mesh! ====");
			Print.err(builder);

			return FakeMesh.makeFakeMesh(2);
		}
	}

	private static PhysBox createPhysBox(PhysObject physInfo)
	{
		PhysboxObject[] boxes = physInfo.physBoxes;
		int triangleCount = getTriangleCount(boxes);

		PhysBox box = new PhysBox(triangleCount, physInfo.boundingBox, boxes.length);
		for (PhysboxObject physBox : boxes)
		{
			box.addObject(physBox.triangles.length, physBox.boneId);

			for (PhysTriangleObject triangle : physBox.triangles)
			{
				box.addTriangle(triangle.v1, triangle.v2, triangle.v3, triangle.normal);
			}
		}

		return box;
	}

	private static int getTriangleCount(PhysboxObject[] physboxes)
	{
		int count = 0;

		for (PhysboxObject box : physboxes)
		{
			count += box.triangles.length;
		}

		return count;
	}
}
