package zyx.opengl.models;

import java.nio.FloatBuffer;
import zyx.opengl.materials.Material;

public abstract class AbstractMultiModel<TMaterial extends Material> extends AbstractModel<TMaterial>
{
	protected int subMeshCount;
	protected ModelData<TMaterial>[] modelData;

	protected AbstractMultiModel()
	{
	}
	
	protected void setSubMeshCount(int count)
	{
		if (subMeshCount == count)
		{
			return;
		}
		
		if (modelData != null)
		{
			for (int i = 0; i < subMeshCount; i++)
			{
				modelData[i].dispose();
			}
			modelData = null;
		}
		
		subMeshCount = count;
		modelData = new ModelData[subMeshCount];
		for (int i = 0; i < subMeshCount; i++)
		{
			modelData[i] = new ModelData<>();
		}
	}
	
	protected void setDefaultMaterials(TMaterial... materials)
	{
		if (materials.length != subMeshCount)
		{
			throw new RuntimeException("Expected material length does not equal given amount:" + subMeshCount + " != " + materials.length);
		}
		
		for (int i = 0; i < subMeshCount; i++)
		{
			modelData[i].setFrom(materials[i]);
		}
	}
	
	protected TMaterial getDefaultMaterial(int index)
	{
		return modelData[index].defaultMaterial;
	}
	
	@Override
	protected void createObjects()
	{
		for (ModelData<TMaterial> data : modelData)
		{
			data.createObjects();
		}
	}

	protected void bindVao(int index)
	{
		modelData[index].bindVao();
	}

	protected void setVertexData(int index, float[] vertexData, int[] elementData)
	{
		modelData[index].setVertexData(vertexData, elementData);
	}

	protected void updateVertexSubData(int index, FloatBuffer buffer, long offset)
	{
		modelData[index].updateVertexSubData(buffer, offset);
	}

	public TMaterial getClonedMaterial(int index)
	{
		return (TMaterial) modelData[index].defaultMaterial.cloneMaterial();
	}
		
	@Override
	public final void draw()
	{
		for (int i = 0; i < subMeshCount; i++)
		{
			draw(i, modelData[i].defaultMaterial);
		}
	}
	
	public void draw(int index, TMaterial material)
	{
		ModelData drawData = modelData[index];
		
		if (drawData.elementCount > 0)
		{
			if (canDraw())
			{
				material.bind();
				
				ModelUtils.drawElements(drawData.vao, drawData.elementCount);
			}
		}
	}

	protected final void addAttribute(int index, String attributeName, int components, int stride, int offset)
	{
		modelData[index].addAttribute(attributeName, components, stride, offset);
	}

	@Override
	public void dispose()
	{
		if (modelData != null)
		{
			for (int i = 0; i < subMeshCount; i++)
			{
				modelData[i].dispose();
			}
			modelData = null;
		}
		
		subMeshCount = 0;
	}
}
