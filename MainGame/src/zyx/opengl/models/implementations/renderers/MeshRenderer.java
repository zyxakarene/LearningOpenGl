package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.Material;
import zyx.opengl.models.AbstractModel;
import zyx.utils.interfaces.IDrawable;

public abstract class MeshRenderer<TMaterial extends Material, TModel extends AbstractModel<TMaterial>> implements IDrawable
{
	protected TModel model;
	
	private TMaterial defaultMaterial;
	private TMaterial clonedMaterial;
	private TMaterial drawMaterial;

	public MeshRenderer(TModel model, TMaterial defaultMaterial)
	{
		this.model = model;
		this.defaultMaterial = defaultMaterial;
		
		drawMaterial = defaultMaterial;
	}
	
	@Override
	public void draw()
	{
		model.draw(drawMaterial);
	}
	
	public TMaterial clonMaterial()
	{
		if (clonedMaterial == null)
		{
			clonedMaterial = (TMaterial) defaultMaterial.cloneMaterial();
			drawMaterial = clonedMaterial;
		}
		return clonedMaterial;
	}
	
}
