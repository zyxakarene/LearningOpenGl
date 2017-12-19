#version 420

in vec2 Texcoord;
in vec3 Normal;

out vec4 outColor;

uniform sampler2D tex;

void main()
{
	vec3 light = vec3(-1, -1, 1);
    vec3 normVertex = normalize(Normal);
	float cosTheta = clamp(dot(normVertex, light), 0, 1);

	vec3 color = vec3(0.75) * cosTheta;

    vec4 materialColor =  texture(tex, vec2(Texcoord.x, -Texcoord.y));

	//View normals
	//normVertex.x = abs(normVertex.x); 
	//normVertex.y = abs(normVertex.y); 
	//normVertex.z = abs(normVertex.z); 
	//materialColor = vec4(normVertex, 1);

    outColor = materialColor * vec4(color, 1) + vec4(0.25) * materialColor;

	//View normals
    //outColor = materialColor;
}