/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyx.debug.views.base;

import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class BaseDebugPanel extends JPanel
{

	public BaseDebugPanel(LayoutManager layout)
	{
		super(layout);
	}
	
	public BaseDebugPanel()
	{
		BoxLayout defaultLayout = new BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS);
		setLayout(defaultLayout);
	}

	public abstract void update();

	public abstract String getTabName();
}
