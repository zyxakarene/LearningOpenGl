#version 420

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texcoord;
layout(location = 2) in vec3 offset; //Per instance
layout(location = 3) in float speed; //Per instance

out vec2 Texcoord;

uniform mat4 view;
uniform mat4 projection;
uniform mat4 model;

uniform int time;

void main(void)
{
	mat4 translateMatrix = mat4(1);
	translateMatrix[3].xyz = vec3(offset.x, offset.y, offset.z + (time * 0.001 * speed));

	Texcoord = vec2(position.x + 0.5, position.y + 0.5);
	Texcoord = texcoord;
	gl_Position = projection * (view * translateMatrix * vec4(0, 0, 0, 1) + vec4(position.x, position.y, 0, 0));
}