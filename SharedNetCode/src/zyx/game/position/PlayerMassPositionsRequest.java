package zyx.game.position;

import org.lwjgl.util.vector.Vector3f;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;
import static zyx.game.position.PositionConstants.*;
import zyx.net.data.WriteableDataArray;

public class PlayerMassPositionsRequest extends BaseNetworkRequest
{

	public PlayerMassPositionsRequest()
	{
		super(NetworkCommands.PLAYER_MASS_POSITION);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		Vector3f[] positions = (Vector3f[]) params[0];
		Vector3f[] lookAts = (Vector3f[]) params[1];
		int[] ids = (int[]) params[2];

		WriteableDataArray<WriteableDataObject> arrayData = new WriteableDataArray(WriteableDataObject.class);

		int length = ids.length;
		for (int i = 0; i < length; i++)
		{
			WriteableDataObject player = new WriteableDataObject();
			arrayData.add(player);
			
			int id = ids[i];
			Vector3f pos = positions[i];
			Vector3f lookAt = lookAts[i];
			
			player.addInteger(ID, id);

			player.addFloat(POS_X, pos.x);
			player.addFloat(POS_Y, pos.y);
			player.addFloat(POS_Z, pos.z);

			player.addFloat(LOOK_AT_X, lookAt.x);
			player.addFloat(LOOK_AT_Y, lookAt.y);
			player.addFloat(LOOK_AT_Z, lookAt.z);
		}

		data.addArray(PLAYERS, arrayData);
	}
}
