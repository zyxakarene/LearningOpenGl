package zyx.opengl.textures.custom;

public interface ITexture
{
	public int getWidth();
	public int getHeight();
	
	public void bind();
	public void dispose();
}
