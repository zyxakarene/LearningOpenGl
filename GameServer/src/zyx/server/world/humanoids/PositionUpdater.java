package zyx.server.world.humanoids;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;
import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.ServerSender;
import zyx.server.utils.IUpdateable;
import zyx.server.world.entity.WorldEntity;
import zyx.server.world.entity.WorldEntityManager;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.humanoids.players.PlayerManager;

public class PositionUpdater implements IUpdateable
{

	private static final int TIME_BETWEEN_UPDATES = 50;

	private int time;
	private PlayerManager playerManager;
	private NpcManager npcManager;

	private ArrayList<WorldEntity> movingEntities;

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

		movingEntities = new ArrayList<>();
		time = 0;
		playerManager = PlayerManager.getInstance();
		npcManager = NpcManager.getInstance();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		time += elapsedTime;

		if (time >= TIME_BETWEEN_UPDATES)
		{
			time = 0;
			
			addFrom(playerManager);
			addFrom(npcManager);

			if (!movingEntities.isEmpty())
			{
				if (arrayLength != movingEntities.size())
				{
					updateArrayCache();
				}

				sendUpdates();
			}
		}
	}

	protected void addFrom(WorldEntityManager<? extends WorldEntity> manager)
	{
		ArrayList<? extends WorldEntity> allEntities = manager.getAllEntities();
		
		for (WorldEntity entity : allEntities)
		{
			if (entity.updatedPosition)
			{
				entity.updatedPosition = false;
				movingEntities.add(entity);
			}
		}
	}

	private void sendUpdates()
	{
		for (int i = 0; i < arrayLength; i++)
		{
			WorldEntity movingPlayer = movingEntities.get(i);
			idArray[i] = movingPlayer.id;
			posArray[i].set(movingPlayer.x, movingPlayer.y, movingPlayer.z);
			rotArray[i].set(movingPlayer.lx, movingPlayer.ly, movingPlayer.lz);
		}

		ServerSender.sendToAll(NetworkCommands.PLAYER_MASS_POSITION, posArray, rotArray, idArray);

		movingEntities.clear();
	}

	private void updateArrayCache()
	{
		arrayLength = movingEntities.size();
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
