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
	public static final String ITEM_SET_FOOD_STATE = "itemfoodState";
	public static final String ITEM_SPOIL_FOOD = "itemSpoilFood";
	public static final String ITEM_PUT_ON_FOOR = "itemPutOnFloor";
	public static final String ITEM_SET_IN_USE = "itemSetInUse";
	
	public static final String GUEST_GIVE_ORDER = "guestGiveOrder";
	public static final String GUEST_NO_ORDERS = "guestNoOrders";
	public static final String GUEST_FULL_TABLE = "guestFullTable";
	public static final String GUEST_GRAB_FOOD = "guestGrabFood";
	public static final String GUEST_PAY = "guestPay";
	
	public static final String ENTITY_INTERACT = "entityInteract";
	
	public static final String PLAYER_GET_ORDER = "playerGetOrder"; //Client -> server: Asking a guest to say their order: PlayerId, GuestId
	public static final String PLAYER_ENTER_ORDER = "playerEnterOrder"; //Client -> server: Adding a dish to the system: DishTypeId
	public static final String PLAYER_PICKUP_ITEM = "playerPickup"; //Client -> Server: Wanting to pick up an item: PlayerId, ItemId
	public static final String PLAYER_GIVE_ITEM = "playerGive"; //Client -> Server: Putting an item in a container: PlayerId, ContainerId
	public static final String PLAYER_GIVE_BILL = "playerGiveBill"; //Client -> Server: Putting the bill on a table: PlayerId, table UniqueId
	public static final String PLAYER_PRINT_BILL = "playerPrintBill"; //Client -> Server: Requesting a bill: PlayerId
	public static final String PLAYER_INTERACT_WITH = "playerInteractWith"; //Client -> Server: Player interacting with a thing: PlayerId, furniture UniqueId
}
