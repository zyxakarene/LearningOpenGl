package zyx.engine.components.screen.base.events.types;

public interface IDisplayObjectEventListener<TEnum extends Enum, TType extends DisplayObjectEvent<TEnum>>
{
	Class<? extends IDisplayObjectEventListener> getTypeClass();
}
