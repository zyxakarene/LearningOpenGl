package zyx.game.scene.gamescene;

import java.util.ArrayList;
import zyx.OnTeaPotClicked;
import zyx.engine.scene.Scene;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;

public class GameScene extends Scene
{

	private ArrayList<SimpleMesh> children;

	public GameScene()
	{
		children = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		AnimatedMesh knight = new AnimatedMesh();
		knight.load("mesh.worm.worm");
		knight.setAnimation("wiggle");
		world.addChild(knight);

		addPickedObject(knight, new OnTeaPotClicked());

		children.add(knight);
		
		AnimatedMesh parent = knight;
		for (int i = 0; i < 10; i++)
		{

			AnimatedMesh child = new AnimatedMesh();
			child.load("mesh.worm.worm");
			child.setAnimation("wiggle");
			parent.addChildAsAttachment(child, "Bone002");

			parent = child;
			
			children.add(child);
			
			addPickedObject(child, new OnTeaPotClicked());
		}
	}

	@Override
	protected void onDispose()
	{
		for (SimpleMesh simpleMesh : children)
		{
			simpleMesh.dispose();
		}

		children.clear();
		children = null;
	}
}
