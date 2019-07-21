#version 420

in vec2 Texcoord;

layout (binding = 0) uniform sampler2D tex;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;
layout (location = 4) out vec3 gScreenPosition;

void main()
{
	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y));
	materialColor.a = 0;

	gPosition = vec3(1000, 1000, 1000);
	gScreenPosition = vec3(1000, 1000, 1000);

	gAlbedoSpec = materialColor;
	gNormal = vec3(0, 0, 1);
}