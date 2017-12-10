package zyx.dev;

import java.io.File;
import java.util.ArrayList;
import zyx.UtilConstants;

public class DevMain
{

	public static void main(String[] args) throws Exception
	{
		File ref = new File(UtilConstants.MESH_FOLDER + "knight.smd");
		File walk = new File(UtilConstants.MESH_FOLDER + "knight_walk.smd");
		File attack = new File(UtilConstants.MESH_FOLDER + "knight_attack.smd");

		ArrayList<File> animations = new ArrayList<>();
		animations.add(walk);
		animations.add(attack);

		SmdParser parser = new SmdParser(ref, animations);
		parser.parseFiles();
	}

}
