package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

class BoneInfoObject
{
	byte[] boneIds;
	String[] boneNames;

	private HashMap<Byte, String> idToNameMap;
	
	BoneInfoObject()
	{
		idToNameMap = new HashMap<>();
	}
	
	void read(DataInputStream in) throws IOException
	{
		byte boneCount = in.readByte();
	
		boneIds = new byte[boneCount];
		boneNames = new String[boneCount];
		
		for (int i = 0; i < boneCount; i++)
		{
			byte id = in.readByte();
			String name = in.readUTF();
			
			boneIds[i] = id;
			boneNames[i] = name;
			
			idToNameMap.put(id, name);
		}
	}

	String getBoneName(byte id)
	{
		return idToNameMap.get(id);
	}
}
