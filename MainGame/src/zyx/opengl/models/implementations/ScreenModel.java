package zyx.opengl.models.implementations;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.controls.SharedPools;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.BufferWrapper;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.ListUtils;

public class ScreenModel extends AbstractModel
{
	private static final int FLOATS_PER_QUAD = 32;
	private static final int INTS_PER_QUAD = 6;
	private static final int VERTECES_PER_QUAD = 4;
	
	private ArrayList<Float> tempVertexData = new ArrayList<>();
	private ArrayList<Integer> tempElementData = new ArrayList<>();
	private int currentVertexCount;
	private int tempVertexCount;
	
	public final ScreenShader shader;

	private Vector4f colors;
	
	public ScreenModel(AbstractTexture texture, Vector4f color)
	{
		super(Shader.SCREEN);

		colors = SharedPools.VECTOR_POOL_4F.getInstance();
		shader = (ScreenShader) meshShader;

		colors.set(color);

		setTexture(texture);
	}
	
	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawUi();
	}
	
	public void buildModel()
	{
		float[] vertexData = ListUtils.toFloatArray(tempVertexData);
		int[] elementData = ListUtils.toIntArray(tempElementData);
		
		setVertexData(vertexData, elementData);
		
		currentVertexCount = tempVertexCount;
		tempVertexData = new ArrayList<>();
		tempElementData = new ArrayList<>();
		tempVertexCount = 0;
	}
	
	public void addVertexData(float x, float y, AbstractTexture tex)
	{
		addVertexData(x, y, tex.getWidth(), tex.getHeight(), tex);
	}
	
	public void addVertexData(float x, float y, float width, float height, AbstractTexture tex)
	{
		AbstractTexture t = tex;
		Vector4f c = colors;
		float w = width;
		float h = height;
		float vertexData[] =
		{
			//x			y			Texcoords	//Colors
			x,			-y,			t.x, t.y,	c.x, c.y, c.z, c.w, // Top-left
			x + w,		-y,			t.u, t.y,	c.x, c.y, c.z, c.w, // Top-right
			x + w,		-h - y,		t.u, t.v,	c.x, c.y, c.z, c.w, // Bottom-right
			x,			-h - y,		t.x, t.v,	c.x, c.y, c.z, c.w,// Bottom-left
		};
		
		int elementData[] =
		{
			2 + tempVertexCount, 1 + tempVertexCount, 0 + tempVertexCount,
			0 + tempVertexCount, 3 + tempVertexCount, 2 + tempVertexCount
		};
		
		for (int i = 0; i < vertexData.length; i++)
		{
			tempVertexData.add(vertexData[i]);
		}
		
		for (int i = 0; i < elementData.length; i++)
		{
			tempElementData.add(elementData[i]);
		}
		
		tempVertexCount += VERTECES_PER_QUAD;
	}
	
	public int[] addElementData()
	{
		int elementData[] =
		{
			2, 1, 0,
			0, 3, 2
		};
		
		return elementData;
	}
	
	public void setColors(Vector4f colors)
	{
		bindVao();
		
		float vertexData[] = new float[]
		{
			colors.x, colors.y, colors.z, colors.w
		};
		FloatBuffer buffer = BufferWrapper.toBuffer(vertexData);

		int offsetPerVertex = Float.BYTES * 8;

		for (int i = 0; i < currentVertexCount; i++)
		{
			long base = offsetPerVertex * i;
			long offset = base + (Float.BYTES * VERTECES_PER_QUAD);
			updateVertexSubData(buffer, offset);
		}
	}
	
	public float getWidth()
	{
		return getDefaultTexture().getWidth();
	}
	
	public float getHeight()
	{
		return getDefaultTexture().getHeight();
	}
	
	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 8, 0);
		addAttribute("texcoord", 2, 8, 2);
		addAttribute("color", 4, 8, 4);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		if(colors != null)
		{
			SharedPools.VECTOR_POOL_4F.releaseInstance(colors);
			colors = null;
		}
	}

	public void prepareBatchCount(int count)
	{
		tempVertexData.ensureCapacity(count * FLOATS_PER_QUAD);
		tempElementData.ensureCapacity(count * INTS_PER_QUAD);
	}
}
