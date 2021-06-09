package zyx.logic.converter.output.mesh.builders;

import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.mesh.ZafMeshVo;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdSurface;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdTriangle;

public class BoundingBoxBuilder
{

	private ParsedSmdFile fileToUse;

	private Vector3f min;
	private Vector3f max;

	public BoundingBoxBuilder(ParsedSmdFile boundingFile, ParsedSmdFile refFile)
	{
		fileToUse = (boundingFile != null) ? boundingFile : refFile;

		min = new Vector3f();
		max = new Vector3f();
	}

	public void addTo(ZafMeshVo vo)
	{
		if (fileToUse == null)
		{
			return;
		}
		
		CalculateBounding();

		vo.colliders.boundingBoxMax.set(max);
		vo.colliders.boundingBoxMin.set(min);

		float diffX = max.x - min.x;
		float diffY = max.y - min.y;
		float diffZ = max.z - min.z;

		float centerX = min.x + (diffX / 2f);
		float centerY = min.y + (diffY / 2f);
		float centerZ = min.z + (diffZ / 2f);

		float localRadius = diffX > diffY ? diffX : diffY;
		localRadius = localRadius > diffZ ? localRadius : diffZ;
		localRadius = localRadius / 2f;

		vo.radius = localRadius;
		vo.centerPosition.set(centerX, centerY, centerZ);
	}

	private void checkPosition(Vector3f pos)
	{
		float x = pos.x;
		float y = pos.y;
		float z = pos.z;

		min.x = (x < min.x) ? x : min.x;
		min.y = (y < min.y) ? y : min.y;
		min.z = (z < min.z) ? z : min.z;

		max.x = (x > max.x) ? x : max.x;
		max.y = (y > max.y) ? y : max.y;
		max.z = (z > max.z) ? z : max.z;
	}

	private void CalculateBounding()
	{
		for (ParsedSmdSurface surface : fileToUse.surfaces.values())
		{
			for (ParsedSmdTriangle triangle : surface.triangles)
			{
				checkPosition(triangle.v1.pos);
				checkPosition(triangle.v2.pos);
				checkPosition(triangle.v3.pos);
			}
		}
	}

}
