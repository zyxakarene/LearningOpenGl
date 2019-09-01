#version 420

in vec2 Texcoord;

layout (binding = 0) uniform sampler2D overTex;
layout (binding = 1) uniform sampler2D underTex;

layout (location = 0) out vec3 gOutput;

float blendDarken(float base, float blend)
{
	return min(blend, base);
}

vec3 blendDarken(vec3 base, vec3 blend)
{
	float r = blendDarken(base.r, blend.r);
	float g = blendDarken(base.g, blend.g);
	float b = blendDarken(base.b, blend.b);
	return vec3(r, g ,b);
}

vec3 blendDarken(vec3 base, vec3 blend, float opacity)
{
	return blendDarken(base, blend) * opacity + base * (1.0 - opacity);
}

void main()
{
	vec4 overColor = texture(overTex, vec2(Texcoord.x, Texcoord.y));
	vec4 underColor = texture(underTex, vec2(Texcoord.x, Texcoord.y));

	vec3 blendColor = blendDarken(underColor.rgb, overColor.rgb, overColor.a);

	gOutput = blendColor.rgb;
}