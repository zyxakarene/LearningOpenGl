package zyx.debug.network.communication;

import java.io.DataOutputStream;

public class DebugSender extends AbstractDebugIO
{
	private DataOutputStream out;

	public DebugSender(DataOutputStream out)
	{
		this.out = out;
	}

	@Override
	protected void onRun()
	{
	}

}
