package zyx.opengl.models.implementations.renderers.wrappers;

import org.lwjgl.util.vector.ReadableVector3f;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.renderers.WorldModelRenderer;

public class WorldModelWrapper extends MeshWrapper<WorldModelMaterial, WorldModel, WorldModelRenderer>
{
	
	public WorldModelWrapper(WorldModelRenderer[] renderers, WorldModel model)
	{
		super(renderers, model);
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

	public PhysBox getPhysbox()
	{
		return model.getPhysbox();
	}

	public Joint getBoneById(int boneId)
	{
		return model.getBoneById(boneId);
	}

	public void setCubemapIndex(int cubemapIndex)
	{
		int len = renderers.length;
		for (int i = 0; i < len; i++)
		{
			renderers[i].setCubemapIndex(cubemapIndex);
		}
	}

	public void setAnimation(AnimationController animationController)
	{
		int len = renderers.length;
		for (int i = 0; i < len; i++)
		{
			renderers[i].setAnimationController(animationController);
		}
	}
}
