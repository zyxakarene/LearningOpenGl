package zyx.debug.views.resources;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.engine.resources.impl.DebugResourceList;
import zyx.engine.resources.impl.Resource;
import zyx.utils.cheats.Print;

public class DebugResourcePanel extends BaseDebugPanel
{

	private ArrayList<Resource> out;

	private JList<Resource> list;
	private DefaultListModel<Resource> listModel;
	private ResourceRenderer cellRenderer;
	private JScrollPane listScrollPane;

	public DebugResourcePanel()
	{
		listScrollPane = new JScrollPane();
		add(listScrollPane);
		
		listModel = new DefaultListModel<>();
		cellRenderer = new ResourceRenderer();

		list = new JList<>();
		list.setModel(listModel);
		list.setCellRenderer(cellRenderer);
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		listScrollPane.setViewportView(list);

		out = new ArrayList<>();
	}

	@Override
	public void update()
	{
		boolean changes = DebugResourceList.getActiveResources(out);

		if (changes)
		{
			int oldIndex = list.getSelectedIndex();
			oldIndex = oldIndex < 0 ? 0 : oldIndex;
			
			listModel.removeAllElements();

			for (Resource resource : out)
			{
				listModel.addElement(resource);
			}
			
			if (oldIndex >= out.size())
			{
				list.setSelectedIndex(out.size() - 1);
			}
			else
			{
				list.setSelectedIndex(oldIndex);
			}
		}
	}

	@Override
	public String getTabName()
	{
		return "Resources";
	}

}
