package zyx.game.scene.gamescene;

import java.util.ArrayList;
import zyx.engine.components.screen.Button;
import zyx.engine.components.screen.Image;
import zyx.engine.components.screen.InteractableContainer;
import zyx.engine.scene.Scene;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.components.MeshObject;
import zyx.game.components.screen.AddBitmapFontButton;
import zyx.utils.FloatMath;
import zyx.utils.cheats.Print;

public class TestScene extends Scene implements ICallback<InteractableContainer>
{
	
	private Button button;
	
	private ArrayList<MeshObject> objects;

	public TestScene()
	{
		objects = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 10; i++)
		{
			MeshObject model = new MeshObject();
			model.load("mesh.box");
			model.setX(FloatMath.random() * 300);
			model.setY(FloatMath.random() * 300);

			world.addChild(model);
			objects.add(model);
		}
		
		button = new AddBitmapFontButton("texture.BtnUp", "texture.BtnHover", "texture.BtnDown");
		stage.addChild(button);
		
		button.onButtonClicked.addCallback(this);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		for (MeshObject object : objects)
		{
			object.update(timestamp, elapsedTime);
		}
	}

	@Override
	protected void onDispose()
	{
		for (MeshObject object : objects)
		{
			object.dispose();
		}
		
		button.dispose();
		button = null;
		
		objects.clear();
		objects = null;
	}

	@Override
	public void onCallback(InteractableContainer data)
	{
		
	}
	
}
