package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.game.controls.textures.TextureManager;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.attachments.Attachment;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.utils.DeltaTime;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class WorldModel extends AbstractModel
{

	protected static final Vector3f SHARED_ROTATION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_SCALE = new Vector3f(1, 1, 1);

	private static final Matrix4f MODEL_MATRIX = WorldShader.MATRIX_MODEL;

	private WorldShader shader;
	private Skeleton skeleton;

	public WorldModel(LoadableValueObject vo)
	{
		super(Shader.WORLD);
		this.shader = (WorldShader) meshShader;

		skeleton = vo.skeleton;
		setVertexData(vo.vertexData, vo.elementData);
		setTexture(TextureManager.getTexture(vo.texture));
	}

	public Joint getAttatchment(String name)
	{
		return skeleton.getBoneByName(name);
	}
	
	public void transform(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		SHARED_POSITION.set(position);
		SHARED_ROTATION.set(rotation);
		SHARED_SCALE.set(scale);
	}

	public void setAnimation(AnimationController controller)
	{
		skeleton.setCurrentAnimation(controller);
	}

	public void setScale(float newScale)
	{
		SHARED_SCALE.set(newScale, newScale, newScale);
	}

	@Override
	public void draw()
	{
		skeleton.update(DeltaTime.getTimestamp(), DeltaTime.getElapsedTime());

		MODEL_MATRIX.setIdentity();
		transform();

		super.draw();
	}

	private void transform()
	{
		MODEL_MATRIX.translate(SHARED_POSITION);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.x), GeometryUtils.ROTATION_X);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.y), GeometryUtils.ROTATION_Y);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.z), GeometryUtils.ROTATION_Z);

		MODEL_MATRIX.scale(SHARED_SCALE);

		shader.upload();
	}

	public void drawAsAttachment(Attachment attachment)
	{
		Matrix4f bonePosCopy = new Matrix4f(attachment.joint.getFinalTransform());
		Matrix4f.mul(MODEL_MATRIX, bonePosCopy, bonePosCopy);

		Vector3f invertPos = attachment.parent.getPosition().negate(null);

		skeleton.update(DeltaTime.getTimestamp(), DeltaTime.getElapsedTime());

		MODEL_MATRIX.setIdentity();

		MODEL_MATRIX.translate(attachment.parent.getPosition());
		Matrix4f.mul(MODEL_MATRIX, bonePosCopy, MODEL_MATRIX);
		MODEL_MATRIX.translate(invertPos);

		transform();
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
