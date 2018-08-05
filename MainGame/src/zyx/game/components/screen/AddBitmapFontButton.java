package zyx.game.components.screen;

import zyx.engine.components.screen.Button;
import zyx.engine.components.screen.InteractableContainer;
import zyx.engine.components.screen.Quad;
import zyx.engine.components.screen.Stage;
import zyx.engine.components.screen.Textfield;
import zyx.engine.utils.callbacks.ICallback;
import zyx.net.io.responses.ResponseDispatcher;
import zyx.net.io.responses.ResponseManager;
import zyx.utils.FloatMath;

public class AddBitmapFontButton extends Button implements ICallback<InteractableContainer>
{

	private Textfield field;

	public AddBitmapFontButton(String upTexture, String hoverTexture, String downTexture)
	{
		super(upTexture, hoverTexture, downTexture);

		onButtonClicked.addCallback(this);

		ResponseDispatcher serverDispatcher = new ResponseDispatcher();
		serverDispatcher.addResponseCallback("login", new LoginResponse());

		ResponseManager.getInstance().registerDispatcher(serverDispatcher);
	}

	@Override
	protected void onMouseDown()
	{
		super.onMouseDown();
		
		if(field != null)
		{
			field.setColor(FloatMath.random(), FloatMath.random(), FloatMath.random());
		}
		
		setColor(FloatMath.random(), FloatMath.random(), FloatMath.random());
		
	}

	@Override
	public void onCallback(InteractableContainer ref)
	{
//		WriteableDataObject data = new WriteableDataObject();
//		data.addString("name", "Zyx");
//		ConnectionRequest request = new ConnectionRequest("auth", data);
//
//		ConnectionLoader.getInstance().addRequest(request);
		if (field == null)
		{
			field = new Textfield("console", "Lorem ipsum?");
			field.setScale(1, 1);

			addChild(field);
		}
		
		Quad q = new Quad(100 * FloatMath.random(), 100 * FloatMath.random(), (int) (0xFFFFFF * FloatMath.random()));
		q.setPosition(true, 200 * FloatMath.random(), 200 * FloatMath.random());
		Stage.instance.addChild(q);
	}
}
