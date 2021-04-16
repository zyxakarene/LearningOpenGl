package zyx.opengl.materials;

public enum RenderQueue
{
	/**
	 * Normal opaque geometry
	 */
	OPAQUE,
		
	/**
	 * Transparent geometry that should be drawn back-to-front
	 */
	TRANSPARENT
}
