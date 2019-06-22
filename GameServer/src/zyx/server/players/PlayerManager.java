package zyx.server.players;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.net.io.connections.ConnectionData;

public class PlayerManager
{
	private static final PlayerManager instance = new PlayerManager();

	public static PlayerManager getInstance()
	{
		return instance;
	}
	
	
	private static int playerCounter;
	private HashMap<Integer, Player> playersById;
	private ArrayList<Player> players;
	private ArrayList<ConnectionData> connections;
	
	private ConnectionData[] allConnections;
	private ConnectionData[] allButOneConnections;

	public PlayerManager()
	{
		playersById = new HashMap<>();
		players = new ArrayList<>();
		connections = new ArrayList<>();
		
		createConnectionArrays();
	}
	
	private void createConnectionArrays()
	{
		int length = players.size();
		allConnections = new ConnectionData[length];
		
		int lenMinusOne = length <= 0 ? 0 : length - 1;
		allButOneConnections = new ConnectionData[lenMinusOne];
	}
	
	public Player createPlayer(String name, ConnectionData connection)
	{
		playerCounter++;
		Player player = new Player(playerCounter, name, connection);
		
		players.add(player);
		connections.add(player.connection);
		playersById.put(player.id, player);
		
		createConnectionArrays();
		
		return player;
	}
	
	public void removePlayer(Player player)
	{
		players.remove(player);
		connections.remove(player.connection);
		playersById.remove(player.id);
		
		createConnectionArrays();
	}

	public ArrayList<Player> getAllPlayers()
	{
		return players;
	}
	
	public Player getPlayer(int id)
	{
		return playersById.get(id);
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
}
