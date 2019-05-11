#version 420

layout (location = 0) out vec4 gDepth;

void main()
{
	float dist = gl_FragCoord.z;
	gDepth = vec4(dist, dist, dist, 1);
}