package zyx.engine.components.tooltips;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.Stage;
import zyx.opengl.camera.Camera;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.interfaces.IUpdateable;

public class TooltipManager implements IUpdateable
{

	private static final TooltipManager instance = new TooltipManager();

	private ArrayList<AbstractTooltip> tooltips;
	private Vector2f outVector;
	private Vector3f cameraPos;

	public static TooltipManager getInstance()
	{
		return instance;
	}

	private TooltipManager()
	{
		tooltips = new ArrayList<>();
		outVector = new Vector2f();
		cameraPos = new Vector3f();
	}

	public void register(AbstractTooltip tooltip)
	{
		Stage.instance.tooltipLayer.addChild(tooltip);

		if (tooltips.contains(tooltip) == false)
		{
			tooltips.add(tooltip);
		}
	}

	public void unRegister(AbstractTooltip tooltip)
	{
		tooltip.removeFromParent(false);

		tooltips.remove(tooltip);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		SharedShaderObjects.calculateProjectionView();
		Camera.getInstance().getPosition(false, cameraPos);
		
		for (AbstractTooltip tooltip : tooltips)
		{
			tooltip.getScreenPosition(outVector, cameraPos);

			tooltip.setPosition(false, outVector.x, outVector.y);
		}
	}
}
