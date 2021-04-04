package zyx.engine.components.screen.base;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.engine.components.screen.base.events.types.focus.FocusEvent;
import zyx.engine.components.screen.base.events.types.focus.FocusEventType;
import zyx.engine.components.screen.base.events.types.mouse.MouseEvent;
import zyx.engine.components.screen.base.events.types.mouse.MouseEventType;
import zyx.engine.components.screen.base.events.types.stage.StageEvent;
import zyx.engine.components.screen.base.events.types.stage.StageEventType;

public class EventCache
{
	private static final HashMap<Class, ArrayList<? extends DisplayObjectEvent>> EVENT_LIST = new HashMap<>();
	static
	{
		ArrayList<MouseEvent> mouseEvents = new ArrayList<>();
		ArrayList<StageEvent> stageEvents = new ArrayList<>();
		ArrayList<FocusEvent> focusEvents = new ArrayList<>();
		
		EVENT_LIST.put(MouseEventType.class, mouseEvents);
		EVENT_LIST.put(StageEventType.class, stageEvents);
		EVENT_LIST.put(FocusEventType.class, focusEvents);
	}
	
	public static MouseEvent get(MouseEventType type)
	{
		ArrayList<? extends DisplayObjectEvent> list = EVENT_LIST.get(MouseEventType.class);
		
		int len = list.size();
		if (len == 0)
		{
			return new MouseEvent(type);
		}
		else
		{
			MouseEvent event = (MouseEvent) list.remove(len - 1);
			event.type = type;
			return event;
		}
	}
	
	public static StageEvent get(StageEventType type)
	{
		ArrayList<? extends DisplayObjectEvent> list = EVENT_LIST.get(StageEventType.class);
		
		int len = list.size();
		if (len == 0)
		{
			return new StageEvent(type);
		}
		else
		{
			StageEvent event = (StageEvent) list.remove(len - 1);
			event.type = type;
			return event;
		}
	}
	
	public static FocusEvent get(FocusEventType type)
	{
		ArrayList<? extends DisplayObjectEvent> list = EVENT_LIST.get(FocusEventType.class);
		
		int len = list.size();
		if (len == 0)
		{
			return new FocusEvent(type);
		}
		else
		{
			FocusEvent event = (FocusEvent) list.remove(len - 1);
			event.type = type;
			return event;
		}
	}

	static void returnEvent(DisplayObjectEvent event)
	{
		Class eventClass = event.type.getClass();
		ArrayList list = EVENT_LIST.get(eventClass);
		
		if (list != null)
		{
			list.add(event);
		}
	}
}
