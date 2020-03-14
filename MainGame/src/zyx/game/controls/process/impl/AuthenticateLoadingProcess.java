package zyx.game.controls.process.impl;

import zyx.engine.scene.SceneManager;
import zyx.engine.scene.loading.LoadingScreenProcess;
import zyx.game.models.GameModels;
import zyx.game.scene.SceneType;
import zyx.game.vo.Gender;
import zyx.net.io.controllers.NetworkChannel;
import zyx.net.io.controllers.NetworkCommands;

public class AuthenticateLoadingProcess extends LoadingScreenProcess
{

	private String name;
	private Gender gender;

	private int waitingTime;

	public AuthenticateLoadingProcess(String name, Gender gender)
	{
		super("Authenticating with the server");

		this.name = name;
		this.gender = gender;
		this.waitingTime = 2000;
	}

	@Override
	protected void onStart()
	{
		NetworkChannel.sendRequest(NetworkCommands.LOGIN, name, gender);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (GameModels.player.authenticated)
		{
			addDone(1);
			finish();
		}
		else
		{
			waitingTime -= elapsedTime;
			if (waitingTime <= 0)
			{
				SceneManager.getInstance().changeScene(SceneType.MENU);
			}
		}
	}

	@Override
	public int getTotalProgress()
	{
		return 1;
	}

}
