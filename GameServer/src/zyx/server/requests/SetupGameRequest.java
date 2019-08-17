package zyx.server.requests;

import java.util.ArrayList;
import zyx.net.data.WriteableDataArray;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.humanoids.players.PlayerManager;
import static zyx.game.joining.SetupConstants.*;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.npc.BaseNpc;
import zyx.server.world.humanoids.npc.NpcManager;



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
		
		ArrayList<Player> allPlayers = PlayerManager.getInstance().getAllEntities();
		ArrayList<BaseNpc> allNpcs = NpcManager.getInstance().getAllEntities();
		
		WriteableDataArray<WriteableDataObject> playerDataArray = new WriteableDataArray(WriteableDataObject.class);
		addFromList(allPlayers, player, playerDataArray);
		addFromList(allNpcs, player, playerDataArray);
		
		data.addArray(CHARACTERS, playerDataArray);
	}

	protected void addFromList(ArrayList<? extends HumanoidEntity> entities, Player player, WriteableDataArray<WriteableDataObject> dataArray)
	{
		for (HumanoidEntity entity : entities)
		{
			if (entity != player)
			{
				WriteableDataObject playerData = new WriteableDataObject();
				playerData.addInteger(CHARACTER_ID, entity.id);
				playerData.addString(CHARACTER_NAME, entity.name);
				playerData.addFloat(CHARACTER_X, entity.x);
				playerData.addFloat(CHARACTER_Y, entity.y);
				playerData.addFloat(CHARACTER_Z, entity.z);
				playerData.addFloat(CHARACTER_LOOK_X, entity.lx);
				playerData.addFloat(CHARACTER_LOOK_Y, entity.ly);
				playerData.addFloat(CHARACTER_LOOK_Z, entity.lz);

				dataArray.add(playerData);
			}
		}
	}

}