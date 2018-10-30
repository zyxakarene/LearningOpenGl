package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;

public class WorldModel extends AbstractModel
{
	private WorldShader shader;
	private Skeleton skeleton;
	
	private PhysBox physBox;

	public WorldModel(LoadableWorldModelVO vo)
	{
		super(Shader.WORLD);
		this.shader = (WorldShader) meshShader;

		skeleton = vo.skeleton;
		physBox = vo.physBox;
		setVertexData(vo.vertexData, vo.elementData);
		setTexture(vo.gameTexture);
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
		skeleton.update();
		shader.uploadBones();
		super.draw();
	}

	public PhysBox getPhysbox()
	{
		return physBox;
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
