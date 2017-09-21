package zyx.utils;

public class FloatMath
{
    public static final float PI = (float) Math.PI;

    public static float abs(float value)
    {
        return Math.abs(value);
    }

    public static float toRadians(float degrees)
    {
        return degrees / 180.0f * PI;
    }

    public static float toDegrees(float radians)
    {
        return radians * 180.0f / PI;
    }

    public static float sin(float a)
    {
        return (float) Math.sin(a);
    }

    public static float asin(float a)
    {
        return (float) Math.asin(a);
    }

    public static float cos(float a)
    {
        return (float) Math.cos(a);
    }

    public static float acos(float a)
    {
        return (float) Math.acos(a);
    }

    public static float tan(float a)
    {
        return (float) Math.tan(a);
    }
    
    public static float atan(float a)
    {
        return (float) Math.atan(a);
    }
    
    public static float atan2(float x, float y)
    {
        return (float) Math.atan2(y, x);
    }

    public static float sqrt(float value)
    {
        return (float) Math.sqrt(value);
    }
    
    public static float random()
    {
        return (float) Math.random();
    }
    
    public static float square(float value)
    {
        return value * value;
    }
}
