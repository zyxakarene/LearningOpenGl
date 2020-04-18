package zyx;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import zyx.debug.DebugController;
import zyx.engine.GameStarter;
import zyx.game.scene.SceneType;
import zyx.net.core.packages.PacketCombiner;
import zyx.net.core.packages.PacketSplitter;
import zyx.net.io.connections.ConnectionResponseData;

public class SceneMain
{

	private static final boolean SHOW_DEBUG_RESOURCES = true;

	public static void main(String[] args) throws UnknownHostException
	{
//		PacketCombiner combiner = new PacketCombiner();
//		
//		byte[] inputData = new byte[3];
//		for (int i = 0; i < inputData.length; i++)
//		{
//			inputData[i] = (byte) i;
//		}
//		
//		DatagramPacket[] splits = PacketSplitter.splitDataToPackets(inputData, InetAddress.getLocalHost(), 8888);
//		
//		System.out.println("Input data:");
//		System.out.println(Arrays.toString(inputData));
//		
//		
//		System.out.println("output data:");
//		for (DatagramPacket split : splits)
//		{
//			ConnectionResponseData response = combiner.tryCombine(split);
//			if (response != null)
//			{
//				System.out.print(Arrays.toString(response.byteData));
//			}
//		}
		
		if (SHOW_DEBUG_RESOURCES)
		{
			GameStarter starter = new GameStarter(SceneType.MENU);
			Thread gameThread = new Thread(starter);

			gameThread.start();

			DebugController.show();
		}
		else
		{
			java.awt.EventQueue.invokeLater(new GameStarter(SceneType.MENU));
		}
	}

}
