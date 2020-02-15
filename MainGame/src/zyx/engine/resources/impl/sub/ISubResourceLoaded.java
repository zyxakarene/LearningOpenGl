package zyx.engine.resources.impl.sub;

import java.util.ArrayList;

public interface ISubResourceLoaded<T>
{
	public void onLoaded(ArrayList<T> data);
}
