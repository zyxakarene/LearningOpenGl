package zyx.debug.views.hierarchy;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import zyx.debug.views.base.BaseDebugPanel;

abstract class DebugHierarchyPanel<I> extends BaseDebugPanel
{

	private JScrollPane listScrollPane;
	private HierarchyTreeModel<I> model;

	protected JTree tree;

	DebugHierarchyPanel()
	{
		listScrollPane = new JScrollPane();
		add(listScrollPane);

		model = new HierarchyTreeModel<>();
		tree = new JTree(model);

		listScrollPane.setViewportView(tree);
	}

	protected AbstractHierarchyData<I> getSelectedData()
	{
		HierarchyNode node = (HierarchyNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();
		
		return node.data;
	}

	@Override
	public void update()
	{
		AbstractHierarchyData<I> node = getUpdatedNode();
		if (node != null)
		{
			model.reset(node);
		}

		repaint();
	}

	protected abstract AbstractHierarchyData<I> getUpdatedNode();
}
