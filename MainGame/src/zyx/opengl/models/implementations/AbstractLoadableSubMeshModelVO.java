package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.DepthMaterial;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.loading.meshes.IMaterialObject;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public abstract class AbstractLoadableSubMeshModelVO<TMaterial extends WorldModelMaterial> implements ISubMeshVO
{

	private static String[] TEXTURE_ID_HELPER = new String[3];

	private static final Shader[] WORLD_SHADERS = new Shader[]
	{
		null,
		Shader.WORLD_1,
		Shader.WORLD_2,
		Shader.WORLD_3,
		Shader.WORLD_4,
	};

	private static final Shader[] SHADOW_SHADERS = new Shader[]
	{
		null,
		Shader.DEPTH_1,
		Shader.DEPTH_2,
		Shader.DEPTH_3,
		Shader.DEPTH_4,
	};

	Shader worldShader;
	Shader shadowShader;

	String diffuseTextureId;
	String normalTextureId;
	String specularTextureId;
	
	AbstractTexture diffuseTexture;
	AbstractTexture normalTexture;
	AbstractTexture specularTexture;

	int boneCount;
	float vertexData[];
	int elementData[];

	PhysBox physBox;

	IMaterialObject materialInfo;
	TMaterial material;
	DepthMaterial shadowMaterial;

	void asWorldModel()
	{
		this.worldShader = WORLD_SHADERS[boneCount];
		this.shadowShader = SHADOW_SHADERS[boneCount];

		createMaterials();

		if (materialInfo != null)
		{
			materialInfo.applyTo(material);
		}
	}

	protected abstract void createMaterials();

	void asMeshBatch()
	{
		this.worldShader = Shader.MESH_BATCH;
		this.shadowShader = Shader.MESH_BATCH_DEPTH;
		
		createMaterials();

		if (materialInfo != null)
		{
			materialInfo.applyTo(material);
		}
	}

	void asSkybox()
	{
		this.worldShader = Shader.SKYBOX;
		this.shadowShader = Shader.DEPTH_1;
		
		createMaterials();

		if (materialInfo != null)
		{
			materialInfo.applyTo(material);
		}
	}

	@Override
	public void setBoneCount(int boneCount)
	{
		this.boneCount = boneCount;
	}

	@Override
	public void setVertexData(float[] vertexData, int[] elementData)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
	}

	@Override
	public void setTextureIds(String diffuse, String normal, String specular)
	{
		this.diffuseTextureId = diffuse;
		this.normalTextureId = normal;
		this.specularTextureId = specular;
	}

	@Override
	public String[] GetTextureIds()
	{
		TEXTURE_ID_HELPER[0] = diffuseTextureId;
		TEXTURE_ID_HELPER[1] = normalTextureId;
		TEXTURE_ID_HELPER[2] = specularTextureId;

		return TEXTURE_ID_HELPER;
	}

	@Override
	public String getDiffuseTextureId()
	{
		return diffuseTextureId;
	}

	@Override
	public String getNormalTextureId()
	{
		return normalTextureId;
	}

	@Override
	public String getSpecularTextureId()
	{
		return specularTextureId;
	}

	@Override
	public void setDiffuseTexture(AbstractTexture diffuse)
	{
		diffuseTexture = diffuse;
	}

	@Override
	public void setNormalTexture(AbstractTexture normal)
	{
		normalTexture = normal;
	}

	@Override
	public void setSpecularTexture(AbstractTexture specular)
	{
		specularTexture = specular;
	}

	@Override
	public void setTextures(AbstractTexture diffuse, AbstractTexture normal, AbstractTexture specular)
	{
		material.setDiffuse(diffuse);
		material.setNormal(normal);
		material.setSpecular(specular);
	}

	@Override
	public void setMaterialData(IMaterialObject materialInfo)
	{
		this.materialInfo = materialInfo;
	}

	void dispose()
	{
		if (physBox != null)
		{
			physBox.dispose();
			physBox = null;
		}

		vertexData = null;
		elementData = null;

		material = null;
		shadowMaterial = null;
	}

}
