package zyx.engine.resources.rules;

import zyx.engine.resources.impl.*;

public enum ResourceRule
{
	PARTICLE_BASE		("particles.#",			"assets/effects/#.zpf", 2, ParticleResource.class),
	
	MESHES_0_FOLDERS	("mesh.#",				"assets/models/#.zaf", 2, MeshResource.class),
	MESHES_1_FOLDERS	("mesh.#.#",			"assets/models/#/#.zaf", 3, MeshResource.class),
	
	TEXTURES_BASE		("texture.#",			"assets/textures/#.png", 2, TextureResource.class),
	SOUNDS				("sound.#",				"assets/sounds/#.wav", 2, SoundResource.class);
	
	public final String template;
	public final String path;
	public final int segments;
	public final Class<? extends Resource> resource;

	ResourceRule(String template, String path, int segments, Class<? extends Resource> resource)
	{
		this.template = template;
		this.path = path;
		this.segments = segments;
		this.resource = resource;
	}
}