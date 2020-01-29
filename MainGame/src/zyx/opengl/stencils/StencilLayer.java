package zyx.opengl.stencils;

public enum StencilLayer
{
	PLAYER_CHARACTER(1 << 1);
	
	public final int maskValue;

	private StencilLayer(int maskValue)
	{
		this.maskValue = maskValue;
	}
}
