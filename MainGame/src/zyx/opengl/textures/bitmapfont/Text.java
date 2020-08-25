package zyx.opengl.textures.bitmapfont;

import java.nio.FloatBuffer;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.materials.Material;
import zyx.opengl.materials.impl.ScreenModelMaterial;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.BufferWrapper;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;
import zyx.opengl.textures.bitmapfont.alignment.VAlignment;

public class Text extends AbstractModel<ScreenModelMaterial>
{

	private BitmapFont font;
	private int characterCount;
	private int vertexCount;

	private float fontScale;
	
	private float width;
	private float height;
	
	private String lastText;
	private Vector4f lastColor;
	
	private boolean shouldUpdate;
	private HAlignment hAlign;
	private VAlignment vAlign;
	
	public Text(BitmapFont font, float fontScale, float width, float height)
	{
		super(font.material);
		setup();
		
		this.font = font;
		this.fontScale = fontScale;
		this.width = width;
		this.height = height;
		
		hAlign = HAlignment.CENTER;
		vAlign = VAlignment.MIDDLE;
		
		shouldUpdate = true;
		
		lastText = "";
		lastColor = new Vector4f(1, 1, 1, 1);
	}

	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawUi();
	}

	@Override
	public void draw(Material material)
	{
		if (shouldUpdate)
		{
			shouldUpdate = false;
			update();
		}
		
		super.draw(material);
	}
	
	private void update()
	{
		TextGenerator generator = new TextGenerator(font.fontFile, lastColor, width, height, fontScale);
		generator.sethAlign(hAlign, vAlign);
		
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

	@Override
	public void dispose()
	{
		super.dispose();

		font = null;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setColors(Vector4f colors)
	{
		lastColor.set(colors);
		
		if (vertexCount > 0)
		{
			bindVao();

			float vertexData[] = new float[]
			{
				colors.x, colors.y, colors.z, colors.w
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
}
