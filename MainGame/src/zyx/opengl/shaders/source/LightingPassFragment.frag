#version 420
const float AMBIENT_LIGHT = 0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 1.0 - AMBIENT_LIGHT;//The light recieved when facing the light
const int LIGHT_COUNT = 325;//How many lights do we support

in vec2 TexCoords;

out vec4 FragColor;

layout (binding = 0) uniform sampler2D gPosition;
layout (binding = 1) uniform sampler2D gNormal;
layout (binding = 2) uniform sampler2D gAlbedoSpec;
layout (binding = 3) uniform sampler2D gDepth;
layout (binding = 4) uniform sampler2D gShadowMap;

uniform int[LIGHT_COUNT] lightPowers;
uniform vec3[LIGHT_COUNT] lightColors;
uniform vec3[LIGHT_COUNT] lightPositions;

uniform vec3 lightDir = vec3(0, 0, -1);

uniform mat4 sunProjView;

vec3 handleLightInfo(in int index, in vec3 normal, in vec3 fragmentPosition)
{
	vec3 LightPosition = lightPositions[index];
	vec3 LightColor = lightColors[index];
	int LightPower = lightPowers[index];

	vec3 ToLightVec = LightPosition - fragmentPosition;

	float dist = length(ToLightVec);
	float power = (1 / (dist * dist)) * (LightPower / 100);
	power = clamp(power, 0.0, 0.75);

	vec3 normalLightVec = normalize(ToLightVec);
	float nDot1 = dot(normal, normalLightVec);
	float brightness = max(nDot1, 0.0);
	vec3 difuse = power * brightness * LightColor;

	return difuse;
}

float ShadowCalculation(vec4 fragPosLightSpace, float cascadeDepth)
{
    // perform perspective divide [-1, 1]
    vec3 projCoords = fragPosLightSpace.xyz;
    // Transform to [0,1] range
    projCoords = projCoords * 0.5 + 0.5;
	//projCoords.x = projCoords.x * 0.5;
	//projCoords.y = projCoords.y * 0.5;

    // Get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
    float closestDepth = texture(gShadowMap, projCoords.xy).r; 
    // Get depth of current fragment from light's perspective
    float currentDepth = projCoords.z;
    // Check whether current frag pos is in shadow
    float shadow = currentDepth > closestDepth  ? 1.0 : 0.0;
    // Keep the shadow at 0.0 when outside the far_plane region of the light's frustum.


	float bias = 0.0025f;
    shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;  

    if(projCoords.z < 0.0 || projCoords.x < 0.0 || projCoords.y < 0.0 ||
       projCoords.z > 1.0 || projCoords.x > 1.0 || projCoords.y > 1.0)
      {
        shadow = 0.0;
      }

      if(shadow > 0)
      {
        vec2 texelSize = 1.0 / textureSize(gShadowMap, 0);
        for(int x = -1; x <= 1; ++x)
        {
            for(int y = -1; y <= 1; ++y)
            {
                float pcfDepth = texture(gShadowMap, projCoords.xy + vec2(x, y) * texelSize).r; 
                shadow += currentDepth - bias > pcfDepth ? 1.0 : 0.0;        
            }    
        }
        shadow /= 9.0;
      }

    return shadow;
}

void main()
{
    // retrieve data from gbuffer
    vec4 FragPos = texture(gPosition, TexCoords).rgba;
    vec3 Normal = texture(gNormal, TexCoords).rgb;
    vec3 Diffuse = texture(gAlbedoSpec, TexCoords).rgb;
    float CascadeDepth = texture(gDepth, TexCoords).r;
	
	vec4 FragPosSunSpace = sunProjView * FragPos;
	float shadowValue = ShadowCalculation(FragPosSunSpace, CascadeDepth);
	float invShadowValue = 1 - shadowValue;

	float ambLight = AMBIENT_LIGHT;
	float dirLight = DIRECT_LIGHT * invShadowValue;
	
    float cosTheta = clamp(dot(Normal, lightDir), 0, 1);
	vec3 sunBrightness = vec3((dirLight * cosTheta) + ambLight);
    
	float r = 0;
	float g = 0;
	float b = 0;
	for(int i = 0; i < LIGHT_COUNT; i++)
    {
		vec3 difuse = handleLightInfo(i, Normal, FragPos.xyz);

		sunBrightness.r += difuse.r;
		sunBrightness.g += difuse.g;
		sunBrightness.b += difuse.b;
	}

	vec3 outColor = Diffuse * sunBrightness;
    FragColor = vec4(outColor, 1.0);
}