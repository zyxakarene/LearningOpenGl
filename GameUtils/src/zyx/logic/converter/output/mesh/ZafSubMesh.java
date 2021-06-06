package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import zyx.logic.converter.output.ISaveable;

public class ZafSubMesh implements ISaveable
{

	public byte boneCount;
	public ZafVertex[] vertecies;
	public short[] elementData;
	public String diffuseTexture;
	public String normalTexture;
	public String specularTexture;
	public ZafMaterial material;

	public ZafSubMesh()
	{
		boneCount = 0;
		vertecies = new ZafVertex[0];
		elementData = new short[0];

		diffuseTexture = "texture.default";
		normalTexture = "normal.default";
		specularTexture = "specular.default";
		material = new ZafMaterial();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(boneCount);
		
		out.writeInt(vertecies.length);
		for (ZafVertex vertex : vertecies)
		{
			vertex.save(out);
		}
		
		out.writeInt(elementData.length);
		for (short element : elementData)
		{
			out.writeShort(element);
		}
		
		out.writeUTF(diffuseTexture);
		out.writeUTF(normalTexture);
		out.writeUTF(specularTexture);
		
		material.save(out);
	}
}
