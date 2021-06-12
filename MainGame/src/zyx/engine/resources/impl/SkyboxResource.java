package zyx.engine.resources.impl;

import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import java.util.ArrayList;
import java.util.HashMap;
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
	private static final ArrayList<String> LIST_HELPER = new ArrayList<>();

	private LoadableWorldModelVO loadedVo;
	private SkyboxModel model;
	private SkyboxLoadingTask task;
	private HashMap<Integer, Integer> textureListIndexToLoadedIndex;

	public SkyboxResource(String path)
	{
		super(path);
		textureListIndexToLoadedIndex = new HashMap<>();
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
		
		int listIndex = 0;
		for (int i = 0; i < loadedVo.subMeshCount; i++)
		{
			ISubMeshVO subMesh = loadedVo.getSubMeshVO(i);
			String[] textureIds = subMesh.GetTextureIds();
			
			int textureLength = textureIds.length;
			for (int j = 0; j < textureLength; j++)
			{
				String textureId = textureIds[j];
				int textureIndex = LIST_HELPER.indexOf(textureId);
				
				if (textureIndex == -1)
				{
					LIST_HELPER.add(textureId);
					textureIndex = LIST_HELPER.size() - 1;
				}
				
				textureListIndexToLoadedIndex.put(listIndex, textureIndex);
				listIndex++;
			}
		}
		
		String[] textureArray = new String[LIST_HELPER.size()];
		LIST_HELPER.toArray(textureArray);
		LIST_HELPER.clear();
		
		addResourceBatch(new SubResourceBatch(this, textureArray));
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
			int offset = i * 1;
			
			int diffuseIndex = offset + 0;
			int diffuseDataIndex = textureListIndexToLoadedIndex.get(diffuseIndex);
			
			AbstractTexture diffuse = data.get(diffuseDataIndex);
			
			ISubMeshVO subMesh = loadedVo.getSubMeshVO(i);
			subMesh.setDiffuseTexture(diffuse);
		}

		model = new SkyboxModel(loadedVo);
	}
	
	@Override
	public String getDebugIcon()
	{
		return "skybox.png";
	}
}