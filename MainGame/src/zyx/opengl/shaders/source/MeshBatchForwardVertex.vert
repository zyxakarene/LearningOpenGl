#version 420
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;

in vec3 insPosition;
in vec4 insRotation;
in float insScale;
in float insCubemap;

#include "SharedMatricesShaderLibrary.glsl";

out vec4 WorldPos;
out vec2 TexCoords;
out vec3 WorldNormal;
out vec3 Debug;

void main()
{
	vec3 totalPosition = (position * insScale);
	
	vec3 t = 2 * cross(insRotation.xyz, totalPosition);
	totalPosition = insPosition + totalPosition + insRotation.w * t + cross(insRotation.xyz, t);
	
	t = 2 * cross(insRotation.xyz, normals);
	vec3 totalNormal = normals + insRotation.w * t + cross(insRotation.xyz, t);

	vec4 worldPosition = vec4(totalPosition, 1);

    WorldNormal = vec3(totalNormal);
    gl_Position = projView * worldPosition;
}