package zyx.opengl.buffers;

public enum Buffer
{
	DEFAULT(0),
	RENDER_TO_TEXTURE(-1),
	DEFERRED(-1),
	DEPTH(-1);

	int bufferId;

	private Buffer(int defaultValue)
	{
		bufferId = defaultValue;
	}
}
