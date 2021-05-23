package zyx.engine.resources.impl.meshes;

import java.util.ArrayList;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.ISubMeshVO;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.implementations.renderers.MeshBatchRenderer;
import zyx.opengl.models.loading.MeshBatchLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class MeshBatchResource extends BaseRequiredSubResource implements ISubResourceLoaded<AbstractTexture>, ITaskCompleted<LoadableWorldModelVO>
{

	private LoadableWorldModelVO loadedVo;
	private MeshBatchModel model;
	private MeshBatchLoadingTask task;

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
		task = new MeshBatchLoadingTask(this, data, path);
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
		
		task = new MeshBatchLoadingTask(this, data, path);
		task.start();
	}
	
	@Override
	public void onTaskCompleted(LoadableWorldModelVO data)
	{
		loadedVo = data;

		for (int i = 0; i < loadedVo.subMeshCount; i++)
		{
			ISubMeshVO subMesh = loadedVo.getSubMeshVO(i);
			String[] textures = subMesh.GetTextureIds();
			
			SubResourceBatch<AbstractTexture> textureBatch = new SubResourceBatch(this, textures);
			addResourceBatch(textureBatch);
		}
	}

	@Override
	public void onLoaded(ArrayList<AbstractTexture> data)
	{
		for (int i = 0; i < loadedVo.subMeshCount; i++)
		{
			int offset = i * 3;
			AbstractTexture diffuse = data.get(offset + 0);
			AbstractTexture normal = data.get(offset + 1);
			AbstractTexture spec = data.get(offset + 2);
			
			ISubMeshVO subMesh = loadedVo.getSubMeshVO(i);
			subMesh.setTextures(diffuse, normal, spec);
		}
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
