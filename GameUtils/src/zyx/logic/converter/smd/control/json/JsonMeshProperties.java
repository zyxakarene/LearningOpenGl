package zyx.logic.converter.smd.control.json;

import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class JsonMeshProperties
{
	public static final String PROPERTY_Z_WRITE = "zWrite";
	public static final String PROPERTY_Z_TEST = "zTest";
	public static final String PROPERTY_CULLING = "culling";
	public static final String PROPERTY_BLEND_SRC = "blendSrc";
	public static final String PROPERTY_BLEND_DST = "blendDst";
	public static final String PROPERTY_PRIORITY = "priority";
	public static final String PROPERTY_STENCIL_MODE = "stencilMode";
	public static final String PROPERTY_STENCIL_LAYER = "stencilLayer";

	public boolean zWrite;
	public int zTest;
	public int culling;
	public int blendSrc;
	public int blendDst;
	public int priority;
	public int stencilMode;
	public int stencilLayer;

	public JsonMeshProperties()
	{
		zWrite = true;
		zTest = GL11.GL_LEQUAL;
		culling = GL11.GL_BACK;
		blendSrc = GL11.GL_ONE;
		blendDst = GL11.GL_ZERO;
		priority = 10000;
		stencilMode = 0;
		stencilLayer = 0;
	}
	
	void read(JSONObject jsonProperties)
	{
		zWrite = JsonMethods.getBoolean(jsonProperties, PROPERTY_Z_WRITE, true);
		zTest = JsonMethods.getInt(jsonProperties, PROPERTY_Z_TEST, GL11.GL_LEQUAL);
		culling = JsonMethods.getInt(jsonProperties, PROPERTY_CULLING, GL11.GL_BACK);
		blendSrc = JsonMethods.getInt(jsonProperties, PROPERTY_BLEND_SRC, GL11.GL_ONE);
		blendDst = JsonMethods.getInt(jsonProperties, PROPERTY_BLEND_DST, GL11.GL_ZERO);
		priority = JsonMethods.getInt(jsonProperties, PROPERTY_PRIORITY, 10000);
		stencilMode = JsonMethods.getInt(jsonProperties, PROPERTY_STENCIL_MODE, 0);
		stencilLayer = JsonMethods.getInt(jsonProperties, PROPERTY_STENCIL_LAYER, 0);
	}

	void save(JSONObject json)
	{
		json.put(PROPERTY_Z_WRITE, zWrite);
		json.put(PROPERTY_Z_TEST, zTest);
		json.put(PROPERTY_CULLING, culling);
		json.put(PROPERTY_BLEND_SRC, blendSrc);
		json.put(PROPERTY_BLEND_DST, blendDst);
		json.put(PROPERTY_PRIORITY, priority);
		json.put(PROPERTY_STENCIL_MODE, stencilMode);
		json.put(PROPERTY_STENCIL_LAYER, stencilLayer);
	}
}
