package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.SharedWorldModelTransformation;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.attachments.Attachment;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.textures.MissingTexture;
import zyx.utils.DeltaTime;

public class WorldModel extends AbstractModel
{
	private static final Vector3f ATTACHMENT_POSITION = new Vector3f(0, 0, 0);
	private static final Vector3f ATTACHMENT_ROTATION = new Vector3f(0, 0, 0);
	private static final Vector3f ATTACHMENT_SCALE = new Vector3f(1, 1, 1);

	private static final Matrix4f MODEL_MATRIX = WorldShader.MATRIX_MODEL;

	private WorldShader shader;
	private Skeleton skeleton;
	
	private PhysBox physBox;

	public WorldModel(LoadableValueObject vo)
	{
		super(Shader.WORLD);
		this.shader = (WorldShader) meshShader;

		skeleton = vo.skeleton;
		physBox = vo.physBox;
		setVertexData(vo.vertexData, vo.elementData);
		setTexture(vo.gameTexture);
	}

	public Joint getAttatchment(String name)
	{
		return skeleton.getBoneByName(name);
	}

	public void setAnimation(AnimationController controller)
	{
		skeleton.setCurrentAnimation(controller);
	}

	@Override
	public void draw()
	{
		skeleton.update(DeltaTime.getTimestamp(), DeltaTime.getElapsedTime());
		shader.uploadBones();
		super.draw();
	}

	public PhysBox getPhysbox()
	{
		return physBox;
	}
		
	public void drawAsAttachment(Attachment attachment)
	{
		skeleton.update(DeltaTime.getTimestamp(), DeltaTime.getElapsedTime());
		Matrix4f bonePosCopy = new Matrix4f(attachment.joint.getFinalTransform());
		Matrix4f.mul(MODEL_MATRIX, bonePosCopy, MODEL_MATRIX);

		attachment.child.getPosition(true, ATTACHMENT_POSITION);
		attachment.child.getRotation(true, ATTACHMENT_ROTATION);
		attachment.child.getScale(true, ATTACHMENT_SCALE);
		
		SharedWorldModelTransformation.transform(ATTACHMENT_POSITION, ATTACHMENT_ROTATION, ATTACHMENT_SCALE);
		
		shader.upload();
		super.draw();
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 3, 12, 0);
		addAttribute("normals", 3, 12, 3);
		addAttribute("texcoord", 2, 12, 6);
		addAttribute("indexes", 2, 12, 8);
		addAttribute("weights", 2, 12, 10);
	}

	public Joint getBoneByName(String boneName)
	{
		return skeleton.getBoneByName(boneName);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		skeleton.dispose();
		
		skeleton = null;
		shader = null;
	}
	
	
}
