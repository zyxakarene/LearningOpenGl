package zyx.engine.components.screen.base;

import java.awt.event.KeyEvent;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.controls.input.InputManager;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.utils.math.Vector2Int;

public final class Stage extends DisplayObjectContainer implements ICallback<Character>, IFocusable
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	private IFocusable focusedTarget;
	
	private ICallback<Vector2Int> screenSizeChanged;
	
	public final DisplayObjectContainer tooltipLayer;
	
	private Stage()
	{
		crawler = new InteractionCrawler(this);
		stage = this;
		
		InputManager.getInstance().OnKeyPressed.addCallback(this);
		
		screenSizeChanged = (Vector2Int data) ->
		{
			updateTransforms(true);
		};
		
		ScreenSize.addListener(screenSizeChanged);
		
		tooltipLayer = new DisplayObjectContainer();
		addChild(tooltipLayer);
	}

	public final void drawStage()
	{
		BufferBinder.bindBuffer(Buffer.DEFAULT);
		
		shader.bind();
		shader.setClipRect(0, ScreenSize.width, 0, ScreenSize.height);
		draw();
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
