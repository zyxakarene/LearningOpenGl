#version 420

in vec2 TexCoords;
in vec4 WorldPos;
in vec3 WorldNormal;

layout (binding = 0) uniform sampler2D tex;
layout (binding = 1) uniform sampler2D norm;
layout (binding = 2) uniform sampler2D spec;
layout (binding = 3) uniform sampler2D gDepth;
layout (binding = 4) uniform sampler2D gShadowMap;

uniform float cubemapColor;

layout (location = 0) out vec3 gOutput;

#include "LightingShaderLibrary.glsl";

void main()
{
    vec3 Diffuse = texture(tex, TexCoords).rgb;
    float CascadeDepth = texture(gDepth, TexCoords).r;

	vec3 Reflect;
	vec3 sunBrightness = getSunBrightness(WorldPos, WorldNormal, CascadeDepth, cubemapColor, gShadowMap, Reflect);

	gOutput = blendNormal(Diffuse * sunBrightness, Reflect, 0);
	//gOutput = Diffuse * sunBrightness * col;
}