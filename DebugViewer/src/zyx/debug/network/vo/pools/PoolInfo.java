package zyx.debug.network.vo.pools;

public class PoolInfo
{
	public String name;
	public int free;
	public int taken;
	public int total;
	
	public int id;

	public PoolInfo(String name, int id)
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
