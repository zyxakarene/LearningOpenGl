package zyx.game.joining.data;

import org.lwjgl.util.vector.Vector3f;
import zyx.game.vo.FurnitureType;

public class FurnitureSetupData
{

	public static final int MAX_COUNT = 30;
	public static final FurnitureSetupData INSTANCE = new FurnitureSetupData();

	public int furnitureCount;
	
	public final Vector3f[] positions;
	public final Vector3f[] lookAts;
	public final int[] ids;
	public final int[] usingIds;
	public final FurnitureType[] types;

	private FurnitureSetupData()
	{
		furnitureCount = 0;
		
		positions = new Vector3f[MAX_COUNT];
		lookAts = new Vector3f[MAX_COUNT];
		ids = new int[MAX_COUNT];
		usingIds = new int[MAX_COUNT];
		types = new FurnitureType[MAX_COUNT];
		
		for (int i = 0; i < MAX_COUNT; i++)
		{
			positions[i] = new Vector3f();
			lookAts[i] = new Vector3f();
			ids[i] = 0;
			usingIds[i] = 0;
			types[i] = FurnitureType.CHAIR;
		}
	}
}
