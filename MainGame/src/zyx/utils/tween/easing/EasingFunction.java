package zyx.utils.tween.easing;

public enum EasingFunction
{
	LINEAR(Easing.LINEAR.in),
	
	BACK_IN(Easing.BACK.in),
	BACK_OUT(Easing.BACK.out),
	BACK_IN_OUT(Easing.BACK.inOut);
	
	public final IEase function;
	
	private EasingFunction(IEase function)
	{
		this.function = function;
	}
}
