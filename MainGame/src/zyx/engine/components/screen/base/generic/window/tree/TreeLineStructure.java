package zyx.engine.components.screen.base.generic.window.tree;

import java.util.HashMap;
import java.util.LinkedList;
import zyx.game.components.MeshObject;
import zyx.game.components.SimpleMesh;
import zyx.utils.cheats.Print;

class TreeLineStructure
{
	
	private static final LinkedList<WindowsTreeNode> OUT_LIST = new LinkedList<>();
	private static final LinkedList<WindowsTreeNode> HELPER_LIST = new LinkedList<>();
	
	private HashMap<WindowsTreeNode, TreeLineType[]> lineData;

	public TreeLineStructure()
	{
		lineData = new HashMap<>();
	}
	
	void buildLines(WindowsTreeNode root)
	{
		lineData.clear();
		HELPER_LIST.add(root);
		
		TreeLineType[] prevLine = null;
		
		while (HELPER_LIST.isEmpty() == false)
		{
			WindowsTreeNode node = HELPER_LIST.removeFirst();
			WindowsTreeNode parent = node.parent;
			if (node.isOpened && node.isLeaf == false)
			{
				node.getChildren(OUT_LIST, false);
				HELPER_LIST.addAll(0, OUT_LIST);
				
				OUT_LIST.clear();
			}
			
			if (node.data instanceof SimpleMesh || node.data instanceof MeshObject)
			{
				Print.out("!");
			}
			
			int len = node.level;
			TreeLineType[] data = new TreeLineType[len];
			lineData.put(node, data);
			
			for (int i = 0; i < len; i++)
			{
				if (i == len - 1)
				{
					//Last line segment
					
					if (parent == null)
					{
						//Special root logic
						data[i] = TreeLineType.CORNER;
					}
					else
					{
						boolean lastChild = parent.isLastChild(node);
						data[i] = lastChild ? TreeLineType.CORNER : TreeLineType.JUNCTION;
					}
				}
				else
				{
					//Leftmost segments
					
					TreeLineType prevType = prevLine[i];
					if (prevType == TreeLineType.JUNCTION || prevType == TreeLineType.LINE)
					{
						data[i] = TreeLineType.LINE;
					}
					else
					{
						data[i] = TreeLineType.NOTHING;
					}
				}
			}
			
			prevLine = data;
		}
		
		HELPER_LIST.clear();
	}

	TreeLineRowStructure getLineDataFor(WindowsTreeNode node)
	{
		TreeLineType[] rowTypes = lineData.get(node);
		return TreeLineRowStructure.from(rowTypes);
	}

}
