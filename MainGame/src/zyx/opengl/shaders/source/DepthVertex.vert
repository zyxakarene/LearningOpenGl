#version 420
const int SHADOW_QUADRANTS = 4;//Amount of quadrants

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in %BoneCount% indexes;
layout(location = 4) in %BoneCount% weights;

#include "BonesShaderLibrary.glsl";

uniform mat4 model;
uniform mat4 projectionViews[SHADOW_QUADRANTS];

uniform int currentQuadrant;
uniform vec2 shadowOffsets;
uniform vec2 shadowOffsetMin;
uniform vec2 shadowOffsetMax;

void main()
{
    vec4 transformedPos = vec4(0);
    vec4 transformedNorm = vec4(0);

    calculateBones(position, normals, indexes, weights, transformedPos, transformedNorm);

	vec4 worldPosition = model * transformedPos;

	mat4 projection = projectionViews[currentQuadrant];
	vec4 glView = projection * worldPosition;

	float relocatedX = (glView.x / 2) + shadowOffsets.x;
	float relocatedY = (glView.y / 2) + shadowOffsets.y;
	glView.x = clamp(relocatedX, shadowOffsetMin.x, shadowOffsetMax.x);
	glView.y = clamp(relocatedY, shadowOffsetMin.y, shadowOffsetMax.y);

    gl_Position = glView;
}