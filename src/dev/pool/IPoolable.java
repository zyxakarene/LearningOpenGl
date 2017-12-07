package dev.pool;

import zyx.utils.interfaces.IDisposeable;

public interface IPoolable extends IDisposeable
{
	public void initialize(Object[] args);
	public void reset();
	public void release();
}
