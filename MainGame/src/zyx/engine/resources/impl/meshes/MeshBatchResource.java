package zyx.engine.resources.impl.meshes;

import java.util.ArrayList;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.loading.MeshLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.TaskScheduler;

public class MeshBatchResource extends BaseRequiredSubResource implements ISubResourceLoaded<AbstractTexture>, ITaskCompleted<LoadableWorldModelVO>
{

	private LoadableWorldModelVO loadedVo;
	private MeshBatchModel model;

	public MeshBatchResource(String path)
	{
		super(path);
	}

	@Override
	public MeshBatchModel getContent()
	{
		return model;
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();

		if (model != null)
		{
			model.dispose();
			model = null;
		}

		if (loadedVo != null)
		{
			loadedVo.dispose();
			loadedVo = null;
		}
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		MeshLoadingTask task = new MeshLoadingTask(this, data);
		TaskScheduler.getInstance().addEntry(task);
	}

	@Override
	public void onTaskCompleted(LoadableWorldModelVO data)
	{
		loadedVo = data;

		String diffuse = loadedVo.getDiffuseTextureId();
		String normal = loadedVo.getNormalTextureId();
//		String specular = loadedVo.getSpecularTextureId();
//		String normal = "normal.default_normal";
		String specular = "specular.mirror_specular";

		SubResourceBatch<AbstractTexture> textureBatch = new SubResourceBatch(this, diffuse, normal, specular);
		addResourceBatch(textureBatch);
	}

	@Override
	public void onLoaded(ArrayList<AbstractTexture> data)
	{
		AbstractTexture diffuse = data.get(0);
		AbstractTexture normal = data.get(1);
		AbstractTexture spec = data.get(2);

		loadedVo.setDiffuseTexture(diffuse);
		loadedVo.setNormalTexture(normal);
		loadedVo.setSpecularTexture(spec);
	}

	@Override
	protected void onSubBatchesLoaded()
	{
		model = new MeshBatchModel(loadedVo);
		onContentLoaded(model);
	}
}
