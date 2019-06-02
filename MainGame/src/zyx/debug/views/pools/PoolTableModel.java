package zyx.debug.views.pools;

import javax.swing.table.DefaultTableModel;

public class PoolTableModel extends DefaultTableModel
{

	public PoolTableModel()
	{
		String[] names = new String[]
		{
			"Name", "Free", "Taken", "Total"
		};

		setColumnIdentifiers(names);
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		Object value = null;
		try
		{
			value = super.getValueAt(row, column);
		}
		catch (Exception e)
		{
			//Item is missing.. Somehow?
		}
		
		return value;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	public void addElement(PoolInfo info)
	{
		Object[] rowData = new Object[]
		{
			info.name, info.free, info.taken, info.total
		};
		
		if (info.free + info.taken != info.total)
		{
			rowData[3] = "!![" + info.total + "]";
		}
		
		addRow(rowData);
	}
	
	public void removeAllElements()
	{
		while (getRowCount() > 0)
		{
			removeRow(0);
		}
	}

}
