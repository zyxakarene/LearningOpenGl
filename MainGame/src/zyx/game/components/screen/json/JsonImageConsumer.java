package zyx.game.components.screen.json;

import zyx.engine.components.screen.AbstractImage;

class JsonImageConsumer extends JsonQuadConsumer<AbstractImage>
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
