package zyx.engine.components.screen.base.events.types.stage;

import zyx.engine.components.screen.base.Stage;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;

public class StageEvent extends DisplayObjectEvent<StageEventType>
{
	public final Stage stage;
	
	public StageEvent(StageEventType type, Stage stage)
	{
		super(type);
		
		this.stage = stage;
	}

}
