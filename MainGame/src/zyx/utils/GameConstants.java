package zyx.utils;

import java.util.logging.Logger;

public class GameConstants
{
	public static final boolean DEBUG = true;
	public static final boolean DRAW_PHYSICS = true;
	
	public static final int FPS = 60;
	public static final int MS_PER_FRAME = 1000 / FPS;

	public static final int LIGHT_COUNT = 100;
	
	public static final int DEFAULT_GAME_WIDTH = (int) (1920 * 0.75);
	public static final int DEFAULT_GAME_HEIGHT = (int) (1080 * 0.75);
	public static final float FOV = 70f;
	
	public static final Logger LOGGER = Logger.getGlobal();
	public static final String TEXTURE_FORMAT = "png";
	
	public static final float ANIMATION_FRAMERATE = 2f;
	public static final float ANIMATION_MS_PER_FRAME = 1000f / ANIMATION_FRAMERATE;

	public static final int PLAYER_POSITION_DELAY = 50;
	public static final float PLAYER_MIN_MOVEMENT = 0.01f;
	public static final float PLAYER_MIN_ROTATION = 0.001f;
}
