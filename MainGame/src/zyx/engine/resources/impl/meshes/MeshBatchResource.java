package zyx.engine.resources.impl.meshes;

import java.util.ArrayList;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.implementations.renderers.MeshBatchRenderer;
import zyx.opengl.models.loading.MeshLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class MeshBatchResource extends BaseRequiredSubResource implements ISubResourceLoaded<AbstractTexture>, ITaskCompleted<LoadableWorldModelVO>
{

	private LoadableWorldModelVO loadedVo;
	private MeshBatchModel model;
	private MeshLoadingTask task;

	public MeshBatchResource(String path)
	{
		super(path);
	}

	@Override
	public MeshBatchRenderer getContent()
	{
		return model.createRenderer();
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		task = new MeshLoadingTask(this, data, path);
		task.start();
	}

	@Override
	protected void onResourceReloaded(ResourceDataInputStream data)
	{
		cancelSubBatches();
		
		if (task != null)
		{
			task.cancel();
			task = null;
		}
		
		task = new MeshLoadingTask(this, data, path);
		task.start();
	}
	
	@Override
	public void onTaskCompleted(LoadableWorldModelVO data)
	{
		loadedVo = data;
		loadedVo.toMeshBatch();

		String diffuse = loadedVo.getDiffuseTextureId();
		String normal = loadedVo.getNormalTextureId();
		String specular = loadedVo.getSpecularTextureId();

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
		if (model == null)
		{
			model = new MeshBatchModel(loadedVo);
			onContentLoaded(model);
		}
		else
		{
			model.refresh(loadedVo);
		}
		
		loadedVo.clean();
	}
	
		@Override
	protected void onDispose()
	{
		super.onDispose();

		if (task != null)
		{
			task.cancel();
			task = null;
		}
		
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
	public String getDebugIcon()
	{
		return "mesh.png";
	}
}
