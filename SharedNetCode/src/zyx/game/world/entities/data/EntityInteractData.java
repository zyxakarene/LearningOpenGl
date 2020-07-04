package zyx.game.world.entities.data;

public class EntityInteractData
{
	public static final EntityInteractData INSTANCE = new EntityInteractData();
	
	public int entityId;
	public int userId;
	public boolean started;

	private EntityInteractData()
	{
	}
}
