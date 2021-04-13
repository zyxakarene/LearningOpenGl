package zyx.opengl.shaders.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.lighs.ILight;

public interface ILightsShader
{
	void uploadLightDirection(Vector3f direction);

	void uploadSunMatrix();
}
