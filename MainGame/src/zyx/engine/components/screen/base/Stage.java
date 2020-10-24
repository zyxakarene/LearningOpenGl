package zyx.engine.components.screen.base;

import org.lwjgl.opengl.GL11;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.image.Image;
import zyx.engine.utils.ScreenSize;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.opengl.buffers.LightingPassRenderer;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.GameConstants;
import zyx.utils.math.Vector2Int;

public final class Stage extends DisplayObjectContainer implements IFocusable
{

	public static final Stage instance = new Stage();

	private InteractionCrawler crawler;
	private IFocusable focusedTarget;

	public final DisplayObjectContainer gameScreen;
	public final DisplayObjectContainer loadingScreenLayer;
	public final DisplayObjectContainer tooltipLayer;
	public final DisplayObjectContainer hudLayer;

	private Stage()
	{
		name = "";

		LightingPassRenderer lightRenderer = LightingPassRenderer.getInstance();
				
		int gameWidth = GameConstants.DEFAULT_GAME_WIDTH;
		int gameHeight = GameConstants.DEFAULT_GAME_HEIGHT;
		
		Image resultImg = new Image();
		resultImg.setSize(gameWidth, -gameHeight);
		resultImg.setPosition(true, 0, gameHeight);
		resultImg.setTexture(new TextureFromInt(gameWidth, gameHeight, lightRenderer.outputInt(), TextureSlot.SHARED_DIFFUSE));
		resultImg.touchable = false;
		
		gameScreen = new DisplayObjectContainer();
		gameScreen.addChild(resultImg);
//		gameScreen.setWidth(GameConstants.DEFAULT_GAME_WIDTH);
//		gameScreen.setHeight(GameConstants.DEFAULT_GAME_HEIGHT);
		gameScreen.setPosition(true, 0, 0);
		gameScreen.name = "GameScreen";
		
		loadingScreenLayer = new DisplayObjectContainer();
		loadingScreenLayer.name = "LoadingContainer";

		tooltipLayer = new DisplayObjectContainer();
		tooltipLayer.name = "TooltipContainer";

		hudLayer = new DisplayObjectContainer();
		hudLayer.name = "HudContainer";

		crawler = new InteractionCrawler(this, hudLayer, tooltipLayer, loadingScreenLayer);
		stage = this;

		ScreenSize.addListener(this::onScreenSizeChanged);		
		gameScreen.addChild(tooltipLayer);
		gameScreen.addChild(hudLayer);
		gameScreen.addChild(loadingScreenLayer);
		addChild(gameScreen);
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
		
		gameScreen.draw();
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
