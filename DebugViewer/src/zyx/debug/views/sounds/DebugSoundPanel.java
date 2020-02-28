package zyx.debug.views.sounds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import zyx.debug.network.vo.sounds.SoundInfo;
import zyx.debug.network.vo.sounds.SoundInformation;
import zyx.debug.views.base.BaseDebugPanel;

public class DebugSoundPanel extends BaseDebugPanel
{

	private static final int SIZE = 10;
	private static final int PADDING = 4;

	private ArrayList<SoundInfo> outSounds;
	
	private SoundInfo selectedSound;

	public DebugSoundPanel()
	{
		outSounds = new ArrayList<>();

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				onMouseDown(e.getX(), e.getY());
			}
		});
		
		setBackground(Color.WHITE);
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
		
		if (index < outSounds.size())
		{
			selectedSound = outSounds.get(index);
		}
	}

	@Override
	public void update()
	{
		if (SoundInformation.hasSoundChanges)
		{
			outSounds.clear();
			outSounds.addAll(SoundInformation.soundInfo);
		}
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		int w = getWidth();

		int x = SIZE;
		int y = SIZE;
		
		
		for (SoundInfo sound : outSounds)
		{
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
