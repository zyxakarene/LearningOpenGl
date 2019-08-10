package zyx.server.world.entity;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.PositionData;

public abstract class WorldEntity
{
	private static int idCounter;

	public float x, y, z;
	public float lx, ly, lz;

	public final int id;
	
	public boolean updatedPosition;

	public WorldEntity()
	{
		this.id = idCounter++;
		
		lx = 100;
	}
	
	protected WorldEntity(int id)
	{
		idCounter++;
		this.id = id;
	}
	
	public void updateFrom(PositionData data)
	{
		updatedPosition = true;
		Vector3f pos = data.position;
		Vector3f rot = data.lookAt;
		
		x = pos.x;
		y = pos.y;
		z = pos.z;
		
		lx = rot.x;
		ly = rot.y;
		lz = rot.z;
	}
}
