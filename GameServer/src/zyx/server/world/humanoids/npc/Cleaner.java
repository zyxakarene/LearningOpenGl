package zyx.server.world.humanoids.npc;

import java.awt.Color;
import zyx.server.world.humanoids.npc.behavior.cleaner.CleanerBehaviorType;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

public class Cleaner extends BaseNpc<CleanerBehaviorType>
{

	public Cleaner(NpcSetup setup)
	{
		super(setup);
	}

	@Override
	public Color getColor()
	{
		return Color.CYAN;
	}
}
