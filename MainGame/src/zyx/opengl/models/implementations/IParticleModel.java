package zyx.opengl.models.implementations;

import zyx.engine.components.world.WorldObject;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public interface IParticleModel extends IDisposeable, IUpdateable
{
	public boolean isWorldParticle();

	public float getRadius();

	public void refresh(LoadableParticleVO loadedVo);
	
	public void setParent(WorldObject parent);
}
