#version 420
const int MAX_JOINTS = 20;//max joints allowed in a skeleton
const int MAX_WEIGHTS = 2;//max number of joints that can affect a vertex
const int LIGHT_COUNT = 30;//How many lights per batch

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in vec2 indexes;
layout(location = 4) in vec2 weights;

out vec2 Texcoord;
out vec3 Normal;
out vec3[LIGHT_COUNT] LightPos;

uniform vec3[LIGHT_COUNT] lightPositions;
uniform mat4 model;
uniform mat4 modelInverseTranspose;
uniform mat4 projectionView;

uniform mat4 bones[MAX_JOINTS];
uniform mat4 bonesInverseTranspose[MAX_JOINTS];

void main()
{
    vec4 totalLocalPos = vec4(0);
    vec4 totalLocalNorm = vec4(0);

    for(int i = 0; i < MAX_WEIGHTS; i++)
    {
        int intIndex = int(indexes[i]);
        mat4 jointTransform = bones[intIndex];
        mat4 inverseTransform = bonesInverseTranspose[intIndex];

        vec4 posePosition = jointTransform * vec4(position, 1.0);
        vec4 normPosition = inverseTransform * vec4(normals, 1.0);

        totalLocalPos += posePosition * weights[i];
        totalLocalNorm += normPosition * weights[i];
    }

	vec4 vertexPos = model * totalLocalPos;

	for(int i = 0; i < LIGHT_COUNT; i++)
    {
		LightPos[i] = lightPositions[i] - vertexPos.xyz;
	}

    Texcoord = texcoord;
    Normal = mat3(modelInverseTranspose) * vec3(totalLocalNorm);
    gl_Position = projectionView * vertexPos;
}