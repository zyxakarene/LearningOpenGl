package zyx.opengl.textures.bitmapfont;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;

public class Text extends AbstractModel
{

	private BitmapFont font;

	public Text(BitmapFont font)
	{
		super(Shader.SCREEN);
		this.font = font;
		
		setTexture(font.texture);
	}
	
	public void setText(String text)
	{
		TextGenerator generator = new TextGenerator(font.fontFile);
		
		int len = text.length();
		char character;
		for (int i = 0; i < len; i++)
		{
			character = text.charAt(i);
			generator.addCharacter(character);
		}
		
		float[] vertexData = generator.getVertexData();
		int[] elementData = generator.getElementData();
		
		bindVao();
		setVertexData(vertexData, elementData);
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		font = null;
	}
}
