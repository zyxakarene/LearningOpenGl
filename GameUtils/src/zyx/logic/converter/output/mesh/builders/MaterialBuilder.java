package zyx.logic.converter.output.mesh.builders;

import zyx.logic.converter.output.mesh.ZafMeshVo;
import zyx.logic.converter.output.mesh.ZafSubMesh;
import zyx.logic.converter.json.JsonMesh;
import zyx.logic.converter.json.JsonMeshPropertyEntry;
import zyx.logic.converter.json.JsonMeshTextureEntry;

public class MaterialBuilder
{

	private JsonMeshTextureEntry[] textures;
	private JsonMeshPropertyEntry[] properties;

	public MaterialBuilder(JsonMesh json)
	{
		properties = json.meshProperties.entries;
		textures = json.textureFiles.entries;
	}

	public void addTo(ZafMeshVo vo)
	{
		int len = vo.subMeshes.size();
		for (int i = 0; i < len; i++)
		{
			ZafSubMesh mesh = vo.subMeshes.get(i);
			JsonMeshTextureEntry texture = textures[i];
			JsonMeshPropertyEntry property = properties[i];
			
			copyTextures(mesh, texture);
			copyProperties(mesh, property);
		}
	}

	private void copyTextures(ZafSubMesh mesh, JsonMeshTextureEntry texture)
	{
		mesh.diffuseTexture = texture.getDiffuseTextureName();
		mesh.normalTexture = texture.getNormalTextureName();
		mesh.specularTexture = texture.getSpecularTextureName();
	}

	private void copyProperties(ZafSubMesh mesh, JsonMeshPropertyEntry property)
	{
		mesh.material.zWrite = property.zWrite;
		mesh.material.zTest = property.zTest;
		mesh.material.culling = property.culling;
		mesh.material.blendSrc = property.blendSrc;
		mesh.material.blendDst = property.blendDst;
		mesh.material.priority = property.priority;
		mesh.material.stencilMode = property.stencilMode;
		mesh.material.stencilLayer = property.stencilLayer;
	}
}
