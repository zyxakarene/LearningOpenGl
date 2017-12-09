package zyx.game.controls.models;

import java.io.DataInputStream;
import java.util.ArrayList;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.bones.ZafLoader;
import zyx.utils.interfaces.IDisposeable;

class ModelRequest implements IResourceLoaded<DataInputStream>, IDisposeable
{

	private String path;
	private IModelLoaded onLoaded;
	private ArrayList<IResourceLoaded<WorldModel>> callbacks;

	ModelRequest(String path, IModelLoaded onLoaded, IResourceLoaded<WorldModel> callback)
	{
		this.path = path;
		this.onLoaded = onLoaded;
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
		WorldModel model = ZafLoader.loadFromZaf(data);
		
		for (IResourceLoaded<WorldModel> callback : callbacks)
		{
			callback.resourceLoaded(model);
		}
		
		onLoaded.modelLoaded(path, model);
	}

	@Override
	public void dispose()
	{
		callbacks.clear();
		
		path = null;
		onLoaded = null;
		callbacks = null;
	}

}
