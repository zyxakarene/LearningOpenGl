package zyx.server;

public class ServerMain
{

    public static void main(String[] args)
    {
		java.awt.EventQueue.invokeLater(() ->
		{
			new DebugServerForm().setVisible(true);
		});
		
		System.out.println("Starting server");
        Server server = new Server();
        server.start();
    }
}
