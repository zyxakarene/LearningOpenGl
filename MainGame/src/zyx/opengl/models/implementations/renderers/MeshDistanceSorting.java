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
		//Make sure we only compare equal priorities
		if (a.drawMaterial.priority != b.drawMaterial.priority)
		{
			return sortByPriority(a, b);
		}

		float distanceToA;
		float distanceToB;
		
		if (a.parent == null)
		{
			//No parent? It is probably very far away!
			distanceToA = Float.MAX_VALUE;
		}
		else
		{
			a.parent.getPosition(false, A_POSITION);
			distanceToA = FloatMath.distance(A_POSITION, CAM_POSITION);
		}
		
		if (b.parent == null)
		{
			//No parent? It is probably very far away!
			distanceToB = Float.MAX_VALUE;
		}
		else
		{
			b.parent.getPosition(false, B_POSITION);
			distanceToB = FloatMath.distance(B_POSITION, CAM_POSITION);
		}
		

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
}
