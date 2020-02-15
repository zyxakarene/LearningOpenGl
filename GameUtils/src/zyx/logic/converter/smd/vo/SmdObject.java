package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.util.vector.Vector3f;
import zyx.logic.converter.smd.reader.SmdTriangleHandler;

public class SmdObject
{
	private Bone rootBone;
	private ArrayList<Vertex> verticies;
	private ArrayList<Animation> animations = new ArrayList<>();
	private PhysInformation phys = new PhysInformation();
	private ArrayList<Integer> elements;
	private String skeletonPath;
	private String diffuseTexturePath;
	private String normalTexturePath;
	private String specularTexturePath;
	private boolean isSkeleton;
	
	private Vector3f radiusCenter = new Vector3f();
	private float radius = 0;

	public void setRootBone(Bone bone)
	{
		this.rootBone = bone;
	}
	
	public void setSkeleton(boolean skeleton)
	{
		this.isSkeleton = skeleton;
	}

	public void setDiffuseTexturePath(String diffuseTexturePath)
	{
		this.diffuseTexturePath = diffuseTexturePath;
	}

	public void setNormalTexturePath(String normalTexturePath)
	{
		this.normalTexturePath = normalTexturePath;
	}

	public void setSpecularTexturePath(String specularTexturePath)
	{
		this.specularTexturePath = specularTexturePath;
	}

	public void setSkeletonPath(String skeletonPath)
	{
		this.skeletonPath = skeletonPath;
	}

	public Bone getRootBone()
	{
		return rootBone;
	}

	public void setTriangleData(SmdTriangleHandler.Response response)
	{
		this.verticies = response.verticies;
		this.elements = response.elements;
		
		calculateRadius(verticies);
	}
	
	private void calculateRadius(ArrayList<Vertex> verticies)
	{
		float minX = 0;
		float maxX = 0;
		float minY = 0;
		float maxY = 0;
		float minZ = 0;
		float maxZ = 0;
		for (Vertex vertex : verticies)
		{
			Vector3f position = vertex.getPos();
			
			if (position.x < minX)
			{
				minX = position.x;
			}
			else if (position.x > maxX)
			{
				maxX = position.x;
			}
			
			if (position.y < minY)
			{
				minY = position.y;
			}
			else if (position.y > maxY)
			{
				maxY = position.y;
			}
			
			if (position.z < minZ)
			{
				minZ = position.z;
			}
			else if (position.z > maxZ)
			{
				maxZ = position.z;
			}
		}
		
		float diffX = maxX - minX;
		float diffY = maxY - minY;
		float diffZ = maxZ - minZ;
		
		float centerX = minX + (diffX / 2f);
		float centerY = minY + (diffY / 2f);
		float centerZ = minZ + (diffZ / 2f);
		
		float localRadius = diffX > diffY ? diffX : diffY;
		localRadius = localRadius > diffZ ? localRadius : diffZ;
		localRadius = localRadius / 2f;
		
		radius = localRadius;
		radiusCenter.set(centerX, centerY, centerZ);
	}
	
	public void addAnimation(Animation animation)
	{
		animations.add(animation);
	}

	public void setPhysBoxes(ArrayList<PhysBox> physBoxes)
	{
		this.phys.setBoxes(physBoxes);
	}
	
	public void setBoundingBox(Vector3f min, Vector3f max)
	{
		this.phys.setBoundingBox(min, max);
	}
	
	public void save(DataOutputStream out) throws IOException
	{
		if (isSkeleton)
		{
			saveAsSkeleton(out);
		}
		else
		{
			saveAsMesh(out);
		}
	}

	private void saveAsSkeleton(DataOutputStream out) throws IOException
	{
		HashMap<Byte, Bone> boneMap = rootBone.toBoneMap();
		out.writeByte(boneMap.size());
		
		for (Map.Entry<Byte, Bone> entry : boneMap.entrySet())
		{
			byte boneId = entry.getKey();
			Bone bone = entry.getValue();
			
			out.writeByte(boneId);
			out.writeUTF(bone.getName());
		}
				
		rootBone.save(out);
		
		out.writeInt(animations.size());
		for (Animation animation : animations)
		{
			animation.save(out);
		}
	}

	private void saveAsMesh(DataOutputStream out) throws IOException
	{
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
		
		phys.save(out);
		
		out.writeUTF(diffuseTexturePath);
		out.writeUTF(normalTexturePath);
		out.writeUTF(specularTexturePath);
		
		out.writeFloat(radiusCenter.x);
		out.writeFloat(radiusCenter.y);
		out.writeFloat(radiusCenter.z);
		out.writeFloat(radius);
		
		out.writeUTF(skeletonPath);
	}
}
