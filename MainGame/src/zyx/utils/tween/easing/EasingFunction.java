package zyx.utils.tween.easing;

public enum EasingFunction
{
	LINEAR(Easing.LINEAR.in),
	
	SINE_IN(Easing.SINE.in),
	SINE_OUT(Easing.SINE.out),
	SINE_IN_OUT(Easing.SINE.inOut),
	
	QUART_IN(Easing.QUART.in),
	QUART_OUT(Easing.QUART.out),
	QUART_IN_OUT(Easing.QUART.inOut),
	
	QUINT_IN(Easing.QUINT.in),
	QUINT_OUT(Easing.QUINT.out),
	QUINT_IN_OUT(Easing.QUINT.inOut),
	
	QUAD_IN(Easing.QUAD.in),
	QUAD_OUT(Easing.QUAD.out),
	QUAD_IN_OUT(Easing.QUAD.inOut),
	
	EXPO_IN(Easing.EXPO.in),
	EXPO_OUT(Easing.EXPO.out),
	EXPO_IN_OUT(Easing.EXPO.inOut),
	
	ELASTIC_IN(Easing.ELASTIC.in),
	ELASTIC_OUT(Easing.ELASTIC.out),
	ELASTIC_IN_OUT(Easing.ELASTIC.inOut),
	
	BOUNCE_IN(Easing.BOUNCE.in),
	BOUNCE_OUT(Easing.BOUNCE.out),
	BOUNCE_IN_OUT(Easing.BOUNCE.inOut),
	
	CIRC_IN(Easing.CIRC.in),
	CIRC_OUT(Easing.CIRC.out),
	CIRC_IN_OUT(Easing.CIRC.inOut),
	
	CUBIC_IN(Easing.CUBIC.in),
	CUBIC_OUT(Easing.CUBIC.out),
	CUBIC_IN_OUT(Easing.CUBIC.inOut),
	
	BACK_IN(Easing.BACK.in),
	BACK_OUT(Easing.BACK.out),
	BACK_IN_OUT(Easing.BACK.inOut);
	
	public final IEase function;
	
	private EasingFunction(IEase function)
	{
		this.function = function;
	}
}
