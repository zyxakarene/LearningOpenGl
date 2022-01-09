#version 420

layout(location = 0) in vec3 position;
layout(location = 2) in vec2 texcoord;

#include "SharedMatricesShaderLibrary.glsl";

out vec2 Texcoord;

uniform mat4 viewPos;

void main()
{
    Texcoord = texcoord;
    gl_Position = proj * viewPos * vec4(position, 1.0);
}