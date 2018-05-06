#version 420

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texcoord;
layout(location = 2) in vec4 random; //Per instance

out vec2 Texcoord;

uniform mat4 view;
uniform mat4 projection;
uniform mat4 model;

uniform float time;
uniform float gravityX = 0;
uniform float gravityY = 0;
uniform float gravityZ = -0.1;

uniform vec2 areaX = vec2(-20, 20);
uniform vec2 areaY = vec2(-20, 20);
uniform vec2 areaZ = vec2(0, 0);

uniform float lifespan = 5000;
uniform float lifespanVariance = 500;

float setBasicArea(in vec2 area, in float random)
{
	return area.x + ((area.y - area.x) * random);
}

void main(void)
{
	float actualLifespan = lifespan + (random.w * lifespanVariance) - lifespanVariance;
	float localTime = mod(time + (gl_InstanceID * lifespan), actualLifespan);

	//Setting start position
	float x = setBasicArea(areaX, random.x);
	float y = setBasicArea(areaY, random.y);
	float z = setBasicArea(areaZ, random.z);

	//Applying gravity
	x += (pow(localTime, 1.5) * 0.001) * gravityX;
	y += (pow(localTime, 1.5) * 0.001) * gravityY;
	z += (pow(localTime, 1.5) * 0.001) * gravityZ;

	mat4 translateMatrix = mat4(1);
	translateMatrix[3].xyz = vec3(x, y, z);

	Texcoord = texcoord;
	gl_Position = projection * (view * translateMatrix * vec4(0, 0, 0, 1) + vec4(position.x, position.y, 0, 0));
}