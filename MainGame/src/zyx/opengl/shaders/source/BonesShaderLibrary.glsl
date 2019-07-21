const int MAX_JOINTS = 20;//max joints allowed in a skeleton
const int MAX_WEIGHTS = 2;//max number of joints that can affect a vertex

uniform mat4 bones[MAX_JOINTS];
uniform mat4 bonesInverseTranspose[MAX_JOINTS];

void calculateBones(vec3 vertexPos, vec3 vertexNormal, vec2 vertexIndex, vec2 vertexWeight, inout vec4 resultPos, inout vec4 resultNormal)
{
	for(int i = 0; i < MAX_WEIGHTS; i++)
    {
        int intIndex = int(vertexIndex[i]);
        mat4 jointTransform = bones[intIndex];
        mat4 inverseTransform = bonesInverseTranspose[intIndex];

        vec4 posePosition = jointTransform * vec4(vertexPos, 1.0);
        vec4 normPosition = inverseTransform * vec4(vertexNormal, 1.0);

        resultPos += posePosition * vertexWeight[i];
        resultNormal += normPosition * vertexWeight[i];
    }
}
