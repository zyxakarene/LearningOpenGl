#version 420

in vec2 Texcoord;
in vec4 Color;

layout(location = 0) out vec4 outColor;

uniform sampler2D tex;

void main(void)
{
	outColor = texture(tex, Texcoord) * Color;
}