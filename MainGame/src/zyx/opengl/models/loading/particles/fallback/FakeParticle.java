package zyx.opengl.models.loading.particles.fallback;

import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleVoBuilder;

public class FakeParticle
{

	public static LoadableParticleVO makeFakeParticle()
	{
		ParticleVoBuilder builder = ParticleVoBuilder.createBuilder();
		
		builder.instanceCount(100);
		builder.areaX(-5, 5);
		builder.areaY(-5, 5);
		builder.areaZ(-5, 5);
		builder.startColor(1, 0, 1, 1);
		builder.speedVariance(10, 10, 10);
		builder.endColor(1, 0, 1, 1);
		builder.startScale(5);
		builder.endScale(5);
		builder.lifespan(1000);
		builder.radius(20);
		
		return builder.get();
	}
}
