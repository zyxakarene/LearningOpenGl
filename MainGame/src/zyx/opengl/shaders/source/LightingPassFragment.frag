#version 420
const float AMBIENT_LIGHT = 0.01; //0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 0.05; //1.0 - AMBIENT_LIGHT;//The light recieved when facing the light
const int LIGHT_COUNT = 500;//How many lights do we support

in vec2 TexCoords;

out vec4 FragColor;

layout (binding = 0) uniform sampler2D gPosition;
layout (binding = 1) uniform sampler2D gNormal;
layout (binding = 2) uniform sampler2D gAlbedoSpec;

uniform int[LIGHT_COUNT] lightPowers;
uniform vec3[LIGHT_COUNT] lightColors;
uniform vec3[LIGHT_COUNT] lightPositions;

uniform vec3 lightDir = vec3(0, 0, -1);

vec3 handleLightInfo(in int index, in vec3 normal, in vec3 fragmentPosition)
{
	vec3 LightPosition = lightPositions[index];
	vec3 LightColor = lightColors[index];
	int LightPower = lightPowers[index];

	vec3 ToLightVec = LightPosition - fragmentPosition;

	float debugPower = 0.05;

	float dist = length(ToLightVec);
	float power = (1 / (dist * dist)) * 10 * debugPower;
	power = clamp(power, 0.0, 0.75);

	vec3 normalLightVec = normalize(ToLightVec);
	float nDot1 = dot(normal, normalLightVec);
	float brightness = max(nDot1, 0.0);
	vec3 difuse = power * brightness * LightColor;

	return difuse;
}

void main()
{
    // retrieve data from gbuffer
    vec3 FragPos = texture(gPosition, TexCoords).rgb;
    vec3 Normal = texture(gNormal, TexCoords).rgb;
    vec3 Diffuse = texture(gAlbedoSpec, TexCoords).rgb;

    float cosTheta = clamp(dot(Normal, lightDir), 0, 1);
	vec3 sunBrightness = (vec3(DIRECT_LIGHT) * cosTheta) + AMBIENT_LIGHT;
    
	float r = 0;
	float g = 0;
	float b = 0;
	for(int i = 0; i < LIGHT_COUNT; i++)
    {
		vec3 difuse = handleLightInfo(i, Normal, FragPos);

		sunBrightness.r += difuse.r;
		sunBrightness.g += difuse.g;
		sunBrightness.b += difuse.b;
	}

	vec3 outColor = (Diffuse * sunBrightness);
    FragColor = vec4(outColor, 1.0);
}