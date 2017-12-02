package zyx.game.components;

import zyx.opengl.models.implementations.BoneModel;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.loading.ZmfLoader;
import zyx.opengl.models.loading.bones.ZafLoader;
import zyx.utils.interfaces.IDrawable;

public class WorldObject extends GameObject implements IDrawable
{
	private final WorldModel model;

	public WorldObject()
	{
//		model = ZafLoader.loadFromZaf("outfile.zaf");
		model = ZafLoader.loadFromZaf("knight.zaf");
//		model = ZmfLoader.loadFromZmf("mesh.zmf");
	}

	@Override
	public void draw()
	{
		model.transform(position, rotation, scale);
		model.draw();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		super.update(timestamp, elapsedTime);
		
		model.update(timestamp, elapsedTime);
	}
	
	
}
