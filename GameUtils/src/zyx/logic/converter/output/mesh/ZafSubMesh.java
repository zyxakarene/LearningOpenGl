package zyx.logic.converter.output.mesh;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import zyx.logic.converter.output.ISaveable;

public class ZafSubMesh implements ISaveable
{

	public byte boneCount;
	public ArrayList<ZafVertex> vertecies;
	public ArrayList<Short> elementData;
	public String diffuseTexture;
	public String normalTexture;
	public String specularTexture;
	public ZafMaterial material;

	public ZafSubMesh()
	{
		boneCount = 0;
		vertecies = new ArrayList<>();
		elementData = new ArrayList<>();

		diffuseTexture = "texture.default_diffuse";
		normalTexture = "normal.default_normal";
		specularTexture = "specular.default_specular";
		material = new ZafMaterial();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(boneCount);
		
		out.writeInt(vertecies.size());
		for (ZafVertex vertex : vertecies)
		{
			vertex.save(out);
		}
		
		out.writeInt(elementData.size());
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
