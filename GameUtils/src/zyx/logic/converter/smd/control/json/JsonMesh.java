package zyx.logic.converter.smd.control.json;

import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zyx.UtilConstants;

public class JsonMesh
{
	public static final String TYPE_SKELETON = "skeleton";
	public static final String TYPE_MESH = "mesh";
	
	public static final String PROPERTY_TYPE = "type";
	public static final String PROPERTY_MESH_SKELETON = "meshSkeleton";
	public static final String PROPERTY_MESH_OUT = "meshOutput";
	
	public static final String PROPERTY_SKELETON_MESH = "skeletonMesh";
	public static final String PROPERTY_SKELETON_OUT = "skeletonOutput";
	
	public static final String PROPERTY_MESH_FILES = "meshFiles";
	public static final String PROPERTY_MESH_PROPERTIES = "meshProperties";
	public static final String PROPERTY_TEXTURE_FILES = "textureFiles";
	public static final String PROPERTY_ANIMATIONS = "animations";

	public File file;
	
	public String type;
	
	public String meshSkeleton;
	public File meshOutput;
	
	public File skeletonMesh;
	public File SkeletonOutput;
	
	public JsonMeshProperties meshProperties;
	public JsonMeshFiles meshFiles;
	public JsonMeshTextures textureFiles;
	public JsonMeshAnimations meshAnimations;
	
	public JsonMesh(File file)
	{
		this.file = file;
		
		meshProperties = new JsonMeshProperties();
		meshFiles = new JsonMeshFiles();
		textureFiles = new JsonMeshTextures();
		meshAnimations = new JsonMeshAnimations();
		
		if (file.exists())
		{
			JSONObject obj = parseFrom(file);
			readFrom(obj);
		}
	}
	
	private void readFrom(JSONObject obj)
	{
		type = JsonMethods.getString(obj, PROPERTY_TYPE);
		meshSkeleton = JsonMethods.getString(obj, PROPERTY_MESH_SKELETON);
		String meshOut = JsonMethods.getString(obj, PROPERTY_MESH_OUT);
		String skeletonRefMesh = JsonMethods.getString(obj, PROPERTY_SKELETON_MESH);
		String skeletonOut = JsonMethods.getString(obj, PROPERTY_SKELETON_OUT);
		
		meshOutput = new File(UtilConstants.ASSETS_OUTPUT + meshOut);
		skeletonMesh = new File(UtilConstants.BASE_FOLDER + skeletonRefMesh);
		SkeletonOutput = new File(UtilConstants.ASSETS_OUTPUT + skeletonOut);

		if (meshOutput.exists() == false || meshOutput.isDirectory())
		{
			meshOutput = null;
		}

		if (skeletonMesh.exists() == false || skeletonMesh.isDirectory())
		{
			skeletonMesh = null;
		}

		if (SkeletonOutput.exists() == false || SkeletonOutput.isDirectory())
		{
			SkeletonOutput = null;
		}
		
		JSONObject jsonFiles = JsonMethods.getObject(obj, PROPERTY_MESH_FILES);
		JSONObject jsonProperties = JsonMethods.getObject(obj, PROPERTY_MESH_PROPERTIES);
		JSONObject jsonTextures = JsonMethods.getObject(obj, PROPERTY_TEXTURE_FILES);
		JSONArray jsonAnimations = JsonMethods.getArray(obj, PROPERTY_ANIMATIONS);
		
		meshFiles.read(jsonFiles);
		meshProperties.read(jsonProperties);
		textureFiles.read(jsonTextures);
		meshAnimations.read(jsonAnimations);
	}

	private JSONObject parseFrom(File file)
	{
		try (Reader reader = new FileReader(file);)
		{
			return (JSONObject) new JSONParser().parse(reader);
		}
		catch (IOException | ParseException ex)
		{
			ex.printStackTrace();
			return new JSONObject();
		}
	}
	
	public boolean isMesh()
	{
		if (type == null)
		{
			type = TYPE_MESH;
		}
		
		return type.equals(TYPE_MESH);
	}
	
	public boolean isSkeleton()
	{
		return !isMesh();
	}

	public void save() throws IOException
	{
		JSONObject json = new JSONObject();
		JSONObject jsonFiles = new JSONObject();
		JSONObject jsonProperties = new JSONObject();
		JSONObject jsonTextures = new JSONObject();
		JSONArray jsonAnimations = new JSONArray();
		
		json.put(PROPERTY_TYPE, type);
		JsonMethods.putOutputFile(json, PROPERTY_MESH_OUT, meshOutput);
		JsonMethods.putOutputFile(json, PROPERTY_SKELETON_OUT, SkeletonOutput);
		JsonMethods.putSourceFile(json, PROPERTY_SKELETON_MESH, skeletonMesh);
		json.put(PROPERTY_MESH_SKELETON, meshSkeleton);		
		json.put(PROPERTY_MESH_FILES, jsonFiles);
		json.put(PROPERTY_MESH_PROPERTIES, jsonProperties);
		json.put(PROPERTY_TEXTURE_FILES, jsonTextures);
		json.put(PROPERTY_ANIMATIONS, jsonAnimations);
		
		meshFiles.save(jsonFiles);
		meshProperties.save(jsonProperties);
		textureFiles.save(jsonTextures);
		meshAnimations.save(jsonAnimations);
		
		String output = json.toJSONString();
		try (FileWriter writer = new FileWriter(file))
		{
			writer.write(output);
			writer.flush();
		}
	}

	public String getSkeletonResourceName()
	{
		if (meshSkeleton != null && meshSkeleton.length() > 0)
		{
			if (meshSkeleton.endsWith(".skeleton"))
			{
				String prePart = meshSkeleton.replace(".skeleton", "");
				return "skeleton." + prePart;
			}
			
			return meshSkeleton;
		}

		return "skeleton.default";
	}
}
