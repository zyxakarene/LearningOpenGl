package zyx.opengl.models.implementations.renderers;

import java.util.ArrayList;
import java.util.Comparator;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.materials.RenderQueue;
import zyx.utils.interfaces.IDrawable;

public class MeshRenderList implements IDrawable
{

	private static final MeshRenderList INSTANCE = new MeshRenderList();
	
	private ArrayList<MeshRenderer> geometryRenderers;
	private ArrayList<MeshRenderer> transparentRenderers;
	
	private MeshDistanceSorting frontToBackSorting;
	private MeshDistanceSorting backToFrontSorting;
	
	private boolean dirty;

	private MeshRenderList()
	{
		geometryRenderers = new ArrayList<>();
		transparentRenderers = new ArrayList<>();
		dirty = false;
		
		frontToBackSorting = new MeshDistanceSorting(true);
		backToFrontSorting = new MeshDistanceSorting(false);
	}

	public static MeshRenderList getInstance()
	{
		return INSTANCE;
	}

	@Override
	public void draw()
	{
		if (dirty)
		{
			CheckList(geometryRenderers, RenderQueue.OPAQUE);
			CheckList(transparentRenderers, RenderQueue.TRANSPARENT);
			dirty = false;
		}

		DeferredRenderer.getInstance().bindBuffer();
		
		frontToBackSorting.sortMe(geometryRenderers);
		backToFrontSorting.sortMe(transparentRenderers);
		draw(geometryRenderers);
	}
	
	private void CheckList(ArrayList<MeshRenderer> list, RenderQueue queue)
	{
		for (int i = list.size() - 1; i >= 0; i--)
		{
			MeshRenderer renderer = list.get(i);
			RenderQueue rendererQueue = renderer.drawMaterial.queue;
			if (rendererQueue != queue)
			{
				switch (rendererQueue)
				{
					case OPAQUE:
						geometryRenderers.add(renderer);
						break;
					case TRANSPARENT:
						transparentRenderers.add(renderer);
						break;
				}
				
				list.remove(i);
			}
		}
	}

	private void draw(ArrayList<MeshRenderer> list)
	{
		int len = list.size();
		for (int i = 0; i < len; i++)
		{
			MeshRenderer renderer = list.get(i);

			renderer.draw();
		}
	}

	public void drawTransparent()
	{
		draw(transparentRenderers);
	}

	void add(MeshRenderer renderer)
	{
		switch (renderer.drawMaterial.queue)
		{
			case OPAQUE:
				addTo(renderer, geometryRenderers);
				break;
			case TRANSPARENT:
				addTo(renderer, transparentRenderers);
				break;
		}
	}

	void remove(MeshRenderer renderer)
	{
		switch (renderer.drawMaterial.queue)
		{
			case OPAQUE:
				removeFrom(renderer, geometryRenderers);
				break;
			case TRANSPARENT:
				removeFrom(renderer, transparentRenderers);
				break;
		}
	}

	private void addTo(MeshRenderer renderer, ArrayList<MeshRenderer> list)
	{
		if (list.contains(renderer) == false)
		{
			list.add(renderer);
			dirty = true;
		}
	}

	private void removeFrom(MeshRenderer renderer, ArrayList<MeshRenderer> list)
	{
		boolean changed = list.remove(renderer);
		if (changed)
		{
			dirty = true;
		}
	}
}
