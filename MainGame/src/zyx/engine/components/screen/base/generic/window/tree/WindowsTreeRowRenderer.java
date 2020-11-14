package zyx.engine.components.screen.base.generic.window.tree;

import java.util.ArrayList;
import java.util.Arrays;
import org.lwjgl.util.vector.Vector2f;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.MultiSheetImage;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.cheats.Print;

final class WindowsTreeRowRenderer<TData> extends DisplayObjectContainer
{

	private static final ArrayList<String> LINE_IMAGES = new ArrayList<>();
	private static final ArrayList<Vector2f> LINE_POSITIONS = new ArrayList<>();
	private static final String[] LAST_LEVEL_TYPE = new String[10];

	private DefaultWindowsTreeRenderer<TData> renderer;
	private WindowsButton expandButton;
	private Image icon;
	private MultiSheetImage lineImage;

	private ICallback<InteractableContainer> onFoldChange;

	private WindowsTreeNode<TData> activeNode;

	WindowsTreeRowRenderer(WindowsTreeNode<TData> node)
	{
		LINE_IMAGES.clear();
		LINE_POSITIONS.clear();

		activeNode = node;
		onFoldChange = this::onFoldChange;

		if (node.data.equals(22))
		{
			Print.out(node);
		}
		
		//"tree_line", "tree_corner", "tree_junction"
		int i = 0;
		WindowsTreeNode childNode = node;
		WindowsTreeNode parentNode = node.parent;
		while (parentNode != null)
		{
			boolean add = false;
			i++;
			if (i == 1)
			{
				add = true;
				if (parentNode.isLastChild(childNode))
				{
					LINE_IMAGES.add("tree_corner");
					LAST_LEVEL_TYPE[childNode.level] = "tree_corner";
				}
				else
				{
					LINE_IMAGES.add("tree_junction");
					LAST_LEVEL_TYPE[childNode.level] = "tree_junction";
				}
			}
			else
			{
				String lastType = LAST_LEVEL_TYPE[childNode.level];
				
				if (lastType.equals("tree_junction") || lastType.equals("tree_line"))
				{
					add = true;
					LINE_IMAGES.add("tree_line");
					LAST_LEVEL_TYPE[childNode.level] = "tree_line";
				}
			}

			if (add)
			{
				LINE_POSITIONS.add(new Vector2f(i * -16, 0));
			}

			childNode = parentNode;
			parentNode = parentNode.parent;
		}

		lineImage = new MultiSheetImage();
		String[] res = LINE_IMAGES.toArray(new String[0]);
		Vector2f[] pos = LINE_POSITIONS.toArray(new Vector2f[0]);
		lineImage.setImages(res, pos);
		lineImage.touchable = false;

		icon = new Image();
		icon.load("container");
		icon.setSize(16, 16);
		
		expandButton = new WindowsButton();
		expandButton.onButtonClicked.addCallback(onFoldChange);
		expandButton.setScale(0.75f, 0.75f);
		expandButton.setPosition(true, -14, 2);
		addChild(lineImage);
		addChild(icon);
		addChild(expandButton);

		if (node.isLeaf)
		{
			expandButton.visible = false;
			expandButton.touchable = false;
			expandButton.hoverIcon = GameCursor.POINTER;
		}
		else if (node.isOpened)
		{
			expandButton.setColor(0, 1, 0);
		}
		else
		{
			expandButton.setColor(0, 0.5f, 0);
		}

		addRenderer();
	}

	float getRendererHeight()
	{
		return renderer.getHeight();
	}

	private void addRenderer()
	{
		renderer = activeNode.createRenderer();
		renderer.setData(activeNode.data);

		renderer.setX(16);
		addChild(renderer);
	}

	private void onFoldChange(InteractableContainer container)
	{
		expandButton.touchable = !activeNode.isLeaf;
		activeNode.toggleOpened();
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
