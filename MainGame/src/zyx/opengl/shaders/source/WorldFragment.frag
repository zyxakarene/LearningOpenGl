#version 420

in vec2 Texcoord;
in vec3 WorldPos;
in vec3 WorldNormal;
in vec3 ScreenPos;
in vec3 ScreenNormal;
in float Z;
in float CubemapAddition;

layout (binding = 0) uniform sampler2D tex;
layout (binding = 1) uniform sampler2D norm;
layout (binding = 2) uniform sampler2D spec;

uniform int debugColor;
uniform float cubemapColor;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;
layout (location = 3) out float gDepth;
layout (location = 4) out vec3 gScreenPosition;
layout (location = 5) out vec3 gScreenNormal;
layout (location = 6) out float gCubeIndex;

void main()
{
	vec4 normValue =  texture(norm, vec2(Texcoord.x, -Texcoord.y));
	vec4 specValue =  texture(spec, vec2(Texcoord.x, -Texcoord.y));
	normValue = (normValue - 0.5) * 2;

	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y));
	materialColor += (100 * debugColor);

	vec3 worldNorms = normalize(vec3(WorldNormal.xy + normValue.xy, WorldNormal.z));

	gPosition = WorldPos;
	gNormal = worldNorms;
	gAlbedoSpec = vec4(materialColor.rgb, specValue.r);
	gDepth = Z;
	gScreenPosition = ScreenPos;
	gScreenNormal = ScreenNormal;
	gCubeIndex = (cubemapColor + CubemapAddition);

    //Pretty normals
    //outColor = vec4(0.5 + 0.5 * Normal, 1);
}