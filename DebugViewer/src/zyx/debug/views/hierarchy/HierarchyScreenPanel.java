package zyx.debug.views.hierarchy;

import zyx.debug.network.vo.hierarchy.HierarchyInformation;
import zyx.debug.network.vo.hierarchy.HierarchyInfo;

public class HierarchyScreenPanel extends HierarchyPanel
{

	@Override
	protected HierarchyInfo getUpdatedNode()
	{
		if (HierarchyInformation.hasScreenChanges)
		{
			return HierarchyInformation.currentScreenHierarchy;
		}

		return null;
	}

	@Override
	public String getTabName()
	{
		return "Screen Hierarchy";
	}

}
