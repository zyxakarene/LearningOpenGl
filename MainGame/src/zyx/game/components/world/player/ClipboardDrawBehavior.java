package zyx.game.components.world.player;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.engine.scene.SceneManager;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.MouseData;
import zyx.game.scene.game.GameScene;
import zyx.opengl.buffers.DrawingRenderer;
import zyx.engine.utils.worldpicker.IWorldPickedItem;

public class ClipboardDrawBehavior extends Behavior implements IWorldPickedItem
{

	private GameScene scene;
	private PlayerClipboard clipboard;
	private int noGeometryFrames;

	public ClipboardDrawBehavior()
	{
		super(BehaviorType.CLIP_BOARD_DRAWING);
	}

	@Override
	public void initialize()
	{
		scene = SceneManager.getInstance().getSceneAs();

		clipboard = (PlayerClipboard) gameObject;
	}

	@Override
	protected void onActivate()
	{
		scene.addPickedObject(clipboard, this);
	}

	@Override
	protected void onDeactivate()
	{
		scene.removePickedObject(clipboard, this);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		noGeometryFrames++;
	}

	@Override
	public void dispose()
	{
		super.dispose();

		scene.removePickedObject(clipboard, this);
	}

	private boolean isDrawing;

	@Override
	public void onGeometryPicked(ClickedInfo info)
	{
		CursorManager.getInstance().setCursor(GameCursor.PENCIL);

		
		boolean wasDrawing = isDrawing;
		isDrawing = MouseData.data.isLeftDown();

		if (noGeometryFrames >= 3)
		{
			DrawingRenderer.getInstance().toggleDraw(false);
			wasDrawing = false;
			isDrawing = false;
		}
		noGeometryFrames = 0;
		
		if (wasDrawing != isDrawing)
		{
			DrawingRenderer.getInstance().toggleDraw(isDrawing);
		}

		if (isDrawing && noGeometryFrames <= 3)
		{
			Vector4f pos = new Vector4f(info.position.x, info.position.y, info.position.z, 1);
			Matrix4f invWorld = Matrix4f.invert(clipboard.getMatrix(), null);
			Matrix4f.transform(invWorld, pos, pos);

			float x = pos.x + 0.5f;
			float y = pos.y + 0.5f;
			DrawingRenderer.getInstance().drawAt(x, y, !wasDrawing);
		}

	}
}
