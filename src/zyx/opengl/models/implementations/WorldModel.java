package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.game.controls.textures.TextureManager;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class WorldModel extends AbstractModel
{

	protected static final Vector3f SHARED_ROTATION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	protected static final Vector3f SHARED_SCALE = new Vector3f(1, 1, 1);

	private static final Matrix4f MODEL_MATRIX = WorldShader.MATRIX_MODEL;

	public final WorldShader shader;

	public WorldModel(float vertexData[], int elementData[])
	{
		super(Shader.WORLD);

		shader = (WorldShader) meshShader;

		setVertexData(vertexData, elementData);
		setTexture(TextureManager.getTexture("tile"));
	}

	public void transform(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		SHARED_POSITION.set(position);
		SHARED_ROTATION.set(rotation);
		SHARED_SCALE.set(scale);
	}

	public void setScale(float newScale)
	{
		SHARED_SCALE.set(newScale, newScale, newScale);
	}

	@Override
	public void draw()
	{
		MODEL_MATRIX.setIdentity();
		MODEL_MATRIX.translate(SHARED_POSITION);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.x), GeometryUtils.ROTATION_X);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.y), GeometryUtils.ROTATION_Y);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.z), GeometryUtils.ROTATION_Z);

		MODEL_MATRIX.scale(SHARED_SCALE);

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
}
