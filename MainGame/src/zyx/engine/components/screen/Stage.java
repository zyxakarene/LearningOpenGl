package zyx.engine.components.screen;

public final class Stage extends DisplayObjectContainer
{

	public static final Stage instance = new Stage();

	private Stage()
	{
	}

	public final void drawStage()
	{
		shader.bind();
		draw();
	}

	public final void checkStageMouseInteractions()
	{
		checkClicks(false);
	}

	@Override
	public void dispose()
	{
		throw new RuntimeException("Do not dispose the stage please");
	}

}
