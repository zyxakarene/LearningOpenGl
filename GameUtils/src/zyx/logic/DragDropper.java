package zyx.logic;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DragDropper extends DropTarget
{

	private static final List<File> EMPTY_FILE_LIST = new ArrayList<>();

	private final String acceptedFormat;
	private final IFilesDropped listener;

	public DragDropper(String format, IFilesDropped listener)
	{
		acceptedFormat = "." + format;
		this.listener = listener;

		listener.getDropPanel().setDropTarget(this);
	}

	private List<File> getFilesFrom(Transferable trans)
	{
		try
		{
			return (List<File>) trans.getTransferData(DataFlavor.javaFileListFlavor);
		}
		catch (UnsupportedFlavorException | IOException ex)
		{
			UtilsLogger.log("Could not import files", ex);
		}

		return EMPTY_FILE_LIST;
	}

	@Override
	public synchronized void dragEnter(DropTargetDragEvent evt)
	{
		List<File> files = getFilesFrom(evt.getTransferable());
		for (File file : files)
		{
			if (file.getName().endsWith(acceptedFormat) == false)
			{
				evt.rejectDrag();
				return;
			}
		}
	}

	@Override
	public synchronized void drop(DropTargetDropEvent evt)
	{
		evt.acceptDrop(DnDConstants.ACTION_COPY);
		List<File> droppedFiles = getFilesFrom(evt.getTransferable());

		listener.filesDropped(droppedFiles);
	}
}
