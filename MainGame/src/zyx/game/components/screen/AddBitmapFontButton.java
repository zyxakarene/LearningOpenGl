package zyx.game.components.screen;

import zyx.engine.components.screen.Button;
import zyx.engine.components.screen.InteractableContainer;
import zyx.engine.components.screen.Textfield;
import zyx.engine.utils.callbacks.ICallback;
import zyx.net.io.responses.ResponseDispatcher;
import zyx.net.io.responses.ResponseManager;

public class AddBitmapFontButton extends Button implements ICallback<InteractableContainer>
{

	public AddBitmapFontButton(String upTexture, String hoverTexture, String downTexture)
	{
		super(upTexture, hoverTexture, downTexture);

		onButtonClicked.addCallback(this);

		ResponseDispatcher serverDispatcher = new ResponseDispatcher();
		serverDispatcher.addResponseCallback("login", new LoginResponse());

		ResponseManager.getInstance().registerDispatcher(serverDispatcher);
	}

	@Override
	public void onCallback(InteractableContainer ref)
	{
//		WriteableDataObject data = new WriteableDataObject();
//		data.addString("name", "Zyx");
//		ConnectionRequest request = new ConnectionRequest("auth", data);
//
//		ConnectionLoader.getInstance().addRequest(request);
		Textfield field = new Textfield("console", "Lorem ipsum?");
		field.scale.set(1f, 1f);
		field.position.y = 600;

		addChild(field);
	}
}
