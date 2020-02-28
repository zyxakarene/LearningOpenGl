package zyx.debug.views.network.tree;

class NetworkTreeItemWrapper
{

	private final Object item;
	private final String text;

	NetworkTreeItemWrapper(Object item, Object treeName)
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
