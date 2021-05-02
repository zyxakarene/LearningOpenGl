package zyx.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JPopupMenu;
import zyx.UtilConstants;
import zyx.gui.files.FileSelector;
import zyx.gui.files.FileSelectorType;
import zyx.gui.tree.JsonTree;
import zyx.logic.UtilsCompiler;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.fnt.FntConverter;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.watcher.WatcherManager;

public class UtilsGui extends javax.swing.JFrame implements WindowCreatedListener.IWindowOpened
{

	private final WindowCreatedListener windowCreated;

	private FntConverter fntConverter;

	private WatcherManager watcher;
	
	private JsonTree jsonTree;

	public UtilsGui()
	{
		initComponents();
		UtilsCompiler.logArea = logArea;
		
		jsonTree = new JsonTree();
		jsonMeshScrollPanel.setViewportView(jsonTree);
		jsonTree.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == 3)
				{
					JPopupMenu menu = new JPopupMenu();
					menu.add("View");
					menu.add("Compile");
					menu.show(jsonTree, e.getX(), e.getY());
				}
				else if (e.getClickCount() == 2)
				{
					handleClickTree();
				}
			}
		});
		
		windowCreated = new WindowCreatedListener(this);
		addWindowListener(windowCreated);
	}

	private void handleClickTree()
	{
		File selectedItem = jsonTree.getSelectedItem();
		if (selectedItem != null && selectedItem.isFile())
		{
			JsonMesh mesh = new JsonMesh(selectedItem);
			MeshView view = new MeshView(mesh);
			view.setVisible(true);
			view.setLocationRelativeTo(this);
		}
	}
	
	@Override
	public void windowOpened()
	{
		removeWindowListener(windowCreated);

		fntConverter = new FntConverter(fntPanel);

		UtilsLogger.setOutput(logArea);

		watcher = new WatcherManager(fntConverter);
		watcher.initialize();
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        logArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        fntPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        smdCompileButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jsonMeshScrollPanel = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GameUtils");

        logArea.setColumns(20);
        logArea.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        logArea.setRows(5);
        jScrollPane1.setViewportView(logArea);

        jLabel2.setText("Log");

        fntPanel.setBackground(new java.awt.Color(255, 255, 255));
        fntPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(".fnt Converter");

        javax.swing.GroupLayout fntPanelLayout = new javax.swing.GroupLayout(fntPanel);
        fntPanel.setLayout(fntPanelLayout);
        fntPanelLayout.setHorizontalGroup(
            fntPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fntPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        fntPanelLayout.setVerticalGroup(
            fntPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fntPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        smdCompileButton.setText("Convert");
        smdCompileButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                smdCompileButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Create");
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsonMeshScrollPanel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fntPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(smdCompileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsonMeshScrollPanel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fntPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(smdCompileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 76, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void smdCompileButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_smdCompileButtonActionPerformed
    {//GEN-HEADEREND:event_smdCompileButtonActionPerformed
		File[] files = jsonTree.getSelectedSubItems();
		UtilsCompiler.compile(files);
    }//GEN-LAST:event_smdCompileButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
		File file = FileSelector.saveFile(this, FileSelectorType.JSON, UtilConstants.MESH_FOLDER);
		if (file != null)
		{
			MeshView view = new MeshView(new JsonMesh(file));
			view.setVisible(true);
			view.setLocationRelativeTo(this);
		}
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fntPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jsonMeshScrollPanel;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton smdCompileButton;
    // End of variables declaration//GEN-END:variables

}
