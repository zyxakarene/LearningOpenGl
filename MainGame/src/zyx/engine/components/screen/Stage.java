package zyx.engine.components.screen;

import java.util.ArrayList;
import zyx.game.controls.input.KeyboardData;

public final class Stage extends DisplayObjectContainer
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	private DisplayObject focusedTarget;
	
	private Stage()
	{
		crawler = new InteractionCrawler(this);
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

	void setFocusedObject(DisplayObject target)
	{
		focusedTarget = target;
	}
	
	@Override
	public void dispose()
	{
		throw new RuntimeException("Do not dispose the stage please");
	}

	public void update(long timestamp, int elapsedTime)
	{
		if (focusedTarget != null)
		{
			ArrayList<Character> keys = KeyboardData.data.downKeys;
			for (Character key : keys)
			{
//				System.out.println("Adding " + key);
//				focusedTarget.keyDown(key);
			}
		}
	}

}
