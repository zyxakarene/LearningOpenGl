#version 420

in vec2 Texcoord;
in vec4 Color;

out vec4 outColor;

uniform sampler2D tex;
uniform sampler2D gPosition;
uniform sampler2D gNormal;
uniform sampler2D gAlbedoSpec;

uniform vec2 clipX;
uniform vec2 clipY;

void main()
{
	float clipLeft = clipX.x;
	float clipRight = clipX.y;

	float clipUp = clipY.x;
	float clipDown = clipY.y;

	float left = gl_FragCoord.x - clipLeft;
	left = clamp(left, 0, 1);

	float right = clipRight - gl_FragCoord.x;
	right = clamp(right, 0, 1);

	float up = clipUp - gl_FragCoord.y;
	up = clamp(up, 0, 1);

	float down = gl_FragCoord.y - clipDown;
	down = clamp(down, 0, 1);

	int clipRect = int(left * right * up * down);
    vec4 outCol = texture(tex, Texcoord) * Color * clipRect;
	
	//float grayColor = dot(outCol.rgb, vec3(0.3, 0.59, 0.11));

	outColor = outCol;
}