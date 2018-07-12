package zyx.game.controls.loading;

import java.util.ArrayList;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.ILoadableVO;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IDisposeable;

class Request<T extends IDisposeable> implements IResourceLoaded<ResourceDataInputStream>, IDisposeable, IModelTextureLoaded
{

	private AbstractLoader<T> loader;
	private String path;
	private ArrayList<IResourceLoaded<T>> callbacks;
	
	private ILoadableVO loadedVo;
	private TextureLoadWrapper textureLoader;

	Request(String path, IResourceLoaded<T>callback, AbstractLoader<T> factory)
	{
		this.path = path;
		this.callbacks = new ArrayList<>();
		this.loader = factory;
		
		callbacks.add(callback);

		loadModel();
	}

	void addCallback(IResourceLoaded<T> callback)
	{
		callbacks.add(callback);
	}

	private void loadModel()
	{
		ResourceRequest request = new ResourceRequestDataInput(path, this);
		ResourceLoader.getInstance().addRequest(request);
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadedVo = ZafLoader.loadFromZaf(data);
		textureLoader = new TextureLoadWrapper(loadedVo.getTexture(), this);
	}
	
	@Override
	public void onModelTextureLoaded(AbstractTexture texture)
	{
		loadedVo.setGameTexture(texture);
		T model = loader.createNewInstance(new Object[]{loadedVo});

		for (IResourceLoaded<T> callback : callbacks)
		{
			callback.resourceLoaded(model);
		}

		loader.loadComplete(path, model);
	}

	@Override
	public void dispose()
	{
		callbacks.clear();
		
		textureLoader = null;
		path = null;
		callbacks = null;
		loadedVo = null;
	}

}
