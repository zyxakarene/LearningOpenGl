package zyx.engine.components.screen.base.docks;

import zyx.engine.components.screen.base.ContainerDock;
import zyx.engine.components.screen.image.Image;
import zyx.opengl.buffers.LightingPassRenderer;
import zyx.opengl.materials.StencilLayer;
import zyx.opengl.materials.StencilMode;
import zyx.opengl.materials.impl.ScreenModelMaterial;
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
		
		ScreenModelMaterial material = sceneImage.material;
		material.stencilMode = StencilMode.NOTHING;
		material.stencilLayer = StencilLayer.NOTHING;
		
		addChild(sceneImage);
	}

	@Override
	protected void onSizeChanged()
	{
		sceneImage.setSize(width, -height);
		sceneImage.setPosition(true, 0, height);
	}
}
