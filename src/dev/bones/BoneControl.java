package dev.bones;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class BoneControl extends javax.swing.JFrame
{

	private Matrix4f bone1;
	private Matrix4f bone2;
	private Matrix4f bone3;
	private Matrix4f bone4;

	private Matrix4f bone1Normal;
	private Matrix4f bone2Normal;
	private Matrix4f bone3Normal;
	private Matrix4f bone4Normal;

	private Matrix4f bone1Rest;
	private Matrix4f bone2Rest;
	private Matrix4f bone3Rest;
	private Matrix4f bone4Rest;
	private final WorldShader shader;

	private float slider1X = 0;
	private float slider2X = 0;
	private float slider3X = 0;
	private float slider4X = 0;

	private static final Vector3f SHARED_POSITION = new Vector3f();
	private static final Vector3f SHARED_ROTATION = new Vector3f();

	public BoneControl()
	{
		initComponents();

		shader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);

		bone1 = shader.BONES[1];
		bone2 = shader.BONES[2];
		bone3 = shader.BONES[3];
		bone4 = shader.BONES[4];

		transform(bone1, 0, 0, 0, 0, -1.5708f, 0);
		transform(bone2, 25, 0, 0, 0, 0, 0);
		transform(bone3, 25, 0, 0, 0, 0, 0);
		transform(bone4, 25, 0, 0, 0, 0, 0);

		Matrix4f.mul(bone4, bone3, bone4);
		Matrix4f.mul(bone4, bone2, bone4);
		Matrix4f.mul(bone4, bone1, bone4);

		Matrix4f.mul(bone3, bone2, bone3);
		Matrix4f.mul(bone3, bone1, bone3);

		Matrix4f.mul(bone2, bone1, bone2);

		bone1Normal = new Matrix4f(bone1);
		bone2Normal = new Matrix4f(bone2);
		bone3Normal = new Matrix4f(bone3);
		bone4Normal = new Matrix4f(bone4);

		setupRest();
		SetupEvents();

		update();
	}

	private void update()
	{
		Matrix4f bone1End = new Matrix4f();
		Matrix4f.mul(bone1Normal, bone1Rest, bone1End);
		transform(bone1End, 0, 0, 0, slider1X, 0, 0);
		
		Matrix4f bone2End = new Matrix4f();
		Matrix4f.mul(bone2Normal, bone2Rest, bone2End);
		Matrix4f.mul(bone2End, bone1End, bone2End);
		transform(bone2End, 0, 0, 0, slider2X, 0, 0);

		Matrix4f bone3End = new Matrix4f();
		Matrix4f.mul(bone3Normal, bone3Rest, bone3End);
		Matrix4f.mul(bone3End, bone2End, bone3End);
		transform(bone3End, 0, 0, 0, slider3X, 0, 0);

		Matrix4f bone4End = new Matrix4f();
		Matrix4f.mul(bone4Normal, bone4Rest, bone4End);
		Matrix4f.mul(bone4End, bone3End, bone4End);
		transform(bone4End, 0, 0, 0, slider4X, 0, 0);

		bone1.load(bone1End);
		bone2.load(bone2End);
		bone3.load(bone3End);
		bone4.load(bone4End);

	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        slider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        slider2 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        slider3 = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        slider4 = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        slider1.setMaximum(180);
        slider1.setMinimum(-180);
        slider1.setValue(0);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bone 1");

        slider2.setMaximum(180);
        slider2.setMinimum(-180);
        slider2.setValue(0);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Bone 1");

        slider3.setMaximum(180);
        slider3.setMinimum(-180);
        slider3.setValue(0);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Bone 3");

        slider4.setMaximum(180);
        slider4.setMinimum(-180);
        slider4.setValue(0);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Bone 4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slider1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slider2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slider3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slider4, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slider1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slider2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slider3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slider4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(112, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void setupRest()
	{
		bone1Rest = new Matrix4f(bone1);
		bone2Rest = new Matrix4f(bone2);
		bone3Rest = new Matrix4f(bone3);
		bone4Rest = new Matrix4f(bone4);

		bone1Rest.invert();
		bone2Rest.invert();
		bone3Rest.invert();
		bone4Rest.invert();
	}

	private void transform(Matrix4f bone, float x, float y, float z, float rotX, float rotY, float rotZ)
	{
		SHARED_POSITION.set(x, y, z);
		SHARED_ROTATION.set(rotX, rotY, rotZ);
		bone.translate(SHARED_POSITION);
		bone.rotate(SHARED_ROTATION.x, GeometryUtils.ROTATION_X);
		bone.rotate(SHARED_ROTATION.y, GeometryUtils.ROTATION_Y);
		bone.rotate(SHARED_ROTATION.z, GeometryUtils.ROTATION_Z);
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider slider1;
    private javax.swing.JSlider slider2;
    private javax.swing.JSlider slider3;
    private javax.swing.JSlider slider4;
    // End of variables declaration//GEN-END:variables

	private void SetupEvents()
	{
		MouseMotionAdapter adap = new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				slider1X = FloatMath.toRadians(slider1.getValue());
				slider2X = FloatMath.toRadians(slider2.getValue());
				slider3X = FloatMath.toRadians(slider3.getValue());
				slider4X = FloatMath.toRadians(slider4.getValue());

				update();
			}

		};

		slider1.addMouseMotionListener(adap);
		slider2.addMouseMotionListener(adap);
		slider3.addMouseMotionListener(adap);
		slider4.addMouseMotionListener(adap);
	}

}
