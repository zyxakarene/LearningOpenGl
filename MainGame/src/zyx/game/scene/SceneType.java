package zyx.game.scene;

import zyx.engine.scene.Scene;
import zyx.game.scene.dev.TestScene;
import zyx.game.scene.dev.ParticleScene;
import zyx.game.scene.dev.showcase.*;
import zyx.game.scene.game.DinerScene;
import zyx.game.scene.menu.MenuScene;

public enum SceneType
{
	
	MENU(MenuScene.class),
	GAME(DinerScene.class),
	
	SHOWCASE_NESTED_OBJECTS(NestedObjectsScene.class),
	SHOWCASE_GUI_INTERACTION(GuiInteractionScene.class),
	SHOWCASE_MESH_CLICK(MeshClickScene.class),
	SHOWCASE_PARTICLES(ParticleShowScene.class),
	SHOWCASE_SKELETON(SkeletonScene.class),
	SHOWCASE_LIGHT(LightScene.class),
	SHOWCASE_SHADOWS(ShadowScene.class),
	SHOWCASE_CUBEMAPS(CubemapScene.class),
	SHOWCASE_DEFERRED(DeferredScene.class),
	SHOWCASE_DRAWING(DrawScene.class),
	SHOWCASE_LOADING(LoadingScene.class),
	
	TEST(TestScene.class),
	PARTICLE(ParticleScene.class),
	EMPTY(EmptyScene.class);
	
	public final Class<? extends Scene> sceneClass;
	
	private SceneType(Class<? extends Scene> sceneClass)
	{
		this.sceneClass = sceneClass;
	}
	
	public Scene createScene()
	{
		try
		{
			return sceneClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			throw new RuntimeException("Could not create new scene:" + sceneClass.getName());
		}
	}
	
}
