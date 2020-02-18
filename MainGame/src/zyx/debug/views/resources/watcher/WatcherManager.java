package zyx.debug.views.resources.watcher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.resources.impl.Resource;
import zyx.utils.cheats.Print;

public class WatcherManager implements IFileUpdated
{

	private final FileWatcher watcher;
	private final Thread thread;

	private Resource[] activeResources;

	private final ArrayList<File> changedFiles;
	private final HashMap<String, Boolean> pathMap;

	public WatcherManager()
	{
		changedFiles = new ArrayList<>();
		pathMap = new HashMap<>();

		watcher = new FileWatcher(this);
		thread = new Thread(watcher);
	}

	public void initialize()
	{
		thread.setDaemon(true);
		thread.start();
	}

	public void dispose()
	{
		thread.interrupt();
	}

	@Override
	public String[] getPaths()
	{
		File baseFolder = new File("assets");
		ArrayList<File> allFolders = new ArrayList<>();

		findFolders(baseFolder, allFolders);

		String[] out = new String[allFolders.size()];
		for (int i = 0; i < allFolders.size(); i++)
		{
			File folder = allFolders.get(i);
			out[i] = folder.getAbsolutePath();
		}

		return out;
	}

	@Override
	public void filesUpdated(File[] files)
	{
		synchronized (changedFiles)
		{
			for (File file : files)
			{
				String path = file.getAbsolutePath();
				if (pathMap.containsKey(path) == false)
				{
					changedFiles.add(file);
					pathMap.put(path, Boolean.TRUE);
				}
			}
		}
	}

	public void setActiveResources(ArrayList<Resource> out)
	{
		synchronized (changedFiles)
		{
			activeResources = new Resource[out.size()];
			out.toArray(activeResources);
		}
	}

	private void findFolders(File folder, ArrayList<File> out)
	{
		File[] list = folder.listFiles(new FolderFilter());
		for (File file : list)
		{
			out.add(file);

			findFolders(file, out);
		}
	}

	public void checkChanged()
	{
		synchronized (changedFiles)
		{
			for (File file : changedFiles)
			{
				String filePath = file.getAbsolutePath();
				for (Resource res : activeResources)
				{
					File resourceFile = new File(res.path);
					String resourcePath = resourceFile.getAbsolutePath();
					
					if (resourcePath.equals(filePath))
					{
						Print.out("Resource", res.path, "was refreshed");
						res.forceRefresh();
					}
				}
			}
			
			changedFiles.clear();
			pathMap.clear();
		}
	}

}
