package zyx.engine.resources.rules;

enum ResourceRule
{
	A("mesh.#.#", "assets/model/#/#.zaf", 3),
	B("mesh.#", "assets/model/#.zaf", 2),
	
	C("texture.#", "assets/textures/#.png", 2),
	D("sound.#", "assets/sounds/#.wav", 2);
	
	public final String template;
	public final String path;
	public final int segments;

	ResourceRule(String template, String path, int segments)
	{
		this.template = template;
		this.path = path;
		this.segments = segments;
	}
}