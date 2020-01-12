package zyx.game.components.screen.json;

import org.json.simple.JSONArray;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.composed.ComposedConstants;
import zyx.engine.components.screen.composed.IComposedElement;

public class JsonComposedConsumer<T extends DisplayObjectContainer & IComposedElement> extends JsonContainerConsumer<T>
{
	protected static final String STYLE = "style";
	protected static final String TEXTURE = "texture";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case TEXTURE:
				JSONArray jsonTextures = (JSONArray) value;
				int texLen = jsonTextures.size();
				String[] stringTextures = new String[texLen];
				for (int i = 0; i < texLen; i++)
				{
					stringTextures[i] = jsonTextures.get(i).toString();
				}

				currentDisplayObject.setTextures(stringTextures);
				break;
			case STYLE:
				String[] textures = ComposedConstants.texturesFromStyle(value.toString());
				currentDisplayObject.setTextures(textures);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

}
