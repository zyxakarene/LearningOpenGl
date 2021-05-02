package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.materials.impl.DepthMaterial;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.renderers.MeshBatchRenderer;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.MeshBatchDepthShader;
import zyx.opengl.shaders.implementations.MeshBatchShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.interfaces.IShadowable;

public class MeshBatchModel extends AbstractInstancedModel<WorldModelMaterial> implements IShadowable
{
	private static final int POSITION_LENGTH = 3;
	private static final int NORMALS_LENGTH = 3;
	private static final int TEX_COORDS_LENGTH = 2;
	private static final int BONE_LENGTH = 2;

	public static final int INSTANCE_DATA_AMOUNT = 9;
	
	private MeshBatchShader shader;
	private MeshBatchDepthShader shadowShader;
	private DepthMaterial shadowMaterial;
	private int boneCount;

	private Vector3f radiusCenter;
	private float radius;

	public MeshBatchModel(LoadableWorldModelVO vo)
	{
		super(vo.material);
		
		boneCount = vo.boneCount;
		setup();

		this.shadowMaterial = vo.shadowMaterial;
		this.shader = (MeshBatchShader) meshShader;
		this.shadowShader = ShaderManager.getInstance().<MeshBatchDepthShader>get(Shader.MESH_BATCH_DEPTH);

		refresh(vo);
	}

	public void refresh(LoadableWorldModelVO vo)
	{
		radiusCenter = vo.radiusCenter;
		radius = vo.radius;

		defaultMaterial = vo.material;
		shadowMaterial = vo.shadowMaterial;
		
		bindVao();
		setVertexData(vo.vertexData, vo.elementData);
	}
	
	public void setMeshBatchData(float[] instanceData)
	{
		setInstanceData(instanceData, instanceData.length / INSTANCE_DATA_AMOUNT);
	}
	
	@Override
	public void draw(WorldModelMaterial material)
	{
		DeferredRenderer.getInstance().bindBuffer();
		shader.bind();
		shader.upload();

		super.draw(material);

		if (material.castsShadows)
		{
			DepthRenderer.getInstance().drawShadowable(this, material.activeShadowCascades);
		}
	}

	@Override
	public void drawShadow(byte activeCascades)
	{
		shadowShader.bind();
		shadowShader.upload();

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_0);
		super.draw(shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_1);
		super.draw(shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_2);
		super.draw(shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_3);
		super.draw(shadowMaterial);
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
		int stride = POSITION_LENGTH + NORMALS_LENGTH + TEX_COORDS_LENGTH + (BONE_LENGTH * boneCount);
		addAttribute("position", POSITION_LENGTH, stride, 0);
		addAttribute("normals", NORMALS_LENGTH, stride, 3);
		addAttribute("texcoord", TEX_COORDS_LENGTH, stride, 6);
//		addAttribute("indexes", boneCount, stride, 8);
//		addAttribute("weights", boneCount, stride, 8 + boneCount);

		addInstanceAttribute("insPosition", 3, 9, 0);
		addInstanceAttribute("insRotation", 4, 9, 3);
		addInstanceAttribute("insScale", 1, 9, 7);
		addInstanceAttribute("insCubemap", 1, 9, 8);
	}

	@Override
	public MeshBatchRenderer createRenderer()
	{
		return new MeshBatchRenderer(this, defaultMaterial);
	}

}
