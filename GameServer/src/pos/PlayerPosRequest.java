package pos;

import com.sun.javafx.geom.Vec4f;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;

public class PlayerPosRequest extends BaseNetworkRequest
{

	public PlayerPosRequest()
	{
		super(NetworkCommands.PLAYER_UPDATE_POSITION);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		Vec4f pos = (Vec4f) params[0];
		int id = (int) pos.w;
		
		float x = pos.x;
		float y = pos.y;
		float z = pos.z;
		
		data.addFloat("x", x);
		data.addFloat("y", y);
		data.addFloat("z", z);
		data.addInteger("id", id);
	}
}
