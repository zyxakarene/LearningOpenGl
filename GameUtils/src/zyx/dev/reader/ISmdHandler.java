package zyx.dev.reader;

public interface ISmdHandler
{
	public void handleLine(String line);
	public Object getResult();
	public void setData(Object data);
}
