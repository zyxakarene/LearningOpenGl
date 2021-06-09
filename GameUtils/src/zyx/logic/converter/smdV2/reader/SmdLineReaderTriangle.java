package zyx.logic.converter.smdV2.reader;

import java.util.HashMap;
import java.util.Scanner;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdSurface;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdTriangle;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdVertex;

class SmdLineReaderTriangle extends AbstractSmdLineReader
{

	private static final int POS_X_INDEX = 1;
	private static final int POS_Y_INDEX = 2;
	private static final int POS_Z_INDEX = 3;
	private static final int NORM_X_INDEX = 4;
	private static final int NORM_Y_INDEX = 5;
	private static final int NORM_Z_INDEX = 6;
	private static final int U_INDEX = 7;
	private static final int V_INDEX = 8;
	private static final int BONE_COUNT_INDEX = 9;
	private static final int BONE_WEIGHT_INDEX = 10;
	private static final int BONE_ID_INDEX = 11;
	
	private final HashMap<String, ParsedSmdSurface> surfaces;

	SmdLineReaderTriangle(HashMap<String, ParsedSmdSurface> surfaces)
	{
		this.surfaces = surfaces;
	}

	@Override
	void readFrom(Scanner scan)
	{
		while (scan.hasNextLine())
		{
			String line = scan.nextLine();
			if (line.equals(TOKEN_END))
			{
				return;
			}

			String surfaceName = line;
			String v1 = scan.nextLine();
			String v2 = scan.nextLine();
			String v3 = scan.nextLine();

			ParsedSmdSurface surface = getSurface(surfaceName);
			ParsedSmdTriangle triangle = createTriangle(v1, v2, v3);
			surface.triangles.add(triangle);
		}
	}

	private ParsedSmdSurface getSurface(String key)
	{
		ParsedSmdSurface surface = surfaces.get(key);
		if (surface == null)
		{
			surface = new ParsedSmdSurface(key);
			surfaces.put(key, surface);
		}

		return surface;
	}

	private ParsedSmdTriangle createTriangle(String v1, String v2, String v3)
	{
		ParsedSmdTriangle triangle = new ParsedSmdTriangle();
		toVertex(triangle.v1, v1);
		toVertex(triangle.v2, v2);
		toVertex(triangle.v3, v3);

		return triangle;
	}

	private ParsedSmdVertex toVertex(ParsedSmdVertex vertex, String vertexLine)
	{
		String[] split = vertexLine.split(SPLIT_TEXT);

		vertex.pos.x = toFloat(split, POS_X_INDEX);
		vertex.pos.y = toFloat(split, POS_Y_INDEX);
		vertex.pos.z = toFloat(split, POS_Z_INDEX);
		vertex.norm.x = toFloat(split, NORM_X_INDEX);
		vertex.norm.y = toFloat(split, NORM_Y_INDEX);
		vertex.norm.z = toFloat(split, NORM_Z_INDEX);
		vertex.uv.x = toFloat(split, U_INDEX);
		vertex.uv.y = toFloat(split, V_INDEX);


		if (split.length > BONE_COUNT_INDEX)
		{
			vertex.boneCount = toByte(split, BONE_COUNT_INDEX);
			
			for (int i = 0; i < vertex.boneCount; i++)
			{
				byte boneId = toByte(split, BONE_WEIGHT_INDEX + (2 * i));
				float weight = toFloat(split, BONE_ID_INDEX + (2 * i));

				vertex.boneIndexes[i] = boneId;
				vertex.boneWeights[i] = weight;
			}
		}
		else
		{
			vertex.boneCount = 1;
			vertex.boneIndexes[0] = 0;
			vertex.boneWeights[0] = 1f;
		}

		return vertex;
	}
}
