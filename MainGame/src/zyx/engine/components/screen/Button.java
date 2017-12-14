package zyx.engine.components.screen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.KeyboardControl;
import zyx.game.controls.MouseControl;
import zyx.game.controls.SharedPools;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.utils.cheats.Print;

public class Button extends DisplayObjectContainer implements IClickable
{

	private static final Vector4f MOUSE_POS_HELPER = new Vector4f();
	private Matrix4f invertedModel;

	private Image upImg;
	private Image hoverImg;
	private Image downImg;

	public Button(String upTexture, String hoverTexture, String downTexture)
	{
		upImg = new Image(upTexture);
		hoverImg = new Image(hoverTexture);
		downImg = new Image(downTexture);
		invertedModel = SharedPools.MATRIX_POOL.getInstance();

		addChild(upImg);
		addChild(hoverImg);
		addChild(downImg);
		
		KeyboardControl.listenForHolding(Keyboard.KEY_F);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		upImg = null;
		hoverImg = null;
		downImg = null;
	}

	@Override
	public void checkClick()
	{
		Matrix4f.invert(ScreenShader.MATRIX_MODEL, invertedModel);
		MOUSE_POS_HELPER.set(MouseControl.MOUSE_POS);
		MOUSE_POS_HELPER.y *= -1;
		Matrix4f.transform(invertedModel, MOUSE_POS_HELPER, MOUSE_POS_HELPER);

		boolean hit = MOUSE_POS_HELPER.x >= 0 && MOUSE_POS_HELPER.y <= 0 && MOUSE_POS_HELPER.x <= 100 && MOUSE_POS_HELPER.y >= -100;
		hoverImg.visible = hit;
		upImg.visible = !hit;
		Print.out("Did I hit the button:", hit);
		if (hit && KeyboardControl.isKeyDown(Keyboard.KEY_F))
		{
			downImg.visible = true;
			hoverImg.visible = false;
			upImg.visible = false;
		}
		else
		{
			downImg.visible = false;
		}

	}

}
