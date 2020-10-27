package zyx.game.components.screen.json;

import java.util.HashMap;
import org.json.simple.JSONObject;
import zyx.engine.components.screen.base.ContainerDock;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.docks.DockType;
import zyx.engine.components.screen.base.docks.GameDock;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.IResourceReloaded;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.JsonResource;
import zyx.engine.resources.impl.Resource;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IUpdateable;

public abstract class JsonSprite extends DisplayObjectContainer implements IResourceReady<JsonResource>, IUpdateable, IResourceReloaded<JsonResource>
{

	private static final String[] EMPTY_DEPENDENCIES = new String[0];

	private JsonResource resource;
	private HashMap<String, DisplayObject> jsonChildren;

	private boolean initialized;
	protected boolean loaded;

	private Resource[] resourceDependencies;
	private int dependenciesLoaded;
	private IResourceReady dependencyLoaded;
	
	public JsonSprite()
	{
		onPreInitialize();

		JsonSpriteAnimator.getInstance().addJsonSprite(this);
		
		dependencyLoaded = (IResourceReady) this::onDependencyLoaded;

		jsonChildren = new HashMap<>();
	}

	@Override
	protected void onSetParent(DisplayObjectContainer parent)
	{
		if (!loaded && resource == null)
		{
			if (parent != null)
			{
				load();
			}
		}
	}

	protected void onPreInitialize()
	{
	}

	protected final void load()
	{
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
		DockType dockType = getDockingType();
		
		JSONObject content = resource.getContent();
		JsonSpriteParser.getInstance().createSpriteFrom(this, content, dockType);

		onComponentsCreated();

		loaded = true;
	}
	
	private DockType getDockingType()
	{
		DisplayObjectContainer item = this;
		while (item != null)
		{
			if (item instanceof ContainerDock)
			{
				return ((ContainerDock)item).type;
			}
			
			item = item.getParent();
		}
		
		return null;
	}
	
	@Override
	public void onResourceReloaded(JsonResource resource)
	{
		if (loaded)
		{
			removeChildren(true);
			onAllResourcesLoaded();
		}
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

		JsonSpriteAnimator.getInstance().removeJsonSprite(this);
		
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
