#version 420

in vec2 Texcoord;
in vec3 Normal;
in vec4 WorldPos;

layout (binding = 0) uniform sampler2D tex;

uniform int debugColor;

layout (location = 0) out vec4 gPosition;
layout (location = 1) out vec4 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;
layout (location = 3) out vec4 gDepth;

void main()
{
	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y));
	materialColor += (100 * debugColor);

	float dist = distance(WorldPos, vec4(0, 0, 0, 1));
	dist = dist / 100;

	gPosition = vec4(WorldPos.rgb, 1);
	gNormal = vec4(Normal, 1);
	gAlbedoSpec = materialColor;
	gDepth = vec4(dist, dist, dist, 1);

    //Pretty normals
    //outColor = vec4(0.5 + 0.5 * Normal, 1);
}