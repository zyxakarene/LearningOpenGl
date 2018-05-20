package zyx.opengl.particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.game.controls.loading.particle.ParticleLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class ParticleSystem extends WorldObject implements IResourceLoaded<ParticleModel>
{
	private static Matrix4f ROTATION_X = new Matrix4f();
	private static Matrix4f ROTATION_Y = new Matrix4f();
	private static Matrix4f ROTATION_Z = new Matrix4f();
	private static Matrix4f ROTATION = SharedShaderObjects.SHARED_ROTATION_MATRIX;
	
	float elapsedTime;
	
	private boolean loaded;
	private ParticleModel model;
	private String path;

	public ParticleSystem()
	{
		super(Shader.PARTICLE);
		
		loaded = false;
		ParticleManager.getInstance().add(this);
		setZ(-3);
	}

	public void load(String path)
	{
		this.path = path;
		ParticleLoader.getInstance().load(path, this);
	}
	
	@Override
	public void resourceLoaded(ParticleModel data)
	{
		model = data;
		loaded = true;
	}
	
	@Override
	protected void onDraw()
	{
	}
	
	void drawParticle()
	{
		if (loaded)
		{
			ROTATION_X.setIdentity();
			ROTATION_Y.setIdentity();
			ROTATION_Z.setIdentity();
			ROTATION.setIdentity();

			SharedShaderObjects.SHARED_MODEL_TRANSFORM.load(worldMatrix());

			Vector3f rotation = getRotation(false, null);
			ROTATION_X.rotate(FloatMath.toRadians(rotation.x), GeometryUtils.ROTATION_X);
			ROTATION_Y.rotate(FloatMath.toRadians(rotation.y), GeometryUtils.ROTATION_Y);
			ROTATION_Z.rotate(FloatMath.toRadians(rotation.z), GeometryUtils.ROTATION_Z);

			Matrix4f.mul(ROTATION_Z, ROTATION_Y, ROTATION);
			Matrix4f.mul(ROTATION, ROTATION_X, ROTATION);

			shader.bind();
			shader.upload();
			model.draw();
		}
	}

	@Override
	protected void onDispose()
	{
		model = null;
	}

	@Override
	public String toString()
	{
		return String.format("%s{%s}", getClass().getSimpleName(), path);
	}
	
	
}
