#version 420

layout (location = 0) out float gDepth;

void main()
{
	float dist = gl_FragCoord.z;
	gDepth = dist;
}