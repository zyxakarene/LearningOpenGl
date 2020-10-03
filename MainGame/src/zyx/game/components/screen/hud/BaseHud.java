package zyx.game.components.screen.hud;

import java.util.ArrayList;
import zyx.engine.components.screen.image.Image;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;
import zyx.opengl.buffers.*;
import zyx.opengl.textures.ColorTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;
import zyx.utils.math.Vector2Int;

public class BaseHud extends JsonSprite implements ICallback<Vector2Int>
{

	private ArrayList<Image> debugImages;
	private final boolean showRenderers;

	public BaseHud(boolean showRenderers)
	{
		debugImages = new ArrayList<>();
		this.showRenderers = showRenderers;
	}
	
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
		
		
		DeferredRenderer renderer = DeferredRenderer.getInstance();
		DepthRenderer depthRenderer = DepthRenderer.getInstance();
		AmbientOcclusionRenderer ambientRenderer = AmbientOcclusionRenderer.getInstance();
		DrawingRenderer drawRenderer = DrawingRenderer.getInstance();
		LightingPassRenderer lightRenderer = LightingPassRenderer.getInstance();

		if (!showRenderers)
		{
			Image img = new Image();
			img.setTexture(new ColorTexture(0xFFFFFF));
			addChild(img);
			return;
		}
		
		int screenWidth = ScreenSize.width;
		int screenHeight = ScreenSize.height;
		int sizeX = screenWidth / 10;
		int sizeY = screenHeight / 5;
				
		Image debugPos = new Image();
		debugPos.setScale(1, -1);
		debugPos.setPosition(true, sizeX * 0, screenHeight);
		debugPos.setTexture(new TextureFromInt(sizeX, sizeY, renderer.positionInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugPos);

		Image debugNorm = new Image();
		debugNorm.setScale(1, -1);
		debugNorm.setPosition(true, sizeX * 1, screenHeight);
		debugNorm.setTexture(new TextureFromInt(sizeX, sizeY, renderer.normalInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugNorm);

		Image debugScreenPos = new Image();
		debugScreenPos.setScale(1, -1);
		debugScreenPos.setPosition(true, sizeX * 0, screenHeight - (sizeY * 1));
		debugScreenPos.setTexture(new TextureFromInt(sizeX, sizeY, renderer.screenPositionInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugScreenPos);

		Image debugScreenNorm = new Image();
		debugScreenNorm.setScale(1, -1);
		debugScreenNorm.setPosition(true, sizeX * 1, screenHeight - (sizeY * 1));
		debugScreenNorm.setTexture(new TextureFromInt(sizeX, sizeY, renderer.screenNormalInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugScreenNorm);

		Image debugCol = new Image();
		debugCol.setScale(1, -1);
		debugCol.setPosition(true, sizeX * 2, screenHeight);
		debugCol.setTexture(new TextureFromInt(sizeX, sizeY, renderer.colorInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugCol);

		Image debugDepth = new Image();
		debugDepth.setScale(1, -1);
		debugDepth.setPosition(true, sizeX * 3, screenHeight);
		debugDepth.setTexture(new TextureFromInt(sizeX, sizeY, renderer.depthInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugDepth);

		Image debugShadow = new Image();
		debugShadow.setScale(1, -1);
		debugShadow.setPosition(true, sizeX * 4, screenHeight);
		debugShadow.setTexture(new TextureFromInt(sizeX, sizeY, depthRenderer.depthInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugShadow);

		Image debugAmbientOcclusion = new Image();
		debugAmbientOcclusion.setScale(1, -1);
		debugAmbientOcclusion.setPosition(true, sizeX * 5, screenHeight);
		debugAmbientOcclusion.setTexture(new TextureFromInt(sizeX, sizeY, ambientRenderer.ambientOcclusionInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugAmbientOcclusion);

		Image debugCubeIndex = new Image();
		debugCubeIndex.setScale(1, -1);
		debugCubeIndex.setPosition(true, sizeX * 6, screenHeight);
		debugCubeIndex.setTexture(new TextureFromInt(sizeX, sizeY, renderer.cubeIndexInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugCubeIndex);

		Image debugDrawUnder = new Image();
		debugDrawUnder.setScale(1, -1);
		debugDrawUnder.setPosition(true, sizeX * 7, screenHeight);
		debugDrawUnder.setTexture(new TextureFromInt(sizeX, sizeY, drawRenderer.underlayInt(), TextureSlot.SHARED_DIFFUSE));
		debugImages.add(debugDrawUnder);
		
		for (Image image : debugImages)
		{
			addChild(image);
		}
	}
	
	@Override
	public void onCallback(Vector2Int data)
	{
		for (Image image : debugImages)
		{
			removeChild(image);
			image.dispose();
		}
		
		debugImages.clear();
		
		addDebugImages();
	}

	@Override
	protected void onDispose()
	{
		ScreenSize.removeListener(this);
	}
}
