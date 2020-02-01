package zyx.game.components.screen.json;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.composed.IComposedImage;

public class JsonComposedImageConsumer<T extends DisplayObjectContainer & IComposedImage> extends JsonComposedConsumer<T>
{
	protected static final String COLOR_SCHEME = "colorScheme";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
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
