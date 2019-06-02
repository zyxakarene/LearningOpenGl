package zyx.utils.pooling;

public interface IPoolable
{
	public void initialize(Object[] args);
	public void reset();
	public void release();
}
