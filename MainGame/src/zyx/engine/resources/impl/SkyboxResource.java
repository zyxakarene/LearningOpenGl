package zyx.engine.resources.impl;

import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import java.util.ArrayList;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.SkyboxModel;
import zyx.opengl.models.loading.MeshLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;
import zyx.utils.tasks.TaskScheduler;

public class SkyboxResource extends BaseRequiredSubResource implements ISubResourceLoaded<AbstractTexture>, ITaskCompleted<LoadableWorldModelVO>
{

	private LoadableWorldModelVO loadedVo;
	private SkyboxModel model;

	public SkyboxResource(String path)
	{
		super(path);
	}

	@Override
	public SkyboxModel getContent()
	{
		return model;
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
		MeshLoadingTask task = new MeshLoadingTask(this, data);
		TaskScheduler.getInstance().addEntry(task);
	}
	
	@Override
	public void onTaskCompleted(LoadableWorldModelVO data)
	{
		loadedVo = data;
		
		String diffuse = loadedVo.getDiffuseTextureId();
		addResourceBatch(new SubResourceBatch(this, diffuse));
	}
	
	@Override
	protected void onSubBatchesLoaded()
	{
		onContentLoaded(model);
	}

	@Override
	public void onLoaded(ArrayList<AbstractTexture> data)
	{
		loadedVo.setDiffuseTexture(data.get(0));
		model = new SkyboxModel(loadedVo);
	}
	
	@Override
	public String getResourceIcon()
	{
		return "skybox.png";
	}
}