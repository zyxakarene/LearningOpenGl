package zyx.server.world.interactable.common;

import zyx.server.world.humanoids.npc.Cleaner;

public interface ICleanable
{
	public abstract void clean(Cleaner cleaner);
	public abstract boolean canBeCleaned();
}
