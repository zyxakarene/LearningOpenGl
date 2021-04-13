package zyx.opengl.materials;

public enum RenderQueue
{
	/**
	 * Normal opaque geometry
	 */
	GEOMETRY,
	
	/**
	 * Alpha transparency, or other modes which requires distance sorting
	 */
	ALPHA,
	
	/**
	 * Additive transparency, or other modes which does NOT require distance sorting
	 */
	TRANSPARENT
}
