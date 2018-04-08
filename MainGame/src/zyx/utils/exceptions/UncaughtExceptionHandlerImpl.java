package zyx.utils.exceptions;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import zyx.utils.GameConstants;

public class UncaughtExceptionHandlerImpl implements Thread.UncaughtExceptionHandler
{
    private static final Logger LOG = GameConstants.LOGGER;

    public UncaughtExceptionHandlerImpl() throws IOException
    {
		File logFolder = new File("log");
		if (logFolder.exists() == false)
		{
			logFolder.mkdir();
		}
		
        FileHandler handler = new FileHandler("log/logFile.txt", true);
        handler.setFormatter(new SimpleFormatter());
        LOG.addHandler(handler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception)
    {
        String msg = String.format("An uncaught exception was thrown by thread '%s'", thread.getName());
        LOG.log(Level.SEVERE, msg, exception);
		
		if (GameConstants.DEBUG == false)
		{
			new UncaughtExceptionGui(exception).setVisible(true);
		}
    }
}
