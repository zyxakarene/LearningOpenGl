package zyx.game.components.screen.json;

import zyx.engine.components.screen.Checkbox;

class JsonCheckboxConsumer extends JsonButtonConsumer<Checkbox>
{
	protected static final String CHECK_ICON = "checkIcon";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case CHECK_ICON:
					currentDisplayObject.loadCheckmark(value.toString());
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

	

}
