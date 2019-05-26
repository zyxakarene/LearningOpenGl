package zyx.opengl.buffers;

public class BufferRenderer
{
	public static void setupBuffers()
	{
		AmbientOcclusionRenderer ao = AmbientOcclusionRenderer.getInstance();
		DeferredRenderer deferred = DeferredRenderer.getInstance();
		DepthRenderer depth = DepthRenderer.getInstance();
		
		depth.initialize();
		ao.initialize();
		deferred.initialize();
		
		depth.onBuffersCreated();
		ao.onBuffersCreated();
		deferred.onBuffersCreated();
	}
}
