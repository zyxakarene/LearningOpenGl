#version 420

#define MAX_NUM_TOTAL_LIGHTS 100
const float AMBIENT_LIGHT = 0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 1.0 - AMBIENT_LIGHT;//The light recieved when facing the light
const int LIGHT_COUNT = 5;//How many lights per batch
const int LIGHT_BACTHES = 2;//How many light batches
struct Light
{
	vec3 Position;
	vec3 Color;
	float Intensity;
};

in vec2 Texcoord;
in vec3 Normal;
in Light[MAX_NUM_TOTAL_LIGHTS] Lights_1;
in Light[MAX_NUM_TOTAL_LIGHTS] Lights_2;

layout(location = 0) out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightDir = vec3(0, -1, 1);

uniform int debugColor;

vec3 handleLightInfo(in Light light, in vec3 normVertex)
{
	vec3 ToLightVec = light.Position;
	vec3 LightColor = light.Color;

	float dist = length(ToLightVec);
	float power = (1 / (dist * dist)) * 20;
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


	for(int i = 0; i < MAX_NUM_TOTAL_LIGHTS; i++)
    {
		vec3 difuse_1 = handleLightInfo(Lights_1[i], normVertex);
		vec3 difuse_2 = handleLightInfo(Lights_2[i], normVertex);

		color.r += difuse_1.r + difuse_2.r;
		color.g += difuse_1.g + difuse_2.g;
		color.b += difuse_1.b + difuse_2.b;
	}

    color.a = 1;

	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y)) + debugColor;

    outColor = materialColor * color + vec4(AMBIENT_LIGHT) * materialColor;
    
    //View normals
    //outColor = vec4(0.5 + 0.5 * normVertex, 1);
}