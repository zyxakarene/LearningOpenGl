package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.game.controls.textures.TextureManager;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
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

	public final WorldShader shader;

	private boolean loaded;

	protected Skeleton skeleton;
	private Matrix4f attachPoint;

	public WorldModel()
	{
		super(Shader.WORLD);
		this.shader = (WorldShader) meshShader;
		this.loaded = false;
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

	public void setPos(Matrix4f attatchPoint)
	{
		this.attachPoint = attatchPoint;
	}

	@Override
	public void draw()
	{
		if (loaded)
		{
			skeleton.update(DeltaTime.getTimestamp(), DeltaTime.getElapsedTime());

			if (attachPoint != null)
			{
				MODEL_MATRIX.setIdentity();
				
				MODEL_MATRIX.translate(new Vector3f(100, 0, 0));
				Matrix4f.mul(MODEL_MATRIX, attachPoint, MODEL_MATRIX);
			}
			else
			{
				MODEL_MATRIX.setIdentity();
			}

			MODEL_MATRIX.translate(SHARED_POSITION);
			MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.x), GeometryUtils.ROTATION_X);
			MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.y), GeometryUtils.ROTATION_Y);
			MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.z), GeometryUtils.ROTATION_Z);

			MODEL_MATRIX.scale(SHARED_SCALE);

			shader.upload();
			super.draw();
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
