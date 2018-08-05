package zyx.engine.components.screen;

public final class Stage extends DisplayObjectContainer
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	
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

	@Override
	public void dispose()
	{
		throw new RuntimeException("Do not dispose the stage please");
	}

}
