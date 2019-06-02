package zyx.engine.components.world;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.SkyboxResource;
import zyx.engine.resources.impl.TextureResource;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.SkyboxModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.GameTexture;

public class Skybox extends WorldObject
{

	private SkyboxResource meshResource;
	private TextureResource textureResource;

	private SkyboxModel model;
	private GameTexture texture;
	private boolean loaded;
	
	private IResourceReady<SkyboxResource> onMeshLoaded;
	private IResourceReady<TextureResource> onTextureLoaded;

	Skybox()
	{
		super(Shader.SKYBOX);
		
		onMeshLoaded = (SkyboxResource resource) ->
		{
			model = resource.getContent();
			onResourceLoaded();
		};
		
		onTextureLoaded = (TextureResource resource) ->
		{
			texture = resource.getContent();
			onResourceLoaded();
		};
	}

	public void load(String res)
	{
		clean();

		meshResource = ResourceManager.getInstance().<SkyboxResource>getResourceAs("skybox.mesh.skybox");
		meshResource.registerAndLoad(onMeshLoaded);

		textureResource = ResourceManager.getInstance().<TextureResource>getResourceAs(res);
		textureResource.registerAndLoad(onTextureLoaded);
	}

	private void onResourceLoaded()
	{
		if (model != null && texture != null)
		{
			loaded = true;
			model.setSkyboxTexture(texture);
		}
	}
	
	public void clean()
	{
		loaded = false;
		
		if (textureResource != null)
		{
			textureResource.unregister(onTextureLoaded);
			textureResource = null;
		}
		
		texture = null;
		
		if (model != null)
		{
			model.removeSkyboxTexture();
		}
	}
	
	@Override
	protected void onDraw()
	{
		if (loaded)
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
	protected void onDispose()
	{
		clean();
		
		if (meshResource != null)
		{
			meshResource.unregister(onMeshLoaded);
			meshResource = null;
		}
	}
}
