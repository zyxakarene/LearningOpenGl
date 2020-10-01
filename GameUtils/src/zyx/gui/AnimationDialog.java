package zyx.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import zyx.gui.files.FileSelector;
import zyx.gui.files.FileSelectorType;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.converter.smd.control.json.JsonMeshAnimation;

public class AnimationDialog extends javax.swing.JDialog
{

	private JsonMeshAnimation animation;
	private File file;
	private JsonMesh mesh;

	public AnimationDialog(Frame parent, JsonMesh mesh, JsonMeshAnimation animation)
	{
		super(parent, true);
		initComponents();
		setLocationRelativeTo(parent);

		this.animation = animation;
		this.mesh = mesh;
		nameField.setText(animation.name);
		if (animation.file != null)
		{
			file = animation.file;
			fileField.setText(animation.file.getName());
		}
		blendField.setText(String.valueOf(animation.blend));
		loopCheckbox.setSelected(animation.loop);
		frameStartField.setText(String.valueOf(animation.framesStart));
		frameEndField.setText(String.valueOf(animation.framesEnd));
		
		if (animation.isFullFileAnimation())
		{
			frameStartField.setEditable(false);
			frameEndField.setEditable(false);
			
			frameStartField.setText("");
			frameEndField.setText("");
		}

		KeyAdapter closeListener = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					setVisible(false);
					dispose();
				}
			}
		};
		
		this.addKeyListener(closeListener);
		nameField.addKeyListener(closeListener);
		blendField.addKeyListener(closeListener);
		loopCheckbox.addKeyListener(closeListener);
		frameStartField.addKeyListener(closeListener);
		frameEndField.addKeyListener(closeListener);
		saveBtn.addKeyListener(closeListener);
		locateFileBtn.addKeyListener(closeListener);
		fullFileAnimationCheck.addKeyListener(closeListener);
		
		fullFileAnimationCheck.addActionListener(this::OnChangedFullFileAnimation);
	}

	private void OnChangedFullFileAnimation(ActionEvent evt)
	{
		boolean selected = fullFileAnimationCheck.isSelected();
		
		frameStartField.setEditable(!selected);
		frameEndField.setEditable(!selected);
		
		if (selected)
		{
			frameStartField.setText("");
			frameEndField.setText("");
		}
		else
		{
			frameStartField.setText(String.valueOf(animation.framesStart));
			frameEndField.setText(String.valueOf(animation.framesEnd));
		}
	}
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fileField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        blendField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        loopCheckbox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        fullFileAnimationCheck = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        frameStartField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        frameEndField = new javax.swing.JTextField();
        locateFileBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Animation Editor");

        jPanel1.setLayout(new java.awt.GridLayout(0, 2, 0, 10));

        jLabel1.setText("Name");
        jPanel1.add(jLabel1);
        jPanel1.add(nameField);

        jLabel2.setText("File");
        jPanel1.add(jLabel2);

        fileField.setEditable(false);
        jPanel1.add(fileField);

        jLabel4.setText("Blend");
        jPanel1.add(jLabel4);
        jPanel1.add(blendField);

        jLabel3.setText("Looping");
        jPanel1.add(jLabel3);
        jPanel1.add(loopCheckbox);

        jLabel7.setText("Frames for entire file");
        jPanel1.add(jLabel7);
        jPanel1.add(fullFileAnimationCheck);

        jLabel5.setText("Frame Start");
        jPanel1.add(jLabel5);
        jPanel1.add(frameStartField);

        jLabel6.setText("Frame End");
        jPanel1.add(jLabel6);
        jPanel1.add(frameEndField);

        locateFileBtn.setText("Locate File");
        locateFileBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                locateFileBtnActionPerformed(evt);
            }
        });
        jPanel1.add(locateFileBtn);

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel1.add(saveBtn);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveBtnActionPerformed
    {//GEN-HEADEREND:event_saveBtnActionPerformed
		if (sanitize())
		{
			animation.name = nameField.getText();
			animation.file = file;
			animation.blend = Integer.parseInt(blendField.getText());
			animation.loop = loopCheckbox.isSelected();
			
			if (fullFileAnimationCheck.isSelected())
			{
				animation.setFullFileAnimation();
			}
			else
			{
				animation.framesStart = Integer.parseInt(frameStartField.getText());
				animation.framesEnd = Integer.parseInt(frameEndField.getText());
			}
		}
    }//GEN-LAST:event_saveBtnActionPerformed

    private void locateFileBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_locateFileBtnActionPerformed
    {//GEN-HEADEREND:event_locateFileBtnActionPerformed
		File result = FileSelector.openFile(this, FileSelectorType.SMD, mesh.file.getAbsolutePath());
		if (result != null)
		{
			file = result;
			fileField.setText(file.getName());
		}
    }//GEN-LAST:event_locateFileBtnActionPerformed

	private boolean sanitize()
	{
		boolean result = true;
		if (nameField.getText().isEmpty())
		{
			result = false;
			nameField.setBackground(Color.RED);
		}
		else
		{
			nameField.setBackground(Color.WHITE);
		}

		if (file == null)
		{
			result = false;
			fileField.setBackground(Color.RED);
		}
		else
		{
			fileField.setBackground(new Color(240, 240, 240));
		}

		try
		{
			if (blendField.getText().isEmpty())
			{
				blendField.setText("0");
			}
			Integer.parseInt(blendField.getText());
			blendField.setBackground(Color.WHITE);
		}
		catch (Exception e)
		{
			result = false;
			blendField.setBackground(Color.RED);
		}

		if (fullFileAnimationCheck.isSelected() == false)
		{
			try
			{
				if (frameStartField.getText().isEmpty())
				{
					frameStartField.setText("0");
				}
				Integer.parseInt(frameStartField.getText());
				frameStartField.setBackground(Color.WHITE);
			}
			catch (Exception e)
			{
				result = false;
				frameStartField.setBackground(Color.RED);
			}

			try
			{
				if (frameEndField.getText().isEmpty())
				{
					frameEndField.setText("0");
				}
				Integer.parseInt(frameEndField.getText());
				frameEndField.setBackground(Color.WHITE);
			}
			catch (Exception e)
			{
				result = false;
				frameEndField.setBackground(Color.RED);
			}
		}

		return result;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField blendField;
    private javax.swing.JTextField fileField;
    private javax.swing.JTextField frameEndField;
    private javax.swing.JTextField frameStartField;
    private javax.swing.JCheckBox fullFileAnimationCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton locateFileBtn;
    private javax.swing.JCheckBox loopCheckbox;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables

}
