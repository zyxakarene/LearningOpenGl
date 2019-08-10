package zyx.server.world.humanoids.npc;

import zyx.server.world.humanoids.npc.behavior.chef.ChefBehaviorType;
import zyx.server.world.humanoids.npc.naming.NpcSetup;

public class Chef extends BaseNpc<ChefBehaviorType>
{

	public Chef(NpcSetup setup)
	{
		super(setup);
		
		lx = 0;
		ly = 0;
		lz = 0;
	}
}
