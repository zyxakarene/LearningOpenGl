package zyx.debug.views.hierarchy;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.DebugWorldObjectList;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.camera.Camera;

public class DebugHierarchyWorldPanel extends DebugHierarchyPanel<WorldObject>
{

	public DebugHierarchyWorldPanel()
	{
		tree.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				AbstractHierarchyData<WorldObject> data = getSelectedData();

				Vector3f worldPos = data.getInstance().getPosition(false, null);
				Vector3f cameraDir = Camera.getInstance().getDir(false, null);

				Vector3f camPos = new Vector3f();
				camPos.x = worldPos.x + (cameraDir.x * 100);
				camPos.y = worldPos.y + (cameraDir.y * 100);
				camPos.z = worldPos.z + (cameraDir.z * 100);

				Camera.getInstance().setPosition(false, camPos);
			}

		});
	}

	@Override
	protected AbstractHierarchyData<WorldObject> getUpdatedNode()
	{
		boolean changes = DebugWorldObjectList.hasUpdate();

		if (changes)
		{
			return DebugWorldObjectList.getActiveWorldObjects();
		}

		return null;
	}

	@Override
	public String getTabName()
	{
		return "World Hierarchy";
	}

}
