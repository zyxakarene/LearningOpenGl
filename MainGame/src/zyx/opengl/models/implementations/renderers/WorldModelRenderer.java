package zyx.opengl.models.implementations.renderers;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.shaders.implementations.WorldShader;

public class WorldModelRenderer extends MeshRenderer<WorldModelMaterial, WorldModel>
{
	private static final Vector3f HELPER_CAM_POS = new Vector3f();
	private static final AnimationController DEFAULT_CONTROLLER = new AnimationController();
	
	private int cubemapIndex;
	private AnimationController animController;

	public WorldModelRenderer(WorldModel model, int meshIndex, WorldModelMaterial material)
	{
		super(model, meshIndex, material);		
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
//			drawMaterial.setShadowDistance(distance);
//		}
//		else
//		{
//			drawMaterial.setShadowDistance(-1);
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

	public void setAnimationController(AnimationController animationController)
	{
		animController = animationController;
	}

	public void setCubemapIndex(int index)
	{
		cubemapIndex = index;
	}
}
