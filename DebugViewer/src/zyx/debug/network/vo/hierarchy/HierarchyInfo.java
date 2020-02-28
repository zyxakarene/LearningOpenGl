package zyx.debug.network.vo.hierarchy;

import java.util.ArrayList;

public class HierarchyInfo
{

	public String title;
	public String icon;
	
	public ArrayList<HierarchyInfo> children;

	public HierarchyInfo(String title, String icon)
	{
		this.title = title;
		this.icon = icon;
		this.children = new ArrayList<>();
	}

	@Override
	public String toString()
	{
		return title;
	}
}
