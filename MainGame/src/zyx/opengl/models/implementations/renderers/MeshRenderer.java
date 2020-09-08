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
	
	private TMaterial defaultMaterial;
	private TMaterial clonedMaterial;
	TMaterial drawMaterial;
	
	private WorldObject parent;

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
			SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(parent.worldMatrix());
		}
		
		onPreDraw();
		drawMaterial.shader.bind();
		drawMaterial.shader.upload();

		model.draw(drawMaterial);
	}
	
	public TMaterial cloneMaterial()
	{
		if (clonedMaterial == null)
		{
			clonedMaterial = (TMaterial) defaultMaterial.cloneMaterial();
			drawMaterial = clonedMaterial;
		}
		return clonedMaterial;
	}

	@Override
	public final void dispose()
	{
		MeshRenderList.getInstance().remove(this);
		
		onDispose();
		
		parent = null;
		model = null;
		drawMaterial = null;
		defaultMaterial = null;
		clonedMaterial = null;
	}

	protected void onPreDraw()
	{
	}

	protected void onDispose()
	{
	}
}
