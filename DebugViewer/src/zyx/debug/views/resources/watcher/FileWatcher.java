package zyx.debug.views.resources.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.util.ArrayList;

class FileWatcher implements Runnable
{

	private boolean active = true;

	private final IFileUpdated updater;

	private WatchService watcher;

	private WatchKey key;

	FileWatcher(IFileUpdated updater)
	{
		this.updater = updater;
		
		String[] folderPaths = updater.getPaths();

		try
		{
			watcher = FileSystems.getDefault().newWatchService();
			
			for (String folderPath : folderPaths)
			{
				Path path = new File(folderPath).toPath();
				path.register(watcher, ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_CREATE);
			}
		}
		catch (IOException ex)
		{
			System.out.println("Could not create watcher " + ex);
		}
	}

	@Override
	public void run()
	{
		while (active)
		{
			ArrayList<File> updatedFiles = new ArrayList<>();
			
			// wait for key to be signaled
			try
			{
				key = watcher.take();
			}
			catch (InterruptedException ex)
			{
				active = false;
				System.out.println("FileWatcher [take] was interrupted");
				return;
			}

			for (WatchEvent<?> event : key.pollEvents())
			{
				WatchEvent.Kind<?> kind = event.kind();

				// This key is registered only
				// for ENTRY_CREATE events,
				// but an OVERFLOW event can
				// occur regardless if events
				// are lost or discarded.
				if (kind == OVERFLOW)
				{
					continue;
				}

				// The filename is the
				// context of the event.
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path filePath = ev.context();
				Path dir = (Path)key.watchable();
				Path fullPath = dir.resolve(filePath);
				
				File file = fullPath.toFile();
				if (file.exists())
				{
					updatedFiles.add(file);
				}
				else
				{
					System.out.println("File updated, but not found: " + filePath + " - " + kind);
				}
			}

			// Reset the key -- this step is critical if you want to
			// receive further watch events.  If the key is no longer valid,
			// the directory is inaccessible so exit the loop.
			active = key.reset();
			
			File[] updated = new File[updatedFiles.size()];
			updatedFiles.toArray(updated);
			
			updater.filesUpdated(updated);
		}
	}
}
