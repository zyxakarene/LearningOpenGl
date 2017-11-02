package dev.bones;

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

	private Matrix4f bone1Rest;
	private Matrix4f bone2Rest;
	private Matrix4f bone3Rest;
	private Matrix4f bone4Rest;
	private final WorldShader shader;

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
		
		setupRest();

		Matrix4f bone1End = new Matrix4f();
		Matrix4f.mul(bone1, bone1Rest, bone1End);

		Matrix4f bone2End = new Matrix4f();
		Matrix4f.mul(bone2, bone2Rest, bone2End);
		Matrix4f.mul(bone2End, bone1End, bone2End);

		Matrix4f bone3End = new Matrix4f();
		Matrix4f.mul(bone3, bone3Rest, bone3End);
		Matrix4f.mul(bone3End, bone2End, bone3End);
		
		Matrix4f bone4End = new Matrix4f();
		Matrix4f.mul(bone4, bone4Rest, bone4End);
		Matrix4f.mul(bone4End, bone3End, bone4End);
		
		bone1.load(bone1End);
		bone2.load(bone2End);
		bone3.load(bone3End);
		bone4.load(bone4End);

	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void setupRest()
	{
		bone1Rest = new Matrix4f(bone1);
		bone2Rest = new Matrix4f(bone2);
		bone3Rest = new Matrix4f(bone1);
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
    // End of variables declaration//GEN-END:variables
}
