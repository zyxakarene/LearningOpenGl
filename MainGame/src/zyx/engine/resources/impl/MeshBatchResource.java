package zyx.engine.resources.impl;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.loading.ZafLoader;
import zyx.opengl.textures.AbstractTexture;

public class MeshBatchResource extends BaseTextureRequiredResource
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
	void onDispose()
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
		loadedVo = ZafLoader.loadFromZaf(data);

		String diffuse = loadedVo.getDiffuseTextureId();
		String normal = loadedVo.getNormalTextureId();
//		String specular = loadedVo.getSpecularTextureId();
//		String normal = "normal.default_normal";
		String specular = "specular.mirror_specular";
		loadTextures(diffuse, normal, specular);
	}

	@Override
	protected void onTexturesLoaded(AbstractTexture[] texture)
	{
		loadedVo.setDiffuseTexture(texture[0]);
		loadedVo.setNormalTexture(texture[1]);
		loadedVo.setSpecularTexture(texture[2]);
		
		model = new MeshBatchModel(loadedVo);
		
		onContentLoaded(model);
	}
}