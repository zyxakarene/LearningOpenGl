package zyx.game.scene;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.World3D;
import zyx.game.behavior.player.OnlinePositionInterpolator;
import zyx.game.components.GameObject;
import zyx.opengl.models.implementations.shapes.Box;
import zyx.utils.interfaces.IUpdateable;

public class CharacterHandler implements IUpdateable
{
	private World3D world;
	
	private HashMap<Integer, GameObject> characterMap;
	private ArrayList<GameObject> characterList;
	
	public CharacterHandler()
	{
		world = World3D.instance;
		
		characterMap = new HashMap<>();
		characterList = new ArrayList<>();
	}
	
	public void addCharacter(int uniqueId, Vector3f startPos, Vector3f startLookAt)
	{
		GameObject player = new GameObject();
		player.addChild(new Box(2, 2, 2));
		
		player.setPosition(false, startPos);
		player.lookAt(startLookAt.x, startLookAt.y, startLookAt.z);
		
		player.addBehavior(new OnlinePositionInterpolator());
		
		characterMap.put(uniqueId, player);
		characterList.add(player);
		
		world.addChild(player);
	}
	
	public GameObject getCharacterById(int playerId)
	{
		return characterMap.get(playerId);
	}
	
	public void removeCharacter(int playerId)
	{
		GameObject character = characterMap.remove(playerId);
		characterList.remove(character);
		
		character.dispose();
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (GameObject character : characterList)
		{
			character.update(timestamp, elapsedTime);
		}
	}

	public void clean()
	{
		for (GameObject character : characterList)
		{
			character.dispose();
		}
		
		characterMap.clear();
		characterList.clear();
	}
}
