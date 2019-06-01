#version 420
in vec2 TexCoords;

layout (location = 0) out float gAmbientOcclusion;

layout (binding = 0) uniform sampler2D gPosition;
layout (binding = 1) uniform sampler2D gNormal;
layout (binding = 2) uniform sampler2D gNoise;

uniform vec3 samples[64];
uniform mat4 projection;

const vec2 noiseScale = vec2(1920.0 / 2 * 0.75 / 4.0, 1080.0 / 2 * 0.75 / 4.0); // screen = 1280x720

void main()
{
	// get input for SSAO algorithm
    vec3 fragPos = texture(gPosition, TexCoords).xyz;
	float occlusion = 0;

	int posX = int(fragPos.x);
	int posY = int(fragPos.y);
	int posZ = int(fragPos.z);

	if(posX == 1000 && posY == 1000 && posZ == 1000)
	{
		occlusion = 1;
	}
	else
	{
		// parameters (you'd probably want to use them as uniforms to more easily tweak the effect)
		int kernelSize = 32;
		float radius = 0.8;
		float bias = 0.025;

		vec3 normal = normalize(texture(gNormal, TexCoords).rgb);
		vec3 randomVec = normalize(texture(gNoise, TexCoords * noiseScale).xyz);
		// create TBN change-of-basis matrix: from tangent-space to view-space
		vec3 tangent = normalize(randomVec - normal * dot(randomVec, normal));
		vec3 bitangent = cross(normal, tangent);
		mat3 TBN = mat3(tangent, bitangent, normal);
		// iterate over the sample kernel and calculate occlusion factor

		for(int i = 0; i < kernelSize; ++i)
		{
			// get sample position
			vec3 Sample = TBN * samples[i]; // from tangent to view-space
			Sample = fragPos + Sample * radius; 

			// project sample position (to sample texture) (to get position on screen/texture)
			vec4 offset = vec4(Sample, 1.0);
			offset = projection * offset; // from view to clip-space
			offset.xyz /= offset.w; // perspective divide
			offset.xyz = offset.xyz * 0.5 + 0.5; // transform to range 0.0 - 1.0

			// get sample depth
			float sampleDepth = texture(gPosition, offset.xy).z; // get depth value of kernel sample

			// range check & accumulate
			float rangeCheck = smoothstep(0.0, 1.0, radius / abs(fragPos.z - sampleDepth));
			occlusion += (sampleDepth >= Sample.z + bias ? 1.0 : 0.0) * rangeCheck;           
		}
		occlusion = 1.0 - (occlusion / kernelSize);
	}

    gAmbientOcclusion = occlusion;
}