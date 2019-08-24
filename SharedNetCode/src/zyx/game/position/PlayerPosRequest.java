package zyx.game.position;

import org.lwjgl.util.vector.Vector3f;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;
import static zyx.game.position.PositionConstants.*;

public class PlayerPosRequest extends BaseNetworkRequest
{

	public PlayerPosRequest()
	{
		super(NetworkCommands.CHARACTER_UPDATE_POSITION);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		Vector3f position = (Vector3f) params[0];
		Vector3f lookAt = (Vector3f) params[1];
		int id = (int) params[2];

		data.addInteger(ID, id);
		
		data.addFloat(POS_X, position.x);
		data.addFloat(POS_Y, position.y);
		data.addFloat(POS_Z, position.z);
		
		data.addFloat(LOOK_AT_X, lookAt.x);
		data.addFloat(LOOK_AT_Y, lookAt.y);
		data.addFloat(LOOK_AT_Z, lookAt.z);
	}
}
