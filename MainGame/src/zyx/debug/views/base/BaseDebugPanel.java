package zyx.debug.views.base;

public abstract class BaseDebugPanel extends javax.swing.JPanel
{

	public BaseDebugPanel()
	{
		initComponents();
	}
	
	public abstract void update();
	
	public abstract String getTabName();
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
