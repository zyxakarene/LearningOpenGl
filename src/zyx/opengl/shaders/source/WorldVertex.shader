#version 420

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in vec2 indexes;
layout(location = 4) in vec2 weights;

out vec2 Texcoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform mat4 bones[10];

void main()
{
	mat4 BoneTransform = weights.x * bones[int(indexes.x)];
    BoneTransform += weights.y * bones[int(indexes.y)];

	vec4 newVertex = BoneTransform * vec4(position, 1.0);

	Texcoord = texcoord;
    gl_Position = projection * view * model * newVertex;
}