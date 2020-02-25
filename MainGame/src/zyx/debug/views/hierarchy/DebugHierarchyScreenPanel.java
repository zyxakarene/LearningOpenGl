package zyx.debug.views.hierarchy;

import zyx.engine.components.screen.base.DebugDisplayObjectList;
import zyx.engine.components.screen.base.DisplayObject;

public class DebugHierarchyScreenPanel extends DebugHierarchyPanel<DisplayObject>
{

	@Override
	protected AbstractHierarchyData<DisplayObject> getUpdatedNode()
	{
		boolean changes = DebugDisplayObjectList.hasUpdate();

		if (changes)
		{
			return DebugDisplayObjectList.getActiveDisplayObjects();
		}

		return null;
	}

	@Override
	public String getTabName()
	{
		return "Screen Hierarchy";
	}

}
