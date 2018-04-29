#version 420

layout(location = 0) in vec2 position;
layout(location = 2) in vec2 texcoord;

out vec2 Texcoord;

// Values that stay constant for the whole mesh.
uniform vec3 CameraRight_worldspace;
uniform vec3 CameraUp_worldspace;

uniform vec3 BillboardPos; // Position of the center of the billboard
uniform vec2 BillboardSize; // Size of the billboard, in world units (probably meters)

uniform mat4 view;
uniform mat4 projection;

void main(void)
{
	vec3 particleCenter_wordspace = BillboardPos;
	
	vec3 vertexPosition_worldspace = 
		particleCenter_wordspace
		+ CameraRight_worldspace * position.x * BillboardSize.x
		+ CameraUp_worldspace * position.y * BillboardSize.y;


	// Output position of the vertex
	gl_Position = view * projection * vec4(vertexPosition_worldspace, 1.0f);

	//gl_Position = projection * view * vec4(position, 0.0, 1.0);
}