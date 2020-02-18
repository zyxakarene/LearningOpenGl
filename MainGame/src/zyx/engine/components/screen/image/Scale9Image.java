package zyx.engine.components.screen.image;

import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.impl.textures.TextureResource;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.geometry.Rectangle;
import zyx.utils.math.Scale9Texture;

public class Scale9Image extends AbstractImage implements IResourceReady<TextureResource>
{

	private float scaleX;
	private float scaleY;
	
	private AbstractTexture gameTexture;

	public Scale9Image()
	{
		scaleX = 1;
		scaleY = 1;
	}

	@Override
	protected void onTextureResourceReady(AbstractTexture texture)
	{
		gameTexture = texture;
		
		model = new ScreenModel(texture, colors);

		constructModel(texture.getWidth(), texture.getHeight());

		onModelCreated();
	}

	@Override
	public void setScale(float x, float y)
	{
		scaleX = x;
		scaleY = y;
		
		float width = getWidth();
		float height = getHeight();
		constructModel(width, height);
	}

	@Override
	public Vector2f getScale(boolean local, Vector2f out)
	{
		if (!local)
		{
			super.setScale(scaleX, scaleY);
			out = super.getScale(local, out);
			super.setScale(1, 1);
		}
		else
		{
			if (out == null)
			{
				out = new Vector2f();
			}
			
			out.set(scaleX, scaleY);
		}
		
		return out;
	}
	
	private void constructModel(float width, float height)
	{
		model.prepareBatchCount(9);

		Rectangle grid = Scale9Grids.getGridFor(gameTexture.getName());

		HashMap<String, AbstractTexture> textureMap = Scale9Texture.ToScale9TextureMap(grid, gameTexture);

		AbstractTexture topLeftTexture = textureMap.get(Scale9Texture.TOP_LEFT);
		AbstractTexture topMidTexture = textureMap.get(Scale9Texture.TOP_MIDDLE);
		AbstractTexture topRightTexture = textureMap.get(Scale9Texture.TOP_RIGHT);
		AbstractTexture midLeftTexture = textureMap.get(Scale9Texture.MIDDLE_LEFT);
		AbstractTexture midMidTexture = textureMap.get(Scale9Texture.MIDDLE_MIDDLE);
		AbstractTexture midRightTexture = textureMap.get(Scale9Texture.MIDDLE_RIGHT);
		AbstractTexture lowLeftTexture = textureMap.get(Scale9Texture.LOWER_LEFT);
		AbstractTexture lowMidTexture = textureMap.get(Scale9Texture.LOWER_MIDDLE);
		AbstractTexture lowRightTexture = textureMap.get(Scale9Texture.LOWER_RIGHT);
		
		float colLeftWidth = topLeftTexture.getWidth();
		float colMidWidth;
		float colRightWidth = topRightTexture.getWidth();
		float rowTopHeight = topLeftTexture.getHeight();
		float rowMidHeight;
		float rowLowHeight = lowLeftTexture.getHeight();

		if(colLeftWidth + colRightWidth > width)
		{
			//Tiny image, so we have to scale the outer columns
			colLeftWidth = width / 2;
			colMidWidth = 0;
			colRightWidth = width / 2;
		}
		else
		{
			//Big image, so only scale middle column
			colMidWidth = width - colLeftWidth - colRightWidth;
		}

		if(rowTopHeight + rowLowHeight > height)
		{
			//Tiny image, so we have to scale the outer rows
			rowTopHeight = height / 2;
			rowMidHeight = 0;
			rowLowHeight = height / 2;
		}
		else
		{
			//Big image, so only scale middle row
			rowMidHeight = height - rowTopHeight - rowLowHeight;
		}
		
		model.addVertexData(0, 0, colLeftWidth, rowTopHeight, topLeftTexture);
		model.addVertexData(colLeftWidth, 0, colMidWidth, rowTopHeight, topMidTexture);
		model.addVertexData(colLeftWidth + colMidWidth, 0, colRightWidth, rowTopHeight, topRightTexture);

		model.addVertexData(0, rowTopHeight, colLeftWidth, rowMidHeight, midLeftTexture);
		model.addVertexData(colLeftWidth, rowTopHeight, colMidWidth, rowMidHeight, midMidTexture);
		model.addVertexData(colLeftWidth + colMidWidth, rowTopHeight, colRightWidth, rowMidHeight, midRightTexture);

		model.addVertexData(0, rowTopHeight + rowMidHeight, colLeftWidth, rowLowHeight, lowLeftTexture);
		model.addVertexData(colLeftWidth, rowTopHeight + rowMidHeight, colMidWidth, rowLowHeight, lowMidTexture);
		model.addVertexData(colLeftWidth + colMidWidth, rowTopHeight + rowMidHeight, colRightWidth, rowLowHeight, lowRightTexture);
		
		model.buildModel();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		if (model != null)
		{
			model.dispose();
			model = null;
		}
		
		gameTexture = null;
	}
	
	@Override
	public boolean hitTest(int x, int y)
	{
		if (!touchable || !visible)
		{
			return false;
		}

		HELPER_VEC4.x = x;
		HELPER_VEC4.y = -y;
		HELPER_VEC4.z = -1;
		HELPER_VEC4.w = 1;
		
		Matrix4f.transform(invWorldMatrix(), HELPER_VEC4, HELPER_VEC4);
		
		float w = getWidth();
		float h = getHeight();
		
		boolean collision = HELPER_VEC4.x >= 0 && HELPER_VEC4.y <= 0 && HELPER_VEC4.x <= w && HELPER_VEC4.y >= -h;
		return collision;
	}
	
	@Override
	public String toString()
	{
		return String.format("Scale9Image{%s}", resource);
	}
	
}
