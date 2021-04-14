package zyx.opengl.models.implementations.renderers;

import zyx.engine.components.world.WorldObject;
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
	
	TMaterial drawMaterial;

	public MeshRenderer(TModel model, TMaterial defaultMaterial)
	{
		this.model = model;
		this.defaultMaterial = defaultMaterial;
		
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
		drawMaterial.shader.bind();
		drawMaterial.shader.upload();

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
		if (drawMaterial != null)
		{
			return drawMaterial;
		}
		
		return defaultMaterial;
	}
	
	public void setCustomMaterial(TMaterial material)
	{
		drawMaterial = material;
	}
	
	public TMaterial cloneMaterial()
	{
		if (drawMaterial == null)
		{
			drawMaterial = (TMaterial) defaultMaterial.cloneMaterial();
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
		defaultMaterial = null;
	}

	protected void onPreDraw()
	{
	}
}
