package udp;

import java.io.IOException;

public class ServerMain
{

    public static void main(String[] args) throws IOException
    {
		System.out.println("Starting server");
        Server server = new Server();
        server.start();
    }
}
