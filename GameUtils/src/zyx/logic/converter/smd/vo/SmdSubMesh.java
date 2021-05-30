package zyx.logic.converter.smd.vo;

import java.util.ArrayList;
import zyx.logic.converter.smd.control.json.JsonMeshPropertyEntry;
import zyx.logic.converter.smd.reader.SmdTriangleHandler;

public class SmdSubMesh
{

	byte maxBoneCount;
	ArrayList<Vertex> verticies;
	ArrayList<Integer> elements;
	MaterialInformation materialInfo = new MaterialInformation();
	String diffuseTexturePath;
	String normalTexturePath;
	String specularTexturePath;
	
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
	
	public void setTriangleData(SmdTriangleHandler.Response response)
	{
		this.verticies = response.verticies;
		this.elements = response.elements;
	}
	
	public void setMaxBoneCount(byte maxBoneCount)
	{
		this.maxBoneCount = maxBoneCount;
	}

	public int getMaxBoneCount()
	{
		return maxBoneCount;
	}
	
	public void setMaterialInfo(JsonMeshPropertyEntry meshProperties)
	{
		materialInfo.SetFrom(meshProperties);
	}
}
