package zyx.game.scene.gamescene;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.OnTeaPotClicked;
import zyx.engine.scene.Scene;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.camera.ViewFrustum;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;

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
		for (int i = 0; i < 0; i++)
		{

			AnimatedMesh child = new AnimatedMesh();
			child.load("mesh.worm.worm");
			child.setAnimation("wiggle");
			parent.addChildAsAttachment(child, "Bone002");

			parent = child;

			children.add(child);

			addPickedObject(child, new OnTeaPotClicked());
		}
		
		for (int i = -10; i < 10; i++)
		{
			for (int j = -10; j < 10; j++)
			{
				for (int k = -10; k < 10; k++)
				{
					DebugPoint.addToScene(i * 10,j * 10,k * 10 - 100, 0);
				}
			}
		}
		
		DebugPoint.addToScene(0, 0, 20, 0);
	}

	@Override
	protected void onDraw()
	{
		if (KeyboardData.data.isDown(Keyboard.KEY_SPACE))
		{
			ViewFrustum fr = new ViewFrustum();
			fr.extractPlanesFrom(SharedShaderObjects.SHARED_PROJECTION_VIEW_TRANSFORM);
			
			Print.out("==");
			boolean isInView = fr.isInsideView(new Vector3f(0, 0, 20), 0);
			if (!isInView)
			{
				Print.out("Thing outside!!!");
			}
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
