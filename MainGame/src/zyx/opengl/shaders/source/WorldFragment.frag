#version 420

in vec2 Texcoord;
in vec3 Normal;
in vec4 WorldPos;

layout (binding = 0) uniform sampler2D tex;

uniform int debugColor;

layout (location = 0) out vec4 gPosition;
layout (location = 1) out vec4 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;

void main()
{
	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y));
	materialColor += (100 * debugColor);
	materialColor.a = 1;

	gPosition = vec4(WorldPos.rgb, 1);
	gNormal = vec4(Normal, 1);
	gAlbedoSpec = materialColor;

    //Pretty normals
    //outColor = vec4(0.5 + 0.5 * Normal, 1);
}