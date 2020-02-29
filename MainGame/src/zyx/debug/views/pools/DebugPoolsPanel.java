package zyx.debug.views.pools;

import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import zyx.debug.link.DebugInfo;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.debug.link.DebugPoolLink;

public class DebugPoolsPanel extends BaseDebugPanel
{
	private ArrayList<PoolInfo> out;

	private JTable list;
	private PoolTableModel listModel;
	private JScrollPane listScrollPane;
	private DebugPoolLink poolLink;

	public DebugPoolsPanel()
	{
		listScrollPane = new JScrollPane();
		add(listScrollPane);
		
		listModel = new PoolTableModel();

		list = new JTable(listModel);
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		listScrollPane.setViewportView(list);
		
		out = new ArrayList<>();
		poolLink = DebugInfo.pools;
	}

	@Override
	public void update()
	{
		boolean changes = poolLink.hasChanges();

		if (changes)
		{
			poolLink.getPoolStatus(out);
			
			listModel.removeAllElements();

			for (PoolInfo poolInfo : out)
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
