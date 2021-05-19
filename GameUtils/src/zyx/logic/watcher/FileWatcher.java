package zyx.logic.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import zyx.logic.UtilsLogger;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

class FileWatcher implements Runnable
{

	private boolean active;

	private final IFileUpdated updater;

	private String folderPath;
	private WatchService watcher;
	private final String fileFormat;

	private WatchKey key;

	FileWatcher(IFileUpdated updater)
	{
		this.updater = updater;
		this.folderPath = updater.getPath();
		this.fileFormat = updater.getFormat();

		try
		{
			File folder = new File(folderPath);
			
			if (folder.exists())
			{
				Path path = new File(folderPath).toPath();
				watcher = FileSystems.getDefault().newWatchService();
				path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);
			}
			else
			{
				UtilsLogger.log("Folder does not exist: " + folderPath);
			}
		}
		catch (IOException ex)
		{
			UtilsLogger.log("Could not create watcher", ex);
		}
		
		active = watcher != null;
	}

	@Override
	public void run()
	{
		while (active)
		{
			// wait for key to be signaled
			try
			{
				key = watcher.take();
			}
			catch (InterruptedException ex)
			{
				active = false;
				UtilsLogger.log("FileWatcher [take] was interrupted");
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
				Path filename = ev.context();

				if (filename.toString().endsWith(fileFormat))
				{
					File file = new File(folderPath + filename);
					if (file.exists())
					{
						UtilsLogger.log("File was updated: " + filename + " - " + kind);
						updater.fileUpdated(file);
					}
					else
					{
						UtilsLogger.log("File updated, but not found: " + filename + " - " + kind);
					}
				}
			}

			// Reset the key -- this step is critical if you want to
			// receive further watch events.  If the key is no longer valid,
			// the directory is inaccessible so exit the loop.
			active = key.reset();
		}
	}
}
