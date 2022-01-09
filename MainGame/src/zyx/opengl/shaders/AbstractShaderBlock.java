package zyx.opengl.shaders;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Vector;

public abstract class AbstractShaderBlock
{
	static final Object LOCK = new Object();
	
	private static int linkCounter = 0;
	private int linkIndex;
	
	private int ubo;
	
	private int floatCount;
	private int itemCount;
	
	private FloatBuffer buffer;
	
	private IFillableObject[] fillables;
	
	
	public AbstractShaderBlock(Object lock)
	{
		if (lock != LOCK)
		{
			throw new RuntimeException();
		}
		
		linkIndex = linkCounter++;
		addComponents();
	}
	
	protected abstract void addComponents();
	protected abstract void onUpload();
	public abstract String GetName();
	
	protected void onPostBuild()
	{
	}
	
	public void link(int program)
	{
		ShaderUtils.createBlockUniform(program, GetName(), linkIndex);
	}
	
	void build()
	{
		ubo = ShaderUtils.generateBufferObject();
		
		buffer = BufferUtils.createFloatBuffer(floatCount);
		fillables = new IFillableObject[itemCount];
		onPostBuild();
		
		GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, ubo);
		GL15.glBufferData(GL31.GL_UNIFORM_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL31.GL_UNIFORM_BUFFER, 0);
		
		GL30.glBindBufferRange(GL31.GL_UNIFORM_BUFFER, linkIndex, ubo, 0, floatCount);
	}
	
	protected void setData(int index, Matrix value)
	{
		if (fillables[index] instanceof FillableMatrix)
		{
			((FillableMatrix) fillables[index]).item = value;
		}
		else
		{
			fillables[index] = new FillableMatrix(value);
		}
	}
	
	protected void setData(int index, Vector value)
	{
		if (fillables[index] instanceof FillableVector)
		{
			((FillableVector) fillables[index]).item = value;
		}
		else
		{
			fillables[index] = new FillableVector(value);
		}
	}
	
	protected void setData(int index, float value)
	{
		if (fillables[index] instanceof FillableFloat)
		{
			((FillableFloat) fillables[index]).item = value;
		}
		else
		{
			fillables[index] = new FillableFloat(value);
		}
	}
	
	void upload()
	{
		onUpload();
		
		buffer.clear();
		for (IFillableObject fillable : fillables)
		{
			fillable.store(buffer);
		}
		
		ShaderUtils.fillBufferObject(ubo, buffer);
	}
	
	protected int addMatrix(int rows, int columns)
	{
		floatCount += rows * columns;
		
		int index = itemCount;
		itemCount++;
		return index;
	}
	
	protected int addVector(int size)
	{
		floatCount += size;
		
		int index = itemCount;
		itemCount++;
		return index;
	}
	
	protected int addFloat()
	{
		floatCount += 1;
		
		int index = itemCount;
		itemCount++;
		return index;
	}

	private interface IFillableObject
	{
		void store(FloatBuffer buffer);
	}
	
	private abstract class BaseFillable<T> implements AbstractShaderBlock.IFillableObject
	{
		protected T item;

		BaseFillable(T item)
		{
			this.item = item;
		}

		void setItem(T item)
		{
			this.item = item;
		}
	}
	
	private class FillableMatrix extends BaseFillable<Matrix>
	{
		FillableMatrix(Matrix item)
		{
			super(item);
		}
		
		@Override
		public void store(FloatBuffer buffer)
		{
			item.store(buffer);
		}
	}
	
	private class FillableVector extends BaseFillable<Vector>
	{
		FillableVector(Vector item)
		{
			super(item);
		}
		
		@Override
		public void store(FloatBuffer buffer)
		{
			item.store(buffer);
		}
	}
	
	private class FillableFloat extends BaseFillable<Float>
	{
		FillableFloat(float item)
		{
			super(item);
		}
		
		@Override
		public void store(FloatBuffer buffer)
		{
			buffer.put(item);
		}
	}
}
