package zyx.logic.watcher;

public class WatcherManager
{

	private final IFileUpdated[] updaters;
	private final FileWatcher[] watchers;
	private final Thread[] threads;

	public WatcherManager(IFileUpdated... updaters)
	{
		this.updaters = updaters;
		watchers = new FileWatcher[updaters.length];
		threads = new Thread[updaters.length];
	}

	public void initialize()
	{
		for (int i = 0; i < updaters.length; i++)
		{
			IFileUpdated updater = updaters[i];
			FileWatcher watcher = new FileWatcher(updater);
			Thread thread = new Thread(watcher);
			thread.setDaemon(true);
			
			watchers[i] = watcher;
			threads[i] = thread;
			
			thread.start();
		}
	}
	
	public void close()
	{
		for (Thread thread : threads)
		{
			thread.interrupt();
		}
	}
	
}
