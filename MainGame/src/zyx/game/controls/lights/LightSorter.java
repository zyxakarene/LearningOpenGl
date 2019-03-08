package zyx.game.controls.lights;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.lighs.ILight;
import zyx.utils.FloatMath;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.pooling.GenericPool;
import zyx.utils.pooling.ObjectPool;
import zyx.utils.pooling.model.PoolableVector3f;

class LightSorter implements Comparator<ILight>
{

	private ObjectPool<Vector3f> posPool;
	private HashMap<ILight, Vector3f> posCache;

	private IPositionable viewer;
	private Vector3f viewerPos;

	public LightSorter()
	{
		posCache = new HashMap<>();
		viewerPos = new Vector3f();
		posPool = new GenericPool(PoolableVector3f.class, 10);
	}

	void setViewer(IPositionable viewer)
	{
		this.viewer = viewer;
	}

	public void reset()
	{
		Set<ILight> cacheKeys = posCache.keySet();
		Iterator<ILight> keyIterator = cacheKeys.iterator();

		Vector3f pos;
		ILight light;
		while (keyIterator.hasNext())
		{
			light = keyIterator.next();
			pos = posCache.get(light);

			posPool.releaseInstance(pos);
		}

		posCache.clear();

		viewer.getPosition(false, viewerPos);
	}

	@Override
	public int compare(ILight a, ILight b)
	{
		Vector3f posA;
		Vector3f posB;

		if (posCache.containsKey(a))
		{
			posA = posCache.get(a);
		}
		else
		{
			posA = posPool.getInstance();
			a.getLightPosition(posA);
		}

		if (posCache.containsKey(b))
		{
			posB = posCache.get(b);
		}
		else
		{
			posB = posPool.getInstance();
			b.getLightPosition(posB);
		}

		float distanceA = FloatMath.distance(posA, viewerPos);
		float distanceB = FloatMath.distance(posB, viewerPos);

		if (distanceA < distanceB)
		{
			return -1;
		}
		if (distanceA > distanceB)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
