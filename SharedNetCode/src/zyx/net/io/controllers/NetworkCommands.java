package zyx.net.io.controllers;

public final class NetworkCommands
{
	public static final String PING = "Ping";
	
	public static final String LOGIN = "Login";
	public static final String AUTHENTICATE = "Authenticate";
	
	public static final String SETUP_GAME = "SetupGame";
	public static final String CHARACTER_JOINED_GAME = "JoinGame";
	public static final String CHARACTER_LEFT_GAME = "LeaveGame";
	
	public static final String CHARACTER_UPDATE_POSITION = "CharacterPos";
	public static final String CHARACTER_MASS_POSITION = "CharacterMassPos";
	
	public static final String ITEM_CREATE_FOOD = "itemCreateFood";
	public static final String ITEM_CREATE_BILL = "itemCreateBill";
	public static final String ITEM_DESTROY = "itemDestroy";
	public static final String ITEM_SET_OWNER = "itemOwner";
	public static final String ITEM_SET_TYPE = "itemType";
	
	public static final String GUEST_GIVE_ORDER = "guestGiveOrder";
	
	public static final String ENTITY_INTERACT = "entityInteract";
	
	public static final String PLAYER_GET_ORDER = "playerGetOrder";
	public static final String PLAYER_ENTER_ORDER = "playerEnterOrder";
	public static final String PLAYER_PICKUP_ITEM = "playerPickup";
	public static final String PLAYER_GIVE_ITEM = "playerGive";
	public static final String PLAYER_GIVE_BILL = "playerGiveBill";
}
