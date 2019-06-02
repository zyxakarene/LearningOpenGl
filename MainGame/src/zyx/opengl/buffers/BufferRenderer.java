package zyx.opengl.buffers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import zyx.engine.utils.ScreenSize;
import zyx.engine.utils.callbacks.ICallback;
import zyx.opengl.textures.TextureBinder;
import zyx.utils.math.Vector2Int;

public class BufferRenderer
{

	private static ICallback<Vector2Int> sizeChangeListener = new ICallback<Vector2Int>()
	{
		@Override
		public void onCallback(Vector2Int data)
		{
			resize(data.x, data.y);
		}
	};

	public static void setupBuffers()
	{
		ScreenSize.addListener(sizeChangeListener);
		
		AmbientOcclusionRenderer ao = AmbientOcclusionRenderer.getInstance();
		DeferredRenderer deferred = DeferredRenderer.getInstance();
		DepthRenderer depth = DepthRenderer.getInstance();

		depth.initialize();
		ao.initialize();
		deferred.initialize();

		depth.onBuffersCreated();
		ao.onBuffersCreated();
		deferred.onBuffersCreated();
		
		TextureBinder.unbindTextures();
	}

	private static void resize(int width, int height)
	{
		AmbientOcclusionRenderer ao = AmbientOcclusionRenderer.getInstance();
		DeferredRenderer deferred = DeferredRenderer.getInstance();
		DepthRenderer depth = DepthRenderer.getInstance();

		depth.dispose();
		ao.dispose();
		deferred.dispose();

		depth.resize(width, height);
		ao.resize(width, height);
		deferred.resize(width, height);

		setupBuffers();
	}
}
