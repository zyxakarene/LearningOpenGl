package zyx.utils;

public class PrintBuilder
{

	private static final String SPACE = " ";
	private static final String NEW_LINE = "\n";

	private StringBuilder builder;

	public PrintBuilder()
	{
		builder = new StringBuilder();
	}

	public void append(Object... args)
	{
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
		
		builder.append(NEW_LINE);
	}

	@Override
	public String toString()
	{
		return builder.toString();
	}

}
