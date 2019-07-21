package zyx.debug.views;

import java.util.ArrayList;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.debug.views.drawcalls.DebugDrawCallPanel;
import zyx.debug.views.network.DebugNetworkPanel;
import zyx.debug.views.pools.DebugPoolsPanel;
import zyx.debug.views.resources.DebugResourcePanel;
import zyx.debug.views.sounds.DebugSoundPanel;

public class DebugView extends javax.swing.JFrame
{
	private ArrayList<BaseDebugPanel> panels;
	
	public DebugView()
	{
		panels = new ArrayList<>();
		
		initComponents();
		addWindowListener(new DebugWindowAdaptor(this));
		
		setSize(400, 300);
	}

	void onWindowOpened()
	{
		panels.add(new DebugNetworkPanel(DebugNetworkPanel.REQUESTS));
		panels.add(new DebugNetworkPanel(DebugNetworkPanel.RESPONSES));
		panels.add(new DebugResourcePanel());
		panels.add(new DebugSoundPanel());
		panels.add(new DebugDrawCallPanel());
		panels.add(new DebugPoolsPanel());
		
		for (BaseDebugPanel panel : panels)
		{
			tabPane.addTab(panel.getTabName(), panel);
		}
	}
	
	void update()
	{
		for (BaseDebugPanel panel : panels)
		{
			panel.update();
		}
	}
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        tabPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Debug View");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabPane;
    // End of variables declaration//GEN-END:variables

}
