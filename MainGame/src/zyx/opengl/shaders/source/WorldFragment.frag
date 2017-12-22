#version 420

const float AMBIENT_LIGHT = 0.25;//The default light everything is receiving
const float DIRECT_LIGHT = 1.0 - AMBIENT_LIGHT;//The light recieved when facing the light

in vec2 Texcoord;
in vec3 Normal;

layout(location = 0) out vec4 outColor;

uniform sampler2D tex;
uniform vec3 lightDir = vec3(0, -1, 1);

void main()
{
    vec3 normVertex = normalize(Normal);
	float cosTheta = clamp(dot(normVertex, lightDir), 0, 1);

	vec4 color = vec4(DIRECT_LIGHT) * cosTheta;
	color.a = 1;

    vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y));

    outColor = materialColor * color + vec4(AMBIENT_LIGHT) * materialColor;

	//View normals
    //outColor = vec4(0.5 + 0.5 * normVertex, 1);
}