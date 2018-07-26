package zyx.engine.sound;

public interface IAudio
{
	public void dispose();
	
	public Audio createClone();
	
	public void playAt(float x, float y, float z, float volume, boolean loop);
	
	public boolean isPlaying();
	
	public void stop();
	
	public void setPosition(float position);

	public float getPosition();
	
	public void setListenerPosition(float x, float y, float z);
}
