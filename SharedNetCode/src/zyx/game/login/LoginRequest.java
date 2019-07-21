package zyx.game.login;

import zyx.net.data.WriteableDataObject;
import zyx.net.io.requests.BaseNetworkRequest;
import zyx.net.io.controllers.NetworkCommands;
import static zyx.game.login.LoginConstants.*;
import zyx.net.data.WriteableDataArray;

public class LoginRequest extends BaseNetworkRequest
{
	
	public LoginRequest()
	{
		super(NetworkCommands.LOGIN);
	}
	
	@Override
	protected void getDataObject(WriteableDataObject data, Object... params)
	{
		String myName = (String) params[0];
		
		data.addString(NAME, myName);
		
		data.addBoolean("bool", true);
		data.addByte("by", (byte)0);
		data.addCharacter("cha", 'k');
		data.addFloat("f", 0.0182f);
		data.addInteger("i", 1234);
		data.addLong("lo", 273648l);
		data.addShort("shor", (short)283);
		data.addString("str", "I am a string");
		
		byte[] byteArray = new byte[20];
		data.addByteArray("ba", byteArray);
		
		WriteableDataArray<Integer> dataArray = new WriteableDataArray(Integer.class);
		dataArray.add(1234);
		dataArray.add(5484);
		dataArray.add(113326);
		data.addArray("arr", dataArray);
		
		WriteableDataObject dataObj = new WriteableDataObject();
		dataObj.addInteger("a", 1);
		dataObj.addFloat("b", 0.1f);
		dataObj.addBoolean("c", false);
		data.addObject("obj", dataObj);
	}

}
