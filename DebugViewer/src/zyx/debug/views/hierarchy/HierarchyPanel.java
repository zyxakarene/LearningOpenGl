package zyx.debug.views.hierarchy;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import zyx.debug.network.vo.hierarchy.HierarchyInfo;
import zyx.debug.views.base.BaseDebugPanel;

abstract class HierarchyPanel extends BaseDebugPanel
{

	private JScrollPane listScrollPane;
	private HierarchyTreeModel model;

	protected JTree tree;

	HierarchyPanel()
	{
		listScrollPane = new JScrollPane();
		add(listScrollPane);

		model = new HierarchyTreeModel();
		tree = new JTree(model);
		tree.setCellRenderer(new HierarchyRenderer());

		listScrollPane.setViewportView(tree);
	}

	protected HierarchyInfo getSelectedData()
	{
		HierarchyNode node = (HierarchyNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
		
		return node.data;
	}

	@Override
	public void update()
	{
		HierarchyInfo node = getUpdatedNode();
		if (node != null)
		{
			model.reset(node);
		}

		repaint();
	}

	protected abstract HierarchyInfo getUpdatedNode();
}
