package zyx.engine.components.screen.base.events.types.stage;

import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;

public interface IRemovedFromStageListener extends IDisplayObjectEventListener<StageEventType, StageEvent>
{
	void removedFromStage(StageEvent event);
}
