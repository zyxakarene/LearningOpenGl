package zyx.game.scene.dev.showcase;

import zyx.OnTeaPotClicked;
import zyx.game.components.AnimatedMesh;
import zyx.game.scene.dev.DebugScene;
import zyx.utils.GameConstants;

public class MeshClickScene extends DebugScene
{

	public MeshClickScene()
	{
		GameConstants.DRAW_PHYSICS = true;
	}

	
	@Override
	protected void onInitialize()
	{
		addPlayerControls();

		AnimatedMesh mesh = new AnimatedMesh();
		mesh.load("mesh.player");
		world.addChild(mesh);
		mesh.setAnimation("idleCarry");
		
		
		picker.addObject(mesh, new OnTeaPotClicked());
		
		objects.add(mesh);
	}

}
