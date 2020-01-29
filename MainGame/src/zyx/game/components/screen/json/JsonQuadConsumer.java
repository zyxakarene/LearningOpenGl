package zyx.game.components.screen.json;

import zyx.engine.components.screen.image.AbstractQuad;

public class JsonQuadConsumer<T extends AbstractQuad> extends JsonBaseConsumer<T>
{
	protected static final String COLOR = "color";
	protected static final String ALPHA = "alpha";
	
	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case COLOR:
				toColor(value, HELPER_VECTOR_3);
				currentDisplayObject.setColor(HELPER_VECTOR_3.x, HELPER_VECTOR_3.y, HELPER_VECTOR_3.z);
				break;
			case ALPHA:
				currentDisplayObject.setAlpha(toFloat(value));
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

	

}
