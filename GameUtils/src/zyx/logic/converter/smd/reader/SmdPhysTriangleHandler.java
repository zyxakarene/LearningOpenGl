package zyx.logic.converter.smd.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.smd.vo.PhysBox;
import zyx.logic.converter.smd.vo.PhysTriangle;
import zyx.logic.converter.smd.vo.PhysVertex;
import zyx.logic.converter.smd.vo.validator.PhysBoxValidator;

public class SmdPhysTriangleHandler implements ISmdHandler
{

	private HashMap<String, PhysBox> boxes;
	private PhysBox currentBox;
	private PhysTriangle currentTriangle;

	public SmdPhysTriangleHandler()
	{
		boxes = new HashMap<>();
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		if (split.length == 1)
		{
			String key = split[0];
			if (boxes.containsKey(key) == false)
			{
				currentBox = new PhysBox();
				boxes.put(key, currentBox);
			}

			currentTriangle = new PhysTriangle();
			currentBox.addTriangle(currentTriangle);

			return;
		}

		float x = Float.parseFloat(split[1]);
		float y = Float.parseFloat(split[2]);
		float z = Float.parseFloat(split[3]);

		float normX = Float.parseFloat(split[4]);
		float normY = Float.parseFloat(split[5]);
		float normZ = Float.parseFloat(split[6]);
		
		PhysVertex vertex = new PhysVertex(x, y, z, normX, normY, normZ);
		currentTriangle.addVertex(vertex);
	}

	@Override
	public Object getResult()
	{
		ArrayList<PhysBox> boxList = new ArrayList<>();

		for (Map.Entry<String, PhysBox> entry : boxes.entrySet())
		{
			PhysBox value = entry.getValue();

			boolean valid = new PhysBoxValidator(value).validate();
			if (valid)
			{
				boxList.add(value);
			}
			else
			{
				UtilsLogger.log("[ERROR] Physbox is not valid! Ignoring it");
			}
		}

		return boxList;
	}

	@Override
	public void setData(Object data)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
