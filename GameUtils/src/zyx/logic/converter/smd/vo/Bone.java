package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class Bone
{
	private static final Bone MESH_BONE = new Bone((byte) 0, "dummy");
	
	private byte id;
	private String name;
	private ArrayList<Bone> children;
	
	private Vector3f restPos;
	private Quaternion restRot;

	public Bone(byte id, String name)
	{
		this.id = id;
		this.name = name;
		this.children = new ArrayList<>();
	}

	public void setRestData(Vector3f restPos, Vector3f restRot)
	{
		this.restPos = new Vector3f(restPos);
		this.restRot = EulerToQuat.transform(restRot);
	}
	
	public void addChild(Bone child)
	{
		children.add(child);
	}

	public HashMap<Byte, Bone> toBoneMap()
	{
		HashMap<Byte, Bone> map = new HashMap<>();
		addToBoneMap(map);

		if (map.containsKey(MESH_BONE.id) == false)
		{
			System.out.println("No boneId zero. Adding it manually!");
			map.put(MESH_BONE.id, MESH_BONE);
		}
		
		return map;
	}
	
	private void addToBoneMap(HashMap<Byte, Bone> map)
	{
		map.put(id, this);
		for (Bone child : children)
		{
			child.addToBoneMap(map);
		}
	}

	public String getName()
	{
		return name;
	}
	
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(id);
		out.writeUTF(name);
		
		out.writeFloat(restPos.x);
		out.writeFloat(restPos.y);
		out.writeFloat(restPos.z);
		out.writeFloat(restRot.x);
		out.writeFloat(restRot.y);
		out.writeFloat(restRot.z);
		out.writeFloat(restRot.w);
		
		out.writeByte(children.size());
		for (Bone bone : children)
		{
			bone.save(out);
		}
	}

	short getBoneCount()
	{
		short count = 1;
		
		for (Bone child : children)
		{
			count += child.getBoneCount();
		}
		
		return count;
	}
	
	
	
}
