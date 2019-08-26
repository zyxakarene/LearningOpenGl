package zyx.game.position;

import zyx.game.position.data.CharacterMassPositionData;
import org.lwjgl.util.vector.Vector3f;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.position.PositionConstants.*;
import zyx.net.data.ReadableDataArray;

public class CharacterMassPositionsResponse extends BaseNetworkResponse<CharacterMassPositionData>
{

	private static final CharacterMassPositionData OUT = new CharacterMassPositionData();

	public CharacterMassPositionsResponse()
	{
		super(NetworkCommands.CHARACTER_MASS_POSITION);
	}

	@Override
	protected CharacterMassPositionData onMessageRecieved(ReadableDataObject data)
	{
		ReadableDataArray<ReadableDataObject> players = data.getArray(CHARACTERS);
		int len = players.size();
		
		if (len != OUT.count)
		{
			updateResponseLength(len);
		}

		for (int i = 0; i < len; i++)
		{
			ReadableDataObject player = players.get(i);
			
			int id = player.getInteger(ID);

			float px = player.getFloat(POS_X);
			float py = player.getFloat(POS_Y);
			float pz = player.getFloat(POS_Z);

			float lx = player.getFloat(LOOK_AT_X);
			float ly = player.getFloat(LOOK_AT_Y);
			float lz = player.getFloat(LOOK_AT_Z);

			OUT.ids[i] = id;
			OUT.positions[i].set(px, py, pz);
			OUT.lookAts[i].set(lx, ly, lz);
		}

		return OUT;
	}

	private static void updateResponseLength(int count)
	{
		OUT.count = count;
		OUT.ids = new int[count];
		OUT.positions = new Vector3f[count];
		OUT.lookAts = new Vector3f[count];

		for (int i = 0; i < count; i++)
		{
			OUT.positions[i] = new Vector3f();
			OUT.lookAts[i] = new Vector3f();
		}
	}

}
