package zyx.game.components.world.furniture;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.GameObject;
import zyx.game.components.SimpleMesh;
import zyx.game.components.world.IItemHolder;
import zyx.game.components.world.interactable.InteractionAction;
import zyx.game.components.world.items.GameItem;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.utils.interfaces.IPhysbox;

public abstract class BaseFurnitureItem<V extends SimpleMesh> extends GameObject implements IItemHolder, IPhysbox
{

	protected static final ArrayList<InteractionAction> EMPTY_LIST = new ArrayList<>();
	protected static final InteractionAction[] EMPTY_ARRAY = new InteractionAction[0];

	protected ArrayList<GameItem> items;

	protected V view;

	public BaseFurnitureItem(boolean animated)
	{
		if (animated)
		{
			view = (V) new AnimatedMesh();
		}
		else
		{
			view = (V) new SimpleMesh();
		}

		addChild(view);

		items = new ArrayList<>();
	}

	public void load(FurnitureSetupVo vo)
	{
		String resource = getResource();
		view.load(resource);

		setPosition(false, vo.pos);
		lookAt(vo.look);
	}

	@Override
	public void hold(GameItem item)
	{
		items.add(item);
		onGotItem(item);
	}

	@Override
	public void removeItem(GameItem item)
	{
		items.remove(item);
		onLostItem(item);
	}

	@Override
	public boolean isInteractable()
	{
		return false;
	}

	protected void onGotItem(GameItem item)
	{
	}

	protected void onLostItem(GameItem item)
	{
	}

	protected abstract String getResource();

	@Override
	public PhysBox getPhysbox()
	{
		return view.getPhysbox();
	}

	@Override
	public Matrix4f getMatrix()
	{
		return view.getMatrix();
	}

	@Override
	public Matrix4f getBoneMatrix(int boneId)
	{
		return view.getBoneMatrix(boneId);
	}

}
