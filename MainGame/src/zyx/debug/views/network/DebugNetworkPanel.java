package zyx.debug.views.network;

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

public class DebugNetworkPanel extends BaseDebugPanel implements INetworkListClicked
{
	public static int REQUESTS = 0;
	public static int RESPONSES = 1;
	
	private final ArrayList<ReadableDataObject> OUT = new ArrayList<>();
	private final ArrayList<String> OUT_NAMES = new ArrayList<>();

	private int type;
	private String typeName;
	private HashMap<String, NetworkInfo> networkData;
	private NetworkDebugListAdaptor listAdaptor;
	private final JList<NetworkInfo> list;
	private DefaultListModel<NetworkInfo> listModel;
	private JScrollPane listScrollPane;

	public DebugNetworkPanel(int type)
	{
		this.type = type;
		this.typeName = (type == REQUESTS) ? "Requests" : "Responses";
		
		listScrollPane = new JScrollPane();
		add(listScrollPane);
		
		listModel = new SafeDefaultListModel<>();
		networkData = new HashMap<>();

		listAdaptor = new NetworkDebugListAdaptor(this);
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
		
		if (type == REQUESTS)
		{
			changes = DebugNetworkList.getNewestRequests(OUT, OUT_NAMES);
		}
		else if (type == RESPONSES)
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
		return "Network " + typeName;
	}

	@Override
	public void onListClicked()
	{
		synchronized(list)
		{
			NetworkInfo selectedValue = list.getSelectedValue();
			System.out.println(selectedValue);
			
			JFrame frame = new JFrame("Network View");
			
			JScrollPane treePane = new JScrollPane();
			frame.add(treePane);
			
			treePane.setViewportView(new DebugNetworkTree(selectedValue));
			frame.setSize(getSize());
			frame.setLocation(getLocation());
			
			frame.setVisible(true);
		}
	}

}
