package zyx.game.components.screen.json;

import zyx.engine.components.screen.AbstractQuad;
import zyx.utils.Color;

class JsonQuadConsumer<T extends AbstractQuad> extends JsonBaseConsumer<T>
{
	protected static final String COLOR = "color";
	protected static final String ALPHA = "alpha";
	
	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case COLOR:
				String hexColor = value.toString();
				hexColor = hexColor.replace("0x", "");
				int color = Integer.parseInt(hexColor, 16);
				
				Color.toVector(color, HELPER_VECTOR);
				currentDisplayObject.setColor(HELPER_VECTOR.x, HELPER_VECTOR.y, HELPER_VECTOR.z);
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
