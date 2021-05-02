package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.materials.impl.DepthMaterial;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.loading.meshes.IMaterialObject;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public abstract class AbstractLoadableModelVO<TMaterial extends WorldModelMaterial>
{
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
	
	private IMaterialObject materialInfo;
	
	float vertexData[];
	int elementData[];
	Skeleton skeleton;
	String skeletonId;
	String diffuseTextureId;
	String normalTextureId;
	String specularTextureId;
	PhysBox physBox;
	Vector3f radiusCenter;
	float radius;
	Shader worldShader;
	Shader shadowShader;
	int boneCount;
	
	TMaterial material;
	DepthMaterial shadowMaterial;

	public AbstractLoadableModelVO()
	{
	}

	public void setBoneCount(int boneCount)
	{
		this.boneCount = boneCount;
	}
	
	public void asWorldModel()
	{
		this.worldShader = WORLD_SHADERS[boneCount];
		this.shadowShader = SHADOW_SHADERS[boneCount];
		
		createMaterials();
		
		if (materialInfo != null)
		{
			materialInfo.applyTo(material);
		}
	}
	
	public void asMeshBatch()
	{
		this.worldShader = Shader.MESH_BATCH;
		this.shadowShader = Shader.MESH_BATCH_DEPTH;
		
		createMaterials();
		
		if (materialInfo != null)
		{
			materialInfo.applyTo(material);
		}
	}
	
	public void asSkybox()
	{
		this.worldShader = Shader.SKYBOX;
		this.shadowShader = Shader.DEPTH_1;
		
		createMaterials();
		
		if (materialInfo != null)
		{
			materialInfo.applyTo(material);
		}
	}
	
	protected abstract void createMaterials();
	
	public void setVertexData(float[] vertexData, int[] elementData)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
	}
	
	public void setTextureIds(String diffuse, String normal, String specular)
	{
		this.diffuseTextureId = diffuse;
		this.normalTextureId = normal;
		this.specularTextureId = specular;
	}
	
	public void setSkeletonId(String skeletonId)
	{
		this.skeletonId = skeletonId;
	}
	
	public void setPhysBox(PhysBox physBox)
	{
		this.physBox = physBox;
	}
	
	public void setRadius(Vector3f radiusCenter, float radius)
	{
		this.radiusCenter = radiusCenter;
		this.radius = radius;
	}

	public void setSkeleton(Skeleton skeleton)
	{
		this.skeleton = skeleton;
	}

	public String getDiffuseTextureId()
	{
		return diffuseTextureId;
	}

	public String getNormalTextureId()
	{
		return normalTextureId;
	}

	public String getSpecularTextureId()
	{
		return specularTextureId;
	}

	public String getSkeletonId()
	{
		return skeletonId;
	}
	
	public void setDiffuseTexture(AbstractTexture gameTexture)
	{
		material.setDiffuse(gameTexture);
	}
	
	public void setNormalTexture(AbstractTexture gameTexture)
	{
		material.setNormal(gameTexture);
	}

	public void setSpecularTexture(AbstractTexture gameTexture)
	{
		material.setSpecular(gameTexture);
	}
	
	public void dispose()
	{
		if (physBox != null)
		{
			physBox.dispose();
		}
		
		vertexData = null;
		elementData = null;
		physBox = null;
		
		skeleton = null;
		
		material = null;
		shadowMaterial = null;
	}

	public void clean()
	{
		vertexData = null;
		elementData = null;
	}

	public void setMaterialData(IMaterialObject info)
	{
		materialInfo = info;
	}
}
