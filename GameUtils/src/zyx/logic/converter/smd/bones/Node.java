package zyx.logic.converter.smd.bones;

import org.lwjgl.util.vector.Matrix4f;

public class Node implements INode
{

	private int nodeId;
	private String name;
	private Node parentNode;
	private Matrix4f inverseRest;

	public Node()
	{
		inverseRest = new Matrix4f();
	}
	
	public void setResetBone(float x, float y, float z, float rotX, float rotY, float rotZ)
	{
//		FloatMath.changeMatrix(inverseRest, x, y, z, rotX, rotY, rotZ);
		inverseRest.invert();
	}
	
	public void setNodeId(int nodeId)
	{
		this.nodeId = nodeId;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setParentNode(Node parentNode)
	{
		this.parentNode = parentNode;
	}

	@Override
	public int getNodeId()
	{
		return nodeId;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Node getParentNode()
	{
		return parentNode;
	}

	public Matrix4f getInverseRest()
	{
		return inverseRest;
	}
	

}
