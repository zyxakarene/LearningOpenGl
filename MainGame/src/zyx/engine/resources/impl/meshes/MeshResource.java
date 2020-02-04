package zyx.engine.resources.impl.meshes;

import java.util.ArrayList;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;

public class MeshResource extends BaseRequiredSubResource
{

	private LoadableWorldModelVO loadedVo;
	private WorldModel model;

	private ISubResourceLoaded<AbstractTexture> textureLoaded;
	private ISubResourceLoaded<Object> skeletonLoaded;

	public MeshResource(String path)
	{
		super(path);

		textureLoaded = (ISubResourceLoaded<AbstractTexture>) this::onTextureLoaded;
		skeletonLoaded = (ISubResourceLoaded<Object>) this::onSkeletonLoaded;
	}

	@Override
	public WorldModel getContent()
	{
		return model;
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();

		if (model != null)
		{
			model.dispose();
			model = null;
		}

		if (loadedVo != null)
		{
			loadedVo.dispose();
			loadedVo = null;
		}
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadedVo = ZafLoader.loadFromZaf(data);

		String diffuse = loadedVo.getDiffuseTextureId();
		String normal = loadedVo.getNormalTextureId();
		String specular = loadedVo.getSpecularTextureId();
		
		SubResourceBatch<AbstractTexture> textureBatch = new SubResourceBatch(textureLoaded, diffuse, normal, specular);
		addResourceBatch(textureBatch);
	}

	
	private void onTextureLoaded(ArrayList<AbstractTexture> data)
	{
		AbstractTexture diffuse = data.get(0);
		AbstractTexture normal = data.get(1);
		AbstractTexture spec = data.get(2);
		
		loadedVo.setDiffuseTexture(diffuse);
		loadedVo.setNormalTexture(normal);
		loadedVo.setSpecularTexture(spec);
	}

	private void onSkeletonLoaded(ArrayList<Object> data)
	{

	}

	@Override
	protected void onSubBatchesLoaded()
	{
		model = new WorldModel(loadedVo);
		onContentLoaded(model);
	}
}
