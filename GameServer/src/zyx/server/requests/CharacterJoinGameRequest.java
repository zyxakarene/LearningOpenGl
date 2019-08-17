package zyx.server.requests;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.controllers.NetworkCommands;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.server.world.humanoids.HumanoidEntity;
import static zyx.game.joining.SetupConstants.*;
import zyx.net.data.WriteableDataArray;

public class CharacterJoinGameRequest extends BaseNetworkRequest
{

	public CharacterJoinGameRequest()
	{
		super(NetworkCommands.CHARACTER_JOINED_GAME);
	}

	@Override
	protected void getDataObject(WriteableDataObject data, Object[] params)
	{
		HumanoidEntity[] entites = (HumanoidEntity[]) params[0];
		
		WriteableDataArray<WriteableDataObject> characters = new WriteableDataArray<>(WriteableDataObject.class);
		
		for (HumanoidEntity entity : entites)
		{
			WriteableDataObject character = new WriteableDataObject();
			character.addInteger(CHARACTER_ID, entity.id);
			character.addFloat(CHARACTER_X, entity.x);
			character.addFloat(CHARACTER_Y, entity.y);
			character.addFloat(CHARACTER_Z, entity.z);
			character.addFloat(CHARACTER_LOOK_X, entity.lx);
			character.addFloat(CHARACTER_LOOK_Y, entity.ly);
			character.addFloat(CHARACTER_LOOK_Z, entity.lz);
			character.addString(CHARACTER_NAME, entity.name);
			
			characters.add(character);
		}
		
		data.addArray(CHARACTERS, characters);
	}

}
