package zyx.engine.components.screen.base.generic.window.tree;

import org.lwjgl.util.vector.Vector2f;
import zyx.engine.components.animations.IFocusable;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.MultiSheetImage;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.FloatMath;

final class WindowsTreeRowRenderer<TData> extends DisplayObjectContainer implements IFocusable
{

	private DefaultWindowsTreeRenderer<TData> renderer;
	private WindowsButton expandButton;
	private Image icon;
	private MultiSheetImage lineImage;
	private Quad bg;

	private ICallback<InteractableContainer> onFoldChange;

	byte level;

	WindowsTreeNode<TData> activeNode;

	WindowsTreeRowRenderer(WindowsTreeNode<TData> node)
	{
		bg = new Quad(16, 16, 0xFFFFFF);
		addChild(bg);

		touchable = true;
		
		activeNode = node;
		onFoldChange = this::onFoldChange;

		level = node.level;

		if (node.isLeaf == false)
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
		
		makeFocusable(this);
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

		lineImage = new MultiSheetImage();
		lineImage.touchable = false;
		addChild(lineImage);
		
		bg.setSize(renderer.getWidth(), height);

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

	void buildLines(TreeLineStructure lineStructure)
	{
		TreeLineRowStructure lineData = lineStructure.getLineDataFor(activeNode);

		int len = lineData.length;
		String[] res = new String[len];
		Vector2f[] pos = new Vector2f[len];

		for (int i = 0; i < len; i++)
		{
			TreeLineType value = lineData.types[i];
			int offset = lineData.offsets[i];
			res[i] = value.resourceID;
			pos[i] = new Vector2f(offset * 16, 0);
		}

		lineImage.setImages(res, pos);
		lineImage.setX(activeNode.level * -16);
	}

	void updateSelf()
	{
		if (expandButton != null)
		{
			if (activeNode.isLeaf)
			{
				expandButton.visible = false;
			}
			else if (activeNode.isOpened)
			{
				expandButton.setColor(0, 1, 0);
			}
			else
			{
				expandButton.setColor(0, 0.5f, 0);
			}
		}

		if (renderer != null)
		{
			renderer.setData(activeNode.data);
		}
	}

	@Override
	public void onKeyPressed(char character)
	{
		bg.setColor((int) (0xFFFFFF * FloatMath.random()));
	}
}
