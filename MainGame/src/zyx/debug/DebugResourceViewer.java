package zyx.debug;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import zyx.engine.resources.impl.DebugResourceList;
import zyx.engine.resources.impl.Resource;

public class DebugResourceViewer extends javax.swing.JFrame implements Runnable
{
	private static final Object LOCK = new Object();
	
	private final Thread updater;
	private boolean active;
	private ArrayList<Resource> out;

	public DebugResourceViewer()
	{
		initComponents();

		list.setModel(new DefaultListModel<>());
		
		active = true;
		
		out = new ArrayList<>();
		
		updater = new Thread(this);
		updater.setDaemon(true);
		updater.setPriority(2);

		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				active = false;
			}

			@Override
			public void windowOpened(WindowEvent e)
			{
				updater.start();
			}
		});
	}

	@Override
	public void paint(Graphics g)
	{
		synchronized(LOCK)
		{
			super.paint(g);
		}
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(list);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> list;
    // End of variables declaration//GEN-END:variables

	@Override
	public void run()
	{
		while (active)
		{
			synchronized(LOCK)
			{
				boolean changes = DebugResourceList.getActiveResources(out);

				if (changes)
				{
					DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
					model.removeAllElements();

					for (Resource resource : out)
					{
						model.addElement(resource.path);
					}
				}

				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException ex)
				{
				}
			}
		}
	}
}
