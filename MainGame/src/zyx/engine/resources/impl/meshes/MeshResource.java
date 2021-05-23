package zyx.engine.resources.impl.meshes;

import java.util.ArrayList;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.ISubMeshVO;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.renderers.WorldModelRenderer;
import zyx.opengl.models.loading.MeshLoadingTask;
import zyx.opengl.textures.AbstractTexture;
import zyx.utils.tasks.ITaskCompleted;

public class MeshResource extends BaseRequiredSubResource implements ITaskCompleted<LoadableWorldModelVO>
{

	private LoadableWorldModelVO loadedVo;
	private WorldModel model;

	private ISubResourceLoaded<AbstractTexture> textureLoaded;
	private ISubResourceLoaded<Skeleton> skeletonLoaded;
	private MeshLoadingTask loadingTask;

	public MeshResource(String path)
	{
		super(path);

		textureLoaded = (ISubResourceLoaded<AbstractTexture>) this::onTextureLoaded;
		skeletonLoaded = (ISubResourceLoaded<Skeleton>) this::onSkeletonLoaded;
	}

	@Override
	public WorldModelRenderer getContent()
	{
		return model.createRenderer();
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

		for (int i = 0; i < loadedVo.subMeshCount; i++)
		{
			ISubMeshVO subMesh = loadedVo.getSubMeshVO(i);
			String[] textureIds = subMesh.GetTextureIds();
			
			SubResourceBatch<AbstractTexture> textureBatch = new SubResourceBatch(textureLoaded, textureIds);
			addResourceBatch(textureBatch);
		}

		String skeleton = loadedVo.getSkeletonId();

		SubResourceBatch<Skeleton> skeletonBatch = new SubResourceBatch(skeletonLoaded, skeleton);
		addResourceBatch(skeletonBatch);
	}

	private void onTextureLoaded(ArrayList<AbstractTexture> data)
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
