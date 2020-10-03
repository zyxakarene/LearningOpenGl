package zyx.game.scene.dev.showcase;

import zyx.game.behavior.misc.JiggleBehavior;
import zyx.game.components.MeshObject;
import zyx.game.scene.dev.DebugScene;
import zyx.utils.FloatMath;

public class NestedObjectsScene extends DebugScene
{

	@Override
	protected void onInitialize()
	{
		for (int i = 0; i < 10; i++)
		{
			MeshObject model = new MeshObject();
			model.load("mesh.box");
			if (i > 0)
			{
//				model.setX(FloatMath.random() * 40);
//				model.setY(FloatMath.random() * 40);
				model.setZ(55);
				model.setRotation(0, 90, 0);
			}

			if (i == 0)
			{
				model.setPosition(true, 0, 10, 0);
				world.addChild(model);
				model.setScale(0.1f, 0.1f, 0.1f);
			}
			else
			{
				objects.get(i - 1).addChild(model);
			}

			objects.add(model);
			
			model.addBehavior(new JiggleBehavior());
		}
		
		addPlayerControls();
	}
}
