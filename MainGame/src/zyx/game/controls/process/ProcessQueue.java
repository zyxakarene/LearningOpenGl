package zyx.game.controls.process;

import java.util.ArrayList;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class ProcessQueue implements IUpdateable, IDisposeable
{
	private ArrayList<BaseProcess> processes;
	
	private BaseProcess currentProcess;
	private int currentIndex;
	private ICallback<ProcessQueue> onCompleted;
	private ICallback<BaseProcess> onProcessDone;
	
	public ProcessQueue()
	{
		processes = new ArrayList<>();
		currentIndex = -1;
		
		onProcessDone = (BaseProcess process) ->
		{
			loadNext();
		};
	}
	
	public void addProcess(BaseProcess process)
	{
		processes.add(process);
	}
	
	public void start(ICallback<ProcessQueue> onCompleted)
	{
		this.onCompleted = onCompleted;

		loadNext();
	}
	
	public void start()
	{
		start(null);
	}

	private void loadNext()
	{
		currentIndex++;
		
		if (currentIndex >= processes.size())
		{
			if(onCompleted != null)
			{
				onCompleted.onCallback(this);
			}
		}
		else
		{
			currentProcess = processes.get(currentIndex);
			currentProcess.start(onProcessDone);
		}
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (currentProcess != null)
		{
			currentProcess.update(timestamp, elapsedTime);
		}
	}

	@Override
	public void dispose()
	{
		if(processes != null)
		{
			int len = processes.size();
			for (int i = 0; i < len; i++)
			{
				currentProcess = processes.get(i);
				currentProcess.dispose();
			}
			
			processes.clear();
			processes = null;
		}
		
		currentProcess = null;
	}
}