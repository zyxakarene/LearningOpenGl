#version 420

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in vec2 indexes;
layout(location = 4) in vec2 weights;

out vec2 Texcoord;

uniform mat4 projView;
uniform mat4 proj;
uniform mat4 view;

void main()
{
    Texcoord = texcoord;
    gl_Position = proj * view * vec4(position, 1.0);
}