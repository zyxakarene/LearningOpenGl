package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.materials.RenderQueue;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.AbstractMultiModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.renderers.WorldModelRenderer;
import zyx.opengl.models.implementations.renderers.wrappers.WorldModelWrapper;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.DepthShader;
import zyx.utils.interfaces.IShadowable;

public class WorldModel extends AbstractMultiModel<WorldModelMaterial> implements IShadowable
{

	private static final int POSITION_LENGTH = 3;
	private static final int NORMALS_LENGTH = 3;
	private static final int TEX_COORDS_LENGTH = 2;
	private static final int BONE_LENGTH = 2;

	private WorldModelShaderData[] shaderData;
	
	private Skeleton skeleton;
	private PhysBox physBox;
	private Vector3f radiusCenter;
	private float radius;
	
	public boolean ready;
	public boolean refreshed;
	
	public WorldModel(AbstractLoadableModelVO vo)
	{
		refresh(vo);
	}

	public void refresh(AbstractLoadableModelVO vo)
	{
		skeleton = vo.skeleton;
		physBox = vo.physBox;
		radiusCenter = vo.radiusCenter;
		radius = vo.radius;
		
		setSubMeshCount(vo.subMeshCount);
		setDefaultMaterials(vo.getDefaultMaterials());
		createObjects();
		
		shaderData = new WorldModelShaderData[vo.subMeshCount];
		for (int i = 0; i < vo.subMeshCount; i++)
		{
			WorldModelShaderData data = new WorldModelShaderData();
			AbstractLoadableSubMeshModelVO subMesh = vo.subMeshes[i];
			data.shadowMaterial = subMesh.shadowMaterial;
			data.boneCount = subMesh.boneCount;
			data.shader = (WorldShader) subMesh.material.shader;
			data.shadowShader = ShaderManager.getInstance().<DepthShader>get(subMesh.shadowShader);
			
			shaderData[i] = data;
			
			bindVao(i);
			setVertexData(i, subMesh.vertexData, subMesh.elementData);
		}
		
		setupAttributes();

		refreshed = true;
		ready = true;
	}

	@Override
	public WorldModelWrapper createWrapper()
	{
		WorldModelRenderer[] array = new WorldModelRenderer[subMeshCount];
		for (int i = 0; i < subMeshCount; i++)
		{
			WorldModelMaterial material = getDefaultMaterial(i);
			array[i] = new WorldModelRenderer(this, i, material);
		}

		return new WorldModelWrapper(array, this);
	}

	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawWorld();
	}

	public Joint getAttatchment(String name)
	{
		return skeleton.getBoneByName(name);
	}

	public void setAnimation(AnimationController controller)
	{
		skeleton.setCurrentAnimation(controller);
	}

	@Override
	public void draw(int index, WorldModelMaterial material)
	{
		if (material.queue == RenderQueue.OPAQUE)
		{
			DeferredRenderer.getInstance().bindBuffer();
		}
		
		skeleton.update();
		super.draw(index, material);

		if (material.queue == RenderQueue.OPAQUE && material.castsShadows && material.activeShadowCascades > 0)
		{
			DepthRenderer.getInstance().drawShadowable(this, material.activeShadowCascades);
			DeferredRenderer.getInstance().bindBuffer();
		}
	}

	@Override
	public void drawShadow(byte activeCascades)
	{
		//TODO: Shadows for multi meshes
//		shadowShader.bind();
//		shadowShader.upload();
//
//		if ((activeCascades & WorldModelMaterial.DRAW_CASCADE_1) == WorldModelMaterial.DRAW_CASCADE_1)
//		{
//			shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_0);
//			super.draw(shadowMaterial);
//		}
//
//		if ((activeCascades & WorldModelMaterial.DRAW_CASCADE_2) == WorldModelMaterial.DRAW_CASCADE_2)
//		{
//			shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_1);
//			super.draw(shadowMaterial);
//		}
//
//		if ((activeCascades & WorldModelMaterial.DRAW_CASCADE_3) == WorldModelMaterial.DRAW_CASCADE_3)
//		{
//			shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_2);
//			super.draw(shadowMaterial);
//		}
//
//		if ((activeCascades & WorldModelMaterial.DRAW_CASCADE_4) == WorldModelMaterial.DRAW_CASCADE_4)
//		{
//			shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_3);
//			super.draw(shadowMaterial);
//		}
	}

	public PhysBox getPhysbox()
	{
		return physBox;
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
			addAttribute(i, "indexes", data.boneCount, stride, 8);
			addAttribute(i, "weights", data.boneCount, stride, 8 + data.boneCount);
		}
	}

	public Joint getBoneByName(String boneName)
	{
		return skeleton.getBoneByName(boneName);
	}

	public Joint getBoneById(int boneId)
	{
		return skeleton.getBoneById((byte) boneId);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (physBox != null)
		{
			physBox.dispose();
			physBox = null;
		}
		
		skeleton = null;
		shaderData = null;
	}
	
	private class WorldModelShaderData extends ModelShaderData<WorldShader, DepthShader>
	{
	}
}
