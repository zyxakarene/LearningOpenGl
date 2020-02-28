package zyx.debug.views.network;

import zyx.debug.views.network.tree.NetworkTree;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.debug.views.resources.SafeDefaultListModel;
import zyx.net.core.DebugNetworkList;
import zyx.net.data.ReadableDataObject;

public class NetworkPanel extends BaseDebugPanel implements INetworkListClicked
{
	private final ArrayList<ReadableDataObject> OUT = new ArrayList<>();
	private final ArrayList<String> OUT_NAMES = new ArrayList<>();

	private DebugNetworkType type;
	private HashMap<String, NetworkInfo> networkData;
	private NetworkListAdaptor listAdaptor;
	private final JList<NetworkInfo> list;
	private DefaultListModel<NetworkInfo> listModel;
	private JScrollPane listScrollPane;

	public NetworkPanel(DebugNetworkType type)
	{
		this.type = type;
		
		listScrollPane = new JScrollPane();
		add(listScrollPane);
		
		listModel = new SafeDefaultListModel<>();
		networkData = new HashMap<>();

		listAdaptor = new NetworkListAdaptor(this);
		list = new JList<>();
		list.setModel(listModel);
		list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(listAdaptor);
		
		listScrollPane.setViewportView(list);
	}

	@Override
	public void update()
	{
		boolean changes = false;
		
		if (type == DebugNetworkType.REQUEST)
		{
			changes = DebugNetworkList.getNewestRequests(OUT, OUT_NAMES);
		}
		else if (type == DebugNetworkType.RESPONSE)
		{
			changes = DebugNetworkList.getNewestResponses(OUT, OUT_NAMES);
		}

		if (changes)
		{
			synchronized(list)
			{
				for (int i = 0; i < OUT.size(); i++)
				{
					ReadableDataObject data = OUT.get(i);
					String name = OUT_NAMES.get(i);

					NetworkInfo info = networkData.get(name);
					if (info == null)
					{
						info = new NetworkInfo(name);
						networkData.put(name, info);

						listModel.addElement(info);
					}

					info.addData(data);
				}
			}

			OUT.clear();
			OUT_NAMES.clear();
		}
		
		repaint();
	}

	@Override
	public String getTabName()
	{
		return "Network " + type.name;
	}

	@Override
	public void onListClicked()
	{
		synchronized(list)
		{
			NetworkInfo selectedValue = list.getSelectedValue();
			
			if (selectedValue != null)
			{
				JFrame frame = new JFrame("Network View");

				JScrollPane treePane = new JScrollPane();
				frame.add(treePane);

				treePane.setViewportView(new NetworkTree(selectedValue));
				frame.setSize(getSize());
				frame.setLocation(getLocation());

				frame.setVisible(true);
			}
		}
	}
}
