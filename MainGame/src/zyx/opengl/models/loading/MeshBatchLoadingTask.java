package zyx.opengl.models.loading;

import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.utils.tasks.ITaskCompleted;

public class MeshBatchLoadingTask extends AbstractMeshLoadingTask
{
	public MeshBatchLoadingTask(ITaskCompleted<LoadableWorldModelVO> taskDoneCallback, ResourceDataInputStream data, String id)
	{
		super(taskDoneCallback, data, id);
	}

	@Override
	protected void postProcessVo(LoadableWorldModelVO vo)
	{
		vo.asMeshBatch();
	}
}
