package zyx.debug.views.pools;

public class PoolInfo
{
	String name;
	int free;
	int taken;
	int total;

	public PoolInfo(String name)
	{
		this.name = name;
		free = 0;
		taken = 0;
		total = 0;
	}

	public void setAmount(int free, int taken, int total)
	{
		this.free = free;
		this.taken = taken;
		this.total = total;
	}
}
