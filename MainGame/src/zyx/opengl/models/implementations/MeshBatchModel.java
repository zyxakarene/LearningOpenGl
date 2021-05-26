package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.materials.impl.DepthMaterial;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.renderers.MeshBatchRenderer;
import zyx.opengl.models.implementations.renderers.wrappers.MeshBatchModelWrapper;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.*;
import zyx.utils.interfaces.IShadowable;

public class MeshBatchModel extends AbstractInstancedModel<WorldModelMaterial> implements IShadowable
{
	private static final int POSITION_LENGTH = 3;
	private static final int NORMALS_LENGTH = 3;
	private static final int TEX_COORDS_LENGTH = 2;
	private static final int BONE_LENGTH = 2;

	public static final int INSTANCE_DATA_AMOUNT = 9;
	
	private MeshBatchModelShaderData[] shaderData;

	private Vector3f radiusCenter;
	private float radius;

	public MeshBatchModel(LoadableWorldModelVO vo)
	{
		refresh(vo);
	}

	public void refresh(LoadableWorldModelVO vo)
	{
		radiusCenter = vo.radiusCenter;
		radius = vo.radius;

		setSubMeshCount(vo.subMeshCount);
		setDefaultMaterials(vo.getDefaultMaterials());
		createObjects();
		
		shaderData = new MeshBatchModelShaderData[vo.subMeshCount];
		for (int i = 0; i < vo.subMeshCount; i++)
		{
			MeshBatchModelShaderData data = new MeshBatchModelShaderData();
			AbstractLoadableSubMeshModelVO subMesh = vo.subMeshes[i];
			data.shadowMaterial = subMesh.shadowMaterial;
			data.boneCount = subMesh.boneCount;
			data.shader = (MeshBatchShader) subMesh.material.shader;
			data.shadowShader = ShaderManager.getInstance().<MeshBatchDepthShader>get(subMesh.shadowShader);
			
			shaderData[i] = data;
			
			bindVao(i);
			setVertexData(i, subMesh.vertexData, subMesh.elementData);
		}
		
		setupAttributes();
	}
	
	public void setMeshBatchData(int index, float[] instanceData)
	{
		setInstanceData(index, instanceData, instanceData.length / INSTANCE_DATA_AMOUNT);
	}
	
	@Override
	public void draw(int index, WorldModelMaterial material)
	{
		DeferredRenderer.getInstance().bindBuffer();
		shaderData[index].shader.bind();
		shaderData[index].shader.upload();

		super.draw(index, material);

		if (material.castsShadows)
		{
			DepthRenderer.getInstance().drawShadowable(this, index, material.activeShadowCascades);
		}
	}

	@Override
	public void drawShadow(int meshIndex, byte activeCascades)
	{
		MeshBatchModelShaderData data = shaderData[meshIndex];
		MeshBatchDepthShader shadowShader = data.shadowShader;
		DepthMaterial shadowMaterial = data.shadowMaterial;
		
		shadowShader.bind();
		shadowShader.upload();

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_0);
		super.draw(meshIndex, shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_1);
		super.draw(meshIndex, shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_2);
		super.draw(meshIndex, shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_3);
		super.draw(meshIndex, shadowMaterial);
	}

	public Vector3f getRadiusCenter()
	{
		return radiusCenter;
	}

	public float getRadius()
	{
		return radius;
	}

	@Override
	protected void setupAttributes()
	{
		for (int i = 0; i < subMeshCount; i++)
		{
			bindVao(i);

			ModelShaderData data = shaderData[i];
			int stride = POSITION_LENGTH + NORMALS_LENGTH + TEX_COORDS_LENGTH + (BONE_LENGTH * data.boneCount);
			
			addAttribute(i, "position", POSITION_LENGTH, stride, 0);
			addAttribute(i, "normals", NORMALS_LENGTH, stride, 3);
			addAttribute(i, "texcoord", TEX_COORDS_LENGTH, stride, 6);
	//		addAttribute(i, "indexes", boneCount, stride, 8);
	//		addAttribute(i, "weights", boneCount, stride, 8 + boneCount);

			addInstanceAttribute(i, "insPosition", 3, 9, 0);
			addInstanceAttribute(i, "insRotation", 4, 9, 3);
			addInstanceAttribute(i, "insScale", 1, 9, 7);
			addInstanceAttribute(i, "insCubemap", 1, 9, 8);
		}
	}

	@Override
	public MeshBatchModelWrapper createWrapper()
	{
		MeshBatchRenderer[] array = new MeshBatchRenderer[subMeshCount];
		for (int i = 0; i < subMeshCount; i++)
		{
			WorldModelMaterial material = getDefaultMaterial(i);
			array[i] = new MeshBatchRenderer(this, i, material);
		}

		return new MeshBatchModelWrapper(array, this);
	}

	private class MeshBatchModelShaderData extends ModelShaderData<MeshBatchShader, MeshBatchDepthShader>
	{
	}
}
