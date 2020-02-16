package zyx.utils.cheats;

import java.io.PrintStream;
import zyx.utils.DeltaTime;

public class Print
{

	private static final String SPACE = " ";
	
	public static void out(Object... args)
	{
		print(System.out, args);
	}
	
	public static void err(Object... args)
	{
		print(System.err, args);
	}
	
	public static synchronized void print(PrintStream stream, Object... args)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(DeltaTime.getTimestamp());
		builder.append(" - ");
		
		int len = args.length;
		for (int i = 0; i < len; i++)
		{
			if (i != 0)
			{
				builder.append(SPACE);
			}
			
			builder.append(args[i]);
		}
		
//		stream.println(builder.toString());
	}
}
