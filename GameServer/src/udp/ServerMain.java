package udp;

import java.io.IOException;

public class ServerMain
{

    public static void main(String[] args) throws IOException
    {
//		System.out.println(colorize("I must say, I still do love the excessive use of formating"));
		
		System.out.println("Starting server");
        Server server = new Server();
        server.start();
    }

	private static String colorize(String text)
	{
		StringBuilder builder = new StringBuilder();
		
		char[] chars = text.toCharArray();
		for (char letter : chars)
		{
			int colorVal = (int) (0xFFFFFF * Math.random());
			String color = Integer.toHexString(colorVal);
			
			boolean useBold = Math.random() > 0.8;
			boolean useItalic = Math.random() > 0.8;
			boolean useUnderline = Math.random() > 0.8;
			int size = (int) (Math.random() * 5) + 1;
			
			//[COLOR="#0000FF"]letter[/COLOR]
			
			if (letter == ' ')
			{
				builder.append(" ");
			}
			else
			{
				if (useBold)
				{
					builder.append("[B]");
				}
				if (useItalic)
				{
					builder.append("[I]");
				}
				if (useUnderline)
				{
					builder.append("[U]");
				}
				
				builder.append("[SIZE=");
				builder.append(size);
				builder.append("]");
				builder.append("[COLOR=\"#");
				builder.append(color);
				builder.append("\"]");
				builder.append(letter);
				builder.append("[/COLOR]");
				builder.append("[/SIZE]");
				
				if (useBold)
				{
					builder.append("[/B]");
				}
				if (useItalic)
				{
					builder.append("[/I]");
				}
				if (useUnderline)
				{
					builder.append("[/U]");
				}
			}
		}
		return builder.toString();
	}

}
