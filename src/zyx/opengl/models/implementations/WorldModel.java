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
	private static final Vector3f SHARED_ROTATION = new Vector3f(0, 0, 0);
	private static final Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	private static final Vector3f SHARED_SCALE = new Vector3f(1, 1, 1);

	private static final Matrix4f MODEL_MATRIX = WorldShader.MATRIX_MODEL;

	private final WorldShader baseShader;

	public WorldModel()
	{
		super(Shader.WORLD);

		baseShader = (WorldShader) meshShader;

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

	public void transform(Vector3f newPosition, Vector3f newRotation)
	{
		SHARED_POSITION.set(newPosition);
		SHARED_ROTATION.set(newRotation);
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
		
		baseShader.upload();
		super.draw();
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 3, 5, 0);
		addAttribute("texcoord", 2, 5, 3);
	}

	
}
