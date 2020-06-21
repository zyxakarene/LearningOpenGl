const int MAX_JOINTS = 25;//max joints allowed in a skeleton

uniform mat4 bones[MAX_JOINTS];
uniform mat4 bonesInverseTranspose[MAX_JOINTS];

void calculateBones(vec3 vertexPos, vec3 vertexNormal, float index, float vertexWeight, inout vec4 resultPos, inout vec4 resultNormal)
{
	int intIndex = int(index);
	mat4 jointTransform = bones[intIndex];
	mat4 inverseTransform = bonesInverseTranspose[intIndex];

	vec4 posePosition = jointTransform * vec4(vertexPos, 1.0);
	vec4 normPosition = inverseTransform * vec4(vertexNormal, 1.0);

	resultPos += posePosition * vertexWeight;
	resultNormal += normPosition * vertexWeight;
}

void calculateBones(vec3 vertexPos, vec3 vertexNormal, vec4 vertexIndex, vec4 vertexWeight, inout vec4 resultPos, inout vec4 resultNormal)
{
	calculateBones(vertexPos, vertexNormal, vertexIndex.x, vertexWeight.x, resultPos, resultNormal);
	calculateBones(vertexPos, vertexNormal, vertexIndex.y, vertexWeight.y, resultPos, resultNormal);
	calculateBones(vertexPos, vertexNormal, vertexIndex.z, vertexWeight.z, resultPos, resultNormal);
	calculateBones(vertexPos, vertexNormal, vertexIndex.w, vertexWeight.w, resultPos, resultNormal);
}

void calculateBones(vec3 vertexPos, vec3 vertexNormal, vec3 vertexIndex, vec3 vertexWeight, inout vec4 resultPos, inout vec4 resultNormal)
{
	calculateBones(vertexPos, vertexNormal, vertexIndex.x, vertexWeight.x, resultPos, resultNormal);
	calculateBones(vertexPos, vertexNormal, vertexIndex.y, vertexWeight.y, resultPos, resultNormal);
	calculateBones(vertexPos, vertexNormal, vertexIndex.z, vertexWeight.z, resultPos, resultNormal);
}

void calculateBones(vec3 vertexPos, vec3 vertexNormal, vec2 vertexIndex, vec2 vertexWeight, inout vec4 resultPos, inout vec4 resultNormal)
{
	calculateBones(vertexPos, vertexNormal, vertexIndex.x, vertexWeight.x, resultPos, resultNormal);
	calculateBones(vertexPos, vertexNormal, vertexIndex.y, vertexWeight.y, resultPos, resultNormal);
}
