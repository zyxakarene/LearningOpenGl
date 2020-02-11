package zyx.opengl.models.loading;

import java.io.IOException;
import java.util.logging.Level;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;

import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.utils.GameConstants;

public class ZafLoader
{

	public static LoadableWorldModelVO loadFromZaf(ResourceDataInputStream in)
	{
		try
		{
			SmdObject smd = new SmdObject();
			smd.read(in);

			PhysBox phys = createPhysBox(smd.physInformation);

			return new LoadableWorldModelVO(smd.vertexData, smd.elementData, phys, smd.diffuseTexture, smd.normalTexture, smd.specularTexture,
					smd.radiusCenter, smd.radius, smd.skeletonId);
		}
		catch (IOException e)
		{
			GameConstants.LOGGER.log(Level.SEVERE, "Error at loading a zaf data", e);
			return null;
		}
	}

	private static PhysBox createPhysBox(SmdPhysInfo physInfo)
	{
		SmdPhysbox[] boxes = physInfo.physBoxes;
		int triangleCount = getTriangleCount(boxes);

		PhysBox box = new PhysBox(triangleCount, physInfo.boundingBox, boxes.length);
		for (SmdPhysbox physBox : boxes)
		{
			box.addObject(physBox.triangles.length, physBox.boneId);

			for (SmdPhysTriangle triangle : physBox.triangles)
			{
				box.addTriangle(triangle.v1, triangle.v2, triangle.v3, triangle.normal);
			}
		}

		return box;
	}

	private static int getTriangleCount(SmdPhysbox[] physboxes)
	{
		int count = 0;

		for (SmdPhysbox box : physboxes)
		{
			count += box.triangles.length;
		}

		return count;
	}
}
