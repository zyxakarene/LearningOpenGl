package zyx.engine.components.screen.composed;

import org.lwjgl.util.vector.Vector3f;

public class ComposedButtonColorMap
{
	public final Vector3f[] upColors;
	public final Vector3f[] hoverColors;
	public final Vector3f[] downColors;

	public ComposedButtonColorMap(Vector3f[] upColors, Vector3f[] hoverColors, Vector3f[] downColors)
	{
		this.upColors = upColors;
		this.hoverColors = hoverColors;
		this.downColors = downColors;
	}
}
