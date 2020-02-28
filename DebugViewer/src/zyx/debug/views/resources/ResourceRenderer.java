package zyx.debug.views.resources;

import zyx.debug.views.base.DebugIcons;
import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import zyx.debug.network.vo.resources.ResourceInfo;

public class ResourceRenderer extends JLabel implements ListCellRenderer<ResourceInfo>
{

	private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

	public ResourceRenderer()
	{
		setOpaque(true);
		setBorder(DEFAULT_NO_FOCUS_BORDER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends ResourceInfo> list, ResourceInfo value, int index, boolean isSelected, boolean cellHasFocus)
	{
		if (value != null)
		{
			setText(value.path);

			if (value.isLoaded)
			{
				setForeground(Color.BLACK);
			}
			else
			{
				setForeground(Color.RED);
			}

			Icon icon = DebugIcons.createIcon(value.icon);
			setIcon(icon);
		}

		if (isSelected)
		{
			setBackground(Color.LIGHT_GRAY);
		}
		else
		{
			setBackground(Color.WHITE);
		}

		setSize(getWidth(), 16);

		return this;
	}
}
