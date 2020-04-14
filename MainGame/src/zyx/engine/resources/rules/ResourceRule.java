package zyx.engine.resources.rules;

import zyx.engine.resources.impl.*;
import zyx.engine.resources.impl.meshes.MeshBatchResource;
import zyx.engine.resources.impl.meshes.MeshResource;
import zyx.engine.resources.impl.meshes.SkeletonResource;
import zyx.engine.resources.impl.sheet.SpriteSheetJsonResource;
import zyx.engine.resources.impl.sheet.SpriteSheetResource;
import zyx.engine.resources.impl.sheet.SpriteSheetTextureResource;
import zyx.engine.resources.impl.textures.NormalTextureResource;
import zyx.engine.resources.impl.textures.SpecularTextureResource;
import zyx.engine.resources.impl.textures.TextureResource;

public enum ResourceRule
{
	UI_ITEM_1_FOLDERS	("json.#",				"assets/json/#.json",		JsonResource.class),
	UI_ITEM_2_FOLDERS	("json.#.#",			"assets/json/#/#.json",		JsonResource.class),
	
	SPRITE_SHEET_PNG	("sprite_sheet_png",	"assets/sprite_sheet.png",	SpriteSheetTextureResource.class),
	SPRITE_SHEET_JSON	("sprite_sheet_json",	"assets/sprite_sheet.json",	SpriteSheetJsonResource.class),
	SPRITE_SHEET_ITEM	("#",					"#",						SpriteSheetResource.class),
	
	FONT_BASE			("font.#",				"assets/fonts/#.zff",		FontResource.class),
	FONT_TEXTURE		("font.texture.#",		"assets/fonts/#.png",		TextureResource.class),
	
	PARTICLE_BASE		("particles.#",			"assets/effects/#.zpf",		ParticleResource.class),
	
	MESHES_0_FOLDERS	("mesh.#",				"assets/models/#.zaf",		MeshResource.class),
	MESHES_1_FOLDERS	("mesh.#.#",			"assets/models/#/#.zaf",	MeshResource.class),
	
	SKELETONS			("skeleton.#",			"assets/models/skeletons/#.skeleton",	SkeletonResource.class),
	
	MESH_BATCH_0_FOLDER	("meshbatch.#",			"assets/models/#.zaf",		MeshBatchResource.class),
	MESH_BATCH_1_FOLDER	("meshbatch.#.#",		"assets/models/#/#.zaf",	MeshBatchResource.class),
	
	CUBEMAP				("cubemap.#",			"assets/cubemaps/#.cube",		CubemapResource.class),
	SKYBOX_TEXTURE		("skybox.texture.#",	"assets/textures/skybox/#.png",	TextureResource.class),
	SKYBOX				("skybox.mesh.#",		"assets/models/skybox/#.zaf",	SkyboxResource.class),
	
	TEXTURES_BASE		("texture.#",			"assets/textures/#.png",	TextureResource.class),
	TEXTURES_NORMAL		("normal.#",			"assets/textures/#.png",	NormalTextureResource.class),
	TEXTURES_SPECULAR	("specular.#",			"assets/textures/#.png",	SpecularTextureResource.class),
	SOUNDS				("sound.#",				"assets/sounds/#.wav",		SoundResource.class),
	
	SPECIAL_DRAWABLE	("special.drawable",	"",							DrawableTextureResource.class),
	
	UNKNOWN				("",					"",							GenericResource.class);
	
	private static final char SEGMENT_SPLIT = '.';
	
	public final String template;
	public final String path;
	public final int segments;
	public final Class<? extends Resource> resource;

	ResourceRule(String template, String path, Class<? extends Resource> resource)
	{
		this.template = template;
		this.path = path;
		this.resource = resource;
		this.segments = getSegmentCount();
	}
	
	private int getSegmentCount()
	{
		int count = 1;
		char[] characters = template.toCharArray();
		for (char character : characters)
		{
			if (character == SEGMENT_SPLIT)
			{
				count++;
			}
		}
		
		return count;
	}
}