#version 420

in vec2 position;
in vec2 texcoord;
in vec4 color;

out vec2 Texcoord;
out vec4 Color;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
        Color = color;
	Texcoord = texcoord;
        gl_Position = projection * view * model * vec4(position, -1.0, 1.0);
}