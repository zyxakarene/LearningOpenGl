package zyx.synchronizer;

import java.util.LinkedList;

public abstract class BaseExchange<E, R>
{

	private final Object LOCK = new Object();

	private final LinkedList<E> ENTRIES;
	private final LinkedList<R> REPLIES;

	private final LinkedList<R> HELPER_LIST;

	public BaseExchange()
	{
		ENTRIES = new LinkedList<>();
		REPLIES = new LinkedList<>();
		
		HELPER_LIST = new LinkedList<>();
	}
	
	boolean hasEntries()
	{
		synchronized (LOCK)
		{
			return ENTRIES.isEmpty() == false;
		}
	}

	void addEntry(E entry)
	{
		synchronized (LOCK)
		{
			boolean shouldAdd = shouldAddEntry(entry);
			
			if (shouldAdd)
			{
				ENTRIES.add(entry);
				LOCK.notify();
			}
		}
	}

	void removeEntry(E entry)
	{
		synchronized (LOCK)
		{
			ENTRIES.remove(entry);
			
			onRemoveEntry(entry);
		}
	}

	void removeReply(R reply)
	{
		synchronized (LOCK)
		{
			REPLIES.remove(reply);
			
			onRemoveReply(reply);
		}
	}

	void addReply(R reply)
	{
		synchronized (LOCK)
		{
			REPLIES.add(reply);
		}
	}

	E getEntry()
	{
		synchronized (LOCK)
		{
			if (ENTRIES.isEmpty())
			{
				return null;
			}
			else
			{
				return ENTRIES.removeLast();
			}
		}
	}

	R getReply()
	{
		synchronized (LOCK)
		{
			return REPLIES.removeLast();
		}
	}

	void sleep()
	{
		synchronized (LOCK)
		{
			try
			{
				LOCK.wait();
			}
			catch (InterruptedException ex)
			{
			}
		}
	}

	void sendReplies()
	{
		synchronized (LOCK)
		{
			R reply;
			while (!REPLIES.isEmpty())
			{
				reply = REPLIES.removeFirst();
				HELPER_LIST.add(reply);
			}
		}

		while (!HELPER_LIST.isEmpty())
		{
			R reply = HELPER_LIST.remove();
			
			onReplyCompleted(reply);
		}
	}

	void dispose()
	{
		synchronized (LOCK)
		{
			REPLIES.clear();
			ENTRIES.clear();
		}
	}

	protected abstract void onReplyCompleted(R reply);
	
	protected boolean shouldAddEntry(E entry)
	{
		return true;
	}

	protected void onRemoveEntry(E entry)
	{
	}

	protected void onRemoveReply(R entry)
	{
	}
}
