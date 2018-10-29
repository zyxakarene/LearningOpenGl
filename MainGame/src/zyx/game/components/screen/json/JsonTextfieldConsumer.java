package zyx.game.components.screen.json;

import zyx.engine.components.screen.text.Textfield;

public class JsonTextfieldConsumer extends JsonBaseConsumer<Textfield>
{
	protected static final String FONT = "font";
	protected static final String TEXT = "text";

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
			default:
				super.onAccept(name, value);
				break;
		}
	}
}
