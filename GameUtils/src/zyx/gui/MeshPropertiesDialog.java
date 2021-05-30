package zyx.gui;

import java.awt.Frame;
import java.util.HashMap;
import javax.swing.JComboBox;
import org.lwjgl.opengl.GL11;
import zyx.logic.converter.smd.control.json.JsonMeshProperties;
import zyx.logic.converter.smd.control.json.JsonMeshPropertyEntry;


public class MeshPropertiesDialog extends javax.swing.JDialog
{

	private JsonMeshProperties properties;
	private JsonMeshPropertyEntry currentEntry;
	
	public MeshPropertiesDialog(Frame parent, JsonMeshProperties properties)
	{
		super(parent, true);
		initComponents();
		
		this.properties = properties;
		
		for (int i = 0; i < properties.entries.length; i++)
		{
			JsonMeshPropertyEntry entry = properties.entries[i];
			materialDropdown.addItem(entry.name);
		}
		
		buildComboBoxes();
		
		int index = materialDropdown.getSelectedIndex();
		if (index >= 0)
		{
			currentEntry = properties.entries[index];
			setupValues();
		}
		
		setLocationRelativeTo(parent);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        zWriteCheck = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        zTestCombo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cullingCombo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        blendCombo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        prioritySpinner = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        stencilModeCombo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        stencilLayerCombo = new javax.swing.JComboBox<>();
        saveButton = new javax.swing.JButton();
        materialDropdown = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mesh Properties");

        jPanel1.setLayout(new java.awt.GridLayout(0, 2, 0, 10));

        jLabel3.setText("Z Write");
        jPanel1.add(jLabel3);
        jPanel1.add(zWriteCheck);

        jLabel1.setText("Z Test");
        jPanel1.add(jLabel1);

        jPanel1.add(zTestCombo);

        jLabel2.setText("Culling");
        jPanel1.add(jLabel2);

        jPanel1.add(cullingCombo);

        jLabel4.setText("Blend Mode");
        jPanel1.add(jLabel4);

        jPanel1.add(blendCombo);

        jLabel5.setText("Priority");
        jPanel1.add(jLabel5);
        jPanel1.add(prioritySpinner);

        jLabel6.setText("Stencil Mode");
        jPanel1.add(jLabel6);

        jPanel1.add(stencilModeCombo);

        jLabel7.setText("Stencil Layer");
        jPanel1.add(jLabel7);

        jPanel1.add(stencilLayerCombo);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveButtonActionPerformed(evt);
            }
        });

        materialDropdown.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                materialDropdownActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(materialDropdown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(materialDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveButtonActionPerformed
    {//GEN-HEADEREND:event_saveButtonActionPerformed
        currentEntry.zWrite = zWriteCheck.isSelected();
		currentEntry.zTest = zTestToValue.get(zTestCombo.getSelectedItem().toString());
		currentEntry.culling = cullingToValue.get(cullingCombo.getSelectedItem().toString());
		SimplePoint blend = blendToValues.get(blendCombo.getSelectedItem().toString());
		currentEntry.blendSrc = blend.a;
		currentEntry.blendDst = blend.b;
		currentEntry.priority = Integer.valueOf(prioritySpinner.getValue().toString());
		currentEntry.stencilMode = stencilModeToValue.get(stencilModeCombo.getSelectedItem().toString());
		currentEntry.stencilLayer = stencilLayerToValue.get(stencilLayerCombo.getSelectedItem().toString());
    }//GEN-LAST:event_saveButtonActionPerformed

    private void materialDropdownActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_materialDropdownActionPerformed
    {//GEN-HEADEREND:event_materialDropdownActionPerformed
        int index = materialDropdown.getSelectedIndex();
		if (index >= 0)
		{
			currentEntry = properties.entries[index];
		}
		setupValues();
    }//GEN-LAST:event_materialDropdownActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> blendCombo;
    private javax.swing.JComboBox<String> cullingCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> materialDropdown;
    private javax.swing.JSpinner prioritySpinner;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox<String> stencilLayerCombo;
    private javax.swing.JComboBox<String> stencilModeCombo;
    private javax.swing.JComboBox<String> zTestCombo;
    private javax.swing.JCheckBox zWriteCheck;
    // End of variables declaration//GEN-END:variables

	private HashMap<String, Integer> zTestToValue = new HashMap<>();
	private HashMap<String, Integer> cullingToValue = new HashMap<>();
	private HashMap<String, SimplePoint> blendToValues = new HashMap<>();
	private HashMap<String, Byte> stencilModeToValue = new HashMap<>();
	private HashMap<String, Integer> stencilLayerToValue = new HashMap<>();
	
	private HashMap<Integer, String> valueToZTest = new HashMap<>();
	private HashMap<Integer, String> valueToCulling = new HashMap<>();
	private HashMap<SimplePoint, String> valueToBlend = new HashMap<>();
	private HashMap<Byte, String> valueToStencilMode = new HashMap<>();
	private HashMap<Integer, String> valueToStencilLayer = new HashMap<>();
	
	private void buildComboBoxes()
	{
		addCombo(zTestToValue, valueToZTest, zTestCombo, "Less", GL11.GL_LESS);
		addCombo(zTestToValue, valueToZTest, zTestCombo, "Equals", GL11.GL_EQUAL);
		addCombo(zTestToValue, valueToZTest, zTestCombo, "Less Equals", GL11.GL_LEQUAL);
		addCombo(zTestToValue, valueToZTest, zTestCombo, "Greater", GL11.GL_GREATER);
		addCombo(zTestToValue, valueToZTest, zTestCombo, "Not Equals", GL11.GL_NOTEQUAL);
		addCombo(zTestToValue, valueToZTest, zTestCombo, "Greater Equals", GL11.GL_GEQUAL);
		addCombo(zTestToValue, valueToZTest, zTestCombo, "Always", GL11.GL_ALWAYS);
		
		addCombo(cullingToValue, valueToCulling, cullingCombo, "Front", GL11.GL_FRONT);
		addCombo(cullingToValue, valueToCulling, cullingCombo, "Back", GL11.GL_BACK);
		addCombo(cullingToValue, valueToCulling, cullingCombo, "Front & Back", GL11.GL_FRONT_AND_BACK);
		addCombo(cullingToValue, valueToCulling, cullingCombo, "None", GL11.GL_NONE);
		
		addCombo(blendToValues, valueToBlend, blendCombo, "Normal", new SimplePoint(GL11.GL_ONE, GL11.GL_ZERO));
		addCombo(blendToValues, valueToBlend, blendCombo, "Particles", new SimplePoint(GL11.GL_SRC_ALPHA, GL11.GL_ONE));
		addCombo(blendToValues, valueToBlend, blendCombo, "Alpha", new SimplePoint(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA));
		addCombo(blendToValues, valueToBlend, blendCombo, "Additive", new SimplePoint(GL11.GL_ONE, GL11.GL_ONE));
		
		addCombo(stencilModeToValue, valueToStencilMode, stencilModeCombo, "Nothing", (byte)0);
		addCombo(stencilModeToValue, valueToStencilMode, stencilModeCombo, "Writing", (byte)1);
		addCombo(stencilModeToValue, valueToStencilMode, stencilModeCombo, "Masking", (byte)2);
		
		addCombo(stencilLayerToValue, valueToStencilLayer, stencilLayerCombo, "Nothing", 0);
		addCombo(stencilLayerToValue, valueToStencilLayer, stencilLayerCombo, "Player Character", 1 << 0);
	}

	private void setupValues()
	{
		zWriteCheck.setSelected(currentEntry.zWrite);
		zTestCombo.setSelectedItem(valueToZTest.get(currentEntry.zTest));
		cullingCombo.setSelectedItem(valueToCulling.get(currentEntry.culling));
		blendCombo.setSelectedItem(valueToBlend.get(new SimplePoint(currentEntry.blendSrc, currentEntry.blendDst)));
		prioritySpinner.setValue(currentEntry.priority);
		stencilModeCombo.setSelectedItem(valueToStencilMode.get(currentEntry.stencilMode));
		stencilLayerCombo.setSelectedItem(valueToStencilLayer.get(currentEntry.stencilLayer));
	}

	private <T> void addCombo(HashMap<String, T> labelToValue, HashMap<T, String> valueToLabel, JComboBox<String> combobox, String label, T value)
	{
		labelToValue.put(label, value);
		valueToLabel.put(value, label);
		combobox.addItem(label);
	}
	
	private class SimplePoint
	{
		public int a;
		public int b;

		public SimplePoint(int a, int b)
		{
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof SimplePoint)
			{
				SimplePoint p = (SimplePoint) obj;
				return a == p.a && b == p.b;
			}
			
			return false;
		}

		@Override
		public int hashCode()
		{
			int hash = 7;
			hash = 79 * hash + this.a;
			hash = 79 * hash + this.b;
			return hash;
		}
	}
}
