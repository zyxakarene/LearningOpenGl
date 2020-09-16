package zyx.gui;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import zyx.logic.DragDropper;
import zyx.logic.IFilesDropped;

public class TextureDropper implements IFilesDropped
{

	private JLabel label;
	private DragDropper dropper;
	private JLabel textLabel;

	public TextureDropper(JLabel label, JLabel textLabel)
	{
		this.label = label;
		this.textLabel = textLabel;
		dropper = new DragDropper("png", this);
		
		textLabel.setVisible(false);
	}

	public void clear()
	{
		textLabel.setVisible(false);
		
		label.setIcon(null);
		label.setText("N/A");
	}
	
	@Override
	public void filesDropped(List<File> files)
	{
		if (files.size() > 0)
		{
			try
			{
				File file = files.get(0);
				Image image = ImageIO.read(file);
				Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_FAST);
				
				label.setIcon(new ImageIcon(scaledImage));
				label.setText("");
				
				textLabel.setText(file.getName());
				textLabel.setVisible(true);
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	@Override
	public Component getDropPanel()
	{
		return label;
	}
}
