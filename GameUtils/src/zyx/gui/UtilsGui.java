package zyx.gui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import zyx.UtilConstants;
import zyx.gui.files.FileSelector;
import zyx.gui.files.FileSelectorType;
import zyx.gui.smd.JsonComboBox;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.fnt.FntConverter;
import zyx.logic.converter.smd.SmdParser;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.watcher.WatcherManager;

public class UtilsGui extends javax.swing.JFrame implements WindowCreatedListener.IWindowOpened
{

	private final WindowCreatedListener windowCreated;

	private FntConverter fntConverter;

	private WatcherManager watcher;

	private JsonComboBox qcCombo;

	public UtilsGui()
	{
		initComponents();

		qcCombo = new JsonComboBox();
		jPanel1.add(qcCombo);

		qcCombo.addActionListener(this::actionPerformed);

		windowCreated = new WindowCreatedListener(this);
		addWindowListener(windowCreated);
	}

	private void actionPerformed(ActionEvent e)
	{
		viewJsonBtn.setEnabled(qcCombo.getSelectedIndex() > 0);
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
        jPanel1 = new javax.swing.JPanel();
        smdCompileButton = new javax.swing.JButton();
        viewJsonBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        smdCompileButton.setText("Convert");
        smdCompileButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                smdCompileButtonActionPerformed(evt);
            }
        });

        viewJsonBtn.setText("View");
        viewJsonBtn.setEnabled(false);
        viewJsonBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                viewJsonBtnActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(smdCompileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewJsonBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fntPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fntPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(smdCompileButton)
                    .addComponent(viewJsonBtn)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void smdCompileButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_smdCompileButtonActionPerformed
    {//GEN-HEADEREND:event_smdCompileButtonActionPerformed
		try
		{
			logArea.setText("");

			if (qcCombo.getSelectedIndex() == 0)
			{
				int len = qcCombo.getItemCount();
				for (int i = 1; i < len; i++)
				{
					File inputJson = qcCombo.getItemAt(i);
					JsonMesh mesh = new JsonMesh(inputJson);
					new SmdParser(mesh).parseFiles();

					logArea.append("=====\n");
				}
			}
			else
			{
				File inputJson = qcCombo.getSelectedItem();
				JsonMesh mesh = new JsonMesh(inputJson);
				new SmdParser(mesh).parseFiles();
			}
		}
		catch (IOException ex)
		{
			Logger.getLogger(UtilsGui.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (RuntimeException ex)
		{
			UtilsLogger.log("[FATAL] runtime exception", ex);
		}
    }//GEN-LAST:event_smdCompileButtonActionPerformed

    private void viewJsonBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_viewJsonBtnActionPerformed
    {//GEN-HEADEREND:event_viewJsonBtnActionPerformed
		if (qcCombo.getSelectedIndex() > 0)
		{
			File inputJson = qcCombo.getSelectedItem();
			JsonMesh mesh = new JsonMesh(inputJson);
			MeshView view = new MeshView(mesh);
			view.setVisible(true);
			view.setLocationRelativeTo(this);
		}
    }//GEN-LAST:event_viewJsonBtnActionPerformed

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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton smdCompileButton;
    private javax.swing.JButton viewJsonBtn;
    // End of variables declaration//GEN-END:variables

}
