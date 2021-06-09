package zyx.logic.converter.output.mesh.builders;

import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.output.mesh.ZafColliderBox;
import zyx.logic.converter.output.mesh.ZafColliderTriangle;
import zyx.logic.converter.output.mesh.ZafMeshVo;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdSurface;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdTriangle;

public class ColliderBuilder
{

	private ParsedSmdFile physFile;

	public ColliderBuilder(ParsedSmdFile physFile)
	{
		this.physFile = physFile;
	}

	public void addTo(ZafMeshVo vo)
	{
		if (physFile == null)
		{
			return;
		}

		for (ParsedSmdSurface surface : physFile.surfaces.values())
		{
			ZafColliderBox colliderBox = new ZafColliderBox();
			short boneId = -1;

			for (ParsedSmdTriangle triangle : surface.triangles)
			{
				SanityCheck(triangle);
				
				ZafColliderTriangle colliderTriangle = new ZafColliderTriangle();
				colliderTriangle.v1.set(triangle.v1.pos);
				colliderTriangle.v2.set(triangle.v2.pos);
				colliderTriangle.v3.set(triangle.v3.pos);
				setTriangleNormal(colliderTriangle);

				if (boneId == -1)
				{
					boneId = triangle.v1.boneIndexes[0];
				}
				
				colliderBox.triangles.add(colliderTriangle);
			}

			colliderBox.boneId = boneId;
			vo.colliders.boxes.add(colliderBox);
		}
	}

	private void setTriangleNormal(ZafColliderTriangle triangle)
	{
		Vector3f v1 = triangle.v1;
		Vector3f v2 = triangle.v2;
		Vector3f v3 = triangle.v3;
		
		Vector3f AB = new Vector3f();
		Vector3f AC = new Vector3f();

		Vector3f vector1 = new Vector3f(v1.x, v1.y, v1.z);
		Vector3f vector2 = new Vector3f(v2.x, v2.y, v2.z);
		Vector3f vector3 = new Vector3f(v3.x, v3.y, v3.z);

		Vector3f.sub(vector1, vector2, AB);
		Vector3f.sub(vector1, vector3, AC);
		AB.normalise();
		AC.normalise();

		Vector3f.cross(AB, AC, triangle.normal);
		if (triangle.normal.length() == 0)
		{
			throw new RuntimeException("Triangle normal is invalid!!");
		}
		
		triangle.normal.normalise();
	}

	private void SanityCheck(ParsedSmdTriangle triangle)
	{
		if (triangle.v1.boneCount != 1 || triangle.v2.boneCount != 1 || triangle.v3.boneCount != 1)
		{
			throw new RuntimeException("Invalid bone count for vertex");
		}
	}

}
