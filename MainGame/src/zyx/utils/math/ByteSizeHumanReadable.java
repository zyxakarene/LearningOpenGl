package zyx.utils.math;

public class ByteSizeHumanReadable
{	
	public static String format(double bytes)
	{
		boolean si = true;
		
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
		{
			return bytes + " B";
		}
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
