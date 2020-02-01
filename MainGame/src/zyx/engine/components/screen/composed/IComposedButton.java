package zyx.engine.components.screen.composed;

import org.lwjgl.util.vector.Vector3f;

public interface IComposedButton extends IComposedElement
{
	public void setColors(Vector3f[] upColors, Vector3f[] hoverColors, Vector3f[] downColors);
}
