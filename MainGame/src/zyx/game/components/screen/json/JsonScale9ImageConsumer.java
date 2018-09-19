package zyx.game.components.screen.json;

import zyx.engine.components.screen.Scale9Image;

class JsonScale9ImageConsumer extends JsonBaseConsumer<Scale9Image>
{
	protected static final String TEXTURE = "texture";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case TEXTURE:
				currentDisplayObject.load(value.toString());
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

	

}
