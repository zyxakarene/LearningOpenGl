package zyx.debug.views.network.tree;

class TreeItemWrapper
{

	private final Object item;
	private final String text;

	TreeItemWrapper(Object item, Object treeName)
	{
		this.item = item;
		this.text = treeName.toString();
	}

	Class getWrappedClass()
	{
		return item.getClass();
	}
	
	@Override
	public String toString()
	{
		return text;
	}
}
