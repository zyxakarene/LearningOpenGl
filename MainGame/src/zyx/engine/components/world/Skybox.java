package zyx.engine.components.world;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.SkyboxResource;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.SkyboxModel;
import zyx.opengl.shaders.implementations.Shader;

public class Skybox extends WorldObject implements IResourceReady<SkyboxResource>
{

	private SkyboxResource resource;

	private SkyboxModel model;

	Skybox()
	{
		super(Shader.SKYBOX);
	}

	public void load(String res)
	{
		clean();

		resource = ResourceManager.getInstance().<SkyboxResource>getResourceAs(res);
		resource.registerAndLoad(this);
	}

	private void clean()
	{
		if (resource != null)
		{
			resource.unregister(this);
			resource = null;
		}

		model = null;
	}
	
	@Override
	protected void onDraw()
	{
		if (model != null)
		{
			GLUtils.disableDepthWrite();
			GLUtils.disableDepthTest();

			shader.bind();
			shader.upload();
			model.draw();

			GLUtils.enableDepthWrite();
			GLUtils.enableDepthTest();
		}
	}

	@Override
	public void onResourceReady(SkyboxResource resource)
	{
		model = resource.getContent();
	}

	@Override
	protected void onDispose()
	{
		clean();
	}
}
