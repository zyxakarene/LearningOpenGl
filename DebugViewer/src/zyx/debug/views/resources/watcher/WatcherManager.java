package zyx.debug.views.resources.watcher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import zyx.debug.network.vo.resources.ResourceInfo;
import zyx.debug.network.vo.resources.ResourceInformation;

public class WatcherManager implements IFileUpdated
{

	private FileWatcher watcher;
	private Thread thread;

	private ResourceInfo[] activeResources;

	private ArrayList<File> changedFiles;
	private HashMap<String, Boolean> pathMap;

	public WatcherManager()
	{
		changedFiles = new ArrayList<>();
		pathMap = new HashMap<>();
	}

	public void initialize()
	{
		watcher = new FileWatcher(this);
		thread = new Thread(watcher, "FileWatcher");
		
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
		allFolders.add(baseFolder);

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

	public void setActiveResources(ArrayList<ResourceInfo> out)
	{
		synchronized (changedFiles)
		{
			activeResources = new ResourceInfo[out.size()];
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
				for (ResourceInfo res : activeResources)
				{
					File resourceFile = new File(res.path);
					String resourcePath = resourceFile.getAbsolutePath();
					
					if (resourcePath.equals(filePath))
					{
						ResourceInformation.resourceRefreshed(res.path);
					}
				}
			}
			
			changedFiles.clear();
			pathMap.clear();
		}
	}

}
