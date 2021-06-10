package zyx.logic.converter.output;

import java.io.IOException;
import zyx.logic.converter.output.mesh.ZafMeshVo;
import zyx.logic.converter.output.mesh.builders.BoundingBoxBuilder;
import zyx.logic.converter.output.mesh.builders.ColliderBuilder;
import zyx.logic.converter.output.mesh.builders.MaterialBuilder;
import zyx.logic.converter.output.mesh.builders.VertexDataBuilder;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;

public class MeshOutputGenerator extends AbstractOutputGenerator
{
	private final ParsedSmdFile boundingFile;
	private final ParsedSmdFile refFile;
	private final ParsedSmdFile physFile;
	private final String meshSkeleton;
	private final JsonMesh json;

	public MeshOutputGenerator(JsonMesh mesh)
	{
		super(mesh);
		
		json = mesh;
		meshSkeleton = mesh.meshSkeleton;
		boundingFile = tryParse(mesh.meshFiles.boundingFile);
		refFile = tryParse(mesh.meshFiles.meshFile);
		physFile = tryParse(mesh.meshFiles.physFile);
	}

	public ZafMeshVo getMesh() throws IOException
	{
		ZafMeshVo vo = new ZafMeshVo();
		vo.skeleton = getSkeletonResourceName();
		
		new ColliderBuilder(physFile).addTo(vo);
		new VertexDataBuilder(refFile).addTo(vo);
		new BoundingBoxBuilder(boundingFile, refFile).addTo(vo);
		new MaterialBuilder(json).addTo(vo);

		return vo;
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
