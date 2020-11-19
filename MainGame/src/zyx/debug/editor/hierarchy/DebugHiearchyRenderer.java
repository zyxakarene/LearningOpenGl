package zyx.debug.editor.hierarchy;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.generic.window.WindowsTextfield;
import zyx.engine.components.screen.base.generic.window.tree.DefaultWindowsTreeRenderer;
import zyx.engine.components.world.GameLight;
import zyx.engine.components.world.WorldObject;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.opengl.particles.ParticleSystem;
import zyx.opengl.textures.bitmapfont.alignment.HAlignment;

public class DebugHiearchyRenderer extends DefaultWindowsTreeRenderer<WorldObject>
{

	@Override
	protected DisplayObject createDisplayObject(WorldObject data)
	{
		float height = getRendererHeight();

		String text = getLabel(data);
		WindowsTextfield textfield = new WindowsTextfield(text);
		textfield.setSize(200, height);
		textfield.setHAlign(HAlignment.LEFT);

		return textfield;
	}
	
	private String getLabel(WorldObject data)
	{
		boolean hasChildren = false;
		String extra = "";
		String childPart = "";
		if (data instanceof SimpleMesh)
		{
			extra = from((SimpleMesh) data);
		}
		else if (data instanceof GameLight)
		{
			extra = from((GameLight) data);
		}
		else if (data instanceof ParticleSystem)
		{
			extra = from((ParticleSystem) data);
		}
		else if (data instanceof GameObject)
		{
			hasChildren = true;
			extra = from((GameObject) data);
		}
		else
		{
			hasChildren = true;
		}
		
		if (hasChildren)
		{
			childPart = String.format("+(%s)", data.getChildrenCount());
		}
		
		String type = data.getClass().getSimpleName();
		return String.format("%s "+ extra + childPart, type);
	}

	private String from(SimpleMesh mesh)
	{
		return String.format("[%s]", mesh.getResource());
	}

	private String from(ParticleSystem particle)
	{
		return String.format("[%s]", particle.getResource());
	}

	private String from(GameLight light)
	{
		int color = light.getColor();
		String hex = Integer.toHexString(color).toUpperCase();
		return String.format("[0x%s]", hex);
	}

	private String from(GameObject gameObject)
	{
		int behaviorCount = gameObject.getBehaviorCount();
		return String.format("[%s behaviors]", behaviorCount);
	}
}
