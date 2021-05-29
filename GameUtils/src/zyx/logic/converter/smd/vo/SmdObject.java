package zyx.logic.converter.smd.vo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.util.vector.Vector3f;

public class SmdObject
{
	private ArrayList<SmdSubMesh> subMeshes = new ArrayList<>();
	private Bone rootBone;
	private ArrayList<Animation> animations = new ArrayList<>();
	private PhysInformation phys = new PhysInformation();
	private String skeletonPath;
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

	public void setSkeletonPath(String skeletonPath)
	{
		this.skeletonPath = skeletonPath;
	}

	public Bone getRootBone()
	{
		return rootBone;
	}

	public void calculateRadius()
	{
		float minX = 0;
		float maxX = 0;
		float minY = 0;
		float maxY = 0;
		float minZ = 0;
		float maxZ = 0;
		
		for (SmdSubMesh subMesh : subMeshes)
		{
			for (Vertex vertex : subMesh.verticies)
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
		byte subMeshCount = (byte) subMeshes.size();
		out.write(subMeshCount);
		
		for (int i = 0; i < subMeshCount; i++)
		{
			SmdSubMesh subMesh = subMeshes.get(i);
			out.writeByte(subMesh.maxBoneCount);

			out.writeInt(subMesh.verticies.size());
			for (Vertex vertex : subMesh.verticies)
			{
				vertex.save(out);
			}

			out.writeInt(subMesh.elements.size());
			for (Integer element : subMesh.elements)
			{
				out.writeShort(element);
			}

			out.writeUTF(subMesh.diffuseTexturePath);
			out.writeUTF(subMesh.normalTexturePath);
			out.writeUTF(subMesh.specularTexturePath);
			
			subMesh.materialInfo.save(out);
		}
		
		phys.save(out);

		out.writeFloat(radiusCenter.x);
		out.writeFloat(radiusCenter.y);
		out.writeFloat(radiusCenter.z);
		out.writeFloat(radius);

		out.writeUTF(skeletonPath);
	}
}
