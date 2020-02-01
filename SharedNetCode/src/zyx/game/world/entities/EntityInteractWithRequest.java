package zyx.game.world.entities;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.entities.EntityConstants.*;

public class EntityInteractWithRequest extends BaseNetworkRequest
{

	public EntityInteractWithRequest()
	{
		super(NetworkCommands.ENTITY_INTERACT);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		int entityId = (int) params[0];

		data.addInteger(ENTITY_ID, entityId);
	}
}
