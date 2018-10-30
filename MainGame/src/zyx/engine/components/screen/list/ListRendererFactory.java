package zyx.engine.components.screen.list;

class ListRendererFactory
{

	static <T> T createFrom(Class<T> type)
	{
		try
		{
			T renderer = type.newInstance();
			
			return renderer;
		}
		catch (ReflectiveOperationException ex)
		{
			String msg = String.format("Could not create new ItemRenderer from class %s", type);
			throw new RuntimeException(msg, ex);
		}
	}
}
