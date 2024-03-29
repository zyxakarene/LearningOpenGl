package zyx.opengl.models.implementations.renderers;

import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.Material;
import zyx.opengl.models.AbstractMultiModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;

//TODO: Make sure this works with asset reloading
public abstract class MeshRenderer<TMaterial extends Material, TModel extends AbstractMultiModel<TMaterial>> implements IDrawable, IDisposeable
{
	protected TModel model;
	protected WorldObject parent;
	
	private int meshIndex;
	private TMaterial defaultMaterial;
	
	TMaterial drawMaterial;

	public MeshRenderer(TModel model, int meshIndex, TMaterial defaultMaterial)
	{
		this.model = model;
		this.meshIndex = meshIndex;
		this.defaultMaterial = defaultMaterial;
		
		drawMaterial = defaultMaterial;
		MeshRenderList.getInstance().add(this);
	}

	public void setParent(WorldObject parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void draw()
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

		model.draw(meshIndex, drawMaterial);
		
		if (parent != null)
		{
			parent.onPostDraw();
		}
	}
	
	public TMaterial getDefaultMaterial()
	{
		return defaultMaterial;
	}
	
	public TMaterial getActiveMaterial()
	{
		return drawMaterial;
	}
	
	public void setCustomMaterial(TMaterial material)
	{
		drawMaterial = material;
	}
	
	public TMaterial cloneMaterial()
	{
		if (drawMaterial == defaultMaterial)
		{
			drawMaterial = (TMaterial) defaultMaterial.cloneMaterial();
		}
		
		return drawMaterial;
	}

	@Override
	public final void dispose()
	{
		MeshRenderList.getInstance().remove(this);
		
		model = null;
		drawMaterial = null;
		defaultMaterial = null;
	}

	protected void onPreDraw()
	{
	}
}
