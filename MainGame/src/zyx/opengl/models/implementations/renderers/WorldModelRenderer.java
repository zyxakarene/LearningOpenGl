package zyx.opengl.models.implementations.renderers;

import org.lwjgl.util.vector.ReadableVector3f;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.shaders.implementations.WorldShader;

public class WorldModelRenderer extends MeshRenderer<WorldModelMaterial, WorldModel>
{

	public WorldModelRenderer(WorldModel model, WorldModelMaterial material)
	{
		super(model, material);
	}

	@Override
	public void draw()
	{
		if (model.ready)
		{
			super.draw();
		}
	}

	public float getRadius()
	{
		return model.getRadius();
	}

	public ReadableVector3f getRadiusCenter()
	{
		return model.getRadiusCenter();
	}

	public Joint getBoneByName(String attachmentPoint)
	{
		return model.getBoneByName(attachmentPoint);
	}

	public WorldShader getShader()
	{
		return model.getShader();
	}

	public PhysBox getPhysbox()
	{
		return model.getPhysbox();
	}

	public Joint getBoneById(int boneId)
	{
		return model.getBoneById(boneId);
	}

	public void setAnimation(AnimationController animationController)
	{
		if (model.ready)
		{
			model.setAnimation(animationController);
		}
	}

}
