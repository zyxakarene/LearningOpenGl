package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.renderers.WorldModelRenderer;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.DepthShader;
import zyx.utils.interfaces.IShadowable;

public class WorldModel extends AbstractModel<WorldModelMaterial> implements IShadowable
{

	private static final int POSITION_LENGTH = 3;
	private static final int NORMALS_LENGTH = 3;
	private static final int TEX_COORDS_LENGTH = 2;
	private static final int BONE_LENGTH = 2;

	private WorldShader shader;
	private DepthShader shadowShader;

	private Skeleton skeleton;

	private PhysBox physBox;

	private Vector3f radiusCenter;
	private float radius;
	private int boneCount;
	
	public boolean ready;
	
	private WorldModelMaterial shadowMaterial;

	public WorldModel(AbstractLoadableModelVO vo)
	{
		super(vo.material);
		shadowMaterial = vo.shadowMaterial;
		boneCount = vo.boneCount;
		setup();

		this.shader = (WorldShader) meshShader;
		this.shadowShader = ShaderManager.getInstance().<DepthShader>get(vo.shadowShader);

		refresh(vo);
	}

	public void refresh(AbstractLoadableModelVO vo)
	{
		skeleton = vo.skeleton;
		physBox = vo.physBox;
		radiusCenter = vo.radiusCenter;
		radius = vo.radius;
		
		defaultMaterial = vo.material;
		shadowMaterial = vo.shadowMaterial;

		bindVao();

		if (boneCount != vo.boneCount)
		{
			boneCount = vo.boneCount;
			setupAttributes();
		}

		setVertexData(vo.vertexData, vo.elementData);
		
		ready = true;
	}

	@Override
	public WorldModelRenderer createRenderer()
	{
		return new WorldModelRenderer(this, defaultMaterial);
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
	public void draw(WorldModelMaterial material)
	{
		DeferredRenderer.getInstance().bindBuffer();
		skeleton.update();
		shader.uploadBones();
		super.draw(material);

		if (material.castsShadows)
		{
			DepthRenderer.getInstance().drawShadowable(this);
			DeferredRenderer.getInstance().bindBuffer();
		}
	}

	@Override
	public void drawShadow()
	{
		shadowShader.bind();
		shadowShader.upload();
		shadowShader.uploadBones();

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_0);
		super.draw(shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_1);
		super.draw(shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_2);
		super.draw(shadowMaterial);

		shadowShader.prepareShadowQuadrant(shadowShader.QUADRANT_3);
		super.draw(shadowMaterial);
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
		int stride = POSITION_LENGTH + NORMALS_LENGTH + TEX_COORDS_LENGTH + (BONE_LENGTH * boneCount);

		addAttribute("position", POSITION_LENGTH, stride, 0);
		addAttribute("normals", NORMALS_LENGTH, stride, 3);
		addAttribute("texcoord", TEX_COORDS_LENGTH, stride, 6);
		addAttribute("indexes", boneCount, stride, 8);
		addAttribute("weights", boneCount, stride, 8 + boneCount);
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
		shader = null;
	}

	public WorldShader getShader()
	{
		return shader;
	}
}
