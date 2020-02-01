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
			character.addInteger(ID, entity.id);
			character.addFloat(X, entity.x);
			character.addFloat(Y, entity.y);
			character.addFloat(Z, entity.z);
			character.addFloat(LOOK_X, entity.lx);
			character.addFloat(LOOK_Y, entity.ly);
			character.addFloat(LOOK_Z, entity.lz);
			character.addString(NAME, entity.name);
			
			characters.add(character);
		}
		
		data.addArray(CHARACTERS, characters);
	}

}
