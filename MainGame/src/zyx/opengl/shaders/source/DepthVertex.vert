#version 420
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in vec2 indexes;
layout(location = 4) in vec2 weights;

#include "BonesShaderLibrary.glsl";

uniform mat4 model;
uniform mat4 projectionView;
uniform vec4 sunNearPlane;

void main()
{
    vec4 transformedPos = vec4(0);
    vec4 transformedNorm = vec4(0);

    calculateBones(position, normals, indexes, weights, transformedPos, transformedNorm);

	vec4 worldPosition = model * transformedPos;

	vec4 glView = projectionView * worldPosition;

	float origX = glView.x;
	float origY = glView.y;
	float relocatedX = (origX / 2) - 0.5;
	float relocatedY = (origY / 2) - 0.5;

	//glView.x = min(0, relocatedX);
	//glView.y = min(0, relocatedY);

    gl_Position = glView;
}