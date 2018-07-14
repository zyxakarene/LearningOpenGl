package zyx.engine.resources.rules;

import zyx.engine.resources.impl.MeshResource;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.SoundResource;
import zyx.engine.resources.impl.TextureResource;

public enum ResourceRule
{
	MESHES_0_FOLDERS("mesh.#", "assets/models/#.zaf", 2, MeshResource.class),
	MESHES_1_FOLDERS("mesh.#.#", "assets/model/#/#.zaf", 3, MeshResource.class),
	
	TEXTURES_BASE("texture.#", "assets/textures/#.png", 2, TextureResource.class),
	TEXTURES_MODELS("texture.model.#", "assets/textures/models/#.png", 3, TextureResource.class),
	SOUNDS("sound.#", "assets/sounds/#.wav", 2, SoundResource.class);
	
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