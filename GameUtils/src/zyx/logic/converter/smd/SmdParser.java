package zyx.logic.converter.smd;

import java.io.*;
import zyx.logic.converter.smd.vo.SmdObject;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.converter.smd.control.json.JsonMeshAnimation;
import zyx.logic.converter.smd.reader.SmdImporter;

public class SmdParser
{

	private boolean isSkeleton;
	
	private File ref;
	private File phys;
	private File bounding;
	private JsonMeshAnimation[] animations;

	private File outputModel;
	private final JsonMesh jsonMesh;

	public SmdParser(JsonMesh mesh)
	{
		this.jsonMesh = mesh;
		
		isSkeleton = mesh.isSkeleton();
		
		ref = isSkeleton ? mesh.skeletonMesh : mesh.meshFiles.meshFile;
		phys = mesh.meshFiles.physFile;
		bounding = mesh.meshFiles.boundingFile;
		outputModel = isSkeleton ? mesh.SkeletonOutput : mesh.meshOutput;
		
		animations = new JsonMeshAnimation[mesh.meshAnimations.animations.size()];
		mesh.meshAnimations.animations.toArray(animations);
	}

	public void parseFiles() throws IOException
	{
		SmdImporter importer = new SmdImporter();
		importer.importModel(ref);
		importer.importPhys(phys);
		importer.importBounding(bounding);
		importer.importAnimations(animations);
		
		SmdObject smd = importer.getSmd();
		smd.setSkeleton(jsonMesh.isSkeleton());
		smd.setSkeletonPath(jsonMesh.getSkeletonResourceName());
		smd.setDiffuseTexturePath(jsonMesh.textureFiles.getDiffuseTextureName());
		smd.setNormalTexturePath(jsonMesh.textureFiles.getNormalTextureName());
		smd.setSpecularTexturePath(jsonMesh.textureFiles.getSpecularTextureName());
		
		if (outputModel.exists() == false)
		{
			outputModel.getParentFile().mkdirs();
		}

		try (SingleWriteFileOutputStream outBuffer = new SingleWriteFileOutputStream())
		{
			smd.save(outBuffer);
			byte[] data = outBuffer.getData();
			
			try (DataOutputStream out = new DataOutputStream(new FileOutputStream(outputModel)))
			{
				out.write(data);
				out.flush();
			}
		}
	}

}
