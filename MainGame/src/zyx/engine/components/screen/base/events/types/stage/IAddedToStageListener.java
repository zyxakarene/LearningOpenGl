package zyx.engine.components.screen.base.events.types.stage;

import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;

public interface IAddedToStageListener extends IDisplayObjectEventListener<StageEventType, StageEvent>
{
	void addedToStage(StageEvent event);
}
