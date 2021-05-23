package zyx.opengl.models.implementations.renderers;

import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.camera.Camera;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;

public class WorldModelRenderer extends MeshRenderer<WorldModelMaterial, WorldModel>
{
	private static final Vector3f HELPER_CAM_POS = new Vector3f();
	private static final AnimationController DEFAULT_CONTROLLER = new AnimationController();
	
	private int cubemapIndex;
	private AnimationController animController;

	public WorldModelRenderer(WorldModel model, WorldModelMaterial[] materials)
	{
		super(model, materials);
		animController = DEFAULT_CONTROLLER;
	}
	
	//TODO: Shadows
	@Override
	protected void onPreDraw()
	{
//		if (parent != null)
//		{
//			Camera.getInstance().getPosition(true, HELPER_CAM_POS);
//			float distance = FloatMath.distance(parent.position, HELPER_CAM_POS);
//			drawMaterials.setShadowDistance(distance);
//		}
//		else
//		{
//			drawMaterials.setShadowDistance(-1);
//		}

		WorldShader.cubemapIndex = cubemapIndex;
		if (model.ready)
		{
			model.setAnimation(animController);
		}
	}

	@Override
	public void draw()
	{
		if (model.ready)
		{
			if (model.refreshed)
			{
				model.refreshed = false;
				MeshRenderList.getInstance().dirtify();
			}
			
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
		animController = animationController;
	}

	public void SetCubemapIndex(int index)
	{
		cubemapIndex = index;
	}

}
