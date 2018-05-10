package zyx.game.controls.models;

import java.io.DataInputStream;
import java.util.ArrayList;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IDisposeable;

class ModelRequest implements IResourceLoaded<DataInputStream>, IDisposeable, IModelTextureLoaded
{

	private String path;
	private ArrayList<IResourceLoaded<WorldModel>> callbacks;
	
	private LoadableWorldModelVO loadedVo;
	private ModelTextureLoader textureLoader;

	ModelRequest(String path, IResourceLoaded<WorldModel> callback)
	{
		this.path = path;
		this.callbacks = new ArrayList<>();

		callbacks.add(callback);

		loadModel();
	}

	void addCallback(IResourceLoaded<WorldModel> callback)
	{
		callbacks.add(callback);
	}

	private void loadModel()
	{
		ResourceRequest request = new ResourceRequestDataInput(path, this);
		ResourceLoader.getInstance().addRequest(request);
	}

	@Override
	public void resourceLoaded(DataInputStream data)
	{
		loadedVo = ZafLoader.loadFromZaf(data);
		textureLoader = new ModelTextureLoader(loadedVo.getTexture(), this);
	}
	
	@Override
	public void onModelTextureLoaded(AbstractTexture texture)
	{
		loadedVo.setGameTexture(texture);
		WorldModel model = new WorldModel(loadedVo);

		for (IResourceLoaded<WorldModel> callback : callbacks)
		{
			callback.resourceLoaded(model);
		}

		ModelManager.getInstance().modelLoaded(path, model);
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
