package zyx.game.position;

import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;
import static zyx.game.position.PositionConstants.*;
import zyx.game.vo.PlayerPositionData;

public class PlayerPosResponse extends BaseNetworkResponse<PlayerPositionData>
{
	private static final PlayerPositionData OUT = new PlayerPositionData();
	
	public PlayerPosResponse()
	{
		super(NetworkCommands.PLAYER_UPDATE_POSITION);
	}

	@Override
	protected PlayerPositionData onMessageRecieved(ReadableDataObject data)
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
