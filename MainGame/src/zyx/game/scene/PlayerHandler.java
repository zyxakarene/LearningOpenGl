package zyx.game.scene;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.World3D;
import zyx.game.behavior.freefly.OnlinePositionInterpolator;
import zyx.game.components.GameObject;
import zyx.opengl.models.implementations.shapes.Box;
import zyx.utils.interfaces.IUpdateable;

public class PlayerHandler implements IUpdateable
{
	private World3D world;
	
	private HashMap<Integer, GameObject> playerMap;
	private ArrayList<GameObject> playerList;
	
	public PlayerHandler()
	{
		world = World3D.instance;
		
		playerMap = new HashMap<>();
		playerList = new ArrayList<>();
	}
	
	public void addPlayer(int playerId, Vector3f startPos, Vector3f startLookAt)
	{
		GameObject player = new GameObject();
		player.addChild(new Box(2, 2, 2));
		
		player.setPosition(false, startPos);
		player.lookAt(startLookAt.x, startLookAt.y, startLookAt.z);
		
		player.addBehavior(new OnlinePositionInterpolator());
		
		playerMap.put(playerId, player);
		playerList.add(player);
		
		world.addChild(player);
	}
	
	public GameObject getPlayerById(int playerId)
	{
		return playerMap.get(playerId);
	}
	
	public void removePlayer(int playerId)
	{
		GameObject player = playerMap.remove(playerId);
		playerList.remove(player);
		
		player.dispose();
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (GameObject player : playerList)
		{
			player.update(timestamp, elapsedTime);
		}
	}

	public void clean()
	{
		for (GameObject player : playerList)
		{
			player.dispose();
		}
		
		playerMap.clear();
		playerList.clear();
	}
}
