#version 420
const int SHADOW_QUADRANTS = 4;//Amount of quadrants

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;

layout(location = 3) in vec3 insPosition;
layout(location = 4) in vec4 insRotation;
layout(location = 5) in float insScale;
layout(location = 5) in float insCubemap;

uniform mat4 projectionViews[SHADOW_QUADRANTS];

uniform int currentQuadrant;
uniform vec2 shadowOffsets;

void main()
{
	vec3 totalPosition = (position * insScale);
	
	vec3 t = 2 * cross(insRotation.xyz, totalPosition);
	totalPosition = insPosition + totalPosition + insRotation.w * t + cross(insRotation.xyz, t);

	vec4 worldPosition = vec4(totalPosition, 1);

	mat4 projection = projectionViews[currentQuadrant];
	vec4 glView = projection * worldPosition;

	float relocatedX = (glView.x / 2) + shadowOffsets.x;
	float relocatedY = (glView.y / 2) + shadowOffsets.y;

	glView.x = relocatedX;
	glView.y = relocatedY;

    gl_Position = glView;
}