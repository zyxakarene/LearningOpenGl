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
import zyx.logic.converter.json.JsonMeshTextureEntry;

public class TextureDropper implements IFilesDropped
{
	public static final int DIFFUSE = 0;
	public static final int NORMAL = 1;
	public static final int SPECULAR = 2;
	
	private JLabel label;
	private DragDropper dropper;
	private JLabel textLabel;
	private int type;
	private JsonMeshTextureEntry textureEntry;

	public TextureDropper(JLabel label, JLabel textLabel, int type)
	{
		this.label = label;
		this.textLabel = textLabel;
		this.type = type;
		dropper = new DragDropper("png", this);

		textLabel.setVisible(false);
	}

	public void setTexture(JsonMeshTextureEntry entry)
	{
		textureEntry = entry;
		
		switch (type)
		{
			case DIFFUSE:
				loadFileInner(textureEntry.diffuseFile);
				break;
			case NORMAL:
				loadFileInner(textureEntry.normalFile);
				break;
			case SPECULAR:
				loadFileInner(textureEntry.specularFile);
				break;
		}
	}

	public void clear()
	{
		textLabel.setVisible(false);

		label.setIcon(null);
		label.setText("N/A");
		
		switch (type)
		{
			case DIFFUSE:
				textureEntry.diffuseFile = null;
				break;
			case NORMAL:
				textureEntry.normalFile = null;
				break;
			case SPECULAR:
				textureEntry.specularFile = null;
				break;
		}
	}

	@Override
	public void filesDropped(List<File> files)
	{
		if (files.size() > 0)
		{
			File file = files.get(0);
			loadFileInner(file);
		}
	}

	@Override
	public Component getDropPanel()
	{
		return label;
	}

	public void setFile(File file)
	{
		loadFileInner(file);
	}

	private void loadFileInner(File file)
	{
		if (file == null)
		{
			clear();
			return;
		}
		
		try
		{
			Image image = ImageIO.read(file);
			Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_FAST);

			label.setIcon(new ImageIcon(scaledImage));
			label.setText("");

			textLabel.setText(file.getName());
			textLabel.setVisible(true);
			
			switch (type)
			{
				case DIFFUSE:
					textureEntry.diffuseFile = file;
					break;
				case NORMAL:
					textureEntry.normalFile = file;
					break;
				case SPECULAR:
					textureEntry.specularFile = file;
					break;
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
