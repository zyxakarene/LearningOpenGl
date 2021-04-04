package zyx.engine.components.screen.base;

import org.lwjgl.opengl.GL11;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.base.docks.DockType;
import zyx.engine.components.screen.base.docks.EditorDock;
import zyx.engine.components.screen.base.docks.GameDock;
import zyx.engine.components.screen.base.docks.HierarchyDock;
import zyx.engine.focus.FocusManager;
import zyx.engine.utils.ScreenSize;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.utils.geometry.IntRectangle;
import zyx.utils.interfaces.IUpdateable;
import zyx.utils.math.Vector2Int;

public final class Stage extends DisplayObjectContainer implements IFocusable, IUpdateable
{

	private static final Stage INSTANCE = new Stage();

	private InteractionCrawler crawler;

	private ContainerDock gameDock;
	private ContainerDock hierarchyDock;
	private ContainerDock resourcesDock;
	private ContainerDock propertyDock;

	public DisplayObjectContainer loadingScreenLayer;
	public DisplayObjectContainer tooltipLayer;
	public DisplayObjectContainer hudLayer;

	public static Stage getInstance()
	{
		return INSTANCE;
	}
	
	private Stage()
	{
		name = "";
		stage = this;
	}

	public void initialize()
	{
		gameDock = new GameDock();
		hierarchyDock = new HierarchyDock();
		resourcesDock = new EditorDock(DockType.Bottom);
		propertyDock = new EditorDock(DockType.Right);

		setDockBounds();

		addChild(gameDock);
		addChild(hierarchyDock);
		addChild(resourcesDock);
		addChild(propertyDock);

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
	
	private void setDockBounds()
	{
		gameDock.setBounds(ScreenSize.gamePosX, ScreenSize.gameWidth, ScreenSize.gamePosY, ScreenSize.gameHeight);
		hierarchyDock.setBounds(0, ScreenSize.gamePosX, ScreenSize.gamePosY, ScreenSize.windowHeight);
		resourcesDock.setBounds(ScreenSize.gamePosX, ScreenSize.gameWidth, ScreenSize.gameHeight, ScreenSize.windowHeight - ScreenSize.gameHeight);
		propertyDock.setBounds(ScreenSize.gamePosX + ScreenSize.gameWidth, ScreenSize.windowWidth - ScreenSize.gamePosX - ScreenSize.gameWidth, ScreenSize.gamePosY, ScreenSize.windowHeight);
	}

	private void onScreenSizeChanged(Vector2Int size)
	{
		setDockBounds();
		updateTransforms(true);
	}

	public final void drawStage()
	{
		BufferBinder.bindBuffer(Buffer.DEFAULT);
		GL11.glViewport(0, 0, ScreenSize.windowWidth, ScreenSize.windowHeight);

		shader.bind();
		shader.setClipRect(0, ScreenSize.windowWidth, 0, ScreenSize.windowHeight);

		gameDock.draw();
		hierarchyDock.draw();
		resourcesDock.draw();
		propertyDock.draw();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		gameDock.update(timestamp, elapsedTime);
		hierarchyDock.update(timestamp, elapsedTime);
		resourcesDock.update(timestamp, elapsedTime);
		propertyDock.update(timestamp, elapsedTime);
	}
	
	public final void checkStageMouseInteractions(int x, int y)
	{
		if (touchable)
		{
			crawler.interactionTest(x, y);
		}
	}

	@Override
	protected void onChildAdded(DisplayObject child)
	{
		if (child instanceof IFocusable)
		{
			IFocusable focusableChild = (IFocusable) child;
			FocusManager.getInstance().add(focusableChild);
		}
	}

	@Override
	protected void onChildRemoved(DisplayObject child)
	{
		if (child instanceof IFocusable)
		{
			IFocusable focusableChild = (IFocusable) child;
			FocusManager.getInstance().remove(focusableChild);
		}
	}

	@Override
	public float getWidth()
	{
		return ScreenSize.windowWidth;
	}

	@Override
	public float getHeight()
	{
		return ScreenSize.windowHeight;
	}

	@Override
	protected void onDispose()
	{
		throw new RuntimeException("Do not dispose the stage please");
	}

	@Override
	public void onKeyPressed(char character)
	{
	}

	@Override
	public String getDebugIcon()
	{
		return "stage.png";
	}

	public void getDockSize(IntRectangle rect, DockType dockType)
	{
		if (dockType == null)
		{
			gameDock.getBounds(rect);
		}
		else
		{
			switch (dockType)
			{
				case Top:
					gameDock.getBounds(rect);
					break;
				case Left:
					hierarchyDock.getBounds(rect);
					break;
				case Bottom:
					resourcesDock.getBounds(rect);
					break;
				case Right:
					propertyDock.getBounds(rect);
					break;
			}
		}
	}
}
