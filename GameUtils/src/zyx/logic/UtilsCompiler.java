package zyx.logic;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import zyx.UtilConstants;
import zyx.gui.UtilsGui;
import zyx.logic.converter.output.ISaveable;
import zyx.logic.converter.output.MeshOutputGenerator;
import zyx.logic.converter.output.SkeletonOutputGenerator;
import zyx.logic.converter.output.mesh.ZafMeshVo;
import zyx.logic.converter.output.skeleton.SkeletonMeshVo;
import zyx.logic.converter.smd.SingleWriteFileOutputStream;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.converter.smd.control.json.JsonMeshTextureEntry;
import zyx.logic.converter.smd.control.json.JsonMeshTextures;

public class UtilsCompiler
{

	public static void compile(File... files)
	{
		try
		{
			UtilsLogger.clear();
			UtilsLogger.log("Compile start..");

			int len = files.length;
			for (int i = 0; i < len; i++)
			{
				File inputJson = files[i];
				if (inputJson.exists())
				{
					UtilsLogger.log("Handling file " + inputJson.getName());
					
					JsonMesh mesh = new JsonMesh(inputJson);

					if (mesh.isMesh())
					{
						ZafMeshVo output = new MeshOutputGenerator(mesh).getMesh();
						copyTextures(mesh.textureFiles);
						saveAs(output, mesh.meshOutput);
						
					}
					else if (mesh.isSkeleton())
					{
						SkeletonMeshVo output = new SkeletonOutputGenerator(mesh).getSkeleton();
						saveAs(output, mesh.SkeletonOutput);
						
					}
				}
				else
				{
					UtilsLogger.log("Missing file: " + inputJson.getAbsolutePath() + "\n");
				}
				
				UtilsLogger.log("Compile end");
				UtilsLogger.log("=====");
			}
		}
		catch (IOException ex)
		{
			Logger.getLogger(UtilsGui.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (RuntimeException ex)
		{
			UtilsLogger.log("[FATAL] runtime exception", ex);
		}
	}

	private static void saveAs(ISaveable saveable, File output) throws IOException
	{
		try (SingleWriteFileOutputStream outBuffer = new SingleWriteFileOutputStream())
		{
			saveable.save(outBuffer);
			byte[] data = outBuffer.getData();

			try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output)))
			{
				out.write(data);
				out.flush();
			}
		}
	}

	private static void copyTextures(JsonMeshTextures textureFiles)
	{
		for (JsonMeshTextureEntry entry : textureFiles.entries)
		{
			tryCopy(entry.diffuseFile);
			tryCopy(entry.normalFile);
			tryCopy(entry.specularFile);
		}
	}

	private static void tryCopy(File file)
	{
		if (file != null && file.exists() && file.isFile())
		{
			File outFile = new File(UtilConstants.TEXTURE_OUTPUT + file.getName());
			try (FileOutputStream output = new FileOutputStream(outFile))
			{
				Files.copy(file.toPath(), output);
				UtilsLogger.log("Copied " + file.getName() + " to output texture folder");
			}
			catch (IOException ex)
			{
				UtilsLogger.log("Could not copy texture file", ex);
			}
		}
	}
}
