package dev.bones;

import dev.bones.animation.Animation;
import dev.bones.animation.AnimationFrame;
import dev.bones.skeleton.Joint;
import dev.bones.skeleton.Skeleton;
import dev.bones.transform.JointTransform;
import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.WorldShader;

public class SnakeControl extends javax.swing.JFrame
{

	private Matrix4f bone1;
	private Matrix4f bone2;
	private Matrix4f bone3;
	private Matrix4f bone4;

	private final WorldShader shader;
	private Skeleton skeleton;

	public SnakeControl()
	{
		initComponents();

		shader = (WorldShader) ShaderManager.INSTANCE.get(Shader.WORLD);

		bone1 = shader.BONES[1];
		bone2 = shader.BONES[2];
		bone3 = shader.BONES[3];
		bone4 = shader.BONES[4];

		createSkeleton();
		SetupThread();

		update();
	}

	private void createSkeleton()
	{
		String animationName = "idle";
		
		JointTransform bone1Transform = new JointTransform(0, 0, 0, 0, -1.5708f, 0);
		Joint skeletonJoint1 = new Joint(1, "bone1", bone1Transform, bone1);

		JointTransform bone2Transform = new JointTransform(25, 0, 0, 0, 0, 0);
		Joint skeletonJoint2 = new Joint(2, "bone2", bone2Transform, bone2);

		JointTransform bone3Transform = new JointTransform(25, 0, 0, 0, 0, 0);
		Joint skeletonJoint3 = new Joint(3, "bone3", bone3Transform, bone3);

		JointTransform bone4Transform = new JointTransform(25, 0, 0, 0, 0, 0);
		Joint skeletonJoint4 = new Joint(4, "bone4", bone4Transform, bone4);

		skeletonJoint1.addChild(skeletonJoint2);
		skeletonJoint2.addChild(skeletonJoint3);
		skeletonJoint3.addChild(skeletonJoint4);
		
		skeleton = new Skeleton(skeletonJoint1);
		
		Animation idleAnimation = new Animation(animationName, 5);
		AnimationFrame frame0 = new AnimationFrame();
		AnimationFrame frame1 = new AnimationFrame();
		AnimationFrame frame2 = new AnimationFrame();
		AnimationFrame frame3 = new AnimationFrame();
		AnimationFrame frame4 = new AnimationFrame();
		
		frame0.addTransform(skeletonJoint1.name, new JointTransform(0, 0, 0, 0, -1.5708f, 0));
		frame0.addTransform(skeletonJoint2.name, new JointTransform(25, 0, 0, 0, 1.5708f, 0));
		frame0.addTransform(skeletonJoint3.name, new JointTransform(25, 0, 0, 0, -1.5708f, 0));
		frame0.addTransform(skeletonJoint4.name, new JointTransform(25, 0, 0, 0, -1, 0));
		
		frame1.addTransform(skeletonJoint1.name, new JointTransform(0, 0, 0, 0, -1.0472f, 0));
		frame1.addTransform(skeletonJoint2.name, new JointTransform(25, 0, 0, 0, 1.0472f, 0));
		frame1.addTransform(skeletonJoint3.name, new JointTransform(25, 0, 0, 0, -1.0472f, 0));
		frame1.addTransform(skeletonJoint4.name, new JointTransform(25, 0, 0, 0, 0.5f, 0));
		
		frame2.addTransform(skeletonJoint1.name, new JointTransform(0, 0, 0, 0, -0.523599f, 0));
		frame2.addTransform(skeletonJoint2.name, new JointTransform(25, 0, 0, 0, 0.523599f, 0));
		frame2.addTransform(skeletonJoint3.name, new JointTransform(25, 0, 0, 0, -0.523599f, 0));
		frame2.addTransform(skeletonJoint4.name, new JointTransform(25, 0, 0, 0, 0.75f, 0));
		
		frame3.addTransform(skeletonJoint1.name, new JointTransform(0, 0, 0, 0, -1.0472f, 0));
		frame3.addTransform(skeletonJoint2.name, new JointTransform(25, 0, 0, 0, 1.0472f, 0));
		frame3.addTransform(skeletonJoint3.name, new JointTransform(25, 0, 0, 0, -1.0472f, 0));
		frame3.addTransform(skeletonJoint4.name, new JointTransform(25, 0, 0, 0, -2, 0));
		
		frame4.addTransform(skeletonJoint1.name, new JointTransform(0, 0, 0, 0, -1.5708f, 0));
		frame4.addTransform(skeletonJoint2.name, new JointTransform(25, 0, 0, 0, 1.5708f, 0));
		frame4.addTransform(skeletonJoint3.name, new JointTransform(25, 0, 0, 0, -1.5708f, 0));
		frame4.addTransform(skeletonJoint4.name, new JointTransform(25, 0, 0, 0, 1, 0));
		
		idleAnimation.setFrame(0, frame0);
		idleAnimation.setFrame(1, frame1);
		idleAnimation.setFrame(2, frame2);
		idleAnimation.setFrame(3, frame3);
		idleAnimation.setFrame(4, frame4);
		
		skeleton.addAnimation(animationName, idleAnimation);
		skeleton.setCurrentAnimation(animationName);
	}

	private void update()
	{
		skeleton.update(System.currentTimeMillis(), 16);
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

	private void SetupThread()
	{
		Thread t = new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{					
					try
					{
						Thread.sleep(16);
					}
					catch (InterruptedException ex)
					{
					}
					
					update();
				}
			}
		};
		
		t.setDaemon(true);
		t.start();
	}
}
