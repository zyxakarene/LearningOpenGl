package zyx.logic.converter.output;

import java.io.IOException;
import zyx.logic.converter.output.skeleton.*;
import zyx.logic.converter.output.skeleton.builders.AnimationBuilder;
import zyx.logic.converter.output.skeleton.builders.BoneBuilder;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;

public class SkeletonOutputGenerator extends AbstractOutputGenerator
{

	protected ParsedSmdFile skeletonRef;

	public SkeletonOutputGenerator(JsonMesh mesh)
	{
		super(mesh);

		skeletonRef = tryParse(mesh.skeletonMesh);
	}

	public SkeletonMeshVo getSkeleton() throws IOException
	{
		SkeletonMeshVo vo = new SkeletonMeshVo();

		new BoneBuilder(skeletonRef).addTo(vo);
		new AnimationBuilder(animationToFile).addTo(vo);

		return vo;
	}
}
