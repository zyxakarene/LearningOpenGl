package zyx.server.world.humanoids.players;

import java.util.ArrayList;
import zyx.game.vo.Gender;
import zyx.net.io.connections.ConnectionData;
import zyx.server.utils.IUpdateable;
import zyx.server.world.entity.WorldEntityManager;

public class PlayerManager extends WorldEntityManager<Player> implements IUpdateable
{
	private static final PlayerManager INSTANCE = new PlayerManager();

	public static PlayerManager getInstance()
	{
		return INSTANCE;
	}
	
	private ArrayList<ConnectionData> connections;
	
	private ConnectionData[] allConnections;
	private ConnectionData[] allButOneConnections;

	private PlayerManager()
	{
		connections = new ArrayList<>();
		createConnectionArrays();
	}
	
	private void createConnectionArrays()
	{
		int length = entities.size();
		allConnections = new ConnectionData[length];
		
		int lenMinusOne = length <= 0 ? 0 : length - 1;
		allButOneConnections = new ConnectionData[lenMinusOne];
	}
	
	public Player createPlayer(String name, Gender gender, ConnectionData connection)
	{
		Player player = new Player(name, gender, connection);
		addEntity(player);
		
		connections.add(player.connection);
		createConnectionArrays();
		
		return player;
	}

	@Override
	public void removeEntity(Player player)
	{
		super.removeEntity(player);
		
		connections.remove(player.connection);
		
		createConnectionArrays();
	}

	public ConnectionData[] getAllConnections()
	{
		int length = allConnections.length;
		for (int i = 0; i < length; i++)
		{
			allConnections[i] = connections.get(i);
		}
		
		return allConnections;
	}

	public ConnectionData[] getAllConnectionsBut(ConnectionData connection)
	{
		int length = connections.size();
		int j = 0;
		for (int i = 0; i < length; i++)
		{
			ConnectionData entry = connections.get(i);
			
			if (entry != connection)
			{
				allButOneConnections[j] = connections.get(i);
				j++;
			}
		}
		
		return allButOneConnections;
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (Player player : entities)
		{
			player.update(timestamp, elapsedTime);
		}
	}
}
