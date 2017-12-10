package zyx.dev.reader;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.dev.vo.Vertex;

public class SmdTriangleHandler implements ISmdHandler
{

	private final Vector3f pos;
	private final Vector3f norm;
	private final Vector2f uv;

	private final ArrayList<Vertex> verticies;
	private final ArrayList<Integer> elements;

	private int indexCounter;

	public SmdTriangleHandler()
	{
		verticies = new ArrayList<>();
		elements = new ArrayList<>();
		pos = new Vector3f();
		norm = new Vector3f();
		uv = new Vector2f();
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		if (split.length < 9)
		{
			return;
		}
		
		float x = Float.parseFloat(split[1]);
		float y = Float.parseFloat(split[2]);
		float z = Float.parseFloat(split[3]);
		pos.set(x, y, z);

		float normX = Float.parseFloat(split[4]);
		float normY = Float.parseFloat(split[5]);
		float normZ = Float.parseFloat(split[6]);
		norm.set(normX, normY, normZ);

		float u = Float.parseFloat(split[7]);
		float v = Float.parseFloat(split[8]);
		uv.set(u, v);

		byte[] indexes = new byte[]
		{
			0, 0
		};
		float[] weights = new float[]
		{
			0f, 0f
		};

		if (split.length > 9)
		{
			int boneCount = Integer.parseInt(split[9]);
			for (int i = 0; i < boneCount; i++)
			{
				byte boneWeightId = Byte.parseByte(split[10 + (2 * i)]);
				float weight = Float.parseFloat(split[11 + (2 * i)]);

				indexes[i] = boneWeightId;
				weights[i] = weight;
			}
		}
		else
		{
			indexes[0] = 1;
			weights[0] = 1;
		}

		int existingIndex = exists(indexes, weights);
		if (existingIndex >= 0)
		{
			elements.add(existingIndex);
		}
		else
		{
			Vertex vertex = new Vertex(pos, norm, uv, indexCounter);
			vertex.addWeight(indexes[0], weights[0]);
			vertex.addWeight(indexes[1], weights[1]);

			elements.add(indexCounter);
			verticies.add(vertex);
			saveToMap(indexes, weights);

			indexCounter++;
		}
	}

	private HashMap<String, Integer> vertexMap = new HashMap<>();

	private int exists(byte[] indexes, float[] weights)
	{
		String key = getString(indexes, weights);
		if (vertexMap.containsKey(key))
		{
			return vertexMap.get(key);
		}

		return -1;
	}

	private void saveToMap(byte[] indexes, float[] weights)
	{
		String key = getString(indexes, weights);
		vertexMap.put(key, indexCounter);
	}

	private String getString(byte[] indexes, float[] weights)
	{
		return String.format("%s%s%s%s%s%s%s%s%s%s%s%s", pos.x, pos.y, pos.z, norm.x, norm.y, norm.z, uv.x, uv.y, indexes[0], indexes[1], weights[0], weights[1]);
	}

	@Override
	public Object getResult()
	{
		return new Response(verticies, elements);
	}

	@Override
	public void setData(Object data)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public class Response
	{

		public final ArrayList<Vertex> verticies;
		public final ArrayList<Integer> elements;

		public Response(ArrayList<Vertex> verticies, ArrayList<Integer> elements)
		{
			this.verticies = verticies;
			this.elements = elements;
		}

	}
}
