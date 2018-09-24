package zyx.game.components.screen.json;

import zyx.engine.components.screen.AbstractQuad;
import zyx.engine.components.screen.ILoadable;

class JsonImageConsumer extends JsonBaseConsumer<AbstractQuad>
{
	protected static final String TEXTURE = "texture";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case TEXTURE:
				if (currentDisplayObject instanceof ILoadable)
				{
					((ILoadable)currentDisplayObject).load(value.toString());
				}
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

	

}
