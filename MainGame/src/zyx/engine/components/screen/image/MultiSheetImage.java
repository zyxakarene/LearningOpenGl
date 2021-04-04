package zyx.engine.components.screen.image;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.sheet.SpriteSheetResource;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.textures.AbstractTexture;

public class MultiSheetImage extends AbstractQuad implements IResourceReady<SpriteSheetResource>
{

	private int uniqueResourceCount;
	private int uniqueResourceLoaded;
	private HashMap<String, ArrayList<Vector2f>> resourceToPosition;
	private ArrayList<SpriteSheetResource> activeResources;
	
	public MultiSheetImage()
	{
		setColor(1, 1, 1);
		
		resourceToPosition = new HashMap<>();
		activeResources = new ArrayList<>();
	}
	
	public void setImages(String[] resourceIds, Vector2f[] imagePositions)
	{
		if (model != null)
		{
			originalWidth = 0;
			originalHeight = 0;
			
			clean();
			
			model.dispose();
			model = null;
		}
		
		int resourceLen = resourceIds.length;
		int positionLen = imagePositions.length;
		
		if (resourceLen != positionLen)
		{
			throw new IllegalArgumentException("Length of resources and positions are not the same: " + resourceLen + " != " + positionLen);
		}
		
		for (int i = 0; i < resourceLen; i++)
		{
			String resourceId = resourceIds[i];
			Vector2f imagePosition = imagePositions[i];
			
			ArrayList<Vector2f> positions = resourceToPosition.get(resourceId);
			if (positions == null)
			{
				positions = new ArrayList<>();
				resourceToPosition.put(resourceId, positions);
			}
			
			positions.add(imagePosition);
		}
		
		uniqueResourceCount = resourceToPosition.size();
		uniqueResourceLoaded = 0;
		for (String key : resourceToPosition.keySet())
		{
			SpriteSheetResource res = ResourceManager.getInstance().getResourceAs(key);
			res.registerAndLoad(this);
		}
	}
	
	@Override
	public void onResourceReady(SpriteSheetResource resource)
	{
		AbstractTexture texture = resource.getContent();
		
		if (model == null)
		{
			material.setDiffuse(texture);
			model = new ScreenModel(material);
		}
		
		ArrayList<Vector2f> positions = resourceToPosition.get(resource.path);
		for (Vector2f pos : positions)
		{
			model.addVertexData(pos.x, pos.y, texture);
		}
		
		uniqueResourceLoaded++;
		if (uniqueResourceLoaded == uniqueResourceCount)
		{
			model.buildModel();
			onModelCreated();
		}
	}
	
	@Override
	protected void onDispose()
	{
		super.onDispose();

		clean();
		
		if (model != null)
		{
			model.dispose();
			model = null;
		}
	}

	@Override
	public String getDebugIcon()
	{
		return "image.png";
	}
	
	@Override
	public String toString()
	{
		return String.format("ComposedImage{%s}", activeResources.size());
	}

	private void clean()
	{
		for (SpriteSheetResource key : activeResources)
		{
			key.unregister(this);
		}
		
		activeResources.clear();
		resourceToPosition.clear();
	}
}
