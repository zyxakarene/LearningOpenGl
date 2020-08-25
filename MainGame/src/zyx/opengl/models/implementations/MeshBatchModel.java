package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.materials.Material;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.MeshBatchDepthShader;
import zyx.opengl.shaders.implementations.MeshBatchShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.interfaces.IShadowable;

public class MeshBatchModel extends AbstractInstancedModel implements IShadowable
{

	public static final int INSTANCE_DATA_AMOUNT = 9;
	
	private MeshBatchShader shader;
	private MeshBatchDepthShader shadowShader;
	private WorldModelMaterial shadowMaterial;

	private Vector3f radiusCenter;
	private float radius;

	public MeshBatchModel(LoadableWorldModelVO vo)
	{
		super(vo.material);

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
	public void draw(Material material)
	{
		DeferredRenderer.getInstance().bindBuffer();
		shader.bind();
		shader.upload();

		super.draw(material);

		DepthRenderer.getInstance().drawShadowable(this);
	}

	@Override
	public void drawShadow()
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
		addAttribute("position", 3, 12, 0);
		addAttribute("normals", 3, 12, 3);
		addAttribute("texcoord", 2, 12, 6);
//		addAttribute("indexes", 2, 12, 8);
//		addAttribute("weights", 2, 12, 10);

		addInstanceAttribute("insPosition", 3, 9, 0);
		addInstanceAttribute("insRotation", 4, 9, 3);
		addInstanceAttribute("insScale", 1, 9, 7);
		addInstanceAttribute("insCubemap", 1, 9, 8);
		
		
	}

}
