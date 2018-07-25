package zyx.engine.resources.rules;

import zyx.engine.resources.impl.*;

public enum ResourceRule
{
	FONT_BASE			("font.#",				"assets/fonts/#.zff", FontResource.class),
	FONT_TEXTURE		("font.texture.#",		"assets/fonts/#.png", TextureResource.class),
	
	PARTICLE_BASE		("particles.#",			"assets/effects/#.zpf", ParticleResource.class),
	
	MESHES_0_FOLDERS	("mesh.#",				"assets/models/#.zaf", MeshResource.class),
	MESHES_1_FOLDERS	("mesh.#.#",			"assets/models/#/#.zaf", MeshResource.class),
	
	TEXTURES_BASE		("texture.#",			"assets/textures/#.png", TextureResource.class),
	SOUNDS				("sound.#",				"assets/sounds/#.wav", SoundResource.class);
	
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