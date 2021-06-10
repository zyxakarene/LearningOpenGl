package zyx.gui;

import java.awt.Frame;
import java.io.File;
import javax.swing.JTextField;
import zyx.UtilConstants;
import zyx.gui.files.FileSelector;
import zyx.gui.files.FileSelectorType;
import zyx.logic.converter.json.JsonMesh;
import zyx.logic.converter.smd.SmdFileParser;
import zyx.logic.converter.smd.parsedVo.ParsedSmdFile;

public class MeshFilesDialog extends javax.swing.JDialog
{

	private JsonMesh mesh;

	private File meshFile;
	private File physFile;
	private File boundingFile;
	
	public MeshFilesDialog(Frame parent, JsonMesh mesh)
	{
		super(parent, true);
		initComponents();
		
		setLocationRelativeTo(parent);
		
		this.mesh = mesh;
		meshFile = mesh.meshFiles.meshFile;
		physFile = mesh.meshFiles.physFile;
		boundingFile = mesh.meshFiles.boundingFile;
		
		setText(meshField, meshFile);
		setText(physField, physFile);
		setText(boundingField, boundingFile);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        saveBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        meshField = new javax.swing.JTextField();
        meshBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        physBtn = new javax.swing.JButton();
        physField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        boundingBtn = new javax.swing.JButton();
        boundingField = new javax.swing.JTextField();
        meshClearBtn = new javax.swing.JButton();
        physClearBtn = new javax.swing.JButton();
        boundingClearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mesh Files");

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Mesh");

        meshField.setEditable(false);

        meshBtn.setText("Find");
        meshBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                meshBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Phys");

        physBtn.setText("Find");
        physBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                physBtnActionPerformed(evt);
            }
        });

        physField.setEditable(false);

        jLabel3.setText("Bounding");

        boundingBtn.setText("Find");
        boundingBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                boundingBtnActionPerformed(evt);
            }
        });

        boundingField.setEditable(false);

        meshClearBtn.setText("Clear");
        meshClearBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                meshClearBtnActionPerformed(evt);
            }
        });

        physClearBtn.setText("Clear");
        physClearBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                physClearBtnActionPerformed(evt);
            }
        });

        boundingClearBtn.setText("Clear");
        boundingClearBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                boundingClearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(meshField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(physField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boundingField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(meshClearBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(meshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(physClearBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(physBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(boundingClearBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boundingBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(meshField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(meshBtn)
                    .addComponent(meshClearBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(physField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(physBtn)
                    .addComponent(physClearBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(boundingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boundingBtn)
                    .addComponent(boundingClearBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveBtn)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void meshBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_meshBtnActionPerformed
    {//GEN-HEADEREND:event_meshBtnActionPerformed
		File selectedFile = FileSelector.openFile(this, FileSelectorType.SMD, mesh.file.getAbsolutePath());
		if (selectedFile != null)
		{
			meshFile = selectedFile;
			setText(meshField, meshFile);
			
			ParsedSmdFile parsedFile = SmdFileParser.parseFile(meshFile);
			mesh.meshProperties.setSize(parsedFile.surfaces);
			mesh.textureFiles.setSize(parsedFile.surfaces);
			
		}
    }//GEN-LAST:event_meshBtnActionPerformed

    private void physBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_physBtnActionPerformed
    {//GEN-HEADEREND:event_physBtnActionPerformed
        File selectedFile = FileSelector.openFile(this, FileSelectorType.SMD, mesh.file.getAbsolutePath());
		if (selectedFile != null)
		{
			physFile = selectedFile;
			setText(physField, physFile);
		}
    }//GEN-LAST:event_physBtnActionPerformed

    private void boundingBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_boundingBtnActionPerformed
    {//GEN-HEADEREND:event_boundingBtnActionPerformed
        File selectedFile = FileSelector.openFile(this, FileSelectorType.SMD, mesh.file.getAbsolutePath());
		if (selectedFile != null)
		{
			boundingFile = selectedFile;
			setText(boundingField, boundingFile);
		}
    }//GEN-LAST:event_boundingBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveBtnActionPerformed
    {//GEN-HEADEREND:event_saveBtnActionPerformed
        mesh.meshFiles.meshFile = meshFile;
        mesh.meshFiles.physFile = physFile;
        mesh.meshFiles.boundingFile = boundingFile;
    }//GEN-LAST:event_saveBtnActionPerformed

    private void meshClearBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_meshClearBtnActionPerformed
    {//GEN-HEADEREND:event_meshClearBtnActionPerformed
        meshField.setText("");
		meshFile = null;
    }//GEN-LAST:event_meshClearBtnActionPerformed

    private void physClearBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_physClearBtnActionPerformed
    {//GEN-HEADEREND:event_physClearBtnActionPerformed
        physField.setText("");
		physFile = null;
    }//GEN-LAST:event_physClearBtnActionPerformed

    private void boundingClearBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_boundingClearBtnActionPerformed
    {//GEN-HEADEREND:event_boundingClearBtnActionPerformed
        boundingField.setText("");
		boundingFile = null;
    }//GEN-LAST:event_boundingClearBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boundingBtn;
    private javax.swing.JButton boundingClearBtn;
    private javax.swing.JTextField boundingField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton meshBtn;
    private javax.swing.JButton meshClearBtn;
    private javax.swing.JTextField meshField;
    private javax.swing.JButton physBtn;
    private javax.swing.JButton physClearBtn;
    private javax.swing.JTextField physField;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables

	private void setText(JTextField field, File file)
	{
		String text;
		if (file == null || file.isDirectory() || file.exists() == false)
		{
			text = "";
		}
		else
		{
			text = file.getAbsolutePath().replace(UtilConstants.BASE_FOLDER, "");
		}
		
		field.setText(text);
	}
}
