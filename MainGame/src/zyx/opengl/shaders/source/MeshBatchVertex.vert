#version 420
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;

in vec3 insPosition;
in vec4 insRotation;
in float insScale;
in float insCubemap;

#include "SharedMatricesShaderLibrary.glsl";

out vec2 Texcoord;
out vec3 WorldPos;
out vec3 WorldNormal;
out vec3 ScreenPos;
out vec3 ScreenNormal;
out float Z;
out float CubemapAddition;

uniform mat4 viewInverseTranspose;

void main()
{
	vec3 totalPosition = (position * insScale);
	
	vec3 t = 2 * cross(insRotation.xyz, totalPosition);
	totalPosition = insPosition + totalPosition + insRotation.w * t + cross(insRotation.xyz, t);
	
	t = 2 * cross(insRotation.xyz, normals);
	vec3 totalNormal = normals + insRotation.w * t + cross(insRotation.xyz, t);

	vec4 worldPosition = vec4(totalPosition, 1);
	vec4 viewPos = view * worldPosition;
    
    ScreenPos = viewPos.xyz; 
    ScreenNormal = mat3(viewInverseTranspose) * totalNormal;

	WorldPos = worldPosition.xyz;
    WorldNormal = vec3(totalNormal);

	CubemapAddition = insCubemap;

    Texcoord = texcoord;
    gl_Position = projView * worldPosition;
	Z = gl_Position.z;
}