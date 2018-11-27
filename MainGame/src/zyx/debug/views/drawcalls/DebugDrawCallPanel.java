package zyx.debug.views.drawcalls;

import javax.swing.*;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.opengl.models.DebugDrawCalls;

public class DebugDrawCallPanel extends BaseDebugPanel
{
	private static final String CURRENT = "current: ";
	
	private JLabel uiLabel;
	private JSpinner uiStepper;
	private JLabel worldLabel;
	private JSpinner worldStepper;

	private JLabel uiDrawCalls;
	private JLabel worldDrawCalls;

	public DebugDrawCallPanel()
	{
		setLayout(null);
		
		uiLabel = new JLabel("UI");
        worldLabel = new JLabel("World");
		
		uiDrawCalls = new JLabel("UI: 0");
        worldDrawCalls = new JLabel("World: 0");
		
		SpinnerNumberModel uiModel = new SpinnerNumberModel(-1, -1, Integer.MAX_VALUE, 1);
		SpinnerNumberModel worldModel = new SpinnerNumberModel(-1, -1, Integer.MAX_VALUE, 1);
        uiStepper = new JSpinner(uiModel);
        worldStepper = new JSpinner(worldModel);

		worldLabel.setBounds(10, 10, 50, 20);
		worldStepper.setBounds(60, 10, 100, 20);

		uiLabel.setBounds(10, 40, 50, 20);
		uiStepper.setBounds(60, 40, 100, 20);

		worldDrawCalls.setBounds(170, 10, 100, 20);
		uiDrawCalls.setBounds(170, 40, 100, 20);
		
        add(uiLabel);
        add(uiStepper);

        add(worldLabel);
        add(worldStepper);

        add(uiDrawCalls);
        add(worldDrawCalls);
	}

	@Override
	public void update()
	{
		DebugDrawCalls.setUiDrawLimit((int) uiStepper.getValue());
		DebugDrawCalls.setWorldDrawLimit((int) worldStepper.getValue());
		
		worldDrawCalls.setText(CURRENT + DebugDrawCalls.getCurrentDrawWorld());
		uiDrawCalls.setText(CURRENT + DebugDrawCalls.getCurrentDrawUi());
	}

	@Override
	public String getTabName()
	{
		return "Draw Calls";
	}

}
