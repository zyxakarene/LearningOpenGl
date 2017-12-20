#version 420

const int MAX_JOINTS = 20;//max joints allowed in a skeleton
const int MAX_WEIGHTS = 2;//max number of joints that can affect a vertex


layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normals;
layout(location = 2) in vec2 texcoord;
layout(location = 3) in vec2 indexes;
layout(location = 4) in vec2 weights;

out vec2 Texcoord;
out vec3 Normal;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform mat4 bones[MAX_JOINTS];

void main()
{
    vec4 totalLocalPos = vec4(0);
    vec4 totalLocalNorm = vec4(0);
    mat4 normalMatrix = model;

    int a = int(indexes[0]);
    int b = int(indexes[1]);

    for(int i=0;i<MAX_WEIGHTS;i++)
    {
        int intIndex = int(indexes[i]);
        mat4 jointTransform = bones[intIndex];
        mat4 inverseTransform = transpose(inverse(jointTransform));

        vec4 posePosition = jointTransform * vec4(position, 1.0);
        vec4 normPosition = inverseTransform * vec4(normals, 1.0);

        totalLocalPos += posePosition * weights[i];
        totalLocalNorm += normPosition * weights[i];

        normalMatrix *= jointTransform;
    }

    totalLocalNorm = normalize(totalLocalNorm);

    Texcoord = texcoord;
    Normal = mat3(transpose(inverse(model))) * vec3(totalLocalNorm);  
    gl_Position = projection * view * model * totalLocalPos;
}