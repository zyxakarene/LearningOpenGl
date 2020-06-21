package zyx.logic.converter.smd.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import zyx.logic.converter.smd.control.QcAnimation;
import zyx.logic.converter.smd.vo.Animation;
import zyx.logic.converter.smd.vo.Bone;
import zyx.logic.converter.smd.vo.PhysBox;
import zyx.logic.converter.smd.vo.SmdObject;

public class SmdImporter
{

	private static final int FILE_TYPE_REF = 0;
	private static final int FILE_TYPE_BONE_CHECK = 1;
	private static final int FILE_TYPE_ANIMATION = 2;
	private static final int FILE_TYPE_PHYS = 3;
	private static final int FILE_TYPE_BOUNDING = 4;

	private static final int TOKEN_NONE = 0;
	private static final int TOKEN_NODES = 1;
	private static final int TOKEN_SKELETON = 2;
	private static final int TOKEN_TRIANGLES = 3;

	private int currentToken;
	private int currentFileType;
	private ISmdHandler lineHandler;
	private SmdObject smd;

	private String animationName;
	private boolean animationLooping;
	private Bone animationRootBone;
	private int animationStart;
	private int animationEnd;

	public SmdImporter()
	{
		currentToken = TOKEN_NONE;
		smd = new SmdObject();
		currentFileType = -1;
	}

	private void importFile(File file) throws FileNotFoundException
	{
		if (file == null || file.exists() == false)
		{
			return;
		}

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
		currentFileType = FILE_TYPE_BONE_CHECK;
		currentToken = TOKEN_NONE;
		lineHandler = null;
		animationName = null;
		animationRootBone = null;

		importFile(file);

		currentFileType = FILE_TYPE_REF;
		currentToken = TOKEN_NONE;
		lineHandler = null;
		animationName = null;
		animationRootBone = null;

		importFile(file);
	}

	public void importPhys(File file) throws FileNotFoundException
	{
		currentFileType = FILE_TYPE_PHYS;
		currentToken = TOKEN_NONE;
		lineHandler = null;
		animationName = null;
		animationRootBone = null;

		importFile(file);
	}

	public void importBounding(File file) throws FileNotFoundException
	{
		currentFileType = FILE_TYPE_BOUNDING;
		currentToken = TOKEN_NONE;
		lineHandler = null;
		animationName = null;
		animationRootBone = null;

		importFile(file);
	}

	public void importAnimations(QcAnimation[] animations) throws FileNotFoundException
	{
		currentFileType = FILE_TYPE_ANIMATION;
		currentToken = TOKEN_NONE;
		lineHandler = null;

		for (QcAnimation animation : animations)
		{
			animationRootBone = null;
			animationName = animation.name;
			animationLooping = animation.looping;
			animationStart = animation.start;
			animationEnd = animation.end;

			importFile(animation.file);
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
				if (currentFileType == FILE_TYPE_REF || currentFileType == FILE_TYPE_PHYS || currentFileType == FILE_TYPE_BOUNDING)
				{
					lineHandler = new SmdBoneHandler();
					lineHandler.setData(smd.getRootBone());
				}
				else if (currentFileType == FILE_TYPE_ANIMATION)
				{
					lineHandler = new SmdAnimationHandler(animationName, animationLooping, animationRootBone, animationStart, animationEnd);
					lineHandler.setData(smd.getRootBone());
				}
				else
				{
					lineHandler = new SmdDummyAnimationHandler();
				}

				break;
			}
			case ("triangles"):
			{
				currentToken = TOKEN_TRIANGLES;
				switch (currentFileType)
				{
					case FILE_TYPE_PHYS:
						lineHandler = new SmdPhysTriangleHandler();
						break;
					case FILE_TYPE_BOUNDING:
						lineHandler = new SmdBoundsTriangleHandler();
						break;
					case FILE_TYPE_REF:
						lineHandler = new SmdTriangleHandler(smd.getMaxBoneCount());
						break;
					case FILE_TYPE_BONE_CHECK:
						lineHandler = new SmdBoneCheckHandler();
						break;
				}
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
					if (currentFileType == FILE_TYPE_REF)
					{
						Bone rootBone = (Bone) lineHandler.getResult();
						smd.setRootBone(rootBone);
					}
					else if (currentFileType == FILE_TYPE_ANIMATION)
					{
						animationRootBone = (Bone) lineHandler.getResult();
					}
					break;
				}
				case (TOKEN_SKELETON):
				{
					if (currentFileType == FILE_TYPE_ANIMATION)
					{
						Animation animation = (Animation) lineHandler.getResult();
						smd.addAnimation(animation);
					}
					break;
				}
				case (TOKEN_TRIANGLES):
				{
					if (currentFileType == FILE_TYPE_REF)
					{
						SmdTriangleHandler.Response response = (SmdTriangleHandler.Response) lineHandler.getResult();
						smd.setTriangleData(response);
					}
					else if (currentFileType == FILE_TYPE_BONE_CHECK)
					{
						byte maxBoneCount = (byte) lineHandler.getResult();
						smd.setMaxBoneCount(maxBoneCount);
					}
					else if (currentFileType == FILE_TYPE_PHYS)
					{
						ArrayList<PhysBox> response = (ArrayList<PhysBox>) lineHandler.getResult();
						smd.setPhysBoxes(response);
					}
					else if (currentFileType == FILE_TYPE_BOUNDING)
					{
						SmdBoundsTriangleHandler.Response response = (SmdBoundsTriangleHandler.Response) lineHandler.getResult();
						smd.setBoundingBox(response.min, response.max);
					}
					break;
				}
			}
		}

		return isEnd;
	}
}
