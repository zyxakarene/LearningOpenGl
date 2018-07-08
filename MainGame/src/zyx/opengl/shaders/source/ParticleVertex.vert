#version 420

in vec2 position;
in vec2 texcoord;

in float lifespanRandom;
in vec3 areaRandom;
in vec3 speedRandom;
in float scaleRandom;
in float rotRandom;

out vec2 Texcoord;
out vec4 Color;

uniform mat4 view;
uniform mat4 projection;
uniform mat4 model;

uniform float instances = 200;

uniform float time;
uniform vec3 gravity = vec3(0, 0, 0); //xyz

uniform vec2 areaX = vec2(-1, 1); //min, max
uniform vec2 areaY = vec2(-1, 1); //min, max
uniform vec2 areaZ = vec2(0, 0); //min, max

uniform vec3 speed = vec3(0, 0, -2); //xyz
uniform vec3 speedVariance = vec3(0.2, 0.2, 0.2); //xyz

uniform vec4 startColor = vec4(0.2, 0.2, 0.2, 1); //RGBA
uniform vec4 endColor = vec4(0.2, 0.2, 0.2, 1); //RGBA

uniform float parentScale = 0.1;

uniform float startScale = 1;
uniform float endScale = 0.1;
uniform float scaleVariance = 0.5;

uniform float lifespan = 300;
uniform float lifespanVariance = 0;

uniform float rotation = 0;
uniform float rotationVariance = 0;

mat4 getRotMatrix(float angle)
{
    float s = sin(angle);
    float c = cos(angle);
    float oc = 1.0 - c;
    return mat4(c,	-s,		0,			0,
				s,	c,		0,			0,
				0,	0,		oc + c,		0,
				0,	0,		0,			1);
}

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
	return pow(localTime, power) * 0.001 * gravity;
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

	float actualLifespan = lifespan + (lifespanRandom * lifespanVariance) - lifespanVariance;
	float localTime = mod(time + startFrac, actualLifespan);

	//Setting the start area
	float x = applyArea(areaX, areaRandom.x);
	float y = applyArea(areaY, areaRandom.y);
	float z = applyArea(areaZ, areaRandom.z);

	vec4 posVec = model * vec4(x, y, z, 0);
	x = posVec.x;
	y = posVec.y;
	z = posVec.z;

	//Applying gravity
	vec4 gravityVec = model * vec4(gravity, 0);
	x += applyGravity(localTime, 1.5, gravityVec.x);
	y += applyGravity(localTime, 1.5, gravityVec.y);
	z += applyGravity(localTime, 1.5, gravityVec.z);

	//Applying Speed
	vec4 speedVec = model * vec4(speed * parentScale, 0);
	vec4 speedVarianceVec = model * vec4(speedVariance, 0);
	x += applySpeed(speedVec.x, speedVarianceVec.x, speedRandom.x, localTime);
	y += applySpeed(speedVec.y, speedVarianceVec.y, speedRandom.y, localTime);
	z += applySpeed(speedVec.z, speedVarianceVec.z, speedRandom.z, localTime);

	float scale = mix(startScale, endScale, localTime / actualLifespan) + (scaleVariance * scaleRandom);
	scale = scale * parentScale;

	mat4 translateMatrix = mat4(1);
	translateMatrix[3].xyz = vec3(x, y, z);

	float piTwo = 3.14159265358 * 2;
	float percentDone = localTime / actualLifespan;
	float currentRotation = rotation + (rotRandom * rotationVariance) - rotationVariance;
	mat4 R = getRotMatrix(percentDone * currentRotation * piTwo);

	Color = getColor(localTime / actualLifespan, startColor, endColor);
	Texcoord = texcoord;
	gl_Position = projection * (view * translateMatrix * model * vec4(0, 0, 0, 1) + vec4(position.x * scale, position.y * scale, 0, 0) * R);
}

/*
	[RT.x] [UP.x] [BK.x] [POS.x]
	[RT.y] [UP.y] [BK.y] [POS.y]
	[RT.z] [UP.z] [BK.z] [POS.Z]
	[    ] [    ] [    ] [US   ]
*/