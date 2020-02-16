package zyx.opengl.models.implementations;

import zyx.engine.components.world.WorldObject;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public interface IParticleModel extends IDisposeable, IUpdateable
{
	public void setParent(WorldObject parent);

	public void draw();
	
	public boolean isWorldParticle();

	public IParticleModel cloneParticle();

	public float getRadius();

	public void refresh(LoadableParticleVO loadedVo);
}
