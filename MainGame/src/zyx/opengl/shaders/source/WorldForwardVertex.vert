#version 420
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in %BoneCount% indexes;
layout(location = 4) in %BoneCount% weights;

#include "BonesShaderLibrary.glsl";

out vec4 WorldPos;
out vec2 TexCoords;
out vec3 WorldNormal;
out vec3 Debug;

uniform mat4 model;
uniform mat4 modelInverseTranspose;
uniform mat4 viewModelInverseTranspose;
uniform mat4 projectionView;
uniform mat4 view;

void main()
{
    vec4 transformedPos = vec4(position, 1);
    vec4 transformedNorm = vec4(normals, 1);

	vec4 dummyPos = vec4(0);
    vec4 dummyNorm = vec4(0);
    calculateBones(position, normals, indexes, weights, dummyPos, dummyNorm);

	vec4 worldPosition = model * transformedPos;
	
	Debug = transformedNorm.xyz;
	WorldPos = worldPosition;
    TexCoords = texcoord;
	WorldNormal = normalize(mat3(modelInverseTranspose) * transformedNorm.xyz);
    gl_Position = projectionView * worldPosition;
}