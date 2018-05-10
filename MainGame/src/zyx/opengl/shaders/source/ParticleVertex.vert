#version 420

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texcoord;
layout(location = 2) in vec4 random; //Per instance

out vec2 Texcoord;
out vec4 Color;

uniform mat4 view;
uniform mat4 projection;
uniform mat4 model;

uniform float instances = 20;

uniform float time;
uniform float gravityX = 0;
uniform float gravityY = 0;
uniform float gravityZ = 0;

uniform vec3 pos = vec3(0, 0, 0); //xyz

uniform vec2 areaX = vec2(0, 0); //min, max
uniform vec2 areaY = vec2(0, 0); //min, max
uniform vec2 areaZ = vec2(0, 0); //min, max

uniform float speedX = 0;
uniform float speedY = 0;
uniform float speedZ = -2;
uniform float speedVarianceX = 0.1;
uniform float speedVarianceY = 0.1;
uniform float speedVarianceZ = 0;

uniform vec4 startColor = vec4(1, 0, 0, 1); //RGBA
uniform vec4 endColor = vec4(1, 1, 1, 1); //RGBA

uniform float startScale = 1;
uniform float endScale = 0.1;
uniform float scaleVariance = 0.5;

uniform float lifespan = 300;
uniform float lifespanVariance = 0;

float applyArea(in vec2 area, in float random)
{
	return area.x + ((area.y - area.x) * random);
}

float applySpeed(in float speed, in float variance, in float random, in float localTime)
{
	return localTime * 0.0025 * (speed + ((variance) - (variance * 2 * random)));
}

float applyGravity(in float localTime, in float power, in float gravity)
{
	return (pow(localTime, power) * 0.001) * gravity;
}

vec4 getColor(in float fraction, in vec4 startColor, in vec4 endColor)
{
	return vec4
	(
		mix(startColor.r, endColor.r, fraction),
		mix(startColor.g, endColor.g, fraction),
		mix(startColor.b, endColor.b, fraction),
		mix(startColor.a, endColor.a, fraction)
	);
}

void main(void)
{
	float startFrac = (float(gl_InstanceID) / instances) * lifespan;

	float actualLifespan = lifespan + (random.w * lifespanVariance) - lifespanVariance;
	float localTime = mod(time + startFrac, actualLifespan);

	//Setting start position
	float x = pos.x;
	float y = pos.y;
	float z = pos.z;

	//Applying area
	x += applyArea(areaX, random.x);
	y += applyArea(areaY, random.y);
	z += applyArea(areaZ, random.z);

	//Applying gravity
	x += applyGravity(localTime, 1.5, gravityX);
	y += applyGravity(localTime, 1.5, gravityY);
	z += applyGravity(localTime, 1.5, gravityZ);

	//Applying Speed
	x += applySpeed(speedX, speedVarianceX, random.x, localTime);
	y += applySpeed(speedY, speedVarianceY, random.y, localTime);
	z += applySpeed(speedZ, speedVarianceZ, random.z, localTime);

	float scale = mix(startScale, endScale, localTime / actualLifespan) + (scaleVariance * random.z);

	mat4 translateMatrix = mat4(1);
	translateMatrix[3].xyz = vec3(x, y, z);

	Color = getColor(localTime / actualLifespan, startColor, endColor);
	Texcoord = texcoord;
	gl_Position = projection * (view * translateMatrix * vec4(0, 0, 0, 1) + vec4(position.x * scale, position.y * scale, 0, 0));
}

/*
	[RT.x] [UP.x] [BK.x] [POS.x]
	[RT.y] [UP.y] [BK.y] [POS.y]
	[RT.z] [UP.z] [BK.z] [POS.Z]
	[    ] [    ] [    ] [US   ]
*/