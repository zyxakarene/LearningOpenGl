package zyx.engine.components.screen.base.generic.window.tree;

enum TreeLineType
{
	LINE("tree_line"),
	CORNER("tree_corner"),
	JUNCTION("tree_junction");
		
	final String resourceID;

	private TreeLineType(String resourceID)
	{
		this.resourceID = resourceID;
	}
	
}
