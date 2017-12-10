package zyx.logic.converter.smd.bones.importer;

import java.util.ArrayList;
import zyx.logic.converter.smd.bones.INode;
import zyx.logic.converter.smd.bones.ISkeleton;
import zyx.logic.converter.smd.bones.SmdValueObject;
import zyx.logic.converter.smd.bones.Triangle;

public class SmdBuilder
{

	private INode[] nodes;
	private ISkeleton skeleton;
	private Triangle[] triangles;
	
	public void addNodes(INode[] nodes)
	{
		this.nodes = nodes;
	}
	
	public void addSkeleton(ISkeleton skeleton)
	{
		this.skeleton = skeleton;
	}
	
	public void addTriangles(Triangle[] triangles)
	{
		this.triangles = triangles;
	}

	public SmdValueObject build()
	{
		SmdValueObject vo = new SmdValueObject();
		
		skeleton.addNodes(nodes);
		
		skeleton.getFrames()[0].updateBones();
		
		ArrayList<Float> vertexData = new ArrayList<>();
		for (int i = 0; i < triangles.length; i++)
		{
			Triangle triangle = triangles[i];
			for (int j = 0; j < triangle.verticies.length; j++)
			{
				vertexData.clear();
				Triangle.Vertex vertex = triangle.verticies[j];

				addVertexData(vertexData, vertex);
				vo.addVertexData(vertexData);
			}
		}
		
		return vo;
	}

	private void addVertexData(ArrayList<Float> vertexData, Triangle.Vertex vertex)
	{
		vertexData.add(vertex.x);
		vertexData.add(vertex.y);
		vertexData.add(vertex.z);
		
		vertexData.add(vertex.normX);
		vertexData.add(vertex.normY);
		vertexData.add(vertex.normZ);
		
		vertexData.add(vertex.u);
		vertexData.add(vertex.v);
		
		if (vertex.boneWeights.isEmpty())
		{
			vertexData.add(new Float(vertex.nodeId));
			vertexData.add(0f);
			
			vertexData.add(1f);
			vertexData.add(0f);
		}
		else if (vertex.boneWeights.size() == 1)
		{
			vertexData.add(new Float(vertex.boneWeights.get(0).nodeId));
			vertexData.add(0f);
			
			vertexData.add(vertex.boneWeights.get(0).weight);
			vertexData.add(0f);
		}
		else
		{
			float n0 = vertex.boneWeights.get(0).nodeId;
			float w0 = vertex.boneWeights.get(0).weight;
			
			float n1 = vertex.boneWeights.get(1).nodeId;
			float w1 = vertex.boneWeights.get(1).weight;
			
			if (w0 + w1 != 1f)
			{
				System.out.println(w0 + " + " + w1 + " != 1. Was: " + (w0 + w1));
				w0 += (1f - (w0 > w1 ? w0 : w1));
				
				float sum = w0 + w1;
				if (sum > 1f)
				{
					w0 -= sum % 1;
				}
				
				
				System.out.println(w0 + " + " + w1 + " = " + (w0 + w1));
			}
			
			vertexData.add(n0);
			vertexData.add(n1);
			
			vertexData.add(w0);
			vertexData.add(w1);
			
		}
	}
}
