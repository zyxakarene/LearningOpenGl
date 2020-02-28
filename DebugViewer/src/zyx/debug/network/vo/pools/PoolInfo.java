package zyx.debug.network.vo.pools;

public class PoolInfo
{
	private static int idCounter = 0;
	
	public String name;
	public int free;
	public int taken;
	public int total;
	
	private int id;

	public PoolInfo(String name)
	{
		this.name = name;
		free = 0;
		taken = 0;
		total = 0;
		
		id = ++idCounter;
	}

	public void setAmount(int free, int taken, int total)
	{
		this.free = free;
		this.taken = taken;
		this.total = total;
	}
}
