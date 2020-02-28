package zyx.debug.views.hierarchy;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import zyx.debug.network.vo.hierarchy.HierarchyInformation;
import zyx.debug.network.vo.hierarchy.HierarchyInfo;

public class HierarchyWorldPanel extends HierarchyPanel
{

	public HierarchyWorldPanel()
	{
		tree.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
//				AbstractHierarchyData<WorldObject> data = getSelectedData();
//
//				Vector3f worldPos = data.getInstance().getPosition(false, null);
//				Vector3f cameraDir = Camera.getInstance().getDir(false, null);
//
//				Vector3f camPos = new Vector3f();
//				camPos.x = worldPos.x + (cameraDir.x * 100);
//				camPos.y = worldPos.y + (cameraDir.y * 100);
//				camPos.z = worldPos.z + (cameraDir.z * 100);
//
//				Camera.getInstance().setPosition(false, camPos);
			}

		});
	}

	@Override
	protected HierarchyInfo getUpdatedNode()
	{
		if (HierarchyInformation.hasWorldChanges)
		{
			return HierarchyInformation.currentWorldHierarchy;
		}

		return null;
	}

	@Override
	public String getTabName()
	{
		return "World Hierarchy";
	}

}
