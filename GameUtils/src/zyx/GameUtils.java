package zyx;

import zyx.gui.UtilsGui;

public class GameUtils
{

	public static void main(String args[])
	{
		java.awt.EventQueue.invokeLater(() ->
		{
			new UtilsGui().setVisible(true);
		});
	}

}
