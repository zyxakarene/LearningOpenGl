package zyx.debug.views.drawcalls;

import zyx.debug.views.resources.*;
import java.util.ArrayList;
import javax.swing.*;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.engine.resources.impl.DebugResourceList;
import zyx.engine.resources.impl.Resource;
import zyx.opengl.models.DebugDrawCalls;
import zyx.utils.cheats.Print;

public class DebugDrawCallPanel extends BaseDebugPanel
{

	private JLabel uiLabel;
	private JSpinner uiStepper;
	private JLabel worldLabel;
	private JSpinner worldStepper;


	public DebugDrawCallPanel()
	{
		uiLabel = new JLabel("UI");
        worldLabel = new JLabel("World");
		
        uiStepper = new JSpinner();
        worldStepper = new JSpinner();

        add(uiLabel);
        add(uiStepper);

        add(worldLabel);
        add(worldStepper);
		
		uiStepper.setValue(-1);
		worldStepper.setValue(-1);
	}

	@Override
	public void update()
	{
		DebugDrawCalls.setUiDrawLimit((int) uiStepper.getValue());
		DebugDrawCalls.setWorldDrawLimit((int) worldStepper.getValue());
	}

	@Override
	public String getTabName()
	{
		return "Draw Calls";
	}

}
