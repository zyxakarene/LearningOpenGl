package zyx.logic;

import javax.swing.JTextArea;

public class UtilsLogger
{
	private static JTextArea output;

	public static void setOutput(JTextArea output)
	{
		UtilsLogger.output = output;
	}
	
	public static void log(Object obj)
	{
		log(obj.toString());
	}
	
	public static void log(String line)
	{
		log(line, null);
	}
	
	public static void log(String line, Exception ex)
	{
		output.append(line);
		
		if (ex != null)
		{
			output.append(" - " + ex.getMessage());
			ex.printStackTrace();
		}
		
		output.append("\n");
	}
}
