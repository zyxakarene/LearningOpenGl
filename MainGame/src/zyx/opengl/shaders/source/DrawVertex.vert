#version 420
layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texcoord;

out vec2 Texcoord;
out vec3 WorldPos;

void main()
{
    Texcoord = texcoord;
    gl_Position = vec4(position, 0,  1);
}