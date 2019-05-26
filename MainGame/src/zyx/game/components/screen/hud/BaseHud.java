package zyx.game.components.screen.hud;

import zyx.engine.components.screen.image.Image;
import zyx.game.components.screen.json.JsonSprite;
import zyx.opengl.buffers.AmbientOcclusionRenderer;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.GameConstants;

public class BaseHud extends JsonSprite
{

	@Override
	public String getResource()
	{
		return "json.empty_hud";
	}
	
	@Override
	protected void onComponentsCreated()
	{
		DeferredRenderer renderer = DeferredRenderer.getInstance();
		DepthRenderer depthRenderer = DepthRenderer.getInstance();
		AmbientOcclusionRenderer ambientRenderer = AmbientOcclusionRenderer.getInstance();
		
		int SIZE = 128;
		
		Image debugPos = new Image();
		debugPos.setScale(1, -1);
		debugPos.setPosition(true, SIZE * 0, GameConstants.GAME_HEIGHT);
		debugPos.setTexture(new TextureFromInt(SIZE, SIZE, renderer.positionInt(), TextureSlot.SLOT_0));
		addChild(debugPos);
		
		Image debugNorm = new Image();
		debugNorm.setScale(1, -1);
		debugNorm.setPosition(true, SIZE * 1, GameConstants.GAME_HEIGHT);
		debugNorm.setTexture(new TextureFromInt(SIZE, SIZE, renderer.normalInt(), TextureSlot.SLOT_0));
		addChild(debugNorm);
		
		Image debugScreenPos = new Image();
		debugScreenPos.setScale(1, -1);
		debugScreenPos.setPosition(true, SIZE * 0, GameConstants.GAME_HEIGHT - (SIZE * 1));
		debugScreenPos.setTexture(new TextureFromInt(SIZE, SIZE, renderer.screenPositionInt(), TextureSlot.SLOT_0));
		addChild(debugScreenPos);
		
		Image debugScreenNorm = new Image();
		debugScreenNorm.setScale(1, -1);
		debugScreenNorm.setPosition(true, SIZE * 1, GameConstants.GAME_HEIGHT - (SIZE * 1));
		debugScreenNorm.setTexture(new TextureFromInt(SIZE, SIZE, renderer.screenNormalInt(), TextureSlot.SLOT_0));
		addChild(debugScreenNorm);
		
		Image debugCol = new Image();
		debugCol.setScale(1, -1);
		debugCol.setPosition(true, SIZE * 2, GameConstants.GAME_HEIGHT);
		debugCol.setTexture(new TextureFromInt(SIZE, SIZE, renderer.colorInt(), TextureSlot.SLOT_0));
		addChild(debugCol);
		
		Image debugDepth = new Image();
		debugDepth.setScale(1, -1);
		debugDepth.setPosition(true, SIZE * 3, GameConstants.GAME_HEIGHT);
		debugDepth.setTexture(new TextureFromInt(SIZE, SIZE, renderer.depthInt(), TextureSlot.SLOT_0));
		addChild(debugDepth);
		
		Image debugShadow = new Image();
		debugShadow.setScale(1, -1);
		debugShadow.setPosition(true, SIZE * 4, GameConstants.GAME_HEIGHT);
		debugShadow.setTexture(new TextureFromInt(SIZE, SIZE, depthRenderer.depthInt(), TextureSlot.SLOT_0));
		addChild(debugShadow);
		
		Image debugAmbientOcclusion = new Image();
		debugAmbientOcclusion.setScale(1, -1);
		debugAmbientOcclusion.setPosition(true, SIZE * 5, GameConstants.GAME_HEIGHT);
		debugAmbientOcclusion.setTexture(new TextureFromInt(SIZE, SIZE, ambientRenderer.ambientOcclusionInt(), TextureSlot.SLOT_0));
		addChild(debugAmbientOcclusion);
	}
}
