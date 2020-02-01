package zyx.opengl.stencils;

import java.util.HashMap;
import org.lwjgl.opengl.GL11;
import zyx.engine.utils.callbacks.ICallback;
import zyx.opengl.buffers.Buffer;
import zyx.opengl.buffers.BufferBinder;

public class StencilControl implements ICallback<Buffer>
{

	private static final StencilControl INSTANCE = new StencilControl();

	public static StencilControl getInstance()
	{
		return INSTANCE;
	}

	private HashMap<Buffer, Integer> drawMaskValues;
	private HashMap<Buffer, Integer> hideMaskValues;
	private HashMap<Buffer, StencilMode> modeValues;

	private StencilControl()
	{
		drawMaskValues = new HashMap<>();
		hideMaskValues = new HashMap<>();
		modeValues = new HashMap<>();

		Buffer[] buffers = Buffer.values();
		for (Buffer buffer : buffers)
		{
			drawMaskValues.put(buffer, 0);
			hideMaskValues.put(buffer, 0);
			modeValues.put(buffer, StencilMode.NOTHING);
		}

		BufferBinder.addListener(this);
	}

	@Override
	public void onCallback(Buffer buffer)
	{
		StencilMode mode = modeValues.get(buffer);

		switch (mode)
		{
			case DRAWING:
			{
				int mask = drawMaskValues.get(buffer);
				setEnabled(buffer, mask > 0);
				if (mask > 0)
				{
					GL11.glStencilFunc(GL11.GL_ALWAYS, mask, 0xFF);
					GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
				}
				break;
			}
			case MASKING:
			{
				int mask = hideMaskValues.get(buffer);
				setEnabled(buffer, mask > 0);
				if (mask > 0)
				{
					GL11.glStencilFunc(GL11.GL_NOTEQUAL, mask, 0xff);
					GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
				}
				break;
			}
			case NOTHING:
				setEnabled(buffer, false);
				break;
		}
	}

	public void startDrawingToLayer(StencilLayer layer, Buffer buffer)
	{
		setMode(buffer, StencilMode.DRAWING);

		int mask = drawMaskValues.get(buffer);
		mask = mask | layer.maskValue;
		drawMaskValues.put(buffer, mask);

		checkBufferStatus(buffer);
	}

	public void stopDrawingToLayer(StencilLayer layer, Buffer buffer)
	{
		setMode(buffer, StencilMode.DRAWING);

		int mask = drawMaskValues.get(buffer);
		mask = mask & ~layer.maskValue;
		drawMaskValues.put(buffer, mask);

		checkBufferStatus(buffer);
	}

	public void startMaskingLayer(StencilLayer layer, Buffer buffer)
	{
		setMode(buffer, StencilMode.MASKING);

		int mask = hideMaskValues.get(buffer);
		mask = mask | layer.maskValue;
		hideMaskValues.put(buffer, mask);

		checkBufferStatus(buffer);
	}

	public void stopMaskingLayer(StencilLayer layer, Buffer buffer)
	{
		setMode(buffer, StencilMode.MASKING);

		int mask = hideMaskValues.get(buffer);
		mask = mask & ~layer.maskValue;
		hideMaskValues.put(buffer, mask);

		checkBufferStatus(buffer);
	}

	private void checkBufferStatus(Buffer buffer)
	{
		Buffer currentBuffer = BufferBinder.getCurrentBuffer();

		if (currentBuffer == buffer)
		{
			onCallback(buffer);
		}
	}

	private void setEnabled(Buffer buffer, boolean value)
	{
		if (value)
		{
			GL11.glStencilMask(0xFF);
			GL11.glEnable(GL11.GL_STENCIL_TEST);
		}
		else
		{
			setMode(buffer, StencilMode.NOTHING);
			GL11.glDisable(GL11.GL_STENCIL_TEST);
		}
	}

	private void setMode(Buffer buffer, StencilMode stencilMode)
	{
		StencilMode mode = modeValues.get(buffer);

		if (mode == StencilMode.NOTHING || stencilMode == StencilMode.NOTHING)
		{
			modeValues.put(buffer, stencilMode);
		}
		else if (mode != stencilMode)
		{
			String msg = String.format("Attempted to change stencil mode from %s to %s", mode, stencilMode);
			throw new RuntimeException(msg);
		}
	}
}
