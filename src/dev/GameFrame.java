package dev;

import dev.resourceloader.requests.IResourceLoaded;
import dev.resourceloader.ResourceLoader;
import dev.resourceloader.requests.ResourceRequest;
import dev.resourceloader.requests.ResourceRequestString;

public class GameFrame extends javax.swing.JFrame implements IResourceLoaded<String>
{
	public GameFrame()
	{
		initComponents();
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(317, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        ResourceRequest req = new ResourceRequestString("C:\\Users\\Rene\\Documents\\list.txt", this);
		ResourceLoader.getInstance().addRequest(req);
    }//GEN-LAST:event_jButton1ActionPerformed

	public static void main(String args[])
	{
		java.awt.EventQueue.invokeLater(() ->
		{
			ResourceLoader.getInstance().addThreads(3);
			new GameFrame().setVisible(true);
		});
		
		Thread t = new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{					
					try
					{
						Thread.sleep(16);
					}
					catch (InterruptedException ex)
					{
					}
					
					ResourceLoader.getInstance().handleResourceReplies();
				}
			}
		};
		
		t.start();
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

	@Override
	public void resourceLoaded(String text)
	{		
		System.out.println("Got " + text);
	}
}
