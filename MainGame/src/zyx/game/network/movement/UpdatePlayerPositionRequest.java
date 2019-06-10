package zyx.game.network.movement;

import org.lwjgl.util.vector.Vector3f;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;

public class UpdatePlayerPositionRequest extends BaseNetworkRequest
{

	public UpdatePlayerPositionRequest()
	{
		super(NetworkCommands.PLAYER_UPDATE_POSITION);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		Vector3f pos = (Vector3f) params[0];
		int id = (int) params[1];
		
		float x = pos.x;
		float y = pos.y;
		float z = pos.z;
		
		data.addFloat("x", x);
		data.addFloat("y", y);
		data.addFloat("z", z);
		data.addInteger("id", id);
	}

}
