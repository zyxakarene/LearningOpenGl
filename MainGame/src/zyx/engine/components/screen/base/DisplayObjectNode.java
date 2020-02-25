package zyx.engine.components.screen.base;

import java.util.ArrayList;
import zyx.debug.views.hierarchy.AbstractHierarchyData;
import zyx.engine.components.screen.composed.ComposedImage;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.Checkbox;
import zyx.engine.components.screen.list.ItemList;
import zyx.engine.components.screen.list.ItemRenderer;
import zyx.engine.components.screen.text.Textfield;

public class DisplayObjectNode extends AbstractHierarchyData<DisplayObject>
{

	public DisplayObjectNode(DisplayObject instance)
	{
		super(instance);
	}

	@Override
	protected void addChildrenTo(ArrayList<AbstractHierarchyData<DisplayObject>> children)
	{
		if (instance instanceof DisplayObjectContainer)
		{
			ArrayList<DisplayObject> instanceChildren = new ArrayList<>();

			DisplayObjectContainer container = (DisplayObjectContainer) instance;
			container.getChildren(instanceChildren);
			
			for (DisplayObject instanceChild : instanceChildren)
			{
				DisplayObjectNode child = new DisplayObjectNode(instanceChild);
				children.add(child);
			}
		}
	}

	@Override
	public String toString()
	{
		boolean hasChildren = instance instanceof DisplayObjectContainer;
		String extra = "";
		String childPart = "";
		if (instance instanceof Textfield)
		{
			extra = from((Textfield) instance);
		}
		else if (instance instanceof ItemList)
		{
			extra = from((ItemList) instance);
		}
		else if (instance instanceof ItemRenderer)
		{
			extra = from((ItemRenderer) instance);
		}
		else if (instance instanceof Checkbox)
		{
			extra = from((Checkbox) instance);
		}
		else if (instance instanceof Button)
		{
			extra = from((Button) instance);
		}
		else if (instance instanceof Scale9Image)
		{
			extra = from((Scale9Image) instance);
		}
		else if (instance instanceof Image)
		{
			extra = from((Image) instance);
		}
		else if (instance instanceof ComposedImage)
		{
			extra = from((ComposedImage) instance);
		}
		else if (instance instanceof Quad)
		{
			extra = from((Quad) instance);
		}
		else if (instance instanceof DisplayObjectContainer)
		{
			extra = from((DisplayObjectContainer) instance);
		}
		
		if (hasChildren)
		{
			childPart = String.format("+(%s)", childCount);
		}
		
		return String.format("%s: %s %s %s", instance.name, type, extra, childPart);
	}

	private String from(Textfield textfield)
	{
		return String.format("[%s characters]", textfield.getText().length());
	}

	private String from(ItemList itemList)
	{
		Class renderer = itemList.getItemRenderer();
		String rendererName = renderer != null ? renderer.getSimpleName() : "?";
		return String.format("[%s]", rendererName);
	}

	private String from(ItemRenderer renderer)
	{
		return String.format("[%s]", renderer.getData());
	}

	private String from(Checkbox checkbox)
	{
		return String.format("[%s]", checkbox.isChecked());
	}

	private String from(Button button)
	{
		return "";
	}

	private String from(Scale9Image scale9image)
	{
		return String.format("[%s]", scale9image.getResource());
	}

	private String from(Image image)
	{
		return String.format("[%s]", image.getResource());
	}
	
	private String from(ComposedImage composedImage)
	{
		return "";
	}
	
	private String from(Stage stage)
	{
		return "";
	}
	
	private String from(Quad quad)
	{
		int color = quad.getColor();
		String hexColor = Integer.toHexString(color).toUpperCase();
		return String.format("[0x%s]", hexColor);
	}
	
	private String from(DisplayObjectContainer container)
	{
		return "";
	}
}
