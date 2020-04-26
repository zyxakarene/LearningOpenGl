package zyx.engine.components.screen.base;

import zyx.engine.components.animations.IFocusable;
import zyx.engine.utils.ScreenSize;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.opengl.stencils.StencilControl;
import zyx.opengl.stencils.StencilLayer;
import zyx.utils.math.Vector2Int;

public final class Stage extends DisplayObjectContainer implements IFocusable
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	private IFocusable focusedTarget;

	public final DisplayObjectContainer loadingScreenLayer;
	public final DisplayObjectContainer tooltipLayer;
	public final DisplayObjectContainer hudLayer;

	private Stage()
	{
		name = "";

		loadingScreenLayer = new DisplayObjectContainer();
		loadingScreenLayer.name = "LoadingContainer";

		tooltipLayer = new DisplayObjectContainer();
		tooltipLayer.name = "TooltipContainer";

		hudLayer = new DisplayObjectContainer();
		hudLayer.name = "HudContainer";

		crawler = new InteractionCrawler(this, hudLayer, tooltipLayer, loadingScreenLayer);
		stage = this;

		ScreenSize.addListener(this::onScreenSizeChanged);

		addChild(tooltipLayer);
		addChild(hudLayer);
		addChild(loadingScreenLayer);
	}

	private void onScreenSizeChanged(Vector2Int size)
	{
		updateTransforms(true);
	}
	
	public final void drawStage()
	{
		BufferBinder.bindBuffer(Buffer.DEFAULT);

		shader.bind();
		shader.setClipRect(0, ScreenSize.width, 0, ScreenSize.height);

		StencilControl.getInstance().startMaskingLayer(StencilLayer.PLAYER_CHARACTER, Buffer.DEFAULT);
		tooltipLayer.draw();
		hudLayer.draw();
		StencilControl.getInstance().stopMaskingLayer(StencilLayer.PLAYER_CHARACTER, Buffer.DEFAULT);

		loadingScreenLayer.draw();
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
}
