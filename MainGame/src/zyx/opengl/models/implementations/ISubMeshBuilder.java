package zyx.opengl.models.implementations;

import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.loading.meshes.IMaterialObject;
import zyx.opengl.textures.AbstractTexture;

public interface ISubMeshBuilder
{
	public void setBoneCount(int boneCount);
	
	public void setVertexData(float[] vertexData, int[] elementData);
	
	public void setTextureIds(String diffuse, String normal, String specular);
	
	public void setPhysBox(PhysBox physBox);
	
	public void setTextures(AbstractTexture diffuse, AbstractTexture normal, AbstractTexture specular);

	public void setMaterialData(IMaterialObject materialInformation);
}
