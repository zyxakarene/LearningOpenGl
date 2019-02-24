package zyx.engine.components.screen.base;

import java.awt.event.KeyEvent;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.controls.input.InputManager;
import zyx.utils.GameConstants;

public final class Stage extends DisplayObjectContainer implements ICallback<Character>, IFocusable
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	private IFocusable focusedTarget;
	
	private Stage()
	{
		crawler = new InteractionCrawler(this);
		stage = this;
		
		InputManager.getInstance().OnKeyPressed.addCallback(this);
	}

	public final void drawStage()
	{
		shader.bind();
		shader.setClipRect(0, GameConstants.GAME_WIDTH, 0, GameConstants.GAME_HEIGHT);
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
		return GameConstants.GAME_WIDTH;
	}

	@Override
	public float getHeight()
	{
		return GameConstants.GAME_HEIGHT;
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
