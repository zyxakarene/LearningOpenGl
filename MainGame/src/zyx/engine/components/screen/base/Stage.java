package zyx.engine.components.screen.base;

import org.lwjgl.opengl.GL11;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.base.docks.DockType;
import zyx.engine.components.screen.base.docks.GameDock;
import zyx.engine.utils.ScreenSize;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.utils.GameConstants;
import zyx.utils.geometry.IntRectangle;
import zyx.utils.math.Vector2Int;

public final class Stage extends DisplayObjectContainer implements IFocusable
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	private IFocusable focusedTarget;

	private final ContainerDock gameDock;

	public final DisplayObjectContainer loadingScreenLayer;
	public final DisplayObjectContainer tooltipLayer;
	public final DisplayObjectContainer hudLayer;

	private Stage()
	{
		name = "";

		gameDock = new GameDock();
		gameDock.setBounds(GameConstants.DEFAULT_GAME_POS_X, GameConstants.DEFAULT_GAME_WIDTH, GameConstants.DEFAULT_GAME_POS_Y, GameConstants.DEFAULT_GAME_HEIGHT);
		addChild(gameDock);

		loadingScreenLayer = new DisplayObjectContainer();
		loadingScreenLayer.name = "LoadingContainer";

		tooltipLayer = new DisplayObjectContainer();
		tooltipLayer.name = "TooltipContainer";

		hudLayer = new DisplayObjectContainer();
		hudLayer.name = "HudContainer";

		crawler = new InteractionCrawler(this, gameDock);
		stage = this;

		ScreenSize.addListener(this::onScreenSizeChanged);
		gameDock.addChild(tooltipLayer);
		gameDock.addChild(hudLayer);
		gameDock.addChild(loadingScreenLayer);
		addChild(gameDock);
	}

	private void onScreenSizeChanged(Vector2Int size)
	{
		updateTransforms(true);
	}

	public final void drawStage()
	{
		BufferBinder.bindBuffer(Buffer.DEFAULT);
		GL11.glViewport(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
		
		shader.bind();
		shader.setClipRect(0, GameConstants.WINDOW_WIDTH, 0, GameConstants.WINDOW_HEIGHT);

		gameDock.draw();
	}

	public final void checkStageMouseInteractions(int x, int y)
	{
		if (touchable)
		{
			crawler.interactionTest(x, y);
		}
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

	@Override
	public String getDebugIcon()
	{
		return "stage.png";
	}

	public void getDockSize(IntRectangle rect, DockType dockType)
	{
		switch (dockType)
		{
			case Top:
				gameDock.getBounds(rect);
				break;
		}
	}
}
