package zyx.opengl.camera;

import zyx.game.behavior.camera.CameraUpdateViewBehavior;
import zyx.game.behavior.freefly.CameraFreeFlyBehavior;
import zyx.game.components.GameObject;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.interfaces.IUpdateable;

public class Camera extends GameObject implements IUpdateable, IPositionable
{
		
	public Camera()
	{
		Projection.createPerspective(70f, 0.001f, 2f, WorldShader.MATRIX_PROJECTION);
		Projection.createOrthographic(1f, 2f, ScreenShader.MATRIX_PROJECTION);
		
		addBehavior(new CameraFreeFlyBehavior());
		addBehavior(new CameraUpdateViewBehavior());
		
		position.z = -1;
	}
}
