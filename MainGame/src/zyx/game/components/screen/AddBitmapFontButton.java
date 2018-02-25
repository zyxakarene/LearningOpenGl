package zyx.game.components.screen;

import java.io.File;
import java.io.IOException;
import zyx.engine.components.screen.Button;
import zyx.engine.components.screen.InteractableContainer;
import zyx.engine.components.screen.Stage;
import zyx.engine.components.screen.Textfield;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.net.data.WriteableDataObject;
import zyx.net.io.ConnectionLoader;
import zyx.net.io.requests.ConnectionRequest;
import zyx.net.io.responses.ResponseDispatcher;
import zyx.net.io.responses.ResponseManager;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.BitmapFontGenerator;

public class AddBitmapFontButton extends Button implements ICallback<InteractableContainer>, IResourceLoaded<AbstractTexture>
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
		WriteableDataObject data = new WriteableDataObject();
		data.addString("name", "Zyx");
		ConnectionRequest request = new ConnectionRequest("auth", data);
		
		ConnectionLoader.getInstance().addRequest(request);
//		TextureManager.getInstance().loadTexture("assets/fonts/font.png", this);
	}

	@Override
	public void resourceLoaded(AbstractTexture texture)
	{
		try
		{
			File file = new File("assets/fonts/font.zff");

			BitmapFontGenerator gen = new BitmapFontGenerator(texture);

			gen.loadFromFnt(file);
			BitmapFont bmpFont = gen.createBitmapFont();

			Textfield field = new Textfield(bmpFont);
			field.scale.set(1f, 1f);
			field.position.y = 600;
			field.setText("Bitmap font");

			Stage.instance.addChild(field);
		}
		catch (IOException ex)
		{
			throw new RuntimeException("Bitmap font error", ex);
		}
	}

}
