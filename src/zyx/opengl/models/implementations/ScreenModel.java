package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class ScreenModel extends AbstractModel
{

	protected static float SHARED_ROTATION = 0f;
	protected static Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	protected static Vector3f SHARED_SCALE = new Vector3f(1f, 1f, 1f);

	private static final Matrix4f MODEL_MATRIX = ScreenShader.MATRIX_MODEL;

	private final ScreenShader shader;

	public ScreenModel(String texture)
	{
		super(Shader.SCREEN);

		shader = (ScreenShader) meshShader;

		float vertexData[] =
		{
			//  Position      Color             Texcoords
			0, 0, 0, 0, // Top-left
			100, 0, 1, 0, // Top-right
			100, -100, 1, 1, // Bottom-right
			0, -100, 0, 1  // Bottom-left
		};

		int elementData[] =
		{
			0, 1, 2,
			2, 3, 0
		};

		setVertexData(vertexData, elementData);
		setTexture(texture);
	}

	public void transform(Vector2f position, float rotation, Vector2f scale)
	{
		SHARED_POSITION.set(position.x, position.y);
		SHARED_ROTATION = -rotation;
		SHARED_SCALE.set(scale.x, scale.y);
	}

	public void resetTransform()
	{
		MODEL_MATRIX.setIdentity();
	}

	@Override
	public void draw()
	{
		MODEL_MATRIX.translate(SHARED_POSITION);

		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION), GeometryUtils.ROTATION_Z);

		MODEL_MATRIX.scale(SHARED_SCALE);
		shader.upload();
		super.draw();
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
	}

}
