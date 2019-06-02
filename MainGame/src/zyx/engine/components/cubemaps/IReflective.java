package zyx.engine.components.cubemaps;

import zyx.utils.interfaces.IPositionable;

public interface IReflective extends IPositionable
{
	public void enableCubemaps();
	public void disableCubemaps();
	
	public void setCubemapIndex(int index);
}
