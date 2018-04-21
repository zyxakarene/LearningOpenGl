/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyx.utils.cheats;

import java.util.ArrayList;
import zyx.game.components.GameObject;

/**
 *
 * @author Rene
 */
public class DebugContainer extends GameObject
{

	static DebugContainer instance;
	private ArrayList<DebugPoint> points;

	public DebugContainer()
	{
		instance = this;
		points = new ArrayList<>();
	}
	
	void addPoint(DebugPoint point)
	{
		addChild(point);
		points.add(point);
	}
	
	@Override
	public void onUpdate(long timestamp, int elapsedTime)
	{
		DebugPoint point;
		for (int i = points.size() - 1; i >= 0; i--)
		{
			point = points.get(i);
			point.update(timestamp, elapsedTime);
			
			if (point.isAlive() == false)
			{
				points.remove(i);
				point.dispose();
			}
		}
	}

	
}
