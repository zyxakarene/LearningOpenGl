package zyx.opengl.materials;

import zyx.opengl.buffers.Buffer;
import zyx.opengl.stencils.StencilControl;

public enum StencilMode
{
	NOTHING,
	WRITING,
	MASKING;
	
	public static final StencilMode[] values = values();
	
	private static StencilMode currentStencilMode = NOTHING;
	private static StencilLayer currentDrawLayer = StencilLayer.NOTHING;
	private static StencilLayer currentMaskLayer = StencilLayer.NOTHING;

	private StencilMode()
	{
	}
	
	public void invoke(StencilLayer layer)
	{
		StencilControl instance = StencilControl.getInstance();	
		
		if (currentStencilMode != this)
		{
			switch (this)
			{
				case NOTHING:
				{
					if (currentDrawLayer != StencilLayer.NOTHING)
					{
						instance.stopDrawingToAllLayers(Buffer.DEFERRED);
						currentDrawLayer = StencilLayer.NOTHING;
					}
					
					if (currentMaskLayer != StencilLayer.NOTHING)
					{
						instance.stopMaskingAllLayers(Buffer.DEFAULT);
						currentMaskLayer = StencilLayer.NOTHING;
					}

					break;
				}
				case WRITING:
				{
					if (currentDrawLayer != layer)
					{
						instance.stopDrawingToLayer(currentDrawLayer, Buffer.DEFERRED);
						
						currentDrawLayer = layer;
						instance.startDrawingToLayer(currentDrawLayer, Buffer.DEFERRED);
					}
					break;
				}
				case MASKING:
				{
					if (currentMaskLayer != layer)
					{
						instance.stopMaskingLayer(currentMaskLayer, Buffer.DEFAULT);
						
						currentMaskLayer = layer;
						instance.startMaskingLayer(currentMaskLayer, Buffer.DEFAULT);
					}
					break;
				}
			}
			
			currentStencilMode = this;
		}
	}
}
