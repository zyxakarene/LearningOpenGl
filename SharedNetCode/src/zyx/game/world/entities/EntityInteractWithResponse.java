package zyx.game.world.entities;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.responses.BaseNetworkResponse;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.world.entities.EntityConstants.*;
import zyx.game.world.entities.data.EntityInteractData;


public class EntityInteractWithResponse extends BaseNetworkResponse<EntityInteractData>
{
	private static final EntityInteractData INSTANCE = EntityInteractData.INSTANCE;
	
	public EntityInteractWithResponse()
	{
		super(NetworkCommands.ENTITY_INTERACT);
	}

	@Override
	protected EntityInteractData onMessageRecieved(ReadableDataObject data)
	{
		INSTANCE.entityId = data.getInteger(ENTITY_ID);
		INSTANCE.userId = data.getInteger(USER_ID);
		INSTANCE.started = data.getBoolean(STARTED);

		return INSTANCE;
	}

}
