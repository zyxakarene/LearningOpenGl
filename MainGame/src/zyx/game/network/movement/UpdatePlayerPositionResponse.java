package zyx.game.network.movement;

import org.lwjgl.util.vector.Vector3f;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;

public class UpdatePlayerPositionResponse extends BaseNetworkResponse<UpdatePlayerPositionResponse.PlayerPosData>
{
	private static final PlayerPosData RETURN_VAL = new PlayerPosData();
	
	public UpdatePlayerPositionResponse()
	{
		super(NetworkCommands.PLAYER_UPDATE_POSITION);
	}

	@Override
	protected PlayerPosData onMessageRecieved(ReadableDataObject data)
	{
		RETURN_VAL.playerId = data.getInteger("id");
		RETURN_VAL.vec.x = data.getFloat("x");
		RETURN_VAL.vec. y = data.getFloat("y");
		RETURN_VAL.vec. z = data.getFloat("z");
		
		return RETURN_VAL;
	}

	public static class PlayerPosData
	{
		public Vector3f vec = new Vector3f();
		public int playerId;
	}
}
