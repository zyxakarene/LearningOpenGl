package zyx.game.components.world.player;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.screen.base.Stage;
import zyx.engine.touch.MouseTouchManager;
import zyx.engine.utils.callbacks.ICallback;
import zyx.game.behavior.Behavior;
import zyx.game.behavior.BehaviorType;
import zyx.game.controls.input.InputManager;
import zyx.game.controls.input.scheme.InputScheme;
import zyx.utils.tween.BaseTween;
import zyx.utils.tween.easing.EasingFunction;
import zyx.utils.tween.impl.positionable.TweenPositionableVectorPosition;
import zyx.utils.tween.impl.positionable.TweenPositionableVectorRotation;

public class ClipboardViewerBehavior extends Behavior implements ICallback<BaseTween>
{

	private static final int DEFAULT_MOVE_TIME = 1000;

	private boolean viewingBoard;
	private boolean tweening;
	private int tweenTime;

	private Vector3f toPos;
	private Vector3f toRot;
	private Vector3f curRot;
	private Vector3f curPos;

	private InputScheme inputScheme;
	private Behavior clipDrawBehavior;
	private BaseTween rotateTween;
	private BaseTween moveTween;

	public ClipboardViewerBehavior()
	{
		super(BehaviorType.CLIP_BOARD_VIEWING);

		toPos = new Vector3f();
		toRot = new Vector3f();
		curRot = new Vector3f();
		curPos = new Vector3f();
	}

	@Override
	public void initialize()
	{
		viewingBoard = false;
		inputScheme = InputManager.getInstance().scheme;

		curPos.set(-3, -2, -2);
		curRot.set(45, 0, 90);

		gameObject.setScale(0.1f, 0.1f, 0.1f);
		gameObject.setRotation(curRot);
		gameObject.setPosition(true, curPos);

		clipDrawBehavior = gameObject.getBehaviorById(BehaviorType.CLIP_BOARD_DRAWING);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (inputScheme.toggleClipboard)
		{
			toggleClipboard();
			clipDrawBehavior.setActive(viewingBoard);
		}

		if (tweening)
		{
			tweenTime += elapsedTime;
		}
	}

	private void toggleClipboard()
	{
		viewingBoard = !viewingBoard;
		MouseTouchManager.getInstance().setEnabled(!viewingBoard);
		Stage.getInstance().touchable = !viewingBoard;

		int timeToMove = tweening ? tweenTime : DEFAULT_MOVE_TIME;

		tweening = true;
		tweenTime = 0;

		gameObject.getPosition(true, curPos);
		gameObject.getRotation(true, curRot);
		
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
		
		if(moveTween != null)
		{
			moveTween.dispose();
			moveTween = null;
		}
		
		if(rotateTween != null)
		{
			rotateTween.dispose();
			rotateTween = null;
		}
		
		moveTween = new TweenPositionableVectorPosition().setTweenData(gameObject, curPos, toPos, timeToMove, EasingFunction.LINEAR);
		rotateTween = new TweenPositionableVectorRotation().setTweenData(gameObject, curRot, toRot, timeToMove, EasingFunction.LINEAR);
		
		moveTween.setCompletedCallback(this);
		
		moveTween.start();
		rotateTween.start();
	}

	@Override
	public void onCallback(BaseTween data)
	{
		tweening = false;
	}
}
