package zyx.opengl.buffers;

public enum Buffer
{
	
	DEFAULT(0),
	RENDER_TO_TEXTURE(BufferBinder.UNKNOWN_BUFFER),
	DEFERRED(BufferBinder.UNKNOWN_BUFFER),
	DEPTH(BufferBinder.UNKNOWN_BUFFER),
	AMBIENT_OCCLUSION(BufferBinder.UNKNOWN_BUFFER);

	int bufferId;

	private Buffer(int defaultValue)
	{
		bufferId = defaultValue;
	}
}
