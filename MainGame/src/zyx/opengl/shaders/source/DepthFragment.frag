#version 420

layout (location = 0) out float gDepth;

uniform vec2 shadowOffsetMin;
uniform vec2 shadowOffsetMax;

void main()
{
	float dist = gl_FragCoord.z;

	float x = gl_FragCoord.x;
	float y = gl_FragCoord.y;

	if(x < shadowOffsetMin.x || x > shadowOffsetMax.x || y < shadowOffsetMin.y || y > shadowOffsetMax.y)
	{
		discard;
	}

	gDepth = dist;
}