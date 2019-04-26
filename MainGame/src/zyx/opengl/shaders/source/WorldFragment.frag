#version 420

const float AMBIENT_LIGHT = 0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 1.0 - AMBIENT_LIGHT;//The light recieved when facing the light
const int LIGHT_COUNT = 30;//How many lights per batch

in vec2 Texcoord;
in vec3 Normal;
in vec3[LIGHT_COUNT] LightPos;

layout(location = 0) out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightDir = vec3(0, -1, 1);
uniform vec3[LIGHT_COUNT] lightColors;
uniform int[LIGHT_COUNT] lightPowers;

uniform int debugColor;

layout (location = 1) out vec3 gPosition;
layout (location = 2) out vec3 gNormal;
layout (location = 3) out vec4 gAlbedoSpec;

vec3 handleLightInfo(in int index, in vec3 normVertex)
{
	vec3 ToLightVec = LightPos[index];
	vec3 LightColor = lightColors[index];
	int LightPower = lightPowers[index];

	float dist = length(ToLightVec);
	float power = (1 / (dist * dist)) * 10;
	power = clamp(power, 0.0, 0.75);

	vec3 normalLightVec = normalize(ToLightVec);
	float nDot1 = dot(normVertex, normalLightVec);
	float brightness = max(nDot1, 0.0);
	vec3 difuse = power * brightness * LightColor;

	return difuse;
}

void main()
{
	vec3 normVertex = normalize(Normal);
    float cosTheta = clamp(dot(normVertex, lightDir), debugColor, 1);
	vec4 color = (vec4(DIRECT_LIGHT) * cosTheta);


	for(int i = 0; i < LIGHT_COUNT; i++)
    {
		vec3 difuse = handleLightInfo(i, normVertex);

		color.r += difuse.r;
		color.g += difuse.g;
		color.b += difuse.b;
	}

    color.a = 1;

	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y)) + debugColor;

    //outColor = materialColor * color + vec4(AMBIENT_LIGHT) * materialColor;
    
	gPosition.rgb = vec3(1);
	gNormal.rgb = materialColor.rgb;
	gAlbedoSpec.rgb = materialColor.rgb;

    //View normals
    //outColor = vec4(0.5 + 0.5 * normVertex, 1);
}