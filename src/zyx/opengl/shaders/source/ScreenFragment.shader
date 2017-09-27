#version 150 core

in vec2 Texcoord;

out vec4 outColor;

uniform sampler2D tex;
uniform vec2 screenSize;

void main()
{
    outColor =  texture(tex, Texcoord);
}