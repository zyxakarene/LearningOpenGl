/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyx.utils.cheats;

import zyx.game.components.GameObject;

/**
 *
 * @author Rene
 */
public class DebugPoint extends GameObject
{

	private boolean alive;
	private boolean hasLife;
	int lifeSpan;
	
	public DebugPoint(float x, float y, float z, int lifespan)
	{
		this.lifeSpan = lifespan;
		this.hasLife = lifespan > 0;
		this.alive = true;
		
		load("assets/models/box.zaf");
		setPosition(x, y, z - 2.5f);
		setScale(0.1f, 0.1f, 0.1f);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (hasLife)
		{
			lifeSpan -= elapsedTime;
			if (lifeSpan <= 0)
			{
				alive = false;
			}
		}
	}

	public boolean isAlive()
	{
		return alive;
	}
	
	public static void addToScene(float x, float y, float z, int lifespan)
	{
		DebugPoint point = new DebugPoint(x, y, z, lifespan);
		DebugContainer.instance.addPoint(point);
	}
	
}