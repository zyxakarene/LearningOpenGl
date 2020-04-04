package zyx.debug.views.hierarchy;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.lwjgl.util.vector.Vector3f;
import zyx.debug.link.DebugInfo;
import zyx.debug.link.DebugWorldObjectLink;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.camera.Camera;

public class DebugHierarchyWorldPanel extends DebugHierarchyPanel<WorldObject>
{

	private DebugWorldObjectLink worldObject;

	public DebugHierarchyWorldPanel()
	{
		worldObject = DebugInfo.worldObjects;

		tree.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				onKeyPressed(e);
			}
		});
	}

	private void onKeyPressed(KeyEvent e)
	{
		if (e.getKeyChar() == KeyEvent.VK_SPACE)
		{
			AbstractHierarchyData<WorldObject> data = getSelectedData();

			Vector3f targetPos = data.getInstance().getPosition(false, null);
			Camera.getInstance().lookAt(targetPos);
		}
	}

	@Override
	protected AbstractHierarchyData<WorldObject> getUpdatedNode()
	{
		boolean changes = worldObject.hasUpdate();

		if (changes)
		{
			return worldObject.getActiveWorldObjects();
		}

		return null;
	}

	@Override
	public String getTabName()
	{
		return "World Hierarchy";
	}

}
