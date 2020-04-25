package zyx.game.components.screen.radial;

import java.util.HashMap;
import zyx.engine.components.screen.composed.ComposedButtonColorMap;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.components.screen.text.Textfield;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;

public class RadialMenuItemRenderer extends JsonSprite
{

	private Button button;
	private Image image;
	private Textfield textfield;

	private final String texture;
	private final String text;
	
	private ComposedButtonColorMap colorsEnabled;
	private ComposedButtonColorMap colorsDisabled;

	public RadialMenuItemRenderer(Object data)
	{
		super(false);
		
		if (data instanceof HashMap)
		{
			HashMap dataObject = (HashMap) data;

			Object textureObj = dataObject.get("icon");
			Object textObj = dataObject.get("text");

			texture = String.valueOf(textureObj);
			text = String.valueOf(textObj);
		}
		else
		{
			text = "Text";
			texture = "icon_take";
		}
		
		load();
	}

	@Override
	protected void onPreInitialize()
	{
		colorsEnabled = ComposedConstants.buttonColorsFromScheme("green");
		colorsDisabled = ComposedConstants.buttonColorsFromScheme("gray");
	}
	
	@Override
	public String getResource()
	{
		return "json.interaction.radial_menu_button_renderer";
	}

	@Override
	protected void onComponentsCreated()
	{
		button = getComponentByName("renderer_button");
		image = getComponentByName("renderer_icon");
		textfield = getComponentByName("renderer_text");

		image.load(texture);
		textfield.setText(text);
	}

	public void addCallback(ICallback<InteractableContainer> callback)
	{
		button.onButtonClicked.addCallback(callback);
	}

	public void setEnabled(boolean enabled)
	{
		button.focusable = enabled;

		if (enabled)
		{
			button.setColors(colorsEnabled);
			image.load(texture);
		}
		else
		{
			button.setColors(colorsDisabled);
			image.load(texture + "_gray");
		}

	}
}
