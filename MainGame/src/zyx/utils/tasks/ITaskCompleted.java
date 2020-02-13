package zyx.utils.tasks;

public interface ITaskCompleted<T>
{
	void onTaskCompleted(T data);
}
