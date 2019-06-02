#version 420

layout(location = 0) in vec3 position;
layout(location = 2) in vec2 texcoord;

out vec2 Texcoord;

uniform mat4 projView;
uniform mat4 proj;
uniform mat4 view;

void main()
{
    Texcoord = texcoord;
    gl_Position = proj * view * vec4(position, 1.0);
}