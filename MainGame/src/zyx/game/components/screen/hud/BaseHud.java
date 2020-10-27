package zyx.game.components.screen.hud;

import zyx.engine.components.screen.image.Image;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;
import zyx.opengl.buffers.*;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;
import zyx.utils.math.Vector2Int;

public class BaseHud extends JsonSprite implements ICallback<Vector2Int>
{
	@Override
	public String getResource()
	{
		return "json.hud.empty_hud";
	}

	@Override
	protected void onComponentsCreated()
	{
		ScreenSize.addListener(this);
		addDebugImages();
	}

	private void addDebugImages()
	{
		if (Math.random() > 0)
		{
			//return;
		}
		DeferredRenderer renderer = DeferredRenderer.getInstance();
		DepthRenderer depthRenderer = DepthRenderer.getInstance();
		AmbientOcclusionRenderer ambientRenderer = AmbientOcclusionRenderer.getInstance();
		DrawingRenderer drawRenderer = DrawingRenderer.getInstance();
		LightingPassRenderer lightRenderer = LightingPassRenderer.getInstance();

		int screenWidth = GameConstants.DEFAULT_GAME_WIDTH;
		int screenHeight = GameConstants.DEFAULT_GAME_HEIGHT;
		int sizeX = screenWidth / 10;
		int sizeY = screenHeight / 5;
				
//		Image resultImg = new Image();
//		resultImg.setSize(GameConstants.DEFAULT_GAME_WIDTH, -GameConstants.DEFAULT_GAME_HEIGHT);
//		resultImg.setPosition(true, 0, screenHeight);
//		resultImg.setTexture(new TextureFromInt(sizeX, sizeY, lightRenderer.outputInt(), TextureSlot.SHARED_DIFFUSE));
//		resultImg.touchable = false;
//		addChild(resultImg);

		Image debugPos = new Image();
		debugPos.setScale(1, -1);
		debugPos.setPosition(true, sizeX * 0, screenHeight);
		debugPos.setTexture(new TextureFromInt(sizeX, sizeY, renderer.positionInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugPos);

		Image debugNorm = new Image();
		debugNorm.setScale(1, -1);
		debugNorm.setPosition(true, sizeX * 1, screenHeight);
		debugNorm.setTexture(new TextureFromInt(sizeX, sizeY, renderer.normalInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugNorm);

		Image debugScreenPos = new Image();
		debugScreenPos.setScale(1, -1);
		debugScreenPos.setPosition(true, sizeX * 0, screenHeight - (sizeY * 1));
		debugScreenPos.setTexture(new TextureFromInt(sizeX, sizeY, renderer.screenPositionInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugScreenPos);

		Image debugScreenNorm = new Image();
		debugScreenNorm.setScale(1, -1);
		debugScreenNorm.setPosition(true, sizeX * 1, screenHeight - (sizeY * 1));
		debugScreenNorm.setTexture(new TextureFromInt(sizeX, sizeY, renderer.screenNormalInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugScreenNorm);

		Image debugCol = new Image();
		debugCol.setScale(1, -1);
		debugCol.setPosition(true, sizeX * 2, screenHeight);
		debugCol.setTexture(new TextureFromInt(sizeX, sizeY, renderer.colorInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugCol);

		Image debugDepth = new Image();
		debugDepth.setScale(1, -1);
		debugDepth.setPosition(true, sizeX * 3, screenHeight);
		debugDepth.setTexture(new TextureFromInt(sizeX, sizeY, renderer.depthInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugDepth);

		Image debugShadow = new Image();
		debugShadow.setScale(1, -1);
		debugShadow.setPosition(true, sizeX * 4, screenHeight);
		debugShadow.setTexture(new TextureFromInt(sizeX, sizeY, depthRenderer.depthInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugShadow);

		Image debugAmbientOcclusion = new Image();
		debugAmbientOcclusion.setScale(1, -1);
		debugAmbientOcclusion.setPosition(true, sizeX * 5, screenHeight);
		debugAmbientOcclusion.setTexture(new TextureFromInt(sizeX, sizeY, ambientRenderer.ambientOcclusionInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugAmbientOcclusion);

		Image debugCubeIndex = new Image();
		debugCubeIndex.setScale(1, -1);
		debugCubeIndex.setPosition(true, sizeX * 6, screenHeight);
		debugCubeIndex.setTexture(new TextureFromInt(sizeX, sizeY, renderer.cubeIndexInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugCubeIndex);

		Image debugDrawUnder = new Image();
		debugDrawUnder.setScale(1, -1);
		debugDrawUnder.setPosition(true, sizeX * 7, screenHeight);
		debugDrawUnder.setTexture(new TextureFromInt(sizeX, sizeY, drawRenderer.underlayInt(), TextureSlot.SHARED_DIFFUSE));
		addChild(debugDrawUnder);
	}
	
	@Override
	public void onCallback(Vector2Int data)
	{
		removeChildren(true);
		addDebugImages();
	}

	@Override
	protected void onDispose()
	{
		ScreenSize.removeListener(this);
	}
}
