package dev.resourceloader;

import dev.resourceloader.requests.ResourceRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import zyx.utils.GameConstants;

class FileLoader
{

	private static final byte[] EMPTY_DATA = new byte[0];

	private final ResourceRequest request;

	FileLoader(ResourceRequest request)
	{
		this.request = request;
	}

	void loadFile()
	{
		File file = new File(request.path);

		System.out.println("Loading file " + file);

		try (RandomAccessFile raf = new RandomAccessFile(file, "r"))
		{
			readFileData(raf);
			System.out.println("Load done!");
		}
		catch (IOException ex)
		{
			handleIOException();
		}
	}

	private void readFileData(final RandomAccessFile raf) throws IOException
	{
		byte[] buffer = new byte[1024];
		int len;
		int fileSize = (int) raf.length();
		ByteArrayOutputStream out = new ByteArrayOutputStream(fileSize);

		while ((len = raf.read(buffer)) != -1)
		{
			out.write(buffer, 0, len);
		}

		request.setData(out.toByteArray());
	}

	private void handleIOException()
	{
		String msg = String.format("Error while loading file %s", request.path);
		GameConstants.LOGGER.log(Level.SEVERE, msg);

		request.setData(EMPTY_DATA);
	}
}
