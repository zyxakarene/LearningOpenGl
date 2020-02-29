package zyx.debug.views.network;

import zyx.debug.network.vo.network.NetworkCommandInfo;
import zyx.debug.views.network.tree.NetworkTree;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import zyx.debug.network.vo.network.NetworkInformation;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.debug.views.resources.SafeDefaultListModel;

public class NetworkPanel extends BaseDebugPanel implements INetworkListClicked
{

	private DebugNetworkType type;
	private HashMap<String, NetworkCommandInfo> networkData;
	private NetworkListAdaptor listAdaptor;
	private final JList<NetworkCommandInfo> list;
	private DefaultListModel<NetworkCommandInfo> listModel;
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
			changes = NetworkInformation.requestDataChanged;
		}
		else if (type == DebugNetworkType.RESPONSE)
		{
			changes = NetworkInformation.responseDataChanged;
		}

		if (changes)
		{
			ArrayList<NetworkCommandInfo> out = new ArrayList<>();
			if (type == DebugNetworkType.REQUEST)
			{
				out = NetworkInformation.requestCommandData;
			}
			else if (type == DebugNetworkType.RESPONSE)
			{
				out = NetworkInformation.responseCommandData;
			}

			
			for (int i = 0; i < out.size(); i++)
			{
				NetworkCommandInfo data = out.get(i);
				String name = data.command;

				if (networkData.containsKey(name) == false)
				{
					networkData.put(name, data);
					listModel.addElement(data);
				}
			}
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
		synchronized (list)
		{
			NetworkCommandInfo selectedValue = list.getSelectedValue();

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
