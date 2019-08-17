package zyx.server.controller.services;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.Gender;
import zyx.net.io.controllers.NetworkCommands;
import zyx.server.controller.sending.SendType;
import zyx.server.controller.sending.ServerSender;
import zyx.server.world.humanoids.HumanoidEntity;
import zyx.server.world.humanoids.players.Player;

public class PlayerService
{
	public static void playerLeft(Player leavingPlayer)
	{
		ServerSender.sendWithType(SendType.toAllBut(leavingPlayer.connection), NetworkCommands.CHARACTER_LEFT_GAME, leavingPlayer.id);
	}
	
	public static void playerJoined(Player joinedPlayer)
	{
		HumanoidEntity[] humans = new HumanoidEntity[1];
		humans[0] = joinedPlayer;
		
		ServerSender.sendWithType(SendType.toAllBut(joinedPlayer.connection), NetworkCommands.CHARACTER_JOINED_GAME, (Object) humans);
	}
	
	public static void setupGame(Player joinedPlayer)
	{
		ServerSender.sendWithType(SendType.toSingle(joinedPlayer.connection), NetworkCommands.SETUP_GAME, joinedPlayer);
	}
	
	public static void authenticate(Player joinedPlayer)
	{
		String name = joinedPlayer.name;
		int id = joinedPlayer.id;
		Gender gender = joinedPlayer.gender;

		ServerSender.sendWithType(SendType.toSingle(joinedPlayer.connection), NetworkCommands.AUTHENTICATE, name, id, gender);
	}

	public static void setPositions(int[] ids, Vector3f[] positions, Vector3f[] lookAts)
	{
		ServerSender.sendWithType(SendType.toAll(), NetworkCommands.PLAYER_MASS_POSITION, ids, positions, lookAts);
	}
	
	
}
