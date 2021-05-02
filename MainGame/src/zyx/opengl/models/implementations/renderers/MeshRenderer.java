package zyx.opengl.models.implementations.renderers;

import zyx.engine.components.world.WorldObject;
import zyx.opengl.GLUtils;
import zyx.opengl.materials.Material;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;

public abstract class MeshRenderer<TMaterial extends Material, TModel extends AbstractModel<TMaterial>> implements IDrawable, IDisposeable
{
	protected TModel model;
	protected WorldObject parent;
	
	private TMaterial defaultMaterial;
	private TMaterial clonedMaterial;
	
	TMaterial drawMaterial;

	public MeshRenderer(TModel model, TMaterial defaultMaterial)
	{
		this.model = model;
		this.defaultMaterial = defaultMaterial;
		
		clonedMaterial = null;
		drawMaterial = defaultMaterial;
		MeshRenderList.getInstance().add(this);
	}
	
	public void setup(WorldObject drawParent)
	{
		parent = drawParent;
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

		model.draw(drawMaterial);
		
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
		if (clonedMaterial != null)
		{
			return clonedMaterial;
		}
		
		return drawMaterial;
	}
	
	public void setCustomMaterial(TMaterial material)
	{
		clonedMaterial = null;
		drawMaterial = material;
	}
	
	public TMaterial cloneMaterial()
	{
		if (clonedMaterial == null)
		{
			clonedMaterial = (TMaterial) defaultMaterial.cloneMaterial();
			drawMaterial = clonedMaterial;
		}
		
		return drawMaterial;
	}

	@Override
	public final void dispose()
	{
		MeshRenderList.getInstance().remove(this);
		
		parent = null;
		model = null;
		drawMaterial = null;
		clonedMaterial = null;
		defaultMaterial = null;
	}

	protected void onPreDraw()
	{
	}
}
