package zyx.debug.views.resources;

import javax.swing.DefaultListModel;

public class SafeDefaultListModel<E> extends DefaultListModel<E>
{
	@Override
	public E getElementAt(int index)
	{
		E element = null;
		try
		{
			element = super.getElementAt(index);
		}
		catch (Exception e)
		{
			//Item is missing.. Somehow?
		}
		
		return element;
    }
}
