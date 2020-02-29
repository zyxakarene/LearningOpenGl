package zyx.debug.views.pools;

import zyx.debug.network.vo.pools.PoolInfo;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import zyx.debug.network.vo.pools.PoolInformation;
import zyx.debug.views.base.BaseDebugPanel;

public class PoolsPanel extends BaseDebugPanel
{
	private JTable list;
	private PoolTableModel listModel;
	private JScrollPane listScrollPane;

	public PoolsPanel()
	{
		listScrollPane = new JScrollPane();
		add(listScrollPane);
		
		listModel = new PoolTableModel();

		list = new JTable(listModel);
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		listScrollPane.setViewportView(list);
	}

	@Override
	public void update()
	{
		if (PoolInformation.hasPoolChanges())
		{
			ArrayList<PoolInfo> poolData = PoolInformation.getPoolData();
			
			listModel.removeAllElements();

			for (PoolInfo poolInfo : poolData)
			{
				listModel.addElement(poolInfo);
			}
		}
	}

	@Override
	public String getTabName()
	{
		return "Pools";
	}
}
