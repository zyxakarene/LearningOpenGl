package zyx.engine.resources.impl.meshes;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.ISubMeshVO;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.renderers.wrappers.WorldModelWrapper;
import zyx.opengl.models.loading.MeshLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class MeshResource extends BaseRequiredSubResource implements ITaskCompleted<LoadableWorldModelVO>
{
	private static final ArrayList<String> LIST_HELPER = new ArrayList<>();
	
	private LoadableWorldModelVO loadedVo;
	private WorldModel model;

	private ISubResourceLoaded<AbstractTexture> textureLoaded;
	private ISubResourceLoaded<Skeleton> skeletonLoaded;
	private MeshLoadingTask loadingTask;
	private HashMap<Integer, Integer> textureListIndexToLoadedIndex;


	public MeshResource(String path)
	{
		super(path);

		textureLoaded = (ISubResourceLoaded<AbstractTexture>) this::onTextureLoaded;
		skeletonLoaded = (ISubResourceLoaded<Skeleton>) this::onSkeletonLoaded;
		
		textureListIndexToLoadedIndex = new HashMap<>();
	}

	@Override
	public WorldModelWrapper getContent()
	{
		return model.createWrapper();
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		loadingTask = new MeshLoadingTask(this, data, path);
		loadingTask.start();
	}
	
	@Override
	public void onTaskCompleted(LoadableWorldModelVO data)
	{
		loadedVo = data;

		int listIndex = 0;
		
		//TODO: Extraxt this mess
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
		
		String skeleton = loadedVo.getSkeletonId();
		
		addResourceBatch(new SubResourceBatch(textureLoaded, textureArray));
		addResourceBatch(new SubResourceBatch(skeletonLoaded, skeleton));
		
		loadBatches();
	}

	private void onTextureLoaded(ArrayList<AbstractTexture> data)
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

	private void onSkeletonLoaded(ArrayList<Skeleton> data)
	{
		Skeleton skeleton = data.get(0);
		loadedVo.setSkeleton(skeleton);
	}

	@Override
	protected void onResourceReloaded(ResourceDataInputStream data)
	{
		if (model != null)
		{
			model.ready = false;
		}
		
		cancelSubBatches();
		
		if (loadingTask != null)
		{
			loadingTask.cancel();
			loadingTask = null;
		}
		
		if (loadedVo != null)
		{
			loadedVo.dispose();
			loadedVo = null;
		}
		
		loadingTask = new MeshLoadingTask(this, data, path);
		loadingTask.start();
	}
	
	@Override
	protected void onSubBatchesLoaded()
	{
		if (model == null)
		{
			model = new WorldModel(loadedVo);
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

		if (loadingTask != null)
		{
			loadingTask.cancel();
			loadingTask = null;
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
