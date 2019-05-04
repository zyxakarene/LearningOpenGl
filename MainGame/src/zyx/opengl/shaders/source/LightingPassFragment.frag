#version 420
const float AMBIENT_LIGHT = 0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 1.0 - AMBIENT_LIGHT;//The light recieved when facing the light

out vec4 FragColor;

in vec2 TexCoords;

layout (binding = 0) uniform sampler2D gPosition;
layout (binding = 1) uniform sampler2D gNormal;
layout (binding = 2) uniform sampler2D gAlbedoSpec;

uniform vec3 viewPos;
uniform vec3 lightDir = vec3(0, 0, -1);

void main()
{
    // retrieve data from gbuffer
    vec3 FragPos = texture(gPosition, TexCoords).rgb;
    vec3 Normal = texture(gNormal, TexCoords).rgb;
    vec3 Diffuse = texture(gAlbedoSpec, TexCoords).rgb;

	vec3 normVertex = normalize(Normal);
    float cosTheta = clamp(dot(normVertex, lightDir), 0, 1);
	vec3 color = vec3(DIRECT_LIGHT) * cosTheta;
    
	vec3 outColor = (Diffuse * color) + (Diffuse * vec3(AMBIENT_LIGHT));

    FragColor = vec4(outColor, 1.0);
}