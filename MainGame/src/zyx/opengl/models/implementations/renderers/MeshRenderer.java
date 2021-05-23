package zyx.opengl.models.implementations.renderers;

import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.Material;
import zyx.opengl.models.AbstractMultiModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;

//TODO: Make sure this works with asset reloading
//TODO: Sorting of sub meshes somehow
public abstract class MeshRenderer<TMaterial extends Material, TModel extends AbstractMultiModel<TMaterial>> implements IDrawable, IDisposeable
{
	protected TModel model;
	protected WorldObject parent;
	
	private int materialCount;
	private TMaterial[] defaultMaterials;
	
	TMaterial[] drawMaterials;

	public MeshRenderer(TModel model, TMaterial[] defaultMaterials)
	{
		materialCount = defaultMaterials.length;
		
		this.model = model;
		this.defaultMaterials = defaultMaterials;
		
		drawMaterials = defaultMaterials;
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

		for (int i = 0; i < materialCount; i++)
		{
			model.draw(i, drawMaterials[i]);
		}
		
		if (parent != null)
		{
			parent.onPostDraw();
		}
	}
	
	public TMaterial getDefaultMaterial(int index)
	{
		return defaultMaterials[index];
	}
	
	public TMaterial getActiveMaterial(int index)
	{
		return drawMaterials[index];
	}
	
	public void setCustomMaterial(int index, TMaterial material)
	{
		drawMaterials[index] = material;
	}
	
	public TMaterial cloneMaterial(int index)
	{
		if (drawMaterials[index] == defaultMaterials[index])
		{
			drawMaterials[index] = (TMaterial) defaultMaterials[index].cloneMaterial();
		}
		
		return drawMaterials[index];
	}

	@Override
	public final void dispose()
	{
		MeshRenderList.getInstance().remove(this);
		
		parent = null;
		model = null;
		drawMaterials = null;
		defaultMaterials = null;
	}

	protected void onPreDraw()
	{
	}
}
