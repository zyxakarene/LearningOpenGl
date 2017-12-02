#version 420

const int MAX_JOINTS = 20;//max joints allowed in a skeleton
const int MAX_WEIGHTS = 2;//max number of joints that can affect a vertex


layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in vec2 indexes;
layout(location = 4) in vec2 weights;

out vec2 Texcoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform mat4 bones[MAX_JOINTS];

void main()
{
	vec4 totalLocalPos = vec4(0.0);

	for(int i=0;i<MAX_WEIGHTS;i++)
	{
		int intIndex = int(indexes[i]);
		mat4 jointTransform = bones[intIndex];
		vec4 posePosition = jointTransform * vec4(position, 1.0);
		totalLocalPos += posePosition * weights[i];
	}

	//mat4 BoneTransform = weights.x * bones[int(indexes.x)];
    //BoneTransform += weights.y * bones[int(indexes.y)];
	//vec4 newVertex = BoneTransform * vec4(position, 1.0);

	Texcoord = texcoord;
    gl_Position = projection * view * model * totalLocalPos;
}