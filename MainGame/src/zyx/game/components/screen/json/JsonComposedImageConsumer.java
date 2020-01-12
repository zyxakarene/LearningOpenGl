package zyx.game.components.screen.json;

import org.json.simple.JSONArray;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.composed.IComposedImage;
import zyx.utils.Color;

public class JsonComposedImageConsumer<T extends DisplayObjectContainer & IComposedImage> extends JsonComposedConsumer<T>
{
	protected static final String COLOR_SCHEME = "colorScheme";
	protected static final String COLOR = "color";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case COLOR:
				JSONArray jsonColors = (JSONArray) value;
				
				String upCol = jsonColors.get(0).toString();
				String hoverCol = jsonColors.get(1).toString();
				String downCol = jsonColors.get(2).toString();
				
				int upColInt = Integer.decode(upCol);
				int hoverColInt = Integer.decode(hoverCol);
				int downColInt = Integer.decode(downCol);
				
//				Color.toVector(upColInt, UP_COLOR);
//				Color.toVector(hoverColInt, HOVER_COLOR);
//				Color.toVector(downColInt, DOWN_COLOR);

//				currentDisplayObject.setColors(UP_COLOR, HOVER_COLOR, DOWN_COLOR);
				break;
			case COLOR_SCHEME:
				Vector3f[] colors = ComposedConstants.imageColorsFromScheme(value.toString());
				currentDisplayObject.setColors(colors);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

}
