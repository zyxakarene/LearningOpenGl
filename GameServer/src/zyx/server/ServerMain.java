package zyx.server;

public class ServerMain
{

    public static void main(String[] args)
    {
		System.out.println("Starting server");
        Server server = new Server();
        server.start();
    }
}
