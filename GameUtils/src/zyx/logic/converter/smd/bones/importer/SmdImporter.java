package zyx.logic.converter.smd.bones.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import zyx.logic.converter.smd.bones.INode;
import zyx.logic.converter.smd.bones.Skeleton;
import zyx.logic.converter.smd.bones.SmdValueObject;
import zyx.logic.converter.smd.bones.Triangle;

public class SmdImporter
{

	private static final int TOKEN_NONE = 0;
	private static final int TOKEN_NODES = 1;
	private static final int TOKEN_SKELETON = 2;
	private static final int TOKEN_TRIANGLES = 3;

	private int currentToken;
	private ISmdHandler lineHandler;
	private SmdBuilder builder;

	public SmdImporter()
	{
		currentToken = TOKEN_NONE;
		builder = new SmdBuilder();
	}

	public SmdValueObject importModel(File file) throws FileNotFoundException
	{
		Scanner scan = new Scanner(file);

		String line;
		while (scan.hasNextLine())
		{
			line = scan.nextLine();

			if (isEnd(line))
			{
				currentToken = TOKEN_NONE;
			}

			switch (currentToken)
			{
				case (TOKEN_NONE):
				{
					findNextToken(line);
					break;
				}
				case (TOKEN_NODES):
				case (TOKEN_SKELETON):
				case (TOKEN_TRIANGLES):
				{
					lineHandler.handleLine(line);
					break;
				}
			}
		}
		
		scan.close();

		return builder.build();
	}

	private void findNextToken(String line)
	{
		switch (line)
		{
			case ("nodes"):
			{
				currentToken = TOKEN_NODES;
				lineHandler = new SmdNodeHandler();
				break;
			}
			case ("skeleton"):
			{
				currentToken = TOKEN_SKELETON;
				lineHandler = new SmdSkeletonHandler();
				break;
			}
			case ("triangles"):
			{
				currentToken = TOKEN_TRIANGLES;
				lineHandler = new SmdTriangleHandler();
				break;
			}
		}
	}

	private boolean isEnd(String line)
	{
		boolean isEnd = line.equals("end");
		if (isEnd)
		{
			switch (currentToken)
			{
				case (TOKEN_NODES):
				{
					INode[] nodes = (INode[]) lineHandler.getResult();
					builder.addNodes(nodes);
					break;
				}
				case (TOKEN_SKELETON):
				{
					Skeleton skeleton = (Skeleton) lineHandler.getResult();
					builder.addSkeleton(skeleton);
					break;
				}
				case (TOKEN_TRIANGLES):
				{
					Triangle[] triangles = (Triangle[]) lineHandler.getResult();
					builder.addTriangles(triangles);
					break;
				}
			}
		}

		return isEnd;
	}
}
