package zyx.opengl.textures.bitmapfont;

import java.nio.FloatBuffer;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.materials.impl.BitmapTextMaterial;
import zyx.opengl.models.AbstractSingleModel;
import zyx.opengl.models.BufferWrapper;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.models.implementations.renderers.wrappers.MeshWrapper;
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;
import zyx.opengl.textures.bitmapfont.alignment.VAlignment;

public class Text extends AbstractSingleModel<BitmapTextMaterial>
{

	private int characterCount;
	private int vertexCount;

	private float fontScale;
	
	private float width;
	private float height;
	
	private String lastText;
	
	private boolean shouldUpdate;
	private HAlignment hAlign;
	private VAlignment vAlign;
	private boolean allowMultiline;
	
	public Text(BitmapTextMaterial material, float fontScale, float width, float height)
	{
		super(material);
		createObjects();
		setupAttributes();
		
		this.fontScale = fontScale;
		this.width = width;
		this.height = height;
		
		hAlign = HAlignment.CENTER;
		vAlign = VAlignment.MIDDLE;
		
		shouldUpdate = true;
		
		lastText = "";
	}

	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawUi();
	}

	@Override
	public void draw(BitmapTextMaterial material)
	{
		if (shouldUpdate)
		{
			shouldUpdate = false;
			update(material);
		}
		
		super.draw(material);
	}
	
	private void update(BitmapTextMaterial material)
	{
		TextGenerator generator = new TextGenerator(material, width, height, fontScale);
		generator.setAlign(hAlign, vAlign);
		generator.setAllowMultiline(allowMultiline);
		
		characterCount = lastText.length();
		char character;
		for (int i = 0; i < characterCount; i++)
		{
			character = lastText.charAt(i);
			generator.addCharacter(character);
		}

		float[] vertexData = generator.getVertexData();
		int[] elementData = generator.getElementData();

		vertexCount = generator.getVertexCount();
				
		bindVao();
		setVertexData(vertexData, elementData);
	}
	
	public void setText(String text)
	{
		lastText = text;
		shouldUpdate = true;
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 8, 0);
		addAttribute("texcoord", 2, 8, 2);
		addAttribute("color", 4, 8, 4);
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public void updateColors()
	{
		if (vertexCount > 0)
		{
			bindVao();
			
			Vector3f colors = modelData.defaultMaterial.color;
			float alpha = modelData.defaultMaterial.alpha;
			
			float vertexData[] = new float[]
			{
				colors.x, colors.y, colors.z, alpha
			};
			FloatBuffer buffer = BufferWrapper.toBuffer(vertexData);

			int offsetPerVertex = Float.BYTES * 8;

			for (int i = 0; i < vertexCount; i++)
			{
				long base = offsetPerVertex * i;
				long offset = base + (Float.BYTES * 4);
				updateVertexSubData(buffer, offset);
			}
		}
	}

	public void setFontScale(float scale)
	{
		fontScale = scale;
		shouldUpdate = true;
	}

	public void setWidth(float width)
	{
		this.width = width;
		shouldUpdate = true;
	}

	public void setHeight(float height)
	{
		this.height = height;
		shouldUpdate = true;
	}
	
	public void setArea(float width, float height)
	{
		this.width = width;
		this.height = height;
		
		shouldUpdate = true;
	}

	public void setAlignment(VAlignment vAlign, HAlignment hAlign)
	{
		this.vAlign = vAlign;
		this.hAlign = hAlign;
		
		shouldUpdate = true;
	}

	public void setAllowMultiline(boolean value)
	{
		allowMultiline = value;
		
		shouldUpdate = true;
	}

	@Override
	public MeshWrapper createWrapper()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
