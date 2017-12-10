package zyx.logic.converter.smd.bones;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Triangle
{

	private int vertexIndex;
	public final Vertex[] verticies;

	public Triangle()
	{
		verticies = new Vertex[3];
		verticies[0] = new Vertex();
		verticies[1] = new Vertex();
		verticies[2] = new Vertex();
	}
	
	public void setVertexData(int nodeId, Vector3f pos, Vector3f norm, Vector2f uv)
	{
		Vertex vertex = verticies[vertexIndex];
		vertex.nodeId = nodeId;
		vertex.x = pos.x;
		vertex.y = pos.y;
		vertex.z = pos.z;
		vertex.normX = norm.x;
		vertex.normY = norm.y;
		vertex.normZ = norm.z;
		vertex.u = uv.x;
		vertex.v = uv.y;
	}
	
	public void addVertexBoneWeight(int nodeId, float weight)
	{
		VertexBone boneWeight = new VertexBone();
		boneWeight.nodeId = nodeId;
		boneWeight.weight = weight;
		
		Vertex vertex = verticies[vertexIndex];
		vertex.boneWeights.add(boneWeight);
	}
	
	public void endVertex()
	{
		vertexIndex++;
	}

	public class Vertex
	{
		public int nodeId;
		public float x, y, z;
		public float normX, normY, normZ;
		public float u, v;

		public final ArrayList<VertexBone> boneWeights = new ArrayList<>();
	}

	public class VertexBone
	{
		public int nodeId;
		public float weight;
	}
}
