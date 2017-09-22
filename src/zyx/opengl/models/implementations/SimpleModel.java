package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.GameTexture;
import zyx.utils.FloatMath;

public class SimpleModel extends AbstractModel
{

	public static final Vector3f ROTATION_X = new Vector3f(1.0f, 0.0f, 0.0f);
	public static final Vector3f ROTATION_Y = new Vector3f(0.0f, 1.0f, 0.0f);
	public static final Vector3f ROTATION_Z = new Vector3f(0.0f, 0.0f, 1.0f);

	private static final Vector3f TRANSLATE = new Vector3f();
	private static final Vector3f SCALE = new Vector3f();

	private static final Matrix4f MAT = WorldShader.MATRIX_MODEL;

	private final WorldShader baseShader;

	public SimpleModel()
	{
		super(Shader.WORLD);

		baseShader = (WorldShader) meshShader;

		float vertexData[] =
		{
			//Position		//Texcoords
			-0.5f, 0.5f, 0.0f, 0.0f, // Top-left
			0.5f, 0.5f, 1.0f, 0.0f, // Top-right
			0.5f, -0.5f, 1.0f, 1.0f, // Bottom-right
			-0.5f, -0.5f, 0.0f, 1.0f  // Bottom-left
		};

		int elementData[] =
		{
			0, 1, 2,
			2, 3, 0
		};

		setVertexData(vertexData, elementData);
		setTexture(new GameTexture("sample", "png"));
	}

	float angle = 0;
	@Override
	public void draw()
	{
		angle += 0.1f;
		TRANSLATE.x = (FloatMath.random() * 2) - 1;
		TRANSLATE.y = (FloatMath.random() * 2) - 1;
		TRANSLATE.z = 0;
		
		MAT.setIdentity();
		MAT.rotate(angle, ROTATION_Z);
		
		baseShader.upload();
		super.draw();
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
	}
}
