package zyx.game.vo;

import org.lwjgl.util.vector.Vector3f;

public class PlayerMassPositionData
{
	public int count = 0;
	
	public int[] ids = new int[count];
	
	public Vector3f[] positions = new Vector3f[count];
	public Vector3f[] lookAts = new Vector3f[count];
}
