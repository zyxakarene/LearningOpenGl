package zyx.opengl.particles.loading;

import java.util.Scanner;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleVoBuilder;

public class ZpfLoader
{
	private static final String KEY_WORLD_PARTICLE = "worldParticle";
	private static final String KEY_INSTANCE_COUNT = "instanceCount";
	private static final String KEY_GRAVITY = "gravity";
	private static final String KEY_AREA_X = "areaX";
	private static final String KEY_AREA_Y = "areaY";
	private static final String KEY_AREA_Z = "areaZ";
	private static final String KEY_SPEED = "speed";
	private static final String KEY_SPEED_VARIANCE = "speedVariance";
	private static final String KEY_START_COLOR = "startColor";
	private static final String KEY_END_COLOR = "endColor";
	private static final String KEY_START_SCALE = "startScale";
	private static final String KEY_END_SCALE = "endScale";
	private static final String KEY_SCALE_VARIANCE = "scaleVariance";
	private static final String KEY_LIFESPAN = "lifespan";
	private static final String KEY_LIFESPAN_VARIANCE = "lifespanVariance";
	private static final String KEY_ROTATION = "rotation";
	private static final String KEY_ROTATION_VARIANCE = "rotationVariance";
	private static final String KEY_TEXTURE = "texture";
	
	public static LoadableParticleVO loadFromZpf(ResourceDataInputStream in)
	{
		ParticleVoBuilder builder = ParticleVoBuilder.createBuilder();
		
		String particleData = new String(in.getData());
		Scanner scanner = new Scanner(particleData);
		
		String key, value;
		String[] split;
		String line;
		while (scanner.hasNextLine())
		{			
			line = scanner.nextLine();
			split = line.split("=");
			
			key = split[0];
			value = split[1];
			
			handleKeyValue(builder, key, value);
		}
		
		return builder.get();
	}

	private static void handleKeyValue(ParticleVoBuilder builder, String key, String value)
	{
		switch(key)
		{
			case KEY_WORLD_PARTICLE:
			{
				boolean worldParticle = Boolean.parseBoolean(value);
				builder.worldParticle(worldParticle);
				break;
			}
			case KEY_INSTANCE_COUNT:
			{
				int instanceCount = Integer.parseInt(value);
				builder.instanceCount(instanceCount);
				break;
			}
			case KEY_GRAVITY:
			{
				String[] gravitySplit = value.split(",");
				float gravityX = Float.parseFloat(gravitySplit[0]);
				float gravityY = Float.parseFloat(gravitySplit[1]);
				float gravityZ = Float.parseFloat(gravitySplit[2]);
				builder.gravity(gravityX, gravityY, gravityZ);
				break;
			}
			case KEY_AREA_X:
			{
				String[] areaXSplit = value.split(",");
				float areaXMin = Float.parseFloat(areaXSplit[0]);
				float areaXMax = Float.parseFloat(areaXSplit[1]);
				builder.areaX(areaXMin, areaXMax);
				break;
			}
			case KEY_AREA_Y:
			{
				String[] areaYSplit = value.split(",");
				float areaYMin = Float.parseFloat(areaYSplit[0]);
				float areaYMax = Float.parseFloat(areaYSplit[1]);
				builder.areaY(areaYMin, areaYMax);
				break;
			}
			case KEY_AREA_Z:
			{
				String[] areaZSplit = value.split(",");
				float areaZMin = Float.parseFloat(areaZSplit[0]);
				float areaZMax = Float.parseFloat(areaZSplit[1]);
				builder.areaZ(areaZMin, areaZMax);
				break;
			}
			case KEY_SPEED:
			{
				String[] speedSplit = value.split(",");
				float speedX = Float.parseFloat(speedSplit[0]);
				float speedY = Float.parseFloat(speedSplit[1]);
				float speedZ = Float.parseFloat(speedSplit[2]);
				builder.speed(speedX, speedY, speedZ);
				break;
			}
			case KEY_SPEED_VARIANCE:
			{
				String[] speedVarianceSplit = value.split(",");
				float speedVarianceX = Float.parseFloat(speedVarianceSplit[0]);
				float speedVarianceY = Float.parseFloat(speedVarianceSplit[1]);
				float speedVarianceZ = Float.parseFloat(speedVarianceSplit[2]);
				builder.speedVariance(speedVarianceX, speedVarianceY, speedVarianceZ);
				break;
			}
			case KEY_START_COLOR:
			{
				String[] startColorSplit = value.split(",");
				float startR = Float.parseFloat(startColorSplit[0]);
				float startG = Float.parseFloat(startColorSplit[1]);
				float startB = Float.parseFloat(startColorSplit[2]);
				float startA = Float.parseFloat(startColorSplit[3]);
				builder.startColor(startR, startG, startB, startA);
				break;
			}
			case KEY_END_COLOR:
			{
				String[] endColorSplit = value.split(",");
				float endR = Float.parseFloat(endColorSplit[0]);
				float endG = Float.parseFloat(endColorSplit[1]);
				float endB = Float.parseFloat(endColorSplit[2]);
				float endA = Float.parseFloat(endColorSplit[3]);
				builder.endColor(endR, endG, endB, endA);
				break;
			}
			case KEY_START_SCALE:
			{
				float startScale = Float.parseFloat(value);
				builder.startScale(startScale);
				break;
			}
			case KEY_END_SCALE:
			{
				float endScale = Float.parseFloat(value);
				builder.endScale(endScale);
				break;
			}
			case KEY_SCALE_VARIANCE:
			{
				float scaleVariance = Float.parseFloat(value);
				builder.scaleVariance(scaleVariance);
				break;
			}
			case KEY_LIFESPAN:
			{
				float lifespan = Float.parseFloat(value);
				builder.lifespan(lifespan);
				break;
			}
			case KEY_LIFESPAN_VARIANCE:
			{
				float lifespanVariance = Float.parseFloat(value);
				builder.lifespanVariance(lifespanVariance);
				break;
			}
			case KEY_ROTATION:
			{
				float rotation = Float.parseFloat(value);
				builder.rotation(rotation);
				break;
			}
			case KEY_ROTATION_VARIANCE:
			{
				float rotationVariance = Float.parseFloat(value);
				builder.rotationVariance(rotationVariance);
				break;
			}
			case KEY_TEXTURE:
			{
				builder.texture(value);
				break;
			}
		}
	}
}
