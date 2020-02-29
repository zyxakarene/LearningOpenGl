package zyx.debug.views.sounds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import zyx.debug.link.DebugInfo;
import zyx.debug.views.base.BaseDebugPanel;
import zyx.engine.sound.DebugSound;
import zyx.debug.link.DebugSoundLink;
import zyx.engine.sound.SoundSystem;

public class DebugSoundPanel extends BaseDebugPanel
{

	private static final int SIZE = 10;
	private static final int PADDING = 4;

	private DebugSound[] outSounds;
	
	private DebugSound selectedSound;
	private DebugSoundLink soundLink;

	public DebugSoundPanel()
	{
		outSounds = new DebugSound[SoundSystem.MAX_SOURCES];

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				onMouseDown(e.getX(), e.getY());
			}
		});
		
		setBackground(Color.WHITE);
		
		soundLink = DebugInfo.sounds;
	}

	private void onMouseDown(int x, int y)
	{
		if (x < SIZE || y < SIZE)
		{
			return;
		}
		
		int w = getWidth();
		
		int posX = (x - SIZE) / (SIZE + PADDING);
		int posY = (y - SIZE) / (SIZE + PADDING);
		
		int boxesPerWidth = (w - SIZE - SIZE) / (SIZE + PADDING);
		int index = (posY * (boxesPerWidth + 1)) + posX;
		
		if (index < outSounds.length)
		{
			selectedSound = outSounds[index];
		}
	}

	@Override
	public void update()
	{
		soundLink.getSourceStatus(outSounds);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		int w = getWidth();

		int x = SIZE;
		int y = SIZE;
		DebugSound sound;
		
		for (int i = 0; i < SoundSystem.MAX_SOURCES; i++)
		{
			sound = outSounds[i];

			g.setColor(sound.status ? Color.RED : Color.GREEN);
			g.fillRect(x, y, 10, 10);

			
			g.setColor(Color.BLACK);
			g.drawRect(x - 1, y - 1, SIZE + 1, SIZE + 1);

			if (selectedSound == sound)
			{
				g.setColor(Color.BLUE);
				g.drawLine(x, y, x + SIZE, y + SIZE);
				g.drawLine(x, y + SIZE, x + SIZE, y);
				
				if(selectedSound.path != null)
				{
					g.drawString(selectedSound.path, SIZE, 100);
				}
			}
			
			x += SIZE + PADDING;

			if (x + SIZE >= w)
			{
				x = SIZE;
				y += SIZE + PADDING;
			}
		}
	}

	@Override
	public String getTabName()
	{
		return "Sounds";
	}
}
