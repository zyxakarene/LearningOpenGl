package zyx.debug.views.resources;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.debug.views.resources.watcher.WatcherManager;
import zyx.engine.resources.impl.DebugResourceList;
import zyx.engine.resources.impl.Resource;

public class DebugResourcePanel extends BaseDebugPanel
{

	private ArrayList<Resource> out;

	private JList<Resource> list;
	private DefaultListModel<Resource> listModel;
	private ResourceRenderer cellRenderer;
	private JScrollPane listScrollPane;

	private WatcherManager watcher;

	public DebugResourcePanel()
	{
		listScrollPane = new JScrollPane();
		add(listScrollPane);

		listModel = new SafeDefaultListModel<>();
		cellRenderer = new ResourceRenderer();

		list = new JList<>();
		list.setModel(listModel);
		list.setCellRenderer(cellRenderer);
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		listScrollPane.setViewportView(list);

		out = new ArrayList<>();
		
		watcher = new WatcherManager();
		watcher.initialize();
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

			watcher.setActiveResources(out);
			
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

		repaint();
		
		watcher.checkChanged();
	}

	@Override
	public String getTabName()
	{
		return "Resources";
	}

}
