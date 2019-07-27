#version 420
layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;

in vec3 insPosition;
in vec4 intRotation;
in float insScale;

out vec2 Texcoord;
out vec3 WorldPos;
out vec3 WorldNormal;
out vec3 ScreenPos;
out vec3 ScreenNormal;
out float Z;

uniform mat4 modelInverseTranspose;
uniform mat4 viewModelInverseTranspose;
uniform mat4 projectionView;
uniform mat4 view;

void main()
{
	vec3 totalPosition = (position * insScale) + insPosition;
	vec4 worldPosition = vec4(totalPosition, 1);
	
	vec4 viewPos = view * worldPosition;
    
    ScreenPos = viewPos.xyz; 
    ScreenNormal = mat3(viewModelInverseTranspose) * normals;

	WorldPos = worldPosition.xyz;
    WorldNormal = mat3(modelInverseTranspose) * vec3(normals);

    Texcoord = texcoord;
    gl_Position = projectionView * worldPosition;
	Z = gl_Position.z;
}