package zyx.opengl.models.implementations;

import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.loading.meshes.IMaterialObject;
import zyx.opengl.textures.AbstractTexture;

public interface ISubMeshVO
{
	public void setBoneCount(int boneCount);
	
	public void setVertexData(float[] vertexData, int[] elementData);
	
	public void setTextureIds(String diffuse, String normal, String specular);
	
	public void setPhysBox(PhysBox physBox);
	
	public void setTextures(AbstractTexture diffuse, AbstractTexture normal, AbstractTexture specular);

	public void setMaterialData(IMaterialObject materialInformation);
	
	public String[] GetTextureIds();
	
	public String getDiffuseTextureId();

	public String getNormalTextureId();

	public String getSpecularTextureId();
	
	public void setDiffuseTexture(AbstractTexture diffuse);

	public void setNormalTexture(AbstractTexture normal);

	public void setSpecularTexture(AbstractTexture specular);
}
