package zyx.opengl.models.loading.meshes;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
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

			LoadableWorldModelVO vo = new LoadableWorldModelVO();
			vo.setBoneCount(obj.subMeshes[0].boneCount);
			vo.setVertexData(obj.subMeshes[0].vertexData, obj.subMeshes[0].elementData);
			vo.setPhysBox(phys);
			vo.setTextureIds(obj.subMeshes[0].diffuseTexture, obj.subMeshes[0].normalTexture, obj.subMeshes[0].specularTexture);
			vo.setRadius(obj.radiusCenter, obj.radius);
			vo.setSkeletonId(obj.skeletonId);
			vo.setMaterialData(obj.subMeshes[0].materialInformation);
			
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
