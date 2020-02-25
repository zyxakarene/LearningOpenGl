package zyx.debug.views.hierarchy;

import java.util.ArrayList;

public abstract class AbstractHierarchyData<D>
{
	protected D instance;
	protected String type;
	protected int childCount;
	
	private ArrayList<AbstractHierarchyData<D>> children;

	public AbstractHierarchyData(D instance)
	{
		this.instance = instance;
		this.type = instance.getClass().getSimpleName();
		this.children = new ArrayList<>();

		addChildrenTo(children);
		childCount = children.size();
	}
	
	protected abstract void addChildrenTo(ArrayList<AbstractHierarchyData<D>> children);

	public ArrayList<AbstractHierarchyData<D>> getChildren()
	{
		return children;
	}

	public D getInstance()
	{
		return instance;
	}
}
