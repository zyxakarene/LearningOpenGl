package zyx.game.controls.textures;

import java.io.DataInputStream;
import java.util.ArrayList;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.GameTexture;
import zyx.utils.interfaces.IDisposeable;

class TextureRequest implements IResourceLoaded<DataInputStream>, IDisposeable
{

	private String path;
	private ArrayList<IResourceLoaded<AbstractTexture>> callbacks;

	TextureRequest(String path, IResourceLoaded<AbstractTexture> callback)
	{
		this.path = path;
		this.callbacks = new ArrayList<>();

		callbacks.add(callback);

		loadModel();
	}

	void addCallback(IResourceLoaded<AbstractTexture> callback)
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
		AbstractTexture texture = new GameTexture(data);
		
		for (IResourceLoaded<AbstractTexture> callback : callbacks)
		{
			callback.resourceLoaded(texture);
		}
		
		TextureManager.getInstance().modelLoaded(path, texture);
	}

	@Override
	public void dispose()
	{
		callbacks.clear();
		
		path = null;
		callbacks = null;
	}

}
