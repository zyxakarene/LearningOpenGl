#version 420

in vec2 TexCoords;

layout (binding = 0) uniform sampler2D gPosition;
layout (binding = 1) uniform sampler2D gNormal;
layout (binding = 2) uniform sampler2D gAlbedoSpec;
layout (binding = 3) uniform sampler2D gDepth;
layout (binding = 4) uniform sampler2D gShadowMap;
layout (binding = 5) uniform sampler2D gAmbientOcclusion;
layout (binding = 6) uniform sampler2D gCubeIndex;

layout (location = 0) out vec3 gOutput;

#include "LightingShaderLibrary.glsl";

void main()
{
    // retrieve data from gbuffer
    vec4 FragPos = texture(gPosition, TexCoords).rgba;
    vec3 Normal = texture(gNormal, TexCoords).rgb;
    vec3 Diffuse = texture(gAlbedoSpec, TexCoords).rgb;
    float AO = texture(gAmbientOcclusion, TexCoords).r;
    float CascadeDepth = texture(gDepth, TexCoords).r;
    float cubemapIndex = texture(gCubeIndex, TexCoords).r;
    float Shiny = texture(gAlbedoSpec, TexCoords).a;

	int quadrant = getQuadrant(CascadeDepth);
	vec3 col;
	if(quadrant == 0)
	{
		col = vec3(1, 0, 0);
	}
	else if(quadrant == 1)
	{
		col = vec3(0, 1, 0);
	}
	else if(quadrant == 2)
	{
		col = vec3(0, 0, 1);
	}
	else if(quadrant == 3)
	{
		col = vec3(1, 1, 1);
	}

	vec3 Reflect = vec3(0);
	vec3 sunBrightness = getSunBrightness(FragPos, Normal, CascadeDepth, cubemapIndex, gShadowMap, Reflect);

	vec3 outColor = Diffuse * sunBrightness * AO;
	//outColor = outColor * col;

	gOutput = blendNormal(outColor, Reflect, Shiny);
}