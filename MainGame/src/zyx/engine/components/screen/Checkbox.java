package zyx.engine.components.screen;

public class Checkbox extends Button
{

	private Image checkImg;
	private boolean checked;
	
	public Checkbox(String upTexture, String hoverTexture, String downTexture, String checkIcon)
	{
		super(upTexture, hoverTexture, downTexture);
		
		checkImg = new Image();
		checkImg.load(checkIcon);
		checkImg.position.x = (getWidth() / 2) - (checkImg.getWidth() / 2);
		checkImg.position.y = (getHeight()/ 2) - (checkImg.getHeight() / 2);

		addChild(checkImg);
		checkImg.visible = false;
		
		checked = false;
	}

	@Override
	public void dispose()
	{
		super.dispose();
		checkImg = null;
	}
	
	@Override
	protected void onMouseClick()
	{
		super.onMouseClick();
		
		checked = !checked;
		checkImg.visible = checked;
	}

	public boolean isChecked()
	{
		return checked;
	}
}
