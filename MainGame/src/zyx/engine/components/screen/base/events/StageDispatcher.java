package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.stage.*;

class StageDispatcher extends AbstractDispatcher<StageEvent, IStageListener>
{

	private ArrayList<IStageListener> cloneList;

	StageDispatcher()
	{
		cloneList = new ArrayList<>();
	}

	@Override
	protected void addRegistrations()
	{
		registerEventInterface(IAddedToStageListener.class, StageEventType.AddedToStage);
		registerEventInterface(IRemovedFromStageListener.class, StageEventType.RemovedFromStage);
	}

	@Override
	protected void dispatchEvent(StageEvent event, ArrayList<IStageListener> listeners)
	{
		cloneList.addAll(listeners);

		switch (event.type)
		{
			case AddedToStage:
			{
				for (IStageListener listener : cloneList)
				{
					((IAddedToStageListener) listener).addedToStage(event);
				}
				break;
			}
			case RemovedFromStage:
			{
				for (IStageListener listener : cloneList)
				{
					((IRemovedFromStageListener) listener).removedFromStage(event);
				}
				break;
			}
		}
		cloneList.clear();
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		if (cloneList != null)
		{
			cloneList.clear();
			cloneList = null;
		}
	}
	
	@Override
	Class<StageEvent> getEventClass()
	{
		return StageEvent.class;
	}

	@Override
	Class<IStageListener> getListenerClass()
	{
		return IStageListener.class;
	}
}
