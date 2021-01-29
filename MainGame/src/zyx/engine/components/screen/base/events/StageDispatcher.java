package zyx.engine.components.screen.base.events;

import java.util.ArrayList;
import zyx.engine.components.screen.base.events.types.stage.IStageListener;
import zyx.engine.components.screen.base.events.types.stage.StageEvent;

class StageDispatcher extends AbstractDispatcher<StageEvent, IStageListener>
{
	private ArrayList<IStageListener> cloneList;
	
	StageDispatcher()
	{
		cloneList = new ArrayList<>();
	}

	@Override
	void dispatch(StageEvent event)
	{
		cloneList.addAll(listeners);
		
		switch (event.type)
		{
			case AddedToStage:
			{
				for (IStageListener listener : cloneList)
				{
					listener.addedToStage(event);
				}
				break;
			}
			case RemovedFromStage:
			{
				for (IStageListener listener : cloneList)
				{
					listener.removedFromStage(event);
				}
				break;
			}
			default:
		}
		
		cloneList.clear();
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
