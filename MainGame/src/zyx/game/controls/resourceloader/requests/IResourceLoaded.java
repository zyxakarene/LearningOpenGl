package zyx.game.controls.resourceloader.requests;

import java.io.InputStream;

public interface IResourceLoaded<T extends InputStream>
{
	public void resourceLoaded(T data);
}
