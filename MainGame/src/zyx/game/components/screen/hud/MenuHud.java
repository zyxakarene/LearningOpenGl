package zyx.game.components.screen.hud;

import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.scene.SceneManager;
import zyx.game.scene.SceneType;

public class MenuHud extends BaseHud
{

	private Button playButton;

	public MenuHud()
	{
	}

	private void playBtnClicked(InteractableContainer data)
	{
		SceneManager.getInstance().changeScene(SceneType.GAME);
	}
	
	@Override
	public String getResource()
	{
		return "json.menu_hud";
	}
	
	@Override
	protected void onComponentsCreated()
	{
		super.onComponentsCreated();

		playButton = this.<Button>getComponentByName("play_button");
		if (playButton != null)
		{
			playButton.onButtonClicked.addCallback(this::playBtnClicked);
		}
	}
}
