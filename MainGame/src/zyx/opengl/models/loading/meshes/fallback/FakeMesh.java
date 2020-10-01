package zyx.opengl.models.loading.meshes.fallback;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.utils.geometry.Box;

public class FakeMesh
{
	public static LoadableWorldModelVO makeFakeMesh(int size)
	{
		int sizeH = size / 2;
		Box bounding = new Box(-sizeH, sizeH, -sizeH, sizeH, -sizeH, sizeH);
		
		FakeMeshData meshData = FakeMeshConstructor.getVertexData(bounding);
		float[] vertexData = meshData.vertexData;
		int[] elementData = meshData.elementData;
		String skeleton = "skeleton.default";
		String diffuse = "texture.default_diffuse";
		String normal = "normal.default_normal";
		String specular = "specular.default_specular";
		PhysBox physBox = new PhysBox(0, bounding, 0);
		Vector3f radiusCenter = new Vector3f();
		float radius = sizeH;
		
		LoadableWorldModelVO vo = new LoadableWorldModelVO();
		vo.setBoneCount(2);
		vo.asWorldModel();
		vo.setVertexData(vertexData, elementData);
		vo.setPhysBox(physBox);
		vo.setTextureIds(diffuse, normal, specular);
		vo.setRadius(radiusCenter, radius);
		vo.setSkeletonId(skeleton);
		
		return vo;
	}
}
