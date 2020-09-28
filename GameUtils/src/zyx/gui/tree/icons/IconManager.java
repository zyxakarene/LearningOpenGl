package zyx.gui.tree.icons;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class IconManager
{

    public static final String EMPTY_FOLDER = "emptyFolder.png";
    public static final String OPEN_FOLDER = "openFolder.png";
    public static final String SKELETON = "skeleton.png";
    public static final String MESH = "mesh.png";

    public static Icon getIcon(String iconName)
    {
        try
        {
            return new ImageIcon(IconManager.class.getResource(iconName));
        }
        catch (Exception ex)
        {
            return UIManager.getDefaults().getIcon("FileView.directoryIcon");
        }
    }
}
