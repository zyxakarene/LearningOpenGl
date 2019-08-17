package zyx.server.controller.sending;

import zyx.net.io.connections.ConnectionData;

public class SendType
{
	private static final SendType INSTANCE = new SendType();
	
	SendTypeEnum type;
	ConnectionData connection;
		
	private SendType()
	{
	}

	public static SendType toAll()
	{
		INSTANCE.type = SendTypeEnum.ALL;
		INSTANCE.connection = null;
		
		return INSTANCE;
	}

	public static SendType toSingle(ConnectionData receiver)
	{
		INSTANCE.type = SendTypeEnum.SINGLE;
		INSTANCE.connection = receiver;
		
		return INSTANCE;
	}

	public static SendType toAllBut(ConnectionData exception)
	{
		INSTANCE.type = SendTypeEnum.ALL_BUT;
		INSTANCE.connection = exception;
		
		return INSTANCE;
	}
	
}
