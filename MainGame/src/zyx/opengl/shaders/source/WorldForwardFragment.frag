#version 420

in vec2 TexCoords;
in vec4 WorldPos;
in vec3 WorldNormal;
in vec3 Debug;

layout (binding = 0) uniform sampler2D tex;
layout (binding = 1) uniform sampler2D norm;
layout (binding = 2) uniform sampler2D spec;
layout (binding = 3) uniform sampler2D gShadowMap;

uniform float cubemapColor;

layout (location = 0) out vec4 outColor;

#include "LightingShaderLibrary.glsl";

void main()
{
	vec4 specValue =  texture(spec, vec2(TexCoords.x, -TexCoords.y));

	vec4 normValue =  texture(norm, vec2(TexCoords.x, -TexCoords.y));
	normValue = (normValue - 0.5) * 2;
	vec3 endNormal = normalize(vec3(WorldNormal.xy + normValue.xy, WorldNormal.z));

    vec3 Diffuse = texture(tex, TexCoords).rgb;
    float CascadeDepth = 1;

	vec3 Reflect;
	vec3 sunBrightness = getSunBrightness(WorldPos, endNormal, CascadeDepth, cubemapColor, gShadowMap, Reflect);

	vec3 result = blendNormal(Diffuse * sunBrightness, Reflect, specValue.r);
	outColor = vec4(result, 0.5);
	//gOutput = Diffuse * sunBrightness * col;
}