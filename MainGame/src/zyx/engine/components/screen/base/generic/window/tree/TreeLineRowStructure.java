package zyx.engine.components.screen.base.generic.window.tree;

class TreeLineRowStructure
{
	private static final TreeLineRowStructure INSTANCE = new TreeLineRowStructure();
	
	private static final int MAX_INDENTATION = 20;
	
	int length;
	final TreeLineType[] types;
	final int[] offsets;

	private TreeLineRowStructure()
	{
		types = new TreeLineType[MAX_INDENTATION];
		offsets = new int[MAX_INDENTATION];
	}
	
	private void setup(TreeLineType[] rowTypes)
	{
		int len = rowTypes.length;
		if (len > MAX_INDENTATION)
		{
			len = MAX_INDENTATION;
		}
		
		int index = 0;
		for (int i = 0; i < len; i++)
		{
			TreeLineType type = rowTypes[i];
			if (type != TreeLineType.NOTHING)
			{
				types[index] = type;
				offsets[index] = i;
				index++;
			}
		}
		
		length = index;
	}
	
	static TreeLineRowStructure from(TreeLineType[] rowTypes)
	{
		INSTANCE.setup(rowTypes);
		return INSTANCE;
	}
}
