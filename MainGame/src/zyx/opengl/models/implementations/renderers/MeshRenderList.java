package zyx.opengl.models.implementations.renderers;

import java.util.ArrayList;
import java.util.Comparator;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.buffers.DeferredRenderer;
import zyx.opengl.camera.Camera;
import zyx.opengl.materials.RenderQueue;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IDrawable;

public class MeshRenderList implements IDrawable
{

	private static final MeshRenderList INSTANCE = new MeshRenderList();

	private static final Vector3f CAM_POSITION = new Vector3f();
	private static final Vector3f A_POSITION = new Vector3f();
	private static final Vector3f B_POSITION = new Vector3f();
	
	private ArrayList<MeshRenderer> geometryRenderers;
	private ArrayList<MeshRenderer> AlphaRenderers;
	private ArrayList<MeshRenderer> transparentRenderers;
	
	private Comparator<MeshRenderer> prioritySorting;
	private Comparator<MeshRenderer> distanceSorting;
	
	private boolean dirty;

	private MeshRenderList()
	{
		geometryRenderers = new ArrayList<>();
		AlphaRenderers = new ArrayList<>();
		transparentRenderers = new ArrayList<>();
		dirty = false;
		
		prioritySorting = this::sortByPriority;
		distanceSorting = this::sortByDistance;
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
			CheckList(geometryRenderers, RenderQueue.GEOMETRY);
			CheckList(AlphaRenderers, RenderQueue.ALPHA);
			CheckList(transparentRenderers, RenderQueue.TRANSPARENT);

			geometryRenderers.sort(prioritySorting);
			transparentRenderers.sort(prioritySorting);
			dirty = false;
		}

		DeferredRenderer.getInstance().bindBuffer();
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
					case GEOMETRY:
						geometryRenderers.add(renderer);
						break;
					case ALPHA:
						AlphaRenderers.add(renderer);
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
		Camera.getInstance().getPosition(false, CAM_POSITION);
		AlphaRenderers.sort(distanceSorting);
		
		draw(AlphaRenderers);
		draw(transparentRenderers);
	}

	void add(MeshRenderer renderer)
	{
		switch (renderer.drawMaterial.queue)
		{
			case GEOMETRY:
				addTo(renderer, geometryRenderers);
				break;
			case ALPHA:
				addTo(renderer, AlphaRenderers);
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
			case GEOMETRY:
				removeFrom(renderer, geometryRenderers);
				break;
			case ALPHA:
				removeFrom(renderer, AlphaRenderers);
				break;
			case TRANSPARENT:
				removeFrom(renderer, transparentRenderers);
				break;
		}
	}

	private int sortByDistance(MeshRenderer a, MeshRenderer b)
	{
		//Someone doesn't have a parent. So that should always be further back.. Probably
		if (a.parent == null || b.parent == null)
		{
			return Boolean.compare(a.parent != null, b.parent != null);
		}
		
		//Make sure we only compare equal priorities
		if (a.drawMaterial.priority != b.drawMaterial.priority)
		{
			return sortByPriority(a, b);
		}

		a.parent.getPosition(false, A_POSITION);
		b.parent.getPosition(false, B_POSITION);
		
		float distanceToA = FloatMath.distance(A_POSITION, CAM_POSITION);
		float distanceToB = FloatMath.distance(B_POSITION, CAM_POSITION);

		if (distanceToA < distanceToB)
		{
			return 1;
		}

		if (distanceToA > distanceToB)
		{
			return -1;
		}

		return 0;
	}
	
	private int sortByPriority(MeshRenderer a, MeshRenderer b)
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
