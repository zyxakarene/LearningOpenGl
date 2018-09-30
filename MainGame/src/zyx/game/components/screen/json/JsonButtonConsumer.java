package zyx.game.components.screen.json;

import org.json.simple.JSONArray;
import zyx.engine.components.screen.Button;

class JsonButtonConsumer<T extends Button> extends JsonBaseConsumer<T>
{
	protected static final String TEXTURE = "texture";

	@Override
	protected void onAccept(String name, Object value)
	{
		switch (name)
		{
			case TEXTURE:
					JSONArray textures = (JSONArray) value;
					String up = textures.get(0).toString();
					String hover = textures.get(1).toString();
					String down = textures.get(2).toString();
					currentDisplayObject.load(up, hover, down);
				break;
			default:
				super.onAccept(name, value);
				break;
		}
	}

	

}
