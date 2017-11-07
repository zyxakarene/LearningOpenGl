package dev.bones;

import dev.bones.skeleton.Joint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class SnakeControl extends javax.swing.JFrame
{

	private Joint joint1;
	private Joint joint2;
	private Joint joint3;
	private Joint joint4;
	
	private Matrix4f bone1;
	private Matrix4f bone2;
	private Matrix4f bone3;
	private Matrix4f bone4;

	private final WorldShader shader;

	private float slider1X = 0;
	private float slider2X = 0;
	private float slider3X = 0;
	private float slider4X = 0;

	private static final Vector3f SHARED_POSITION = new Vector3f();
	private static final Vector3f SHARED_ROTATION = new Vector3f();

	public SnakeControl()
	{
		initComponents();

		shader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);

		bone1 = shader.BONES[1];
		bone2 = shader.BONES[2];
		bone3 = shader.BONES[3];
		bone4 = shader.BONES[4];

//		setupBonesSnake();
		setupBonesMesh();
		SetupEvents();
		
		update();
	}

	private void update()
	{
		Matrix4f animation1 = new Matrix4f();
		Matrix4f animation2 = new Matrix4f();
		Matrix4f animation3 = new Matrix4f();
		Matrix4f animation4 = new Matrix4f();
//		transform(animation1, 0, 0, 0, 0,  slider1X, 0);
//		transform(animation2, 25, 0, 0, 0, slider2X, 0);
//		transform(animation3, 25, 0, 0, 0, slider3X, 0);
//		transform(animation4, 25, 0, 0, 0, slider4X, 0);
		
		transform(animation1, 0, 0, 0, -0.349054, -1.0979 + slider1X, 0);
		transform(animation2, 25, 0, 0, 1.26766, 0.133236 + slider2X, -0.707357);
		transform(animation3, 25, 0, 0, 1.29614, -0.682041 + slider3X, 0.45644);
		transform(animation4, 25, 0, 0, -0.39605, 0.78552 + slider4X, 0.275484);

		joint1.setAnimationTransform(animation1);
		joint2.setAnimationTransform(animation2);
		joint3.setAnimationTransform(animation3);
		joint4.setAnimationTransform(animation4);
		joint1.calcAnimationTransform(new Matrix4f());
		
//		Matrix4f bone1End = new Matrix4f();
//		transform(bone1End, 0, 0, 0, 0, -1.5708 + slider1X, 0);
//		Matrix4f.mul(bone1End, joint1.getInverse(), bone1End);
//		
//		Matrix4f bone2End = new Matrix4f(joint2.getInverse());
//		transform(bone2End, 25, 0, 0, slider2X, 0, 0);
//		Matrix4f.mul(bone2End, joint2.getInverse(), bone2End);
//		Matrix4f.mul(bone2End, bone1End, bone2End);
//
//		Matrix4f bone3End = new Matrix4f(joint3.getInverse());
//		transform(bone3End, 25, 0, 0, 0, slider3X, 0);
//		Matrix4f.mul(bone3End, joint3.getInverse(), bone3End);
//		Matrix4f.mul(bone3End, bone2End, bone3End);
//
//		Matrix4f bone4End = new Matrix4f(joint4.getInverse());
//		transform(bone4End, 25, 0, 0, 0, slider4X, 0);
//		Matrix4f.mul(bone4End, joint4.getInverse(), bone4End);
//		Matrix4f.mul(bone4End, bone3End, bone4End);
		
		bone1.load(joint1.getAnimation());
		bone2.load(joint2.getAnimation());
		bone3.load(joint3.getAnimation());
		bone4.load(joint4.getAnimation());

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
        setTitle("SNAKE");

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

	private void transform(Matrix4f bone, double x, double y, double z, double rotX, double rotY, double rotZ)
	{
		SHARED_POSITION.set((float) x, (float) y, (float) z);
		SHARED_ROTATION.set((float) rotX, (float) rotY, (float) rotZ);
		bone.translate(SHARED_POSITION);
		bone.rotate(SHARED_ROTATION.z, GeometryUtils.ROTATION_Z);
		bone.rotate(SHARED_ROTATION.y, GeometryUtils.ROTATION_Y);
		bone.rotate(SHARED_ROTATION.x, GeometryUtils.ROTATION_X);
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

	private void setupBonesSnake()
	{
		transform(bone1, 0, 0, 0, -0.349054, -1.0979, 0);
		joint1 = new Joint(bone1);
		
		transform(bone2, 25, 0, 0, 1.26766, 0.133236, -0.707357);
		joint2 = new Joint(bone2);
		
		transform(bone3, 25, 0, 0, 1.29614, -0.682041, 0.45644);
		joint3 = new Joint(bone3);
		
		transform(bone4, 25, 0, 0, -0.39605, 0.78552, 0.275484);
		joint4 = new Joint(bone4);
		
		joint1.addChild(joint2);
		joint2.addChild(joint3);
		joint3.addChild(joint4);
		
		joint1.calcInverseBindTransform(new Matrix4f());
	}
	
	private void setupBonesMesh()
	{
		transform(bone1, 0, 0, 0, 0, -1.5708, 0);
		joint1 = new Joint(bone1);
		
		transform(bone2, 25, 0, 0, 0, 0, 0);
		joint2 = new Joint(bone2);
		
		transform(bone3, 25, 0, 0, 0, 0, 0);
		joint3 = new Joint(bone3);
		
		transform(bone4, 25, 0, 0, 0, 0, 0);
		joint4 = new Joint(bone4);
		
		joint1.addChild(joint2);
		joint2.addChild(joint3);
		joint3.addChild(joint4);
		
		joint1.calcInverseBindTransform(new Matrix4f());
	}

}
