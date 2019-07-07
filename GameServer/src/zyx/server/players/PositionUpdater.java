package zyx.server.players;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.ServerSender;
import zyx.server.utils.IUpdateable;

public class PositionUpdater implements IUpdateable
{

	private static final int TIME_BETWEEN_UPDATES = 50;

	private int time;
	private PlayerManager playerManager;

	private ArrayList<Player> movingPlayers;

	private int arrayLength;
	private int[] idArray;
	private Vector3f[] posArray;
	private Vector3f[] rotArray;

	public PositionUpdater()
	{
		arrayLength = 0;
		idArray = new int[arrayLength];
		posArray = new Vector3f[arrayLength];
		rotArray = new Vector3f[arrayLength];

		movingPlayers = new ArrayList<>();
		time = 0;
		playerManager = PlayerManager.getInstance();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		time += elapsedTime;

		if (time >= TIME_BETWEEN_UPDATES)
		{
			time = 0;
			ArrayList<Player> allPlayers = playerManager.getAllPlayers();

			for (Player player : allPlayers)
			{
				if (player.updatedPosition)
				{
					player.updatedPosition = false;
					movingPlayers.add(player);
				}
			}

			if (!movingPlayers.isEmpty())
			{
				if (arrayLength != movingPlayers.size())
				{
					updateArrayCache();
				}

				sendUpdates();
			}
		}
	}

	private void sendUpdates()
	{
		for (int i = 0; i < arrayLength; i++)
		{
			Player movingPlayer = movingPlayers.get(i);
			idArray[i] = movingPlayer.id;
			posArray[i].set(movingPlayer.x, movingPlayer.y, movingPlayer.z);
			rotArray[i].set(movingPlayer.rx, movingPlayer.ry, movingPlayer.rz);
		}

		ServerSender.sendToAll(NetworkCommands.PLAYER_MASS_POSITION, posArray, rotArray, idArray);

		movingPlayers.clear();
	}

	private void updateArrayCache()
	{
		arrayLength = movingPlayers.size();
		idArray = new int[arrayLength];
		posArray = new Vector3f[arrayLength];
		rotArray = new Vector3f[arrayLength];

		for (int i = 0; i < arrayLength; i++)
		{
			posArray[i] = new Vector3f();
			rotArray[i] = new Vector3f();
		}
	}

}
