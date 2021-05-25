package zyx.engine.resources.impl;

import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import java.util.ArrayList;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.ISubMeshVO;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.SkyboxModel;
import zyx.opengl.models.implementations.renderers.wrappers.SkyboxModelWrapper;
import zyx.opengl.models.loading.SkyboxLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class SkyboxResource extends BaseRequiredSubResource implements ISubResourceLoaded<AbstractTexture>, ITaskCompleted<LoadableWorldModelVO>
{

	private LoadableWorldModelVO loadedVo;
	private SkyboxModel model;
	private SkyboxLoadingTask task;

	public SkyboxResource(String path)
	{
		super(path);
	}

	@Override
	public SkyboxModelWrapper getContent()
	{
		return model.createWrapper();
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();
		
		if(model != null)
		{
			model.dispose();
			model = null;
		}
		
		if(loadedVo != null)
		{
			loadedVo.dispose();
			loadedVo = null;
		}
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		task = new SkyboxLoadingTask(this, data, path);
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
		
		task = new SkyboxLoadingTask(this, data, path);
		task.start();
	}
	
	@Override
	public void onTaskCompleted(LoadableWorldModelVO data)
	{
		loadedVo = data;
		
		for (int i = 0; i < loadedVo.subMeshCount; i++)
		{
			ISubMeshVO subMesh = loadedVo.getSubMeshVO(i);
			String diffuse = subMesh.getDiffuseTextureId();
			
			addResourceBatch(new SubResourceBatch(this, diffuse));
		}
		
		loadBatches();
	}
	
	@Override
	protected void onSubBatchesLoaded()
	{
		onContentLoaded(model);
	}

	@Override
	public void onLoaded(ArrayList<AbstractTexture> data)
	{
		for (int i = 0; i < loadedVo.subMeshCount; i++)
		{
			ISubMeshVO subMesh = loadedVo.getSubMeshVO(i);
			subMesh.setDiffuseTexture(data.get(i));
		}

		model = new SkyboxModel(loadedVo);
	}
	
	@Override
	public String getDebugIcon()
	{
		return "skybox.png";
	}
}