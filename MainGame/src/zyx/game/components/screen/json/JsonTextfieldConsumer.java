package zyx.game.components.screen.json;

import zyx.engine.components.screen.text.Textfield;

public class JsonTextfieldConsumer extends JsonBaseConsumer<Textfield>
{
	protected static final String FONT = "font";
	protected static final String TEXT = "text";
	protected static final String FONT_SIZE = "fontSize";
	protected static final String SHOW_BORDERS = "showBorders";
	protected static final String COLOR = "color";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case FONT:
				currentDisplayObject.load(value.toString());
				break;
			case TEXT:
				currentDisplayObject.setText(value.toString());
				break;
			case FONT_SIZE:
				float fontSize = toFloat(value);
				currentDisplayObject.setFontSize(fontSize);
				break;
			case SHOW_BORDERS:
				boolean borderVisibility = toBoolean(value);
				currentDisplayObject.setShowBorders(borderVisibility);
				break;
			case COLOR:
				toColor(value, HELPER_VECTOR_3);
				currentDisplayObject.setColor(HELPER_VECTOR_3.x, HELPER_VECTOR_3.y, HELPER_VECTOR_3.z);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}
}
