package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.GameTexture;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class WorldModel extends AbstractModel
{
	
	private static final Matrix4f MODEL_MATRIX = WorldShader.MATRIX_MODEL;

	private final WorldShader shader;

	public WorldModel()
	{
		super(Shader.WORLD);

		shader = (WorldShader) meshShader;

		float vertexData[] =
		{
			//Position		//Texcoords
			-0.5f, 0.5f, -1f, 0.0f, 0.0f, // Top-left
			0.5f, 0.5f, 0.0f, 1.0f, 0.0f, // Top-right
			0.5f, -0.5f, 0.0f, 1.0f, 1.0f, // Bottom-right
			-0.5f, -0.5f, 0.0f, 0.0f, 1.0f  // Bottom-left
		};

		int elementData[] =
		{
			0, 1, 2,
			2, 3, 0
		};

		setVertexData(vertexData, elementData);
		setTexture(new GameTexture("sample", "png"));
	}

	public WorldModel(float vertexData[], int elementData[])
	{
		super(Shader.WORLD);
		
		shader = (WorldShader) meshShader;
		
		setVertexData(vertexData, elementData);
		setTexture(new GameTexture("TowerBase", "png"));
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
		addAttribute("position", 3, 8, 0);
		addAttribute("texcoord", 2, 8, 3);
//		addAttribute("normal", 2, 8, 3);
	}
}
