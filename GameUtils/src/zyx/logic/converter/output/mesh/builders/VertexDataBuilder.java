package zyx.logic.converter.output.mesh.builders;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.logic.converter.output.mesh.ZafMeshVo;
import zyx.logic.converter.output.mesh.ZafSubMesh;
import zyx.logic.converter.output.mesh.ZafVertex;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdSurface;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdTriangle;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdVertex;

public class VertexDataBuilder
{

	private ParsedSmdFile refFile;

	public VertexDataBuilder(ParsedSmdFile refFile)
	{
		this.refFile = refFile;
	}

	public void addTo(ZafMeshVo vo)
	{
		if (refFile == null)
		{
			return;
		}

		for (ParsedSmdSurface surface : refFile.surfaces.values())
		{
			HashMap<String, Short> vertexIdToIndex = new HashMap<>();

			ZafSubMesh subMesh = new ZafSubMesh();
			
			ArrayList<ZafVertex> vertecies = subMesh.vertecies;
			ArrayList<Short> elements = subMesh.elementData;

			subMesh.boneCount = getMaxBoneCount(surface.triangles);
			
			for (ParsedSmdTriangle triangle : surface.triangles)
			{
				addVertex(vertexIdToIndex, triangle.v1, vertecies, elements, subMesh.boneCount);
				addVertex(vertexIdToIndex, triangle.v2, vertecies, elements, subMesh.boneCount);
				addVertex(vertexIdToIndex, triangle.v3, vertecies, elements, subMesh.boneCount);
			}

			vo.subMeshes.add(subMesh);
		}
	}

	private void addVertex(HashMap<String, Short> vertexIdToIndex, ParsedSmdVertex input, ArrayList<ZafVertex> vertecies, ArrayList<Short> elements, byte boneCount)
	{
		String id = input.toString();

		if (vertexIdToIndex.containsKey(id))
		{
			short index = vertexIdToIndex.get(id);
			elements.add(index);
		}
		else
		{
			
			ZafVertex vertex = new ZafVertex();
			vertex.position.set(input.pos);
			vertex.normal.set(input.norm);
			vertex.uv.set(input.uv);
			vertex.boneCount = boneCount;

			for (int i = 0; i < ParsedSmdVertex.MAX_BONES; i++)
			{
				vertex.indexes[i] = input.boneIndexes[i];
				vertex.weights[i] = input.boneWeights[i];
			}

			short verteciesLength = (short) vertecies.size();
			
			vertexIdToIndex.put(id, verteciesLength);
			elements.add(verteciesLength);
			vertecies.add(vertex);
		}
	}

	private byte getMaxBoneCount(ArrayList<ParsedSmdTriangle> triangles)
	{
		byte count = 0;

		for (ParsedSmdTriangle triangle : triangles)
		{
			if (triangle.v1.boneCount > count)
			{
				count = triangle.v1.boneCount;
			}

			if (triangle.v2.boneCount > count)
			{
				count = triangle.v2.boneCount;
			}

			if (triangle.v3.boneCount > count)
			{
				count = triangle.v3.boneCount;
			}
		}

		return count;
	}
}
