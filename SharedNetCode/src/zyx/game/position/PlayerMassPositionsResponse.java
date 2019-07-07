package zyx.game.position;

import org.lwjgl.util.vector.Vector3f;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.position.PositionConstants.*;
import zyx.game.vo.PlayerMassPositionData;
import zyx.net.data.ReadableDataArray;

public class PlayerMassPositionsResponse extends BaseNetworkResponse<PlayerMassPositionData>
{

	private static final PlayerMassPositionData OUT = new PlayerMassPositionData();

	public PlayerMassPositionsResponse()
	{
		super(NetworkCommands.PLAYER_MASS_POSITION);
	}

	@Override
	protected PlayerMassPositionData onMessageRecieved(ReadableDataObject data)
	{
		int count = data.getInteger(COUNT);

		if (count != OUT.count)
		{
			updateResponseLength(count);
		}

		ReadableDataArray<ReadableDataObject> players = data.getArray(PLAYERS);
		for (int i = 0; i < count; i++)
		{
			ReadableDataObject player = players.get(i);
			
			int id = player.getInteger(ID);

			float px = player.getFloat(POS_X);
			float py = player.getFloat(POS_Y);
			float pz = player.getFloat(POS_Z);

			float rx = player.getFloat(ROT_X);
			float ry = player.getFloat(ROT_Y);
			float rz = player.getFloat(ROT_Z);

			OUT.ids[i] = id;
			OUT.positions[i].set(px, py, pz);
			OUT.rotations[i].set(rx, ry, rz);
		}

		return OUT;
	}

	private static void updateResponseLength(int count)
	{
		OUT.count = count;
		OUT.ids = new int[count];
		OUT.positions = new Vector3f[count];
		OUT.rotations = new Vector3f[count];

		for (int i = 0; i < count; i++)
		{
			OUT.positions[i] = new Vector3f();
			OUT.rotations[i] = new Vector3f();
		}
	}

}
