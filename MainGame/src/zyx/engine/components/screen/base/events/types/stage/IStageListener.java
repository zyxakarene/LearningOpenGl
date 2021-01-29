package zyx.engine.components.screen.base.events.types.stage;

import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;

public interface IStageListener extends IDisplayObjectEventListener<StageEventType, StageEvent>
{
	void addedToStage(StageEvent event);
	void removedFromStage(StageEvent event);
}
