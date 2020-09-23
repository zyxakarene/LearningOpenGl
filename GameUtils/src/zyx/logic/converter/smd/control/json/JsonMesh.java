package zyx.logic.converter.smd.control.json;

import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	public String meshOutput;
	
	public String skeletonMesh;
	public String SkeletonOutput;
	
	public JsonMeshProperties meshProperties;
	public JsonMeshFiles meshFiles;
	public JsonMeshTextures textureFiles;
	public JsonMeshAnimations animations;
	
	public JsonMesh(File file)
	{
		this.file = file;
		
		meshProperties = new JsonMeshProperties();
		meshFiles = new JsonMeshFiles();
		textureFiles = new JsonMeshTextures();
		animations = new JsonMeshAnimations();
		
		JSONObject obj = parseFrom(file);
		readFrom(obj);
	}
	
	private void readFrom(JSONObject obj)
	{
		type = JsonMethods.getString(obj, PROPERTY_TYPE);
		meshSkeleton = JsonMethods.getString(obj, PROPERTY_MESH_SKELETON);
		meshOutput = JsonMethods.getString(obj, PROPERTY_MESH_OUT);
		skeletonMesh = JsonMethods.getString(obj, PROPERTY_SKELETON_MESH);
		SkeletonOutput = JsonMethods.getString(obj, PROPERTY_SKELETON_OUT);
		
		JSONObject jsonFiles = JsonMethods.getObject(obj, PROPERTY_MESH_FILES);
		JSONObject jsonProperties = JsonMethods.getObject(obj, PROPERTY_MESH_PROPERTIES);
		JSONObject jsonTextures = JsonMethods.getObject(obj, PROPERTY_TEXTURE_FILES);
		JSONArray jsonAnimations = JsonMethods.getArray(obj, PROPERTY_ANIMATIONS);
		
		meshFiles.read(jsonFiles);
		meshProperties.read(jsonProperties);
		textureFiles.read(jsonTextures);
		animations.read(jsonAnimations);
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

	public void save() throws IOException
	{
		JSONObject json = new JSONObject();
		JSONObject jsonFiles = new JSONObject();
		JSONObject jsonProperties = new JSONObject();
		JSONObject jsonTextures = new JSONObject();
		JSONArray jsonAnimations = new JSONArray();
		
		json.put(PROPERTY_TYPE, type);
		json.put(PROPERTY_MESH_SKELETON, meshSkeleton);
		json.put(PROPERTY_MESH_OUT, meshOutput);
		json.put(PROPERTY_SKELETON_MESH, skeletonMesh);
		json.put(PROPERTY_SKELETON_OUT, SkeletonOutput);
		
		json.put(PROPERTY_MESH_FILES, jsonFiles);
		json.put(PROPERTY_MESH_PROPERTIES, jsonProperties);
		json.put(PROPERTY_TEXTURE_FILES, jsonTextures);
		json.put(PROPERTY_ANIMATIONS, jsonAnimations);
		
		meshFiles.save(jsonFiles);
		meshProperties.save(jsonProperties);
		textureFiles.save(jsonTextures);
		animations.save(jsonAnimations);
		
		String output = json.toJSONString();
		try (FileWriter writer = new FileWriter(file))
		{
			writer.write(output);
			writer.flush();
		}
	}
}
