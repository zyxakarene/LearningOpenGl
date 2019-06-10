package pos;

import com.sun.javafx.geom.Vec4f;
import zyx.net.data.ReadableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.responses.BaseNetworkResponse;

public class PlayerPosResponse extends BaseNetworkResponse<Vec4f>
{

	public PlayerPosResponse()
	{
		super(NetworkCommands.PLAYER_UPDATE_POSITION);
	}

	@Override
	protected Vec4f onMessageRecieved(ReadableDataObject data)
	{
		int id = data.getInteger("id");
		float x = data.getFloat("x");
		float y = data.getFloat("y");
		float z = data.getFloat("z");

		
		
		return new Vec4f(x, y, z, id);
	}
}
