package zyx.gui;

import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import zyx.UtilConstants;
import zyx.gui.files.FileSelector;
import zyx.gui.files.FileSelectorType;
import zyx.logic.UtilsCompiler;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.converter.smd.control.json.JsonMeshAnimation;

public class MeshView extends javax.swing.JFrame
{

	private JsonMesh mesh;

	private JList<JsonMeshAnimation> animationList;
	private DefaultListModel<JsonMeshAnimation> animationListModel;

	public MeshView(JsonMesh mesh)
	{
		this.mesh = mesh;

		initComponents();

		setTitle("MeshEditor: [" + mesh.file.getName() + "]");
		
		animationListModel = new DefaultListModel<>();
		animationList = new JList(animationListModel);
		animationScrollPane.setViewportView(animationList);
		animationList.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				handleAnimationClick(e);
			}
		});

		animationList.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				handleAnimationKeyTyped(e);
			}
		});

		cardLayoutPanel.add(skeletonPanel, skeletonPanel.getName());
		cardLayoutPanel.add(meshPanel, meshPanel.getName());

		setup();
	}

	private void handleAnimationClick(MouseEvent e)
	{
		if (e.getClickCount() == 2 && animationList.getSelectedIndex() >= 0)
		{
			new AnimationDialog(this, mesh, animationList.getSelectedValue()).setVisible(true);
		}
	}

	private void handleAnimationKeyTyped(KeyEvent e)
	{
		if (e.getKeyChar() == KeyEvent.VK_DELETE && animationList.getSelectedIndex() > -1)
		{
			JsonMeshAnimation selection = animationList.getSelectedValue();
			animationListModel.removeElement(selection);
			mesh.meshAnimations.animations.remove(selection);
		}
	}

	private void setup()
	{
		CardLayout layout = (CardLayout) cardLayoutPanel.getLayout();

		if (mesh.isMesh())
		{
			meshRadioBtn.setSelected(true);
			layout.show(cardLayoutPanel, meshPanel.getName());
		}
		else
		{
			skeletonRadioBtn.setSelected(true);
			layout.show(cardLayoutPanel, skeletonPanel.getName());
		}

		meshSkeletonTextfield.setText(mesh.meshSkeleton);
		if (mesh.meshOutput != null)
		{
			String fullPath = mesh.meshOutput.getAbsolutePath();
			String subPath = fullPath.replace(UtilConstants.ASSETS_OUTPUT, "");
			meshOutputTextfield.setText(subPath);
		}

		if (mesh.skeletonMesh != null)
		{
			String fullPath = mesh.skeletonMesh.getAbsolutePath();
			String subPath = fullPath.replace(UtilConstants.BASE_FOLDER, "");
			skeletonMeshTextfield.setText(subPath);
		}
		
		if (mesh.SkeletonOutput != null)
		{
			String fullPath = mesh.SkeletonOutput.getAbsolutePath();
			String subPath = fullPath.replace(UtilConstants.ASSETS_OUTPUT, "");
			skeletonOutputTextfield.setText(subPath);
		}

		ArrayList<JsonMeshAnimation> animations = mesh.meshAnimations.animations;
		for (JsonMeshAnimation animation : animations)
		{
			animationListModel.addElement(animation);
		}
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        typeBtnGroup = new javax.swing.ButtonGroup();
        typeLabel = new javax.swing.JLabel();
        meshRadioBtn = new javax.swing.JRadioButton();
        skeletonRadioBtn = new javax.swing.JRadioButton();
        cardLayoutPanel = new javax.swing.JPanel();
        meshPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        meshSkeletonTextfield = new javax.swing.JTextField();
        meshSkeletonBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        meshOutputTextfield = new javax.swing.JTextField();
        meshOutputBtn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        skeletonPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        skeletonMeshTextfield = new javax.swing.JTextField();
        skeletonMeshBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        skeletonOutputTextfield = new javax.swing.JTextField();
        skeletonOutputBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        animationScrollPane = new javax.swing.JScrollPane();
        addAnimationBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        compileBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mesh Editor");
        setResizable(false);

        typeLabel.setText("Type:");

        typeBtnGroup.add(meshRadioBtn);
        meshRadioBtn.setSelected(true);
        meshRadioBtn.setText("Mesh");
        meshRadioBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onTypeChanged(evt);
            }
        });

        typeBtnGroup.add(skeletonRadioBtn);
        skeletonRadioBtn.setText("Skeleton");
        skeletonRadioBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onTypeChanged(evt);
            }
        });

        cardLayoutPanel.setLayout(new java.awt.CardLayout());

        meshPanel.setName("asd"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Skeleton:");

        meshSkeletonBtn.setText("Find");
        meshSkeletonBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                meshSkeletonBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("Output:");

        meshOutputBtn.setText("Find");
        meshOutputBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                meshOutputBtnActionPerformed(evt);
            }
        });

        jButton4.setText("Mesh Files");
        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Mesh Properties");
        jButton5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(meshSkeletonTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(meshSkeletonBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(meshOutputTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(meshOutputBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(meshSkeletonTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(meshSkeletonBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(meshOutputTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(meshOutputBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 175, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout meshPanelLayout = new javax.swing.GroupLayout(meshPanel);
        meshPanel.setLayout(meshPanelLayout);
        meshPanelLayout.setHorizontalGroup(
            meshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        meshPanelLayout.setVerticalGroup(
            meshPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, meshPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        cardLayoutPanel.add(meshPanel, "card2");

        skeletonPanel.setName("asd22d"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Mesh:");

        skeletonMeshBtn.setText("Find");
        skeletonMeshBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                skeletonMeshBtnActionPerformed(evt);
            }
        });

        jLabel7.setText("Output:");

        skeletonOutputBtn.setText("Find");
        skeletonOutputBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                skeletonOutputBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skeletonMeshTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skeletonMeshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skeletonOutputTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(skeletonOutputBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(skeletonMeshTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skeletonMeshBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(skeletonOutputTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skeletonOutputBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Animations");

        addAnimationBtn.setText("Add");
        addAnimationBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addAnimationBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(animationScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 268, Short.MAX_VALUE)
                        .addComponent(addAnimationBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(animationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addAnimationBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout skeletonPanelLayout = new javax.swing.GroupLayout(skeletonPanel);
        skeletonPanel.setLayout(skeletonPanelLayout);
        skeletonPanelLayout.setHorizontalGroup(
            skeletonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        skeletonPanelLayout.setVerticalGroup(
            skeletonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(skeletonPanelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cardLayoutPanel.add(skeletonPanel, "card3");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveBtnActionPerformed(evt);
            }
        });

        compileBtn.setText("Compile");
        compileBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                compileBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(compileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(compileBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(typeLabel)
                .addGap(103, 103, 103)
                .addComponent(meshRadioBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(skeletonRadioBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(cardLayoutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(meshRadioBtn)
                    .addComponent(skeletonRadioBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cardLayoutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onTypeChanged(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onTypeChanged
    {//GEN-HEADEREND:event_onTypeChanged
		CardLayout layout = (CardLayout) cardLayoutPanel.getLayout();

		if (skeletonRadioBtn.isSelected())
		{
			mesh.type = JsonMesh.TYPE_SKELETON;
			layout.show(cardLayoutPanel, skeletonPanel.getName());
		}
		else
		{
			mesh.type = JsonMesh.TYPE_MESH;
			layout.show(cardLayoutPanel, meshPanel.getName());
		}
    }//GEN-LAST:event_onTypeChanged

    private void addAnimationBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addAnimationBtnActionPerformed
    {//GEN-HEADEREND:event_addAnimationBtnActionPerformed
		JsonMeshAnimation animation = new JsonMeshAnimation();
		new AnimationDialog(this, mesh, animation).setVisible(true);

		if (animation.file != null)
		{
			animationListModel.addElement(animation);
			mesh.meshAnimations.animations.add(animation);
		}
    }//GEN-LAST:event_addAnimationBtnActionPerformed

    private void meshSkeletonBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_meshSkeletonBtnActionPerformed
    {//GEN-HEADEREND:event_meshSkeletonBtnActionPerformed
		File file = FileSelector.openFile(this, FileSelectorType.SKELETON, UtilConstants.SKELETON_OUTPUT);
		if (file != null)
		{
			String name = file.getName();
			meshSkeletonTextfield.setText(name);
			mesh.meshSkeleton = name;
		}
    }//GEN-LAST:event_meshSkeletonBtnActionPerformed

    private void meshOutputBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_meshOutputBtnActionPerformed
    {//GEN-HEADEREND:event_meshOutputBtnActionPerformed
        File file = FileSelector.saveFile(this, FileSelectorType.ZAF, UtilConstants.MESH_OUTPUT);
		if (file != null)
		{
			String fullPath = file.getAbsolutePath();
			String subPath = fullPath.replace(UtilConstants.ASSETS_OUTPUT, "");
			meshOutputTextfield.setText(subPath);
			mesh.meshOutput = file;
		}
    }//GEN-LAST:event_meshOutputBtnActionPerformed

    private void skeletonMeshBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_skeletonMeshBtnActionPerformed
    {//GEN-HEADEREND:event_skeletonMeshBtnActionPerformed
        File file = FileSelector.openFile(this, FileSelectorType.SMD, mesh.file.getAbsolutePath());
		if (file != null)
		{
			String fullPath = file.getAbsolutePath();
			String subPath = fullPath.replace(UtilConstants.BASE_FOLDER, "");
			skeletonMeshTextfield.setText(subPath);
			mesh.skeletonMesh = file;
		}
    }//GEN-LAST:event_skeletonMeshBtnActionPerformed

    private void skeletonOutputBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_skeletonOutputBtnActionPerformed
    {//GEN-HEADEREND:event_skeletonOutputBtnActionPerformed
        File file = FileSelector.openFile(this, FileSelectorType.SKELETON, UtilConstants.SKELETON_OUTPUT);
		if (file != null)
		{
			String fullPath = file.getAbsolutePath();
			String subPath = fullPath.replace(UtilConstants.ASSETS_OUTPUT, "");
			skeletonOutputTextfield.setText(subPath);
			mesh.SkeletonOutput = file;
		}
    }//GEN-LAST:event_skeletonOutputBtnActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
    {//GEN-HEADEREND:event_jButton4ActionPerformed
        new MeshFiledDialog(this, mesh).setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton5ActionPerformed
    {//GEN-HEADEREND:event_jButton5ActionPerformed
        new MeshPropertiesDialog(this, mesh).setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveBtnActionPerformed
    {//GEN-HEADEREND:event_saveBtnActionPerformed
		try
		{
			mesh.save();
		}
		catch (IOException ex)
		{
			JOptionPane.showMessageDialog(this, "Could not save the file!");
		}
    }//GEN-LAST:event_saveBtnActionPerformed

    private void compileBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_compileBtnActionPerformed
    {//GEN-HEADEREND:event_compileBtnActionPerformed
        saveBtnActionPerformed(evt);
		UtilsCompiler.compile(mesh.file);
    }//GEN-LAST:event_compileBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAnimationBtn;
    private javax.swing.JScrollPane animationScrollPane;
    private javax.swing.JPanel cardLayoutPanel;
    private javax.swing.JButton compileBtn;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton meshOutputBtn;
    private javax.swing.JTextField meshOutputTextfield;
    private javax.swing.JPanel meshPanel;
    private javax.swing.JRadioButton meshRadioBtn;
    private javax.swing.JButton meshSkeletonBtn;
    private javax.swing.JTextField meshSkeletonTextfield;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton skeletonMeshBtn;
    private javax.swing.JTextField skeletonMeshTextfield;
    private javax.swing.JButton skeletonOutputBtn;
    private javax.swing.JTextField skeletonOutputTextfield;
    private javax.swing.JPanel skeletonPanel;
    private javax.swing.JRadioButton skeletonRadioBtn;
    private javax.swing.ButtonGroup typeBtnGroup;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
