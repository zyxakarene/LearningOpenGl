#version 420
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in vec2 indexes;
layout(location = 4) in vec2 weights;

#include "BonesShaderLibrary.glsl";

out vec2 Texcoord;
out vec3 Normal;
out vec4 WorldPos;

uniform mat4 model;
uniform mat4 modelInverseTranspose;
uniform mat4 projectionView;

void main()
{
    vec4 transformedPos = vec4(0);
    vec4 transformedNorm = vec4(0);

    calculateBones(position, normals, indexes, weights, transformedPos, transformedNorm);

	vec4 worldPosition = model * transformedPos;

    Texcoord = texcoord;
    Normal = mat3(modelInverseTranspose) * vec3(transformedNorm);
	WorldPos = worldPosition;
    gl_Position = projectionView * worldPosition;
}