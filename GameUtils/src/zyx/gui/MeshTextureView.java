package zyx.gui;

import javax.swing.JFrame;
import zyx.logic.converter.smd.control.json.JsonMeshTextureEntry;
import zyx.logic.converter.smd.control.json.JsonMeshTextures;


public class MeshTextureView extends javax.swing.JDialog
{

	private final TextureDropper diffuseDropper;
	private final TextureDropper normalDropper;
	private final TextureDropper specularDropper;
	
	private final JsonMeshTextures textures;
	
	private JsonMeshTextureEntry currentEntry;

	public MeshTextureView(JFrame parent, JsonMeshTextures textures)
	{
		super(parent, true);
		initComponents();
		
		diffuseDropper = new TextureDropper(diffuseLabel, diffuseText, TextureDropper.DIFFUSE);
		normalDropper = new TextureDropper(normalLabel, normalText, TextureDropper.NORMAL);
		specularDropper = new TextureDropper(specularLabel, specularText, TextureDropper.SPECULAR);
		
		this.textures = textures;
		
		for (JsonMeshTextureEntry entry : textures.entries)
		{
			materialDropdown.addItem(entry.name);
		}
		
		int index = materialDropdown.getSelectedIndex();
		if (index >= 0)
		{
			currentEntry = textures.entries[index];
			setupValues();
		}
	}

	private void setupValues()
	{
		diffuseDropper.setTexture(currentEntry);
		normalDropper.setTexture(currentEntry);
		specularDropper.setTexture(currentEntry);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel2 = new javax.swing.JPanel();
        materialDropdown = new javax.swing.JComboBox<>();
        texturePanel = new javax.swing.JPanel();
        diffusePreview = new javax.swing.JPanel();
        diffuseText = new javax.swing.JLabel();
        diffuseLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        diffuseClearBtn = new javax.swing.JButton();
        normalPanel = new javax.swing.JPanel();
        normalPreview = new javax.swing.JPanel();
        normalText = new javax.swing.JLabel();
        normalLabel = new javax.swing.JLabel();
        titleLabel4 = new javax.swing.JLabel();
        normalClearBtn = new javax.swing.JButton();
        specularPanel = new javax.swing.JPanel();
        specularPreview = new javax.swing.JPanel();
        specularText = new javax.swing.JLabel();
        specularLabel = new javax.swing.JLabel();
        titleLabel5 = new javax.swing.JLabel();
        specularClearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        materialDropdown.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                materialDropdownActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialDropdown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(materialDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        texturePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        diffusePreview.setBackground(new java.awt.Color(255, 255, 255));
        diffusePreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        diffusePreview.setLayout(null);

        diffuseText.setBackground(new java.awt.Color(0, 0, 0));
        diffuseText.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        diffuseText.setForeground(new java.awt.Color(255, 255, 255));
        diffuseText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        diffuseText.setText("content_texture_big");
        diffuseText.setToolTipText("");
        diffuseText.setOpaque(true);
        diffusePreview.add(diffuseText);
        diffuseText.setBounds(0, 80, 100, 13);

        diffuseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        diffuseLabel.setText("N/A");
        diffusePreview.add(diffuseLabel);
        diffuseLabel.setBounds(1, 1, 98, 98);

        titleLabel.setText("Diffuse");

        diffuseClearBtn.setText("Clear");
        diffuseClearBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                diffuseClearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout texturePanelLayout = new javax.swing.GroupLayout(texturePanel);
        texturePanel.setLayout(texturePanelLayout);
        texturePanelLayout.setHorizontalGroup(
            texturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(texturePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(texturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(diffusePreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(diffuseClearBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        texturePanelLayout.setVerticalGroup(
            texturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(texturePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diffusePreview, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diffuseClearBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        normalPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        normalPreview.setBackground(new java.awt.Color(255, 255, 255));
        normalPreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        normalPreview.setLayout(null);

        normalText.setBackground(new java.awt.Color(0, 0, 0));
        normalText.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        normalText.setForeground(new java.awt.Color(255, 255, 255));
        normalText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        normalText.setText("content_texture_big");
        normalText.setToolTipText("");
        normalText.setOpaque(true);
        normalPreview.add(normalText);
        normalText.setBounds(0, 80, 100, 13);

        normalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        normalLabel.setText("N/A");
        normalPreview.add(normalLabel);
        normalLabel.setBounds(1, 1, 98, 98);

        titleLabel4.setText("Normal");

        normalClearBtn.setText("Clear");
        normalClearBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                normalClearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout normalPanelLayout = new javax.swing.GroupLayout(normalPanel);
        normalPanel.setLayout(normalPanelLayout);
        normalPanelLayout.setHorizontalGroup(
            normalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(normalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(normalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(titleLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(normalPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(normalClearBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        normalPanelLayout.setVerticalGroup(
            normalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(normalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(normalPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(normalClearBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        specularPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        specularPreview.setBackground(new java.awt.Color(255, 255, 255));
        specularPreview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        specularPreview.setLayout(null);

        specularText.setBackground(new java.awt.Color(0, 0, 0));
        specularText.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        specularText.setForeground(new java.awt.Color(255, 255, 255));
        specularText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        specularText.setText("content_texture_big");
        specularText.setToolTipText("");
        specularText.setOpaque(true);
        specularPreview.add(specularText);
        specularText.setBounds(0, 80, 100, 13);

        specularLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        specularLabel.setText("N/A");
        specularPreview.add(specularLabel);
        specularLabel.setBounds(1, 1, 98, 98);

        titleLabel5.setText("Specular");

        specularClearBtn.setText("Clear");
        specularClearBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                specularClearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout specularPanelLayout = new javax.swing.GroupLayout(specularPanel);
        specularPanel.setLayout(specularPanelLayout);
        specularPanelLayout.setHorizontalGroup(
            specularPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specularPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(specularPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(titleLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(specularPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(specularClearBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        specularPanelLayout.setVerticalGroup(
            specularPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specularPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specularPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specularClearBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(texturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(normalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specularPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(normalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(texturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(specularPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void diffuseClearBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_diffuseClearBtnActionPerformed
    {//GEN-HEADEREND:event_diffuseClearBtnActionPerformed
        diffuseDropper.clear();
    }//GEN-LAST:event_diffuseClearBtnActionPerformed

    private void normalClearBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalClearBtnActionPerformed
    {//GEN-HEADEREND:event_normalClearBtnActionPerformed
        normalDropper.clear();
    }//GEN-LAST:event_normalClearBtnActionPerformed

    private void specularClearBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_specularClearBtnActionPerformed
    {//GEN-HEADEREND:event_specularClearBtnActionPerformed
        specularDropper.clear();
    }//GEN-LAST:event_specularClearBtnActionPerformed

    private void materialDropdownActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_materialDropdownActionPerformed
    {//GEN-HEADEREND:event_materialDropdownActionPerformed
        int index = materialDropdown.getSelectedIndex();
        if (index >= 0)
        {
            currentEntry = textures.entries[index];
        }
        setupValues();
    }//GEN-LAST:event_materialDropdownActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton diffuseClearBtn;
    private javax.swing.JLabel diffuseLabel;
    private javax.swing.JPanel diffusePreview;
    private javax.swing.JLabel diffuseText;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> materialDropdown;
    private javax.swing.JButton normalClearBtn;
    private javax.swing.JLabel normalLabel;
    private javax.swing.JPanel normalPanel;
    private javax.swing.JPanel normalPreview;
    private javax.swing.JLabel normalText;
    private javax.swing.JButton specularClearBtn;
    private javax.swing.JLabel specularLabel;
    private javax.swing.JPanel specularPanel;
    private javax.swing.JPanel specularPreview;
    private javax.swing.JLabel specularText;
    private javax.swing.JPanel texturePanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel titleLabel4;
    private javax.swing.JLabel titleLabel5;
    // End of variables declaration//GEN-END:variables
}
