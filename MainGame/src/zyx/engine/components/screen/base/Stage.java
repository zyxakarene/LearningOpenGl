package zyx.engine.components.screen.base;

import java.awt.event.KeyEvent;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.controls.input.InputManager;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.opengl.stencils.StencilControl;
import zyx.opengl.stencils.StencilLayer;
import zyx.utils.math.Vector2Int;

public final class Stage extends DisplayObjectContainer implements ICallback<Character>, IFocusable
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	private IFocusable focusedTarget;

	private ICallback<Vector2Int> screenSizeChanged;

	public final DisplayObjectContainer tooltipLayer;
	public final DisplayObjectContainer hudLayer;

	private Stage()
	{
		tooltipLayer = new DisplayObjectContainer();
		hudLayer = new DisplayObjectContainer();

		crawler = new InteractionCrawler(this, hudLayer, tooltipLayer);
		stage = this;

		InputManager.getInstance().OnKeyPressed.addCallback(this);

		screenSizeChanged = (Vector2Int data)
				-> 
				{
					updateTransforms(true);
		};

		ScreenSize.addListener(screenSizeChanged);
	}

	public final void drawStage()
	{
		BufferBinder.bindBuffer(Buffer.DEFAULT);

		shader.bind();
		shader.setClipRect(0, ScreenSize.width, 0, ScreenSize.height);
		draw();

		StencilControl.getInstance().startMaskingLayer(StencilLayer.PLAYER_CHARACTER, Buffer.DEFAULT);
		tooltipLayer.draw();
		hudLayer.draw();
		StencilControl.getInstance().stopMaskingLayer(StencilLayer.PLAYER_CHARACTER, Buffer.DEFAULT);
	}

	public final void checkStageMouseInteractions(int x, int y)
	{
		crawler.interactionTest(x, y);
	}

	public void setFocusedObject(IFocusable target)
	{
		if (focusedTarget != null)
		{
			focusedTarget.onUnFocused();
		}

		focusedTarget = target;

		if (focusedTarget != null)
		{
			focusedTarget.onFocused();
		}
	}

	@Override
	public float getWidth()
	{
		return ScreenSize.width;
	}

	@Override
	public float getHeight()
	{
		return ScreenSize.height;
	}

	@Override
	public void dispose()
	{
		throw new RuntimeException("Do not dispose the stage please");
	}

	@Override
	public void onCallback(Character data)
	{
		if (focusedTarget != null)
		{
			boolean printable = !Character.isISOControl(data) || (data == KeyEvent.VK_BACK_SPACE || data == KeyEvent.VK_DELETE || data == KeyEvent.VK_ENTER || data == '\r');
			if (printable)
			{
				focusedTarget.onKeyPressed(data);
			}
		}
	}

	@Override
	public void onKeyPressed(char character)
	{
	}

	@Override
	public void onFocused()
	{
	}

	@Override
	public void onUnFocused()
	{
	}
}
