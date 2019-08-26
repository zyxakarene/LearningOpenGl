package zyx.server.world.entity;

import java.awt.Color;
import java.awt.Graphics;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.position.data.PositionData;

public abstract class WorldEntity
{
	private static int idCounter;

	public float x, y, z;
	public float lx, ly, lz;

	public final int id;
	
	public boolean updatedPosition;

	public WorldEntity()
	{
		this.id = idCounter++;
		
		lx = 100;
	}
	
	public void updateFrom(PositionData data)
	{
		updatedPosition = true;
		Vector3f pos = data.position;
		Vector3f rot = data.lookAt;
		
		x = pos.x;
		y = pos.y;
		z = pos.z;
		
		lx = rot.x;
		ly = rot.y;
		lz = rot.z;
	}
	
	public void updatePosition(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		updatedPosition = true;
	}
	
	public void updateLook(float lx, float ly, float lz)
	{
		this.lx = lx;
		this.ly = ly;
		this.lz = lz;
		
		updatedPosition = true;
	}

	public final void draw(Graphics g)
	{
		int size = getSize();
		int sizeHalf = size / 2;
		
		int xPos = (int) (x - sizeHalf);
		int yPos = (int) (y - sizeHalf);
	
		g.setColor(getColor());
		if (isRound())
		{
			g.fillOval(xPos, yPos, size, size);
		}
		else
		{
			g.fillRect(xPos, yPos, size, size);
		}
		
		onDraw(g);
	}
	
	@Override
	public String toString()
	{
		String clazz = getClass().getSimpleName();
		return String.format("%s[%s]", clazz, id);
	}
	
	public Color getColor()
	{
		return Color.BLACK;
	}
	
	public int getSize()
	{
		return 40;
	}
	
	public boolean isRound()
	{
		return false;
	}

	protected void onDraw(Graphics g)
	{
	}
}
