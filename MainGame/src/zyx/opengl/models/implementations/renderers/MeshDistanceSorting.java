package zyx.opengl.models.implementations.renderers;

import java.util.ArrayList;
import java.util.Comparator;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.camera.Camera;
import zyx.utils.FloatMath;

class MeshDistanceSorting implements Comparator<MeshRenderer>
{
	private static final Vector3f CAM_POSITION = new Vector3f();
	private static final Vector3f A_POSITION = new Vector3f();
	private static final Vector3f B_POSITION = new Vector3f();

	private boolean reversed;

	MeshDistanceSorting(boolean reversed)
	{
		this.reversed = reversed;
	}
	
	void sortMe(ArrayList<MeshRenderer> list)
	{
		Camera.getInstance().getPosition(false, CAM_POSITION);
		list.sort(this);
	}
	
	@Override
	public int compare(MeshRenderer a, MeshRenderer b)
	{
		//Someone doesn't have a parent. So that should always be further back.. Probably
		if (a.parent == null || b.parent == null)
		{
			return Boolean.compare(a.parent != null, b.parent != null);
		}
		
		//Make sure we only compare equal priorities
		if (a.drawMaterials[0].priority != b.drawMaterials[0].priority) //TODO: Sorting
		{
			return sortByPriority(a, b);
		}

		a.parent.getPosition(false, A_POSITION);
		b.parent.getPosition(false, B_POSITION);
		
		float distanceToA = FloatMath.distance(A_POSITION, CAM_POSITION);
		float distanceToB = FloatMath.distance(B_POSITION, CAM_POSITION);

		if (distanceToA < distanceToB)
		{
			return reversed ? -1 : 1;
		}

		if (distanceToA > distanceToB)
		{
			return reversed ? 1 : -1;
		}

		return 0;
	}
	
	private int sortByPriority(MeshRenderer a, MeshRenderer b)
	{
		int x = a.drawMaterials[0].priority;
		int y = b.drawMaterials[0].priority;

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
}
