package zyx.debug.views.resources;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import zyx.debug.network.vo.resources.ResourceInfo;
import zyx.debug.network.vo.resources.ResourceInformation;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.debug.views.resources.watcher.WatcherManager;

public class DebugResourcePanel extends BaseDebugPanel
{

	private ArrayList<ResourceInfo> out;

	private JList<ResourceInfo> list;
	private DefaultListModel<ResourceInfo> listModel;
	private ResourceRenderer cellRenderer;
	private JScrollPane listScrollPane;

	private WatcherManager watcher;

	private int watchFrameCounter;

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
	}

	@Override
	public void update()
	{
		if (ResourceInformation.hasResourceChanges)
		{
			if (watcher == null)
			{
				watcher = new WatcherManager();
				watcher.initialize();
			}

			out.clear();
			out.addAll(ResourceInformation.resourceInfo);

			int oldIndex = list.getSelectedIndex();
			oldIndex = oldIndex < 0 ? 0 : oldIndex;

			listModel.removeAllElements();

			watcher.setActiveResources(out);

			for (ResourceInfo resource : out)
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

		watchFrameCounter++;
		if (watchFrameCounter >= 25)
		{
			watcher.checkChanged();
			watchFrameCounter = 0;
		}
	}

	@Override
	public String getTabName()
	{
		return "Resources";
	}

}
