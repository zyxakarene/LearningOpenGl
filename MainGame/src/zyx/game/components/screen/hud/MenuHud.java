package zyx.game.components.screen.hud;

import zyx.engine.components.screen.interactable.Button;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.scene.SceneManager;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.scene.SceneType;

public class MenuHud extends BaseHud
{

	private Button playButton;
	private ICallback<InteractableContainer> onPlayButtonClicked;

	public MenuHud()
	{
		onPlayButtonClicked = this::playBtnClicked;
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
		playButton.onButtonClicked.addCallback(onPlayButtonClicked);
	}
}
