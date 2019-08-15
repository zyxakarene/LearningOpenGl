package zyx.game.world.entities;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.entities.EntityConstants.*;


public class EntityInteractWithResponse extends BaseNetworkResponse<Integer>
{

	public EntityInteractWithResponse()
	{
		super(NetworkCommands.ENTITY_INTERACT);
	}

	@Override
	protected Integer onMessageRecieved(ReadableDataObject data)
	{
		int entityId = data.getInteger(ENTITY_ID);

		return entityId;
	}

}
