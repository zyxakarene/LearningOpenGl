package zyx.opengl.models.implementations;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.components.world.model.LoadableModel;
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
import zyx.utils.interfaces.IPositionable;

public class WorldModel extends AbstractModel
{

	protected static final Vector3f SHARED_ROTATION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_SCALE = new Vector3f(1, 1, 1);

	private static final Matrix4f MODEL_MATRIX = WorldShader.MATRIX_MODEL;

	public final WorldShader shader;

	private boolean loaded;

	private Skeleton skeleton;
	private ArrayList<Attachment> attachments;

	public WorldModel()
	{
		super(Shader.WORLD);
		this.shader = (WorldShader) meshShader;
		this.loaded = false;
		this.attachments = new ArrayList<>();
	}

	public Joint getAttatchment(String name)
	{
		return skeleton.getBoneByName(name);
	}

	protected void OnLoaded(LoadableValueObject vo)
	{
		shader.bind();
		bindVao();

		skeleton = vo.skeleton;
		setVertexData(vo.vertexData, vo.elementData);
		setTexture(TextureManager.getTexture(vo.texture));

		loaded = true;
	}

	public void transform(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		SHARED_POSITION.set(position);
		SHARED_ROTATION.set(rotation);
		SHARED_SCALE.set(scale);
	}

	public void setAnimation(AnimationController controller)
	{
		if (loaded)
		{
			skeleton.setCurrentAnimation(controller);
		}
	}

	public void setScale(float newScale)
	{
		SHARED_SCALE.set(newScale, newScale, newScale);
	}

	public void addAttachment(LoadableModel model, AnimationController animations, IPositionable position, String attachmentPoint)
	{
		Attachment attachment = new Attachment();
		attachment.child = model;
		attachment.parent = this;
		attachment.animations = animations;
		attachment.position = position;
		attachment.joint = skeleton.getBoneByName(attachmentPoint);

		attachments.add(attachment);
	}

	@Override
	public void draw()
	{
		if (loaded)
		{
			skeleton.update(DeltaTime.getTimestamp(), DeltaTime.getElapsedTime());

			MODEL_MATRIX.setIdentity();
			transform();

			super.draw();

			for (Attachment attachment : attachments)
			{
				attachment.child.drawAsAttachment(attachment);
			}
		}
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

	private void drawAsAttachment(Attachment attachment)
	{
		setAnimation(attachment.animations);

		Matrix4f bonePosCopy = new Matrix4f(attachment.joint.lastFinalTransform);
		Matrix4f.mul(MODEL_MATRIX, bonePosCopy, bonePosCopy);
		
		Vector3f invertPos = attachment.position.getPosition().negate(null);

		skeleton.update(DeltaTime.getTimestamp(), DeltaTime.getElapsedTime());

		MODEL_MATRIX.setIdentity();

		MODEL_MATRIX.translate(attachment.position.getPosition());
		Matrix4f.mul(MODEL_MATRIX, bonePosCopy, MODEL_MATRIX);
		MODEL_MATRIX.translate(invertPos);

		transform();
		super.draw();

		for (Attachment attachment2 : attachments)
		{
			attachment2.child.drawAsAttachment(attachment2);
		}
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
}
