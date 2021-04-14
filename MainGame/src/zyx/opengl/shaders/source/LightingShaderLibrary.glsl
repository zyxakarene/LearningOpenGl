const float AMBIENT_LIGHT = 0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 1.0 - AMBIENT_LIGHT;//The light recieved when facing the light
const int LIGHT_COUNT = 100;//How many lights do we support
const int SHADOW_QUADRANTS = 4;//Amount of quadrants

layout (binding = 10) uniform samplerCubeArray cubemapArray;

uniform vec2 shadowUvOffsetPerQuadrant[SHADOW_QUADRANTS];
uniform vec2 uvLimitsMinPerQuadrant[SHADOW_QUADRANTS];
uniform vec2 uvLimitsMaxPerQuadrant[SHADOW_QUADRANTS];
uniform mat4 sunProjViews[SHADOW_QUADRANTS];

uniform int[LIGHT_COUNT] lightPowers;
uniform vec3[LIGHT_COUNT] lightColors;
uniform vec3[LIGHT_COUNT] lightPositions;

uniform vec3 lightDir = vec3(0, 0, -1);
uniform vec3 camPos = vec3(0, 0, 0);

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

float ShadowCalculation(vec4 fragPosLightSpace, int quadrant, sampler2D shadowMap)
{
	//0  1
	//2  3
	vec2 offsets = shadowUvOffsetPerQuadrant[quadrant];
    // perform perspective divide [-1, 1]
    vec3 projCoords = fragPosLightSpace.xyz;
    // Transform to [0,1] range
    projCoords = projCoords * 0.5 + 0.5;
	projCoords.x = (projCoords.x * 0.5) + offsets.x;
	projCoords.y = (projCoords.y * 0.5) + offsets.y;

    // Get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
    float closestDepth = texture(shadowMap, projCoords.xy).r; 
    // Get depth of current fragment from light's perspective
    float currentDepth = projCoords.z;
    // Check whether current frag pos is in shadow
    float shadow = currentDepth > closestDepth  ? 1.0 : 0.0;
    // Keep the shadow at 0.0 when outside the far_plane region of the light's frustum.


	vec3 uvCoords = projCoords;
	vec2 uvLimitsMin = uvLimitsMinPerQuadrant[quadrant];
	vec2 uvLimitsMax = uvLimitsMaxPerQuadrant[quadrant];
	
    if(uvCoords.x < uvLimitsMin.x || uvCoords.y < uvLimitsMin.y || uvCoords.x > uvLimitsMax.x || uvCoords.y > uvLimitsMax.y ||
	   uvCoords.z < 0.0 || uvCoords.z > 1.0)
	{
	  return 0;
	}

	float bias = 0.0025f;
	shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;  
	if(shadow > 0)
	{
		vec2 texelSize = 1.0 / textureSize(shadowMap, 0);
		for(int x = -1; x <= 1; ++x)
		{
			for(int y = -1; y <= 1; ++y)
			{
				float pcfDepth = texture(shadowMap, projCoords.xy + vec2(x, y) * texelSize).r; 
				shadow += currentDepth - bias > pcfDepth ? 1.0 : 0.0;        
			}    
		}
		shadow = shadow / 9;
	}

    return shadow;
}

float blendLighten(float base, float blend)
{
	return max(blend,base);
}

vec3 blendLighten(vec3 base, vec3 blend)
{
	return vec3(blendLighten(base.r,blend.r),blendLighten(base.g,blend.g),blendLighten(base.b,blend.b));
}

vec3 blendLighten(vec3 base, vec3 blend, float opacity)
{
	return (blendLighten(base, blend) * opacity + base * (1.0 - opacity));
}

vec3 blendNormal(vec3 base, vec3 blend, float opacity)
{
	return (blend * opacity + base * (1.0 - opacity));
}

int getQuadrant(float CascadeDepth)
{
	int quadrant = int(0.74 * pow(1.008, CascadeDepth));
	return min(3, quadrant);
}

vec3 getSunBrightness(vec4 FragPos, vec3 Normal, float CascadeDepth, float cubemapIndex, sampler2D shadowMap, out vec3 Reflect)
{
	int quadrant = getQuadrant(CascadeDepth);
	
	mat4 sunProjection = sunProjViews[quadrant];
	vec4 FragPosSunSpace = sunProjection * FragPos;
	float shadowValue = ShadowCalculation(FragPosSunSpace, quadrant, shadowMap);
	float invShadowValue = 1 - shadowValue;

	float ambLight = AMBIENT_LIGHT;
	float dirLight = DIRECT_LIGHT * invShadowValue;
	
    float cosTheta = clamp(dot(Normal, lightDir), 0, 1);
	vec3 sunBrightness = vec3((dirLight * cosTheta) + (ambLight));
    
	for(int i = 0; i < LIGHT_COUNT; i++)
    {
		vec3 difuse = handleLightInfo(i, Normal, FragPos.xyz);

		sunBrightness.r += difuse.r;
		sunBrightness.g += difuse.g;
		sunBrightness.b += difuse.b;
	}

	int cube = int(cubemapIndex * 255);
	vec3 I = normalize(FragPos.xyz - camPos);
    vec3 R = reflect(I, Normal);
    Reflect = texture(cubemapArray, vec4(R, cube)).rgb * sunBrightness;

	return sunBrightness;
}