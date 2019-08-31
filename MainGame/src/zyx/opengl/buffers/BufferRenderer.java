package zyx.opengl.buffers;

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
		DrawingRenderer draw = DrawingRenderer.getInstance();

		depth.initialize();
		ao.initialize();
		deferred.initialize();
		draw.initialize();

		depth.onBuffersCreated();
		ao.onBuffersCreated();
		deferred.onBuffersCreated();
		draw.onBuffersCreated();
		
		TextureBinder.unbindTextures();
	}

	private static void resize(int width, int height)
	{
		AmbientOcclusionRenderer ao = AmbientOcclusionRenderer.getInstance();
		DeferredRenderer deferred = DeferredRenderer.getInstance();
		DepthRenderer depth = DepthRenderer.getInstance();
		DrawingRenderer draw = DrawingRenderer.getInstance();

		depth.dispose();
		ao.dispose();
		deferred.dispose();
		draw.dispose();

		depth.resize(width, height);
		ao.resize(width, height);
		deferred.resize(width, height);
		draw.resize(width, height);

		setupBuffers();
	}
}
