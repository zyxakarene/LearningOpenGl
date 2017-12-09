package zyx.game.components.world.model;

import java.io.DataInputStream;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestDataInput;
import zyx.opengl.models.implementations.LoadableValueObject;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.bones.ZafLoader;

public class LoadableModel extends WorldModel implements IResourceLoaded<DataInputStream>
{
	public LoadableModel()
	{
	}
	
	public void load(String path)
	{
		ResourceRequest request = new ResourceRequestDataInput(path, this);
		ResourceLoader.getInstance().addRequest(request);
	}

	@Override
	public void resourceLoaded(DataInputStream data)
	{
		LoadableValueObject dataVo = ZafLoader.loadFromZaf(data);
		OnLoaded(dataVo);
	}
}
