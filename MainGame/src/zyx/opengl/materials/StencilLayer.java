package zyx.opengl.materials;

import zyx.opengl.buffers.Buffer;
import zyx.opengl.stencils.StencilControl;

public enum StencilLayer
{
	NOTHING(0),
	PLAYER_CHARACTER(1 << 0); //1
	
	public static final StencilLayer[] values = values();
	
	public final int maskValue;
	
	private static StencilLayer currentWriteStencil = NOTHING;
	private static StencilLayer currentMaskStencil = NOTHING;

	private StencilLayer(int maskValue)
	{
		this.maskValue = maskValue;
	}
	
	public void invokeMask()
	{
		if (currentMaskStencil != this)
		{
			if (this == NOTHING)
			{
				StencilControl.getInstance().stopMaskingLayer(currentWriteStencil, Buffer.DEFAULT);
			}
			else
			{
				StencilControl.getInstance().startMaskingLayer(this, Buffer.DEFAULT);
			}
			
			currentMaskStencil = this;
		}
	}
	
	public void invokeWrite()
	{
		if (currentWriteStencil != this)
		{
			if (this == NOTHING)
			{
				StencilControl.getInstance().stopDrawingToLayer(currentWriteStencil, Buffer.DEFAULT);
			}
			else
			{
				StencilControl.getInstance().startDrawingToLayer(this, Buffer.DEFAULT);
			}
			
			currentWriteStencil = this;
		}
	}
	
	public static StencilLayer fromValue(int value)
	{
		for (StencilLayer layer : values)
		{
			if (layer.maskValue == value)
			{
				return layer;
			}
		}
		
		return NOTHING;
	}
}
