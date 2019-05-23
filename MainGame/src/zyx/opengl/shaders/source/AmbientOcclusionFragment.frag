#version 420
in vec2 TexCoords;

layout (location = 0) out vec4 gAmbientOcclusion;

layout (binding = 0) uniform sampler2D gPosition;
layout (binding = 1) uniform sampler2D gNormal;

void main()
{
	// retrieve data from gbuffer
    vec3 FragPos = texture(gPosition, TexCoords).rgb;
    vec3 Normal = texture(gNormal, TexCoords).rgb;
	float value = 1;

	vec3 startOffset = FragPos + (Normal * 2);
	
	vec2 texelSize = 1.0 / textureSize(gPosition, 0);
	texelSize = vec2(0.999,0.999);
	for(int x = -1; x <= 1; ++x)
	{
		for(int y = -1; y <= 1; ++y)
		{
			vec3 posSample = texture(gPosition, TexCoords.xy + vec2(x, y) * texelSize).rgb; 
			vec3 normalSample = texture(gNormal, TexCoords.xy + vec2(x, y) * texelSize).rgb; 

			vec3 offsetSample = posSample + (normalSample * 2);
			float dist = distance(startOffset, offsetSample);

			if(dist <= 0.3)
			{
				value -= 0.1;
			}
		}    
	}

	value = 1 - value;

    gAmbientOcclusion = vec4(value, value, value, 1);
}