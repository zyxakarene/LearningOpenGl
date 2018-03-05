package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import zyx.logic.converter.smd.reader.SmdTriangleHandler;

public class SmdObject
{
	private Bone rootBone;
	private ArrayList<Vertex> verticies;
	private ArrayList<Animation> animations = new ArrayList<>();
	private ArrayList<PhysBox> physBoxes = new ArrayList<>();
	private ArrayList<Integer> elements;
	private String texturePath;

	public void setRootBone(Bone bone)
	{
		this.rootBone = bone;
	}

	public void setTexturePath(String texturePath)
	{
		this.texturePath = texturePath;
	}

	public Bone getRootBone()
	{
		return rootBone;
	}

	public void setTriangleData(SmdTriangleHandler.Response response)
	{
		this.verticies = response.verticies;
		this.elements = response.elements;
	}
	
	public void addAnimation(Animation animation)
	{
		animations.add(animation);
	}

	public void setPhysBoxes(ArrayList<PhysBox> physBoxes)
	{
		this.physBoxes = physBoxes;
	}
	
	public void save(DataOutputStream out) throws IOException
	{
		rootBone.save(out);
		
		out.writeInt(verticies.size());
		for (Vertex vertex : verticies)
		{
			vertex.save(out);
		}
		
		out.writeInt(elements.size());
		for (Integer element : elements)
		{
			out.writeShort(element);
		}
		
		out.writeInt(animations.size());
		for (Animation animation : animations)
		{
			animation.save(out);
		}
		
		out.writeInt(physBoxes.size());
		for (PhysBox physBox : physBoxes)
		{
			physBox.save(out);
		}
		
		out.writeUTF(texturePath);
	}
}
