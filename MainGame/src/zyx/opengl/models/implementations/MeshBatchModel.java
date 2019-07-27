package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.MeshBatchDepthShader;
import zyx.opengl.shaders.implementations.MeshBatchShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IShadowable;

public class MeshBatchModel extends AbstractInstancedModel implements IShadowable
{

	private static final int INSTANCE_DATA_AMOUNT = 8;
	
	private MeshBatchShader shader;
	private MeshBatchDepthShader shadowShader;

	private Vector3f radiusCenter;
	private float radius;

	public MeshBatchModel(LoadableWorldModelVO vo)
	{
		super(Shader.MESH_BATCH);

		this.shader = (MeshBatchShader) meshShader;
		this.shadowShader = ShaderManager.getInstance().<MeshBatchDepthShader>get(Shader.MESH_BATCH_DEPTH);

		radiusCenter = vo.radiusCenter;
		radius = vo.radius;

		setVertexData(vo.vertexData, vo.elementData);
		AbstractTexture[] texs = new AbstractTexture[]
		{
			vo.gameTexture, vo.normalTexture, vo.specularTexture
		};
		setTextures(texs);
	}

	public void setMeshBatchData(float[] instanceData)
	{
		setInstanceData(instanceData, instanceData.length / INSTANCE_DATA_AMOUNT);
	}
	
	@Override
	public void draw()
	{
		DeferredRenderer.getInstance().bindBuffer();
		shader.bind();
		shader.upload();

		super.draw();

		DepthRenderer.getInstance().drawShadowable(this);
	}

	@Override
	public void drawShadow()
	{
//		shadowShader.bind();
//		shadowShader.upload();
//
//		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_0);
//		super.draw();
//
//		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_1);
//		super.draw();
//
//		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_2);
//		super.draw();
//
//		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_3);
//		super.draw();
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
		addAttribute("position", 3, 12, 0);
		addAttribute("normals", 3, 12, 3);
		addAttribute("texcoord", 2, 12, 6);
//		addAttribute("indexes", 2, 12, 8);
//		addAttribute("weights", 2, 12, 10);

		addInstanceAttribute("insPosition", 3, 8, 0);
		addInstanceAttribute("insRotation", 4, 8, 3);
		addInstanceAttribute("insScale", 1, 8, 7);
	}

}
