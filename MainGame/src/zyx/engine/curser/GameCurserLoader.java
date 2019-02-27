package zyx.engine.curser;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestByteArray;
import zyx.game.controls.resourceloader.requests.vo.ResourceByteArray;
import zyx.utils.exceptions.Msg;

class GameCurserLoader implements IResourceLoaded<ResourceByteArray>
{

	private GameCursor gameCursor;

	void loadCursor(GameCursor gameCursor)
	{
		this.gameCursor = gameCursor;

		ResourceRequest request = new ResourceRequestByteArray(gameCursor.path, this);
		ResourceLoader.getInstance().addEntry(request);
	}

	@Override
	public void resourceLoaded(ResourceByteArray data)
	{
		Cursor cursor = null;

		try
		{
			LoadableImageData imageData = ImageDataFactory.getImageDataFor(gameCursor.format);
			ByteBuffer textureBuffer = imageData.loadImage(data, true, true, null);

			cursor = CursorLoader.get().getCursor(textureBuffer, gameCursor.x, gameCursor.y, imageData.getWidth(), imageData.getHeight());
		}
		catch (IOException | LWJGLException ex)
		{
			String msg = String.format("Could not create cursor: %s", gameCursor.path);
			Msg.error(msg, ex);
		}
		
		CursorManager.getInstance().cursorLoaded(gameCursor, cursor);
	}

}
