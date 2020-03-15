package zyx.utils.tween;

import java.util.ArrayList;
import zyx.utils.interfaces.IUpdateable;

public class TweenManager implements IUpdateable
{

	private static final TweenManager INSTANCE = new TweenManager();

	private ArrayList<BaseTween> tweensToRemove;
	private ArrayList<BaseTween> tweens;
	
	private TweenManager()
	{
		tweens = new ArrayList<>();
		tweensToRemove = new ArrayList<>();
	}

	public static TweenManager getInstance()
	{
		return INSTANCE;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		tweens.removeAll(tweensToRemove);
		for (BaseTween toRemove : tweensToRemove)
		{
			toRemove.dispose();
		}
		tweensToRemove.clear();
		
		for (BaseTween tween : tweens)
		{
			if (tween.started == false)
			{
				tween.internalStart(timestamp);
			}
			
			tween.update(timestamp, elapsedTime);
			
			if (tween.completed)
			{
				tweensToRemove.add(tween);
			}
		}
	}

	void add(BaseTween tween)
	{
		tweens.add(tween);
	}
	
	void remove(BaseTween tween)
	{
		tweens.remove(tween);
	}
}
