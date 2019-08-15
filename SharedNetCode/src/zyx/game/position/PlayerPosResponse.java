package zyx.game.position;

import zyx.game.position.data.PositionData;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.position.PositionConstants.*;

public class PlayerPosResponse extends BaseNetworkResponse<PositionData>
{
	private static final PositionData OUT = new PositionData();
	
	public PlayerPosResponse()
	{
		super(NetworkCommands.PLAYER_UPDATE_POSITION);
	}

	@Override
	protected PositionData onMessageRecieved(ReadableDataObject data)
	{
		int id = data.getInteger(ID);
		
		float px = data.getFloat(POS_X);
		float py = data.getFloat(POS_Y);
		float pz = data.getFloat(POS_Z);
		
		float lx = data.getFloat(LOOK_AT_X);
		float ly = data.getFloat(LOOK_AT_Y);
		float lz = data.getFloat(LOOK_AT_Z);

		OUT.id = id;
		OUT.position.set(px, py, pz);
		OUT.lookAt.set(lx, ly, lz);
		
		return OUT;
	}
	
	
}
