#version 420

in vec2 Texcoord;
in vec3 WorldPos;
in vec3 WorldNormal;
in vec3 ScreenPos;
in vec3 ScreenNormal;
in float Z;

layout (binding = 0) uniform sampler2D tex;

uniform int debugColor;

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec3 gAlbedoSpec;
layout (location = 3) out float gDepth;
layout (location = 4) out vec3 gScreenPosition;
layout (location = 5) out vec3 gScreenNormal;

void main()
{
	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y));
	materialColor += (100 * debugColor);
	
	gPosition = WorldPos;
	gNormal = WorldNormal;
	gAlbedoSpec = materialColor.rgb;
	gDepth = Z;
	gScreenPosition = ScreenPos;
	gScreenNormal = ScreenNormal;

    //Pretty normals
    //outColor = vec4(0.5 + 0.5 * Normal, 1);
}