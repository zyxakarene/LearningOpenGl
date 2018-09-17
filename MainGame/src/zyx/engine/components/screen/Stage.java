package zyx.engine.components.screen;

import java.awt.event.KeyEvent;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.KeyboardData;

public final class Stage extends DisplayObjectContainer implements ICallback<Character>
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
		draw();
	}

	public final void checkStageMouseInteractions(int x, int y)
	{
		crawler.interactionTest(x, y);
	}

	void setFocusedObject(IFocusable target)
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
}
