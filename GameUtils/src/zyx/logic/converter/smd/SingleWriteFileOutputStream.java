package zyx.logic.converter.smd;

import java.io.DataOutputStream;

public class SingleWriteFileOutputStream extends DataOutputStream
{

	public SingleWriteFileOutputStream()
	{
		super(new BufferedOutputStream());
	}
	
	public byte[] getData()
	{
		BufferedOutputStream output = (BufferedOutputStream) out;
		return output.getData();
	}

}
