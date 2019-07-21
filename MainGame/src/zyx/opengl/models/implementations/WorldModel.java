package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.GLUtils;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.DepthShader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IShadowable;

public class WorldModel extends AbstractModel implements IShadowable
{

	protected WorldShader shader;
	protected DepthShader shadowShader;

	protected Skeleton skeleton;

	private PhysBox physBox;

	private Vector3f radiusCenter;
	private float radius;

	public WorldModel(LoadableWorldModelVO vo)
	{
		super(Shader.WORLD);
		this.shader = (WorldShader) meshShader;
		this.shadowShader = ShaderManager.getInstance().<DepthShader>get(Shader.DEPTH);

		skeleton = vo.skeleton;
		physBox = vo.physBox;
		radiusCenter = vo.radiusCenter;
		radius = vo.radius;

		setVertexData(vo.vertexData, vo.elementData);
		AbstractTexture[] texs = new AbstractTexture[]
		{
			vo.gameTexture, vo.normalTexture, vo.specularTexture
		};
		setTextures(texs);
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
	public void draw()
	{
		DeferredRenderer.getInstance().bindBuffer();
		skeleton.update();
		shader.uploadBones();
		super.draw();

		DepthRenderer.getInstance().drawShadowable(this);
	}

	@Override
	public void drawShadow()
	{
		shadowShader.bind();
		shadowShader.upload();
		shadowShader.uploadBones();

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_0);
		super.draw();

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_1);
		super.draw();

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_2);
		super.draw();

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_3);
		super.draw();
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
		addAttribute("position", 3, 12, 0);
		addAttribute("normals", 3, 12, 3);
		addAttribute("texcoord", 2, 12, 6);
		addAttribute("indexes", 2, 12, 8);
		addAttribute("weights", 2, 12, 10);
	}

	public Joint getBoneByName(String boneName)
	{
		return skeleton.getBoneByName(boneName);
	}

	public Joint getBoneById(int boneId)
	{
		return skeleton.getBoneById(boneId);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		skeleton.dispose();

		skeleton = null;
		shader = null;
		physBox = null;
	}

}
