package zyx.engine.components.screen.base.docks;

import zyx.engine.components.screen.base.ContainerDock;
import zyx.engine.components.screen.image.Image;
import zyx.opengl.buffers.LightingPassRenderer;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;

public class GameDock extends ContainerDock
{

	private Image sceneImage;

	public GameDock()
	{
		super(DockType.Top);
	}

	@Override
	protected void setup()
	{
		LightingPassRenderer lightRenderer = LightingPassRenderer.getInstance();

		sceneImage = new Image();
		sceneImage.setSize(width, -height);
		sceneImage.setPosition(true, 0, height);
		sceneImage.setTexture(new TextureFromInt(width, height, lightRenderer.outputInt(), TextureSlot.SHARED_DIFFUSE));
		sceneImage.touchable = false;

		addChild(sceneImage);
	}

	@Override
	protected void onSizeChanged()
	{
		sceneImage.setSize(width, -height);
		sceneImage.setPosition(true, 0, height);

	}
}
