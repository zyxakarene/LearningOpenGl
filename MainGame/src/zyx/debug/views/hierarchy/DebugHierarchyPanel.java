package zyx.debug.views.hierarchy;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import org.lwjgl.util.vector.Vector3f;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.engine.components.world.DebugWorldObjectList;
import zyx.engine.components.world.WorldObject;
import zyx.engine.components.world.WorldObjectNode;
import zyx.opengl.camera.Camera;

public class DebugHierarchyPanel extends BaseDebugPanel
{

	private final ArrayList<WorldObject> OUT = new ArrayList<>();

	private JScrollPane listScrollPane;

	private JTree tree;
	private HierarchyTreeModel model;

	public DebugHierarchyPanel()
	{
		listScrollPane = new JScrollPane();
		add(listScrollPane);

		model = new HierarchyTreeModel();
		tree = new JTree(model);
		tree.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				HierarchyNode node = (HierarchyNode) tree.getSelectionModel().getSelectionPath().getLastPathComponent();

				Vector3f worldPos = node.data.instance.getPosition(false, null);
				Vector3f cameraDir = Camera.getInstance().getDir(false, null);
				
				Vector3f camPos = new Vector3f();
				camPos.x = worldPos.x + (cameraDir.x * 100);
				camPos.y = worldPos.y + (cameraDir.y * 100);
				camPos.z = worldPos.z + (cameraDir.z * 100);
				
				Camera.getInstance().setPosition(false, camPos);
			}
			
		});

		listScrollPane.setViewportView(tree);
	}

	@Override
	public void update()
	{
		boolean changes = DebugWorldObjectList.hasUpdate();

		if (changes)
		{
			WorldObjectNode node = DebugWorldObjectList.getActiveWorldObjects();
			model.reset(node);
		}

		repaint();
	}

	@Override
	public String getTabName()
	{
		return "Hierarchy";
	}
}
