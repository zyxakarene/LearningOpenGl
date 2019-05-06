package zyx.game.components.screen.hud;

import zyx.engine.components.screen.image.Image;
import zyx.game.components.screen.json.JsonSprite;
import zyx.opengl.deferred.DeferredRenderer;
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
		
		Image debugPos = new Image();
		debugPos.setScale(1, -1);
		debugPos.setPosition(true, 0, GameConstants.GAME_HEIGHT);
		debugPos.setTexture(new TextureFromInt(128, 128, renderer.positionInt(), TextureSlot.SLOT_0));
		addChild(debugPos);
		
		Image debugNorm = new Image();
		debugNorm.setScale(1, -1);
		debugNorm.setPosition(true, 128, GameConstants.GAME_HEIGHT);
		debugNorm.setTexture(new TextureFromInt(128, 128, renderer.normalInt(), TextureSlot.SLOT_0));
		addChild(debugNorm);
		
		Image debugCol = new Image();
		debugCol.setScale(1, -1);
		debugCol.setPosition(true, 256, GameConstants.GAME_HEIGHT);
		debugCol.setTexture(new TextureFromInt(128, 128, renderer.colorInt(), TextureSlot.SLOT_0));
		addChild(debugCol);
	}
}
