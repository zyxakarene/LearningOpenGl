package zyx.engine.components.world;

import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.opengl.particles.ParticleSystem;

public class WorldObjectNode
{

	private final WorldObject instance;
	private int childCount;
	private String type;

	WorldObjectNode(WorldObject instance)
	{
		this.instance = instance;
		this.childCount = 0;
		this.type = instance.getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		boolean hasChildren = false;
		String extra = "";
		String childPart = "";
		if (instance instanceof SimpleMesh)
		{
			extra = from((SimpleMesh) instance);
		}
		else if (instance instanceof GameLight)
		{
			extra = from((GameLight) instance);
		}
		else if (instance instanceof ParticleSystem)
		{
			extra = from((ParticleSystem) instance);
		}
		else if (instance instanceof GameObject)
		{
			hasChildren = true;
			extra = from((GameObject) instance);
		}
		else
		{
			hasChildren = true;
		}
		
		if (hasChildren)
		{
			childPart = String.format("+(%s)", childCount);
		}
		
		return String.format("%s "+ extra + childPart, type);
	}

	private String from(SimpleMesh mesh)
	{
		return String.format("[%s]", mesh.getResource());
	}

	private String from(ParticleSystem particle)
	{
		return String.format("[%s]", particle.getResource());
	}

	private String from(GameLight light)
	{
		int color = light.getColor();
		String hex = Integer.toHexString(color).toUpperCase();
		return String.format("[0x%s]", hex);
	}

	private String from(GameObject gameObject)
	{
		int behaviorCount = gameObject.getBehaviorCount();
		return String.format("[%s behaviors]", behaviorCount);
	}
}
