package zyx.game.components.screen;

import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.base.Stage;
import zyx.engine.components.screen.text.Textfield;
import zyx.engine.utils.callbacks.ICallback;
import zyx.net.io.responses.ResponseDispatcher;
import zyx.net.io.responses.ResponseManager;
import zyx.utils.FloatMath;

public class AddBitmapFontButton extends Button
{

	private Textfield field;
	
	private ICallback<InteractableContainer> btnClick;

	public AddBitmapFontButton()
	{
		super(false);

		btnClick = (InteractableContainer data) ->
		{
			onBtnClicked();
		};
		
		onButtonClicked.addCallback(btnClick);

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

	private void onBtnClicked()
	{
//		WriteableDataObject data = new WriteableDataObject();
//		data.addString("name", "Zyx");
//		ConnectionRequest request = new ConnectionRequest("auth", data);
//
//		ConnectionLoader.getInstance().addRequest(request);
		if (field == null)
		{
			field = new Textfield("Lorem ipsum?");
			field.load("font.console");
			field.setScale(1, 1);

			Stage.instance.addChild(field);
		}
		
		Quad q = new Quad(100 * FloatMath.random(), 100 * FloatMath.random(), (int) (0xFFFFFF * FloatMath.random()));
		q.setPosition(true, 200 * FloatMath.random(), 200 * FloatMath.random());
		Stage.instance.addChild(q);
	}
}
