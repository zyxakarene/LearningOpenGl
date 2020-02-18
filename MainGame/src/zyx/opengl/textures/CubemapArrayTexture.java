package zyx.opengl.textures;

import zyx.opengl.reflections.LoadableCubemapVO;
import zyx.opengl.textures.custom.CubeTexture3D;
import zyx.opengl.textures.custom.ITexture;
import zyx.opengl.textures.enums.TextureSlot;

public class CubemapArrayTexture extends AbstractTexture
{

	protected ITexture texture;

	public CubemapArrayTexture(LoadableCubemapVO cubeVo)
	{
		super(cubeVo.name, TextureSlot.WORLD_CUBEMAPS);

		texture = new CubeTexture3D(cubeVo);

		setSizes(texture.getWidth(), texture.getHeight());
	}

	@Override
	protected void onBind()
	{
		texture.bind();
	}

	@Override
	protected void onDispose()
	{
		if (texture != null)
		{
			texture.dispose();
			texture = null;
		}
	}

	@Override
	public ITexture getGlTexture()
	{
		return texture;
	}
}
