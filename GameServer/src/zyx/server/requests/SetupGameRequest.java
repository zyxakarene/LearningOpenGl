package zyx.server.requests;

import java.util.ArrayList;
import zyx.net.data.WriteableDataArray;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.server.players.Player;
import zyx.server.players.PlayerManager;
import static zyx.game.joining.SetupConstants.*;



public class SetupGameRequest extends BaseNetworkRequest
{

	public SetupGameRequest()
	{
		super(NetworkCommands.SETUP_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		Player player = (Player) params[0];
		
		ArrayList<Player> allPlayers = PlayerManager.getInstance().getAllPlayers();
		
		WriteableDataArray<WriteableDataObject> playerDataArray = new WriteableDataArray(WriteableDataObject.class);
		for (Player otherPlayer : allPlayers)
		{
			if (otherPlayer != player)
			{
				WriteableDataObject playerData = new WriteableDataObject();
				playerData.addInteger(PLAYER_ID, otherPlayer.id);
				playerData.addString(PLAYER_NAME, otherPlayer.name);
				playerData.addFloat(PLAYER_X, otherPlayer.x);
				playerData.addFloat(PLAYER_Y, otherPlayer.y);
				playerData.addFloat(PLAYER_Z, otherPlayer.z);
				playerData.addFloat(PLAYER_LOOK_X, otherPlayer.lx);
				playerData.addFloat(PLAYER_LOOK_Y, otherPlayer.ly);
				playerData.addFloat(PLAYER_LOOK_Z, otherPlayer.lx);

				playerDataArray.add(playerData);
			}
		}
		
		data.addArray(PLAYERS, playerDataArray);
	}

}