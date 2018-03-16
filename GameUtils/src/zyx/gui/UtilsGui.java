package zyx.gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import zyx.gui.smd.QcComboBox;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.fnt.FntConverter;
import zyx.logic.converter.smd.SmdParser;
import zyx.logic.converter.smd.control.QcFile;
import zyx.logic.converter.smd.control.QcParser;
import zyx.logic.watcher.WatcherManager;

public class UtilsGui extends javax.swing.JFrame implements WindowCreatedListener.IWindowOpened
{

	private final WindowCreatedListener windowCreated;
	
	private FntConverter fntConverter;
	
	private WatcherManager watcher;
	
	private QcComboBox qcCombo;

	public UtilsGui()
	{
		initComponents();
		
		qcCombo = new QcComboBox();
		jPanel1.add(qcCombo);

		windowCreated = new WindowCreatedListener(this);
		addWindowListener(windowCreated);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logArea.setColumns(20);
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
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fntPanelLayout.setVerticalGroup(
            fntPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fntPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(smdCompileButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fntPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fntPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(smdCompileButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void smdCompileButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_smdCompileButtonActionPerformed
    {//GEN-HEADEREND:event_smdCompileButtonActionPerformed
		try
		{
			File inputQc = qcCombo.getSelectedItem();
			QcFile parsedQc = new QcParser().parseFile(inputQc);
			new SmdParser(parsedQc).parseFiles();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fntPanel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logArea;
    private javax.swing.JButton smdCompileButton;
    // End of variables declaration//GEN-END:variables

}
