#version 420

in vec2 position;
in vec2 texcoord;

out vec2 Texcoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
	Texcoord = texcoord;
    gl_Position = projection * view * model * vec4(position, -1.0, 1.0);
}