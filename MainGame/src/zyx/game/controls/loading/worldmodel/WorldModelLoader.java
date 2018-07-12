package zyx.game.controls.loading.worldmodel;

import zyx.game.controls.loading.AbstractLoader;
import zyx.game.controls.loading.AbstractRequest;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;

public class WorldModelLoader extends AbstractLoader<WorldModel>
{

	private static WorldModelLoader INSTANCE = new WorldModelLoader();
	
	private WorldModelLoader()
	{
	}

	public static WorldModelLoader getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	protected WorldModel createNewInstance(Object[] params)
	{
		LoadableWorldModelVO vo = (LoadableWorldModelVO) params[0];
		return new WorldModel(vo);
	}

	@Override
	protected AbstractRequest<WorldModel> createRequest(String path, IResourceLoaded<WorldModel> callback, AbstractLoader<WorldModel> loader)
	{
		return new WorldModelRequest(path, callback, this);
	}
}
