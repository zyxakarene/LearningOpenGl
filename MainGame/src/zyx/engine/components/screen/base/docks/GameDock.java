package zyx.engine.components.screen.base.docks;

import zyx.engine.components.screen.base.ContainerDock;
import zyx.engine.components.screen.image.Image;
import zyx.opengl.buffers.LightingPassRenderer;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;

public class GameDock extends ContainerDock
{

	public GameDock()
	{
		super(DockType.Top);
	}

	@Override
	protected void setup()
	{
		LightingPassRenderer lightRenderer = LightingPassRenderer.getInstance();
		
		Image resultImg = new Image();
		resultImg.setSize(width, -height);
		resultImg.setPosition(true, 0, height);
		resultImg.setTexture(new TextureFromInt(width, height, lightRenderer.outputInt(), TextureSlot.SHARED_DIFFUSE));
		resultImg.touchable = false;
		
		addChild(resultImg);
	}

}
