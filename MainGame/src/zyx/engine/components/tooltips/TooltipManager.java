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

	private static final TooltipManager INSTANCE = new TooltipManager();

	private ArrayList<AbstractTooltip> tooltips;
	private Vector2f outVector;
	private Vector3f cameraPos;

	public static TooltipManager getInstance()
	{
		return INSTANCE;
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
		
		int len = tooltips.size();
		AbstractTooltip temp;
		AbstractTooltip next;
		for (int i = len - 1; i >= 0; i--)
		{
			next = null;
			
			temp = tooltips.get(i);
			if(i - 1 >= 0)
			{
				next = tooltips.get(i - 1);
			}
			
			if(next != null && next.currentDistance < temp.currentDistance)
			{
				Stage.instance.tooltipLayer.flipChildren(i, i-1);
				
				tooltips.set(i - 1, temp);
				tooltips.set(i, next);
			}
		}
	}

	public void clean()
	{
		Stage.instance.tooltipLayer.removeChildren(true);
		tooltips.clear();
	}
}
