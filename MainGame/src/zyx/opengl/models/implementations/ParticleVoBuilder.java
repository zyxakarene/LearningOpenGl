package zyx.opengl.models.implementations;

public class ParticleVoBuilder
{

	private LoadableParticleVO vo;

	public static ParticleVoBuilder createBuilder()
	{
		return new ParticleVoBuilder();
	}

	private ParticleVoBuilder()
	{
		vo = new LoadableParticleVO();
	}

	public LoadableParticleVO get()
	{
		return vo;
	}
	
	public void instanceCount(int value)
	{
		vo.instanceCount = value;
	}

	public void gravity(float x, float y, float z)
	{
		vo.gravity.x = x;
		vo.gravity.y = y;
		vo.gravity.z = z;
	}

	public void areaX(float min, float max)
	{
		vo.areaX.x = min;
		vo.areaX.y = max;
	}

	public void areaY(float min, float max)
	{
		vo.areaY.x = min;
		vo.areaY.y = max;
	}

	public void areaZ(float min, float max)
	{
		vo.areaZ.x = min;
		vo.areaZ.y = max;
	}

	public void speed(float x, float y, float z)
	{
		vo.speed.x = x;
		vo.speed.y = y;
		vo.speed.z = z;
	}

	public void speedVariance(float x, float y, float z)
	{
		vo.speedVariance.x = x;
		vo.speedVariance.y = y;
		vo.speedVariance.z = z;
	}

	public void startColor(float r, float g, float b, float a)
	{
		vo.startColor.x = r;
		vo.startColor.y = g;
		vo.startColor.z = b;
		vo.startColor.w = a;
	}

	public void endColor(float r, float g, float b, float a)
	{
		vo.endColor.x = r;
		vo.endColor.y = g;
		vo.endColor.z = b;
		vo.endColor.w = a;
	}

	public void startScale(float value)
	{
		vo.startScale = value;
	}

	public void endScale(float value)
	{
		vo.endScale = value;
	}

	public void scaleVariance(float value)
	{
		vo.scaleVariance = value;
	}

	public void lifespan(float value)
	{
		vo.lifespan = value;
	}

	public void lifespanVariance(float value)
	{
		vo.lifespanVariance = value;
	}

	public void rotation(float value)
	{
		vo.rotation = value;
	}

	public void rotationVariance(float value)
	{
		vo.rotationVariance = value;
	}

	public void texture(String value)
	{
		vo.texture = value;
	}
}
