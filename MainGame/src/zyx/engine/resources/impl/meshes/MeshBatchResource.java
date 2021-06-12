package zyx.engine.resources.impl.meshes;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.ISubMeshVO;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.implementations.renderers.wrappers.MeshBatchModelWrapper;
import zyx.opengl.models.loading.MeshBatchLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class MeshBatchResource extends BaseRequiredSubResource implements ISubResourceLoaded<AbstractTexture>, ITaskCompleted<LoadableWorldModelVO>
{
	private static final ArrayList<String> LIST_HELPER = new ArrayList<>();

	private LoadableWorldModelVO loadedVo;
	private MeshBatchModel model;
	private MeshBatchLoadingTask task;
	private HashMap<Integer, Integer> textureListIndexToLoadedIndex;

	public MeshBatchResource(String path)
	{
		super(path);
		textureListIndexToLoadedIndex = new HashMap<>();
	}

	@Override
	public MeshBatchModelWrapper getContent()
	{
		return model.createWrapper();
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
	public void onLoaded(ArrayList<AbstractTexture> data)
	{
		for (int i = 0; i < loadedVo.subMeshCount; i++)
		{
			int offset = i * 3;
			
			int diffuseIndex = offset + 0;
			int normalIndex = offset + 1;
			int specIndex = offset + 2;
			
			int diffuseDataIndex = textureListIndexToLoadedIndex.get(diffuseIndex);
			int normalDataIndex = textureListIndexToLoadedIndex.get(normalIndex);
			int specDataIndex = textureListIndexToLoadedIndex.get(specIndex);
			
			AbstractTexture diffuse = data.get(diffuseDataIndex);
			AbstractTexture normal = data.get(normalDataIndex);
			AbstractTexture spec = data.get(specDataIndex);

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
