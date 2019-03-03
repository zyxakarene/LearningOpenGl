#version 420

const float AMBIENT_LIGHT = 0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 1.0 - AMBIENT_LIGHT;//The light recieved when facing the light
const int LIGHT_COUNT = 2;//How many lights do we have

struct Light
{
	vec3 Position;
	vec3 Color;
	float Intensity;
};

in vec2 Texcoord;
in vec3 Normal;
in Light[LIGHT_COUNT] Lights;

layout(location = 0) out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightDir = vec3(0, -1, 1);

uniform int debugColor;

void main()
{
	vec3 normVertex = normalize(Normal);
    float cosTheta = clamp(dot(normVertex, lightDir), debugColor, 1);
	vec4 color = (vec4(DIRECT_LIGHT) * cosTheta);


	for(int i = 0; i < LIGHT_COUNT; i++)
    {
		vec3 ToLightVec = Lights[i].Position;
		vec3 LightColor = Lights[i].Color;

		float dist = length(ToLightVec);
		float power = (1 / (dist * dist)) * 20;
		power = clamp(power, 0.0, 0.75);

		vec3 normalLightVec = normalize(ToLightVec);
		float nDot1 = dot(normVertex, normalLightVec);
		float brightness = max(nDot1, 0.0);
		vec3 difuse = power * brightness * LightColor;

		color.r += difuse.r;
		color.g += difuse.g;
		color.b += difuse.b;
	}

    color.a = 1;

	vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y)) + debugColor;

    outColor = materialColor * color + vec4(AMBIENT_LIGHT) * materialColor;
    
    //View normals
    //outColor = vec4(0.5 + 0.5 * normVertex, 1);
}