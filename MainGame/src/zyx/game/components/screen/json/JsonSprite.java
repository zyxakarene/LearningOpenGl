package zyx.game.components.screen.json;

import java.util.HashMap;
import org.json.simple.JSONObject;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.JsonResource;
import zyx.engine.resources.impl.Resource;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IUpdateable;

public abstract class JsonSprite extends DisplayObjectContainer implements IResourceReady<JsonResource>, IUpdateable
{

	private static final String[] EMPTY_DEPENDENCIES = new String[0];

	private JsonResource resource;
	private HashMap<String, DisplayObject> jsonChildren;

	private boolean initialized;
	private boolean loaded;

	private Resource[] resourceDependencies;
	private int dependenciesLoaded;
	private IResourceReady dependencyLoaded;

	public JsonSprite()
	{
		super();

		dependencyLoaded = (IResourceReady) (Resource dependency) -> 
		{
					onDependencyLoaded(dependency);
		};

		jsonChildren = new HashMap<>();
		String res = getResource();
		if (res != null)
		{
			resource = (JsonResource) ResourceManager.getInstance().getResource(res);
			resource.registerAndLoad(this);
		}
	}

	public abstract String getResource();

	protected String[] getDependencies()
	{
		return EMPTY_DEPENDENCIES;
	}

	public <T extends DisplayObject> T getComponentByName(String name)
	{
		DisplayObject component = jsonChildren.get(name);
		return (T) component;
	}

	@Override
	public final void onResourceReady(JsonResource resource)
	{
		String[] dependencies = getDependencies();

		if (dependencies.length == dependenciesLoaded)
		{
			onAllResourcesLoaded();
		}
		else
		{
			resourceDependencies = new Resource[dependencies.length];
			for (int i = 0; i < dependencies.length; i++)
			{
				String dependencyId = dependencies[i];
				Resource dependencyResource = ResourceManager.getInstance().getResource(dependencyId);
				resourceDependencies[i] = dependencyResource;

				dependencyResource.registerAndLoad(dependencyLoaded);
			}
		}
	}

	private void onDependencyLoaded(Resource dependency)
	{
		dependenciesLoaded++;

		if (dependenciesLoaded == resourceDependencies.length)
		{
			onAllResourcesLoaded();
		}
	}

	private void onAllResourcesLoaded()
	{
		JSONObject content = resource.getContent();
		JsonSpriteParser.getInstance().createSpriteFrom(this, content);

		onComponentsCreated();

		loaded = true;
	}

	protected void onComponentsCreated()
	{
	}

	protected void onInitialize()
	{
	}

	@Override
	protected void onChildAdded(DisplayObject child)
	{
		if (jsonChildren.containsKey(child.name))
		{
			Print.out("[Warning] JsonChild with the name", child.name, "is already added to", name);
		}

		jsonChildren.put(child.name, child);
	}

	@Override
	protected void onChildRemoved(DisplayObject child)
	{
		jsonChildren.remove(child.name, child);
	}

	@Override
	public final void update(long timestamp, int elapsedTime)
	{
		if (!initialized && loaded)
		{
			onInitialize();
			initialized = true;
		}

		onUpdate(timestamp, elapsedTime);
	}

	protected void onUpdate(long timestamp, int elapsedTime)
	{
	}

	@Override
	public final void dispose()
	{
		super.dispose();

		jsonChildren.clear();
		jsonChildren = null;

		if (resource != null)
		{
			resource.unregister(this);
			resource = null;
		}

		if (resourceDependencies != null)
		{
			for (Resource dependency : resourceDependencies)
			{
				dependency.unregister(dependencyLoaded);
			}

			resourceDependencies = null;
		}

		onDispose();
	}

	protected void onDispose()
	{
	}
}
