package zyx.utils.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import zyx.utils.GameConstants;

public class Msg
{

    private static final Logger LOG = GameConstants.LOGGER;

    public static void info(String msg)
    {
        JOptionPane.showMessageDialog(null, "Info: " + msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(String msg, Exception ex)
    {
        LOG.log(Level.SEVERE, msg, ex);
    }
}
