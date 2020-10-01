package zyx.opengl.models.implementations.renderers;

import java.util.ArrayList;
import java.util.Comparator;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.materials.MaterialPriority;
import zyx.utils.interfaces.IDrawable;

public class MeshRenderList implements IDrawable, Comparator<MeshRenderer>
{

	private static final MeshRenderList INSTANCE = new MeshRenderList();

	private ArrayList<MeshRenderer> geometryRenderers;
	private ArrayList<MeshRenderer> transparentRenderers;
	private boolean dirty;

	private MeshRenderList()
	{
		geometryRenderers = new ArrayList<>();
		transparentRenderers = new ArrayList<>();
		dirty = false;
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
			for (int i = geometryRenderers.size() - 1; i >= 0; i--)
			{
				MeshRenderer renderer = geometryRenderers.get(i);
				if (renderer.drawMaterial.priority > MaterialPriority.GEOMETRY_MAX)
				{
					transparentRenderers.add(renderer);
					geometryRenderers.remove(i);
				}
			}

			for (int i = transparentRenderers.size() - 1; i >= 0; i--)
			{
				MeshRenderer renderer = transparentRenderers.get(i);
				if (renderer.drawMaterial.priority <= MaterialPriority.GEOMETRY_MAX)
				{
					geometryRenderers.add(renderer);
					transparentRenderers.remove(i);
				}
			}

			geometryRenderers.sort(this);
			transparentRenderers.sort(this);
			dirty = false;
		}

		DeferredRenderer.getInstance().bindBuffer();
		draw(geometryRenderers);
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
		BufferBinder.bindBuffer(Buffer.DEFAULT);
		draw(transparentRenderers);
	}

	void add(MeshRenderer renderer)
	{
		if (renderer.drawMaterial.priority > MaterialPriority.GEOMETRY_MAX)
		{
			addTo(renderer, transparentRenderers);
		}
		else
		{
			addTo(renderer, geometryRenderers);
		}
	}

	void remove(MeshRenderer renderer)
	{
		if (renderer.drawMaterial.priority > MaterialPriority.GEOMETRY_MAX)
		{
			removeFrom(renderer, transparentRenderers);
		}
		else
		{
			removeFrom(renderer, geometryRenderers);
		}
	}

	@Override
	public int compare(MeshRenderer a, MeshRenderer b)
	{
		int x = a.drawMaterial.priority;
		int y = b.drawMaterial.priority;

		if (x < y)
		{
			return -1;
		}

		if (x > y)
		{
			return 1;
		}

		return 0;
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
