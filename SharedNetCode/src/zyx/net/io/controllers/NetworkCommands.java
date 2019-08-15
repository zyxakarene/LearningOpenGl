package zyx.net.io.controllers;

public final class NetworkCommands
{
	public static final String PING = "Ping";
	
	public static final String LOGIN = "Login";
	public static final String AUTHENTICATE = "Authenticate";
	
	public static final String SETUP_GAME = "SetupGame";
	public static final String PLAYER_JOINED_GAME = "JoinGame";
	public static final String PLAYER_LEFT_GAME = "LeaveGame";
	
	public static final String PLAYER_UPDATE_POSITION = "PlayerPos";
	public static final String PLAYER_MASS_POSITION = "PlayerMassPos";
	
	public static final String ITEM_CREATE = "itemCreate";
	public static final String ITEM_DESTROY = "itemDestroy";
	public static final String ITEM_SET_OWNER = "itemOwner";
	public static final String ITEM_SET_TYPE = "itemType";
	
	public static final String ENTITY_INTERACT = "entityInteract";
}
