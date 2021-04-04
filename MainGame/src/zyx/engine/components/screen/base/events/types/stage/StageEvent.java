package zyx.engine.components.screen.base.events.types.stage;

import zyx.engine.components.screen.base.Stage;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;

public class StageEvent extends DisplayObjectEvent<StageEventType>
{
	public Stage stage;
	
	public StageEvent(StageEventType type)
	{
		super(type);
	}
	
	public StageEvent setup(Stage stage)
	{
		this.stage = stage;
		
		return this;
	}
}
