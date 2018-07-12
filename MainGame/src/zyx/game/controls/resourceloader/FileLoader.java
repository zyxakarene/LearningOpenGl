package zyx.game.controls.resourceloader;

import zyx.game.controls.resourceloader.requests.ResourceRequest;
import java.io.*;
import java.util.logging.Level;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;

class FileLoader
{

	private static final int BUFFER_SIZE = 8192;
	private static final byte[] EMPTY_DATA = new byte[0];

	private final ResourceRequest request;

	FileLoader(ResourceRequest request)
	{
		this.request = request;
	}

	void loadFile()
	{
		File file = new File(request.path);

		Print.out("Loading file", file);

		try (RandomAccessFile raf = new RandomAccessFile(file, "r"))
		{
			readFileData(raf);
			Print.out("Load done!");
		}
		catch (IOException ex)
		{
			handleIOException(ex);
		}
	}

	private void readFileData(RandomAccessFile raf) throws IOException
	{
		byte[] buffer = new byte[BUFFER_SIZE];
		int len;
		int fileSize = (int) raf.length();
		
		if (raf.length() > fileSize)
		{
			throw new IOException("File size too large");
		}
		
		byte[] data = new byte[fileSize];
		int count = 0;
		while ((len = raf.read(buffer)) != -1)
		{
			System.arraycopy(buffer, 0, data, count, len);
			count += len;
		}

		request.setData(data);
	}

	private void handleIOException(IOException ex)
	{
		String msg = String.format("Error while loading file %s. Error: \"%s\"", request.path, ex.getMessage());
		GameConstants.LOGGER.log(Level.SEVERE, msg);

		request.setData(EMPTY_DATA);
		request.setFailed();
	}
}
