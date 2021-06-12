package zyx.opengl.models;

import zyx.opengl.materials.Material;

public abstract class AbstractInstancedModel<TMaterial extends Material> extends AbstractMultiModel<TMaterial>
{

	private int[] instanceVbos;
	private int[] instanceCounts;

	public AbstractInstancedModel()
	{
		super();
	}

	@Override
	protected void setSubMeshCount(int count)
	{
		super.setSubMeshCount(count);
		
		instanceVbos = new int[count];
		instanceCounts = new int[count];
	}
	
	@Override
	protected void createObjects()
	{
		super.createObjects();
		
		for (int i = 0; i < subMeshCount; i++)
		{
			instanceVbos[i] = ModelUtils.generateBufferObject();
		}
	}

	protected void setInstanceData(int index, float[] data, int count)
	{
		ModelUtils.bindBufferObject_Array(instanceVbos[index]);
		ModelUtils.fillVBO_Dynamic(data);
		instanceCounts[index] = count;
	}
	
	protected final void addInstanceAttribute(int index, String attributeName, int components, int stride, int offset)
	{
		ModelUtils.bindBufferObject_Array(instanceVbos[index]);
		ModelUtils.addInstanceAttribute(modelData[index].meshShader.program, attributeName, components, stride, offset);
	}
	
	@Override
	public void draw(int index, TMaterial material)
	{
		ModelData drawData = modelData[index];
		int instanceCount = instanceCounts[index];

		if (drawData.elementCount > 0 && instanceCount > 0)
		{
			if (canDraw())
			{
				material.bind();
				
				ModelUtils.drawInstancedElements(drawData.vao, drawData.elementCount, instanceCount);
			}
		}
	}

	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawWorld();
	}
	
	@Override
	public void dispose()
	{
		int len = instanceVbos.length;
		for (int i = 0; i < len; i++)
		{
			ModelUtils.disposeBuffer(instanceVbos[i]);
		}
		
		super.dispose();
	}

}
