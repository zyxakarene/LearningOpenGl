package zyx.dev.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import zyx.dev.vo.Animation;
import zyx.dev.vo.Bone;
import zyx.dev.vo.SmdObject;

public class SmdImporter
{

	private static final int TOKEN_NONE = 0;
	private static final int TOKEN_NODES = 1;
	private static final int TOKEN_SKELETON = 2;
	private static final int TOKEN_TRIANGLES = 3;

	private int currentToken;
	private ISmdHandler lineHandler;
	private SmdObject smd;

	private boolean handlingRef;
	private String fileName;

	public SmdImporter()
	{
		currentToken = TOKEN_NONE;
		smd = new SmdObject();
		handlingRef = false;
	}

	private void importFile(File file) throws FileNotFoundException
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
	}

	public void importModel(File file) throws FileNotFoundException
	{
		currentToken = TOKEN_NONE;
		lineHandler = null;
		handlingRef = true;
		fileName = null;

		importFile(file);
	}

	public void importAnimations(File[] animations) throws FileNotFoundException
	{
		currentToken = TOKEN_NONE;
		lineHandler = null;
		handlingRef = false;

		for (File animation : animations)
		{
			fileName = animation.getName().split("_")[1];
			fileName = fileName.replace(".smd", "");
			
			importFile(animation);
		}
	}

	public SmdObject getSmd()
	{
		return smd;
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
				if (handlingRef)
				{
					lineHandler = new SmdBoneHandler();
					lineHandler.setData(smd.getRootBone());
				}
				else
				{
					lineHandler = new SmdAnimationHandler(fileName);
					lineHandler.setData(smd.getRootBone());
				}

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
					if (handlingRef)
					{
						Bone rootBone = (Bone) lineHandler.getResult();
						smd.setRootBone(rootBone);
					}
					break;
				}
				case (TOKEN_SKELETON):
				{
					if (handlingRef == false)
					{
						Animation animation = (Animation) lineHandler.getResult();
						smd.addAnimation(animation);
					}
					break;
				}
				case (TOKEN_TRIANGLES):
				{
					if (handlingRef)
					{
						SmdTriangleHandler.Response response = (SmdTriangleHandler.Response) lineHandler.getResult();
						smd.setTriangleData(response);
					}
					break;
				}
			}
		}

		return isEnd;
	}
}
