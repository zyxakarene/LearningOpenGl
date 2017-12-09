package zyx.game.controls.models;

import zyx.opengl.models.implementations.WorldModel;

interface IModelLoaded
{
	void modelLoaded(String path, WorldModel model);
}
