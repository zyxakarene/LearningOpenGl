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
		super(NetworkCommands.PLAYER_UPDATE_POSITION);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		Vector3f pos = (Vector3f) params[0];
		Vector3f rot = (Vector3f) params[1];
		int id = (int) params[2];

		data.addInteger(ID, id);
		
		data.addFloat(POS_X, pos.x);
		data.addFloat(POS_Y, pos.y);
		data.addFloat(POS_Z, pos.z);
		
		data.addFloat(ROT_X, rot.x);
		data.addFloat(ROT_Y, rot.y);
		data.addFloat(ROT_Z, rot.z);
	}
}
