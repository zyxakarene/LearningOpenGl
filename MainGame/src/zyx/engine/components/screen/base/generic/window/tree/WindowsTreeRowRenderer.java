package zyx.engine.components.screen.base.generic.window.tree;

import org.lwjgl.util.vector.Vector2f;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.image.MultiSheetImage;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.callbacks.ICallback;

final class WindowsTreeRowRenderer<TData> extends DisplayObjectContainer
{

	private DefaultWindowsTreeRenderer<TData> renderer;
	private WindowsButton expandButton;
	private MultiSheetImage lineImage;
	
	private ICallback<InteractableContainer> onFoldChange;
	
	private WindowsTreeNode<TData> activeNode;

	WindowsTreeRowRenderer(WindowsTreeNode<TData> node)
	{
		activeNode = node;
		onFoldChange = this::onFoldChange;
		
		lineImage = new MultiSheetImage();
		String[] res = new String[]
		{
			"tree_line", "tree_corner", "tree_junction"
		};
		
		Vector2f[] pos = new Vector2f[]
		{
			new Vector2f(0, 0), new Vector2f(-16, 0), new Vector2f(-32, 0)
		};
		lineImage.setImages(res, pos);
		lineImage.touchable = false;
		
		
		expandButton = new WindowsButton();
		expandButton.onButtonClicked.addCallback(onFoldChange);
		addChild(expandButton);
		addChild(lineImage);
		
		if (node.isLeaf)
		{
			expandButton.touchable = false;
			expandButton.hoverIcon = GameCursor.POINTER;
			expandButton.setColor(0, 1, 0);
		}
		else
		{
			lineImage.visible = node.isOpened;
			
			if (node.isOpened)
			{
				expandButton.setColor(1, 0, 0);
			}
			else
			{
				expandButton.setColor(0.5f, 0, 0);
			}
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
		
		renderer.setX(expandButton.getWidth());
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
