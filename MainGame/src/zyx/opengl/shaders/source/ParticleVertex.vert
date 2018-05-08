#version 420

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texcoord;
layout(location = 2) in vec4 random; //Per instance

out vec2 Texcoord;

uniform mat4 view;
uniform mat4 projection;
uniform mat4 model;

uniform float instances = 500;

uniform float time;
uniform float gravityX = 0;
uniform float gravityY = 0;
uniform float gravityZ = -0.5;

uniform vec2 areaX = vec2(0, 0); //min, max
uniform vec2 areaY = vec2(0, 0); //min, max
uniform vec2 areaZ = vec2(0, 0); //min, max

uniform float speedX = 0;
uniform float speedY = 0;
uniform float speedZ = 10;
uniform float speedVarianceX = 10;
uniform float speedVarianceY = 10;
uniform float speedVarianceZ = 0;

uniform float lifespan = 2000;
uniform float lifespanVariance = 100;

float setBasicArea(in vec2 area, in float random)
{
	return area.x + ((area.y - area.x) * random);
}

float applySpeed(in float speed, in float variance, in float random, in float localTime)
{
	return localTime * 0.0025 * (speed + ((variance) - (variance * 2 * random)));
}

void main(void)
{
	float startFrac = (float(gl_InstanceID) / instances) * lifespan;
	//Find localtime depending on the fraction of instances vs lifespan to distribute them evenly
	float actualLifespan = lifespan + (random.w * lifespanVariance) - lifespanVariance;
	float localTime = mod(time + startFrac, actualLifespan);

	//Setting start position
	float x = setBasicArea(areaX, random.x);
	float y = setBasicArea(areaY, random.y);
	float z = setBasicArea(areaZ, random.z);

	//x += changeSpeed(speedX, speedVarianceX, random.w); 
	//y += changeSpeed(speedY, speedVarianceY, random.x); 
	//z += changeSpeed(speedZ, speedVarianceZ, random.y); 

	//Applying gravity
	x += (pow(localTime, 1.5) * 0.001) * gravityX + applySpeed(speedX, speedVarianceX, random.x, localTime);
	y += (pow(localTime, 1.5) * 0.001) * gravityY + applySpeed(speedY, speedVarianceY, random.y, localTime);
	z += (pow(localTime, 1.5) * 0.001) * gravityZ + applySpeed(speedZ, speedVarianceZ, random.z, localTime);

	mat4 translateMatrix = mat4(1);
	translateMatrix[3].xyz = vec3(x, y, z);

	Texcoord = texcoord;
	gl_Position = projection * (view * translateMatrix * vec4(0, 0, 0, 1) + vec4(position.x, position.y, 0, 0));
}