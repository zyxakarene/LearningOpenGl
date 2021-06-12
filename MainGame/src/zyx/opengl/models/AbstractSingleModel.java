package zyx.opengl.models;

import java.nio.FloatBuffer;
import zyx.opengl.materials.Material;

public abstract class AbstractSingleModel<TMaterial extends Material> extends AbstractModel<TMaterial>
{
	
	protected final ModelData<TMaterial> modelData;

	protected AbstractSingleModel(TMaterial defaultMaterial)
	{
		modelData = new ModelData();
		modelData.setFrom(defaultMaterial);
	}
	
	@Override
	protected void createObjects()
	{
		modelData.createObjects();
	}

	protected void bindVao()
	{
		modelData.bindVao();
	}

	protected void setVertexData(float[] vertexData, int[] elementData)
	{
		modelData.setVertexData(vertexData, elementData);
	}

	protected void updateVertexSubData(FloatBuffer buffer, long offset)
	{
		modelData.updateVertexSubData(buffer, offset);
	}

	public TMaterial getClonedMaterial()
	{
		return (TMaterial) modelData.defaultMaterial.cloneMaterial();
	}
		
	@Override
	public final void draw()
	{
		draw(modelData.defaultMaterial);
	}
	
	public void draw(TMaterial material)
	{
		if (modelData.elementCount > 0)
		{
			if (canDraw())
			{
				material.bind();
				
				ModelUtils.drawElements(modelData.vao, modelData.elementCount);
			}
		}
	}

	protected final void addAttribute(String attributeName, int components, int stride, int offset)
	{
		modelData.addAttribute(attributeName, components, stride, offset);
	}

	@Override
	public void dispose()
	{
		modelData.dispose();
	}
}
