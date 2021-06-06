package zyx.logic;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import zyx.gui.UtilsGui;
import zyx.logic.converter.output.SkeletonOutputGenerator;
import zyx.logic.converter.output.mesh.ZafMeshVo;
import zyx.logic.converter.output.skeleton.SkeletonMeshVo;
import zyx.logic.converter.smd.SingleWriteFileOutputStream;
import zyx.logic.converter.smd.SmdParser;
import zyx.logic.converter.smd.control.json.JsonMesh;

public class UtilsCompiler
{

	public static JTextArea logArea;

	public static void compile(File... files)
	{
		try
		{
			logArea.setText("");

			int len = files.length;
			for (int i = 0; i < len; i++)
			{
				File inputJson = files[i];
				if (inputJson.exists())
				{
					JsonMesh mesh = new JsonMesh(inputJson);

					if (mesh.isMesh())
					{
//						ZafMeshVo output = SkeletonOutputGenerator.getMesh(mesh);
					}
					else if (mesh.isSkeleton())
					{
						SkeletonMeshVo output = new SkeletonOutputGenerator(mesh).getSkeleton();

						try (SingleWriteFileOutputStream outBuffer = new SingleWriteFileOutputStream())
						{
							output.save(outBuffer);
							byte[] data = outBuffer.getData();

							try (DataOutputStream out = new DataOutputStream
									(
										new FileOutputStream("D:\\Programmering\\LearningOpenGl\\MainGame\\assets\\models\\skeletons\\character.skeleton")
									)
								)
							{
								out.write(data);
								out.flush();
							}
						}
					}
				}
				else
				{
					logArea.append("Missing file: " + inputJson.getAbsolutePath() + "\n");
				}
				logArea.append("=====\n");
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
}
