package zyx.game.controls.input.scheme;

import zyx.game.controls.input.KeyboardData;

public class InputScheme
{
	public final InputSchemeMap map;
	
	public float forward;
	public float strafe;
	public float moveModifier;
	
	public float mouseDx;
	public float mouseDy;
	
	public boolean toggleClipboard;
	public boolean use;

	public InputScheme(InputSchemeMap map)
	{
		this.map = map;
	}

	public void reset()
	{
		forward = 0f;
		strafe = 0f;
		moveModifier = 1f;
		toggleClipboard = false;
		use = false;
	}
	
	public void tryMap(KeyboardData data)
	{
		if (data.btnDown[map.forward])
		{
			forward = 1;
		}
		else if (data.btnDown[map.backward])
		{
			forward = -1;
		}
		
		if (data.btnDown[map.left])
		{
			strafe = -1;
		}
		else if (data.btnDown[map.right])
		{
			strafe = 1;
		}
		
		if (data.btnClicked[map.toggleClipboard])
		{
			toggleClipboard = true;
		}
		
		if (data.btnClicked[map.use])
		{
			use = true;
		}
		
		if (data.btnDown[map.sprint])
		{
			moveModifier = 5f;
		}
		else if (data.btnDown[map.walk])
		{
			moveModifier = 0.2f;
		}
	}
}
