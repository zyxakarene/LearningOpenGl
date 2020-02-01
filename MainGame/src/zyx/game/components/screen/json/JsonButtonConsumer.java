package zyx.game.components.screen.json;

import org.json.simple.JSONArray;
import zyx.engine.components.screen.interactable.Button;
import zyx.utils.Color;

public class JsonButtonConsumer<T extends Button> extends JsonBaseConsumer<T>
{

	protected static final String TEXTURE = "texture";
	protected static final String COLOR = "color";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case TEXTURE:
				JSONArray textures = (JSONArray) value;
				int len = textures.size();
				String[] stringTextures = new String[len];
				for (int i = 0; i < len; i++)
				{
					stringTextures[i] = textures.get(i).toString();
				}
				currentDisplayObject.setTextures(stringTextures);
				break;
			case COLOR:
				String hexColor = value.toString();
				hexColor = hexColor.replace("0x", "");
				int color = Integer.parseInt(hexColor, 16);

				Color.toVector(color, HELPER_VECTOR_3);
				currentDisplayObject.setColor(HELPER_VECTOR_3.x, HELPER_VECTOR_3.y, HELPER_VECTOR_3.z);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

}
