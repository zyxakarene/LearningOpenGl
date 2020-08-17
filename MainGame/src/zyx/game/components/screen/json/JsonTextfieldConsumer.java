package zyx.game.components.screen.json;

import zyx.engine.components.screen.text.Textfield;

public class JsonTextfieldConsumer extends JsonBaseConsumer<Textfield>
{
	protected static final String FONT = "font";
	protected static final String TEXT = "text";
	protected static final String FONT_SIZE = "fontSize";
	protected static final String SHOW_BORDERS = "showBorders";
	protected static final String COLOR = "color";
	protected static final String H_ALIGN = "hAlign";
	protected static final String V_ALIGN = "vAlign";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case FONT:
				currentDisplayObject.load(value.toString());
				break;
			case H_ALIGN:
				currentDisplayObject.setHAlign(value.toString());
				break;
			case V_ALIGN:
				currentDisplayObject.setVAlign(value.toString());
				break;
			case TEXT:
				currentDisplayObject.setText(value.toString());
				break;
			case FONT_SIZE:
				float fontSize = toFloat(value);
				currentDisplayObject.setFontSize(fontSize / 12f);
				break;
			case SHOW_BORDERS:
				boolean borderVisibility = toBoolean(value);
				currentDisplayObject.showBorders(borderVisibility);
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
