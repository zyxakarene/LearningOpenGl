package zyx;

import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.game.components.screen.LoginResponse;
import zyx.game.controls.input.MouseData;
import zyx.game.controls.sound.SoundManager;
import zyx.net.core.ConnectionHandler;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.ConnectionRequest;
import zyx.net.io.responses.ResponseDispatcher;
import zyx.net.io.responses.ResponseManager;
import zyx.utils.cheats.DebugPoint;

public class OnTeaPotClicked implements IHoveredItem
{

	public OnTeaPotClicked()
	{
		ResponseDispatcher serverDispatcher = new ResponseDispatcher();
		serverDispatcher.addResponseCallback("login", new LoginResponse());

		ResponseManager.getInstance().registerDispatcher(serverDispatcher);
	}

	@Override
	public void onClicked(ClickedInfo data)
	{
		CursorManager.getInstance().setCursor(GameCursor.HAND);

		if (MouseData.data.isLeftDown())
		{
//			data.target.setTexture(new ColorTexture((int) (0xFFFFFF * FloatMath.random())));
		}

		if (MouseData.data.isLeftClicked())
		{
			DebugPoint.addToScene(data.position, 1000);

			SoundManager.getInstance().playSound("sound.Explosion", data.gameObject);

			ServerStuff();
		}
	}

	private void ServerStuff()
	{
		WriteableDataObject data = new WriteableDataObject();
		data.addString("name", "Zyx");
		ConnectionRequest request = new ConnectionRequest("auth", data);

		ConnectionHandler.getInstance().addEntry(request);
	}
}
