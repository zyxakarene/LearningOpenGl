package zyx.logic.converter.output;

import java.io.File;
import java.util.HashMap;
import zyx.logic.converter.json.JsonMesh;
import zyx.logic.converter.json.JsonMeshAnimation;
import zyx.logic.converter.smd.SmdFileParser;
import zyx.logic.converter.smd.parsedVo.ParsedSmdFile;

abstract class AbstractOutputGenerator
{
	protected final HashMap<JsonMeshAnimation, ParsedSmdFile> animationToFile;

	protected AbstractOutputGenerator(JsonMesh mesh)
	{
		animationToFile = new HashMap<>();
		
		for (JsonMeshAnimation animation : mesh.meshAnimations.animations)
		{
			ParsedSmdFile animationFile = tryParse(animation.file);
			animationToFile.put(animation, animationFile);
		}
	}
	
	protected final ParsedSmdFile tryParse(File file)
	{
		if (file == null || !file.exists())
		{
			return null;
		}
		
		return SmdFileParser.parseFile(file);
	}

}
