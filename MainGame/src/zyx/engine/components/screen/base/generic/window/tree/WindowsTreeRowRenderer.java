package zyx.engine.components.screen.base.generic.window.tree;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.MultiSheetImage;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;

final class WindowsTreeRowRenderer<TData> extends DisplayObjectContainer
{

	private static final ArrayList<String> LINE_IMAGES = new ArrayList<>();
	private static final ArrayList<Vector2f> LINE_POSITIONS = new ArrayList<>();
	private static final ArrayList<TreeLineType> LAST_LEVEL_TYPE = new ArrayList<>(10);

	private DefaultWindowsTreeRenderer<TData> renderer;
	private WindowsButton expandButton;
	private Image icon;
	private MultiSheetImage lineImage;

	private ICallback<InteractableContainer> onFoldChange;

	byte level;
	
	WindowsTreeNode<TData> activeNode;

	WindowsTreeRowRenderer(WindowsTreeNode<TData> node)
	{
		LINE_IMAGES.clear();
		LINE_POSITIONS.clear();

		activeNode = node;
		onFoldChange = this::onFoldChange;

		level = node.level;
		
		int i = 0;
		WindowsTreeNode childNode = node;
		WindowsTreeNode parentNode = node.parent;
		while (parentNode != null)
		{
			i++;
			if (i == 1)
			{
				if (parentNode.isLastChild(childNode))
				{
					addLinePart(TreeLineType.CORNER, childNode.level, i);
				}
				else
				{
					addLinePart(TreeLineType.JUNCTION, childNode.level, i);
				}
			}
			else
			{
				TreeLineType lastType = LAST_LEVEL_TYPE.get(childNode.level);

				if (lastType == TreeLineType.JUNCTION || lastType == TreeLineType.LINE)
				{
					addLinePart(TreeLineType.LINE, childNode.level, i);
				}
			}

			childNode = parentNode;
			parentNode = parentNode.parent;
		}

		String[] res = LINE_IMAGES.toArray(new String[LINE_IMAGES.size()]);
		Vector2f[] pos = LINE_POSITIONS.toArray(new Vector2f[LINE_POSITIONS.size()]);
		lineImage = new MultiSheetImage();
		lineImage.setImages(res, pos);
		lineImage.touchable = false;

		addChild(lineImage);

		if(node.isLeaf == false)
		{
			expandButton = new WindowsButton();
			expandButton.onButtonClicked.addCallback(onFoldChange);
			expandButton.setScale(0.75f, 0.75f);
			expandButton.setPosition(true, -14, 2);
			addChild(expandButton);

			if (node.isOpened)
			{
				expandButton.setColor(0, 1, 0);
			}
			else
			{
				expandButton.setColor(0, 0.5f, 0);
			}
		}

		addRenderer();
	}

	private void addLinePart(TreeLineType type, byte level, int positionIndex)
	{
		int missingElements = level - LAST_LEVEL_TYPE.size() + 1;
		for (int i = 0; i < missingElements; i++)
		{
			LAST_LEVEL_TYPE.add(null);
		}

		LINE_IMAGES.add(type.resourceID);
		LAST_LEVEL_TYPE.set(level, type);

		LINE_POSITIONS.add(new Vector2f(positionIndex * -16, 0));
	}

	float getRendererHeight()
	{
		return renderer.getRendererHeight();
	}

	private void addRenderer()
	{
		renderer = activeNode.createRenderer();
		renderer.setData(activeNode.data);

		float height = getRendererHeight();
		icon = new Image();
		icon.load(renderer.getIcon());
		icon.setSize(height, height);
		addChild(icon);
		
		renderer.setX(16);
		addChild(renderer);
	}

	private void onFoldChange(InteractableContainer container)
	{
		activeNode.toggleOpened();
		
		if (expandButton != null)
		{
			expandButton.touchable = !activeNode.isLeaf;
			if (activeNode.isOpened)
			{
				expandButton.setColor(0, 1, 0);
			}
			else
			{
				expandButton.setColor(0, 0.5f, 0);
			}
		}
	}

	@Override
	public void dispose()
	{
		if (expandButton != null)
		{
			expandButton.onButtonClicked.removeCallback(onFoldChange);
			expandButton = null;
		}

		if (renderer != null)
		{
			renderer.removeFromParent(true);
			renderer = null;
		}

		onFoldChange = null;

		super.dispose();
	}

}
