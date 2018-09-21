package zyx.engine.components.screen;

import java.util.HashMap;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.TextureResource;
import zyx.opengl.GLUtils;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.GameTexture;
import zyx.utils.geometry.Rectangle;
import zyx.utils.math.Scale9Texture;

public class Scale9Image extends AbstractQuad implements IResourceReady<TextureResource>
{

	private Resource textureResource;
	
	private float scaleX;
	private float scaleY;

	public Scale9Image()
	{
		scaleX = 1;
		scaleY = 1;
	}

	public void load(String resource)
	{
		textureResource = ResourceManager.getInstance().getResource(resource);
		textureResource.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(TextureResource resource)
	{
		AbstractTexture texture = resource.getContent();
		
		HELPER_VEC4.set(1, 1, 1, 1);
		model = new ScreenModel(texture, HELPER_VEC4);

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

		GameTexture texture = (GameTexture) textureResource.getContent();
		Rectangle grid = new Rectangle(25, 25, 14, 14);

		HashMap<String, GameTexture> textureMap = Scale9Texture.ToScale9TextureMap(grid, texture);

		GameTexture topLeftTexture = textureMap.get(Scale9Texture.TOP_LEFT);
		GameTexture topMidTexture = textureMap.get(Scale9Texture.TOP_MIDDLE);
		GameTexture topRightTexture = textureMap.get(Scale9Texture.TOP_RIGHT);
		GameTexture midLeftTexture = textureMap.get(Scale9Texture.MIDDLE_LEFT);
		GameTexture midMidTexture = textureMap.get(Scale9Texture.MIDDLE_MIDDLE);
		GameTexture midRightTexture = textureMap.get(Scale9Texture.MIDDLE_RIGHT);
		GameTexture lowLeftTexture = textureMap.get(Scale9Texture.LOWER_LEFT);
		GameTexture lowMidTexture = textureMap.get(Scale9Texture.LOWER_MIDDLE);
		GameTexture lowRightTexture = textureMap.get(Scale9Texture.LOWER_RIGHT);
		
		float colLeftWidth = topLeftTexture.getWidth();
		float colMidWidth = topMidTexture.getWidth();
		float colRightWidth = topRightTexture.getWidth();
		float rowTopHeight = topLeftTexture.getHeight();
		float rowMidHeight = midLeftTexture.getHeight();
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

		GLUtils.errorCheck();
		
		model.buildModel();
	}
}
