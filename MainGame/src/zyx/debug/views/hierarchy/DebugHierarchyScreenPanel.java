package zyx.debug.views.hierarchy;

import zyx.debug.link.DebugDisplayObjectLink;
import zyx.debug.link.DebugInfo;
import zyx.engine.components.screen.base.DisplayObject;

public class DebugHierarchyScreenPanel extends DebugHierarchyPanel<DisplayObject>
{
	private DebugDisplayObjectLink screenLink;
	
	public DebugHierarchyScreenPanel()
	{
		screenLink = DebugInfo.screenObjects;
	}

	@Override
	protected AbstractHierarchyData<DisplayObject> getUpdatedNode()
	{
		boolean changes = screenLink.hasUpdate();

		if (changes)
		{
			return screenLink.getActiveDisplayObjects();
		}

		return null;
	}

	@Override
	public String getTabName()
	{
		return "Screen Hierarchy";
	}

}
