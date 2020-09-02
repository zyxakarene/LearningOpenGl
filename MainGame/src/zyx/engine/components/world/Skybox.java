package zyx.engine.components.world;

import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.SkyboxResource;
import zyx.engine.resources.impl.textures.TextureResource;
import zyx.opengl.materials.ZTest;
import zyx.opengl.materials.ZWrite;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.renderers.SkyboxRenderer;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.SkyboxShader;
import zyx.opengl.textures.AbstractTexture;

public class Skybox extends WorldObject
{

	private SkyboxResource meshResource;
	private TextureResource textureResource;

	private SkyboxRenderer renderer;
	private AbstractTexture texture;
	private boolean loaded;
	
	private IResourceReady<SkyboxResource> onMeshLoaded;
	private IResourceReady<TextureResource> onTextureLoaded;
	
	private SkyboxShader shader;
	private WorldModelMaterial skyboxMaterial;

	Skybox()
	{
		shader = ShaderManager.getInstance().<SkyboxShader>get(Shader.SKYBOX);

		onMeshLoaded = (SkyboxResource resource) ->
		{
			renderer = resource.getContent();
			skyboxMaterial = renderer.clonMaterial();
			skyboxMaterial.zWrite = ZWrite.DISABLED;
			skyboxMaterial.zTest = ZTest.ALWAYS;
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
		if (skyboxMaterial != null && texture != null)
		{
			loaded = true;
			skyboxMaterial.setDiffuse(texture);
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
		
		if (meshResource != null)
		{
			meshResource.unregister(onMeshLoaded);
			meshResource = null;
		}
		
		texture = null;
		
		if (skyboxMaterial != null)
		{
			skyboxMaterial.setDiffuse(null);
			skyboxMaterial = null;
		}
	}
	
	@Override
	protected void onDraw()
	{
		if (loaded)
		{
			shader.bind();
			shader.upload();
			renderer.draw();
		}
	}

	@Override
	protected void onDispose()
	{
		clean();
	}
}
