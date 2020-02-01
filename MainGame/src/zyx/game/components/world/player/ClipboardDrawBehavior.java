package zyx.game.components.world.player;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.utils.callbacks.ICallback;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.engine.utils.worldpicker.IHoveredItem;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.MouseData;
import zyx.game.scene.game.GameScene;
import zyx.opengl.buffers.DrawingRenderer;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;

public class ClipboardDrawBehavior extends Behavior implements ICallback<Character>, IHoveredItem
{

	private static final Character TOGGLE_CHARACTER = 'z';

	private GameScene scene;
	private boolean active;
	private PlayerClipboard clipboard;

	public ClipboardDrawBehavior()
	{
		super(BehaviorType.CLIP_BOARD_DRAWING);
	}

	@Override
	public void initialize()
	{
		active = !MouseData.data.grabbed;
		scene = GameScene.getCurrent();

		InputManager.getInstance().OnKeyPressed.addCallback(this);
		clipboard = (PlayerClipboard) gameObject;

		int id = DrawingRenderer.getInstance().underlayInt();
		AbstractTexture tex = new TextureFromInt(256, 256, id, TextureSlot.SHARED_DIFFUSE);

		if (active)
		{
			scene.addPickedObject(clipboard, this);
		}
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}

	@Override
	public void dispose()
	{
		super.dispose();

		InputManager.getInstance().OnKeyPressed.removeCallback(this);
	}

	@Override
	public void onCallback(Character data)
	{
		if (data == TOGGLE_CHARACTER)
		{
			active = !active;

			if (active)
			{
				scene.addPickedObject(clipboard, this);
			}
			else
			{
				scene.removePickedObject(clipboard, this);
			}
		}
	}

	private boolean isDrawing;

	@Override
	public void onClicked(ClickedInfo info)
	{

		boolean wasDrawing = isDrawing;
		isDrawing = MouseData.data.isLeftDown();

		if (wasDrawing != isDrawing)
		{
			DrawingRenderer.getInstance().toggleDraw(isDrawing);
		}

		if (isDrawing)
		{
			Vector4f pos = new Vector4f(info.position.x, info.position.y, info.position.z, 1);
			Matrix4f invWorld = Matrix4f.invert(clipboard.getMatrix(), null);
			Matrix4f.transform(invWorld, pos, pos);

			System.out.println(pos);
			float x = pos.x + 0.5f;
			float y = pos.y + 0.5f;
			DrawingRenderer.getInstance().drawAt(x, y, !wasDrawing);
		}

	}
}
