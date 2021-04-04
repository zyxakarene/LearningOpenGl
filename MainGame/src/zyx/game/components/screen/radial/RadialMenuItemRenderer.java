package zyx.game.components.screen.radial;

import zyx.engine.components.screen.composed.ComposedButtonColorMap;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.image.Image;
import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.components.screen.text.Textfield;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.screen.json.JsonSprite;

public class RadialMenuItemRenderer extends JsonSprite implements ICallback<InteractableContainer>
{

	private Button button;
	private Image image;
	private Textfield textfield;
	
	public IRadialMenuOption data;
	
	private ComposedButtonColorMap colorsEnabled;
	private ComposedButtonColorMap colorsDisabled;

	private CustomCallback<RadialMenuItemRenderer> onRendererClicked;
	
	@Override
	protected void onPreInitialize()
	{
		colorsEnabled = ComposedConstants.buttonColorsFromScheme("green");
		colorsDisabled = ComposedConstants.buttonColorsFromScheme("gray");
		
		onRendererClicked = new CustomCallback<>();
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

		if (data != null)
		{
			setData(data);
		}
		
	}

	public void setData(IRadialMenuOption data)
	{
		this.data = data;
		
		if (loaded)
		{
			image.load(data.getIconResource());
			textfield.setText(data.getText());
			
			setEnabled(button.touchable);
		}
	}
	
	void addCallback(ICallback<RadialMenuItemRenderer> callback)
	{
		onRendererClicked.addCallback(callback);
		
		button.onButtonClicked.addCallback(this);
	}

	void setEnabled(boolean enabled)
	{
		button.touchable = enabled;

		if (data != null)
		{
			String iconResource = data.getIconResource();
			if (enabled)
			{
				button.setColors(colorsEnabled);
				image.load(iconResource);
			}
			else
			{
				button.setColors(colorsDisabled);
				image.load(iconResource + "_gray");
			}
		}

	}

	@Override
	public void onCallback(InteractableContainer data)
	{
		if (onRendererClicked != null)
		{
			onRendererClicked.dispatch(this);
		}
	}

	@Override
	protected void onDispose()
	{
		if(onRendererClicked != null)
		{
			onRendererClicked.dispose();
			onRendererClicked = null;
		}
	}
	
	
}
