package zyx.utils;

import java.util.logging.Logger;

public class GameConstants
{
	public static final boolean DEBUG = true;
	public static boolean DRAW_PHYSICS = false;
	
	public static final int FPS = 60;
	public static final int MS_PER_FRAME = 1000 / FPS;

	public static final int LIGHT_COUNT = 100;
	
	public static final int WINDOW_WIDTH = (int) (1920 * 0.75f);
	public static final int WINDOW_HEIGHT = (int) (1080 * 0.75f);
	
	public static final float DEFAULT_GAME_WIDTH_RATIO = 1f;
	public static final float DEFAULT_GAME_HEIGHT_RATIO = 1f;
	
	public static final int DEFAULT_GAME_WIDTH = (int) (WINDOW_WIDTH * DEFAULT_GAME_WIDTH_RATIO);
	public static final int DEFAULT_GAME_HEIGHT = (int) (WINDOW_HEIGHT * DEFAULT_GAME_HEIGHT_RATIO);
	public static final float FOV = 70f;
	
	public static final Logger LOGGER = Logger.getGlobal();
	public static final String TEXTURE_FORMAT = "png";
	
	public static final float ANIMATION_FRAMERATE = 24f;
	public static final float ANIMATION_MS_PER_FRAME = 1000f / ANIMATION_FRAMERATE;

	public static final int PLAYER_POSITION_DELAY = 50;
	public static final float PLAYER_MIN_MOVEMENT = 0.01f;
	public static final float PLAYER_MIN_ROTATION = 0.001f;
		
	public static final float[] SHADOW_CASCADES = new float[]
	{
		1,
		50,
		100,
		180,
		350
	};
	
	private static final int OFFSET_A = 8000;
	private static final int OFFSET_B = 16000;
	private static final int OFFSET_C = 32000;
	
	public static final float CASCADE_MIN_DISTANCE_1 = 0 - OFFSET_A;
	public static final float CASCADE_MAX_DISTANCE_1 = 2000 + OFFSET_A;
	
	public static final float CASCADE_MIN_DISTANCE_2 = 2000 - OFFSET_A;
	public static final float CASCADE_MAX_DISTANCE_2 = 17400 + OFFSET_A;
	
	public static final float CASCADE_MIN_DISTANCE_3 = 17400 - OFFSET_B;
	public static final float CASCADE_MAX_DISTANCE_3 = 33800 + OFFSET_B;
	
	public static final float CASCADE_MIN_DISTANCE_4 = 33800 - OFFSET_C;
	public static final float CASCADE_MAX_DISTANCE_4 = 50000 + OFFSET_C;
}
