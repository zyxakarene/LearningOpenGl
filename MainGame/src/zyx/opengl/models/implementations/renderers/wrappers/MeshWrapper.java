package zyx.opengl.models.implementations.renderers.wrappers;

import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.Material;
import zyx.opengl.models.AbstractMultiModel;
import zyx.opengl.models.implementations.renderers.MeshRenderer;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.interfaces.IDisposeable;

public abstract class MeshWrapper
		<
			TMaterial extends Material,
			TModel extends AbstractMultiModel<TMaterial>, 
			TRenderer extends MeshRenderer<TMaterial, TModel>
		> 
		implements IMeshWrapper, IDisposeable
{
	protected TModel model;
	protected WorldObject parent;
	
	private TRenderer[] renderers;
	
	public MeshWrapper(TRenderer[] renderers, TModel model)
	{
		this.renderers = renderers;
		this.model = model;
	}
	
	protected TRenderer getRenderer(int index)
	{
		return renderers[index];
	}
	
	public void setup(WorldObject drawParent)
	{
		this.parent = drawParent;
		
		int len = renderers.length;
		for (int i = 0; i < len; i++)
		{
			renderers[i].setParent(parent);
		}
	}
	
	public void draw(int index)
	{
		if (parent != null)
		{
			if (parent.inView() == false)
			{
				return;
			}
			
			SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(parent.worldMatrix());
		}
		
		onPreDraw();
		renderers[index].draw();
		
		if (parent != null)
		{
			parent.onPostDraw();
		}
	}
	
	public TMaterial getDefaultMaterial(int index)
	{
		return renderers[index].getDefaultMaterial();
	}
	
	public TMaterial getActiveMaterial(int index)
	{
		return renderers[index].getActiveMaterial();
	}
	
	public void setCustomMaterial(int index, TMaterial material)
	{
		renderers[index].setCustomMaterial(material);
	}
	
	public TMaterial cloneMaterial(int index)
	{
		return renderers[index].cloneMaterial();
	}

	@Override
	public final void dispose()
	{
		if (renderers != null)
		{
			int len = renderers.length;
			for (int i = 0; i < len; i++)
			{
				renderers[i].dispose();
			}
		}
		
		parent = null;
	}

	protected void onPreDraw()
	{
		
	}
}
