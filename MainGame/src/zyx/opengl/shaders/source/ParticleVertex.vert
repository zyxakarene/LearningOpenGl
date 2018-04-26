#version 420

layout(location = 0) in vec2 position;
layout(location = 2) in vec2 texcoord;

out vec2 Texcoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(void)
{
	gl_Position = projection * view * model * vec4(position, 0.0, 1.0);
}