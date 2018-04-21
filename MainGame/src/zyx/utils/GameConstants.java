package zyx.utils;

import java.util.logging.Logger;

public class GameConstants
{
	public static final boolean DEBUG = true;
	
	public static final int FPS = 60;
	public static final int MS_PER_FRAME = 1000 / FPS;

	public static final int GAME_WIDTH = (int) (1920 * 0.75);
	public static final int GAME_HEIGHT = (int) (1080 * 0.75);
	
	public static final Logger LOGGER = Logger.getGlobal();
	public static final String TEXTURE_FORMAT = "png";
	
	public static final float ANIMATION_FRAMERATE = 1f;
	public static final float ANIMATION_MS_PER_FRAME = 1000f / ANIMATION_FRAMERATE;

}
