package zyx.game.components.world.player;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.InputManager;

public class ClipboardViewerBehavior extends Behavior implements ICallback<Character>
{

	private static final Character TOGGLE_CHARACTER = ' ';
	private static final int DEFAULT_MOVE_TIME = 1000;
	
	private boolean viewingBoard;
	private boolean tweening;
	private float timeToMove;
	private float tweenTime;

	private Vector3f fromPos;
	private Vector3f toPos;
	private Vector3f fromRot;
	private Vector3f toRot;
	private Vector3f curRot;
	private Vector3f curPos;

	public ClipboardViewerBehavior()
	{
		super(BehaviorType.CLIP_BOARD_DRAWING);

		fromPos = new Vector3f();
		toPos = new Vector3f();
		fromRot = new Vector3f();
		toRot = new Vector3f();
		curRot = new Vector3f();
		curPos = new Vector3f();
	}

	@Override
	public void initialize()
	{
		viewingBoard = false;
		InputManager.getInstance().OnKeyPressed.addCallback(this);

		curPos.set(-3, -2, -2);
		curRot.set(45, 0, 90);
		
		gameObject.setScale(0.1f, 0.1f, 0.1f);
		gameObject.setRotation(curRot);
		gameObject.setPosition(true, curPos);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (tweening)
		{
			tweenTime += elapsedTime;
			float fraction = tweenTime / timeToMove;
			
			lerp(fromRot, toRot, fraction, curRot);
			lerp(fromPos, toPos, fraction, curPos);

			gameObject.setRotation(curRot);
			gameObject.setPosition(true, curPos);
			
			tweening = fraction < 1;
		}
	}

	private void lerp(Vector3f from, Vector3f to, float fraction, Vector3f out)
	{
		out.x = from.x + fraction * (to.x - from.x);
		out.y = from.y + fraction * (to.y - from.y);
		out.z = from.z + fraction * (to.z - from.z);
	}
	
	@Override
	public void onCallback(Character data)
	{
		if (data == TOGGLE_CHARACTER)
		{
			viewingBoard = !viewingBoard;
			if (tweening)
			{
				timeToMove = tweenTime;
			}
			else
			{
				timeToMove = DEFAULT_MOVE_TIME;
			}
			tweening = true;
			tweenTime = 0;

			fromPos.set(curPos);
			fromRot.set(curRot);
			
			if (viewingBoard)
			{
				toPos.set(0, 0, -2);
				toRot.set(0, 0, 90);
			}
			else
			{
				toPos.set(-3, -2, -2);
				toRot.set(45, 0, 90);
			}
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();

		InputManager.getInstance().OnKeyPressed.removeCallback(this);
	}
}
