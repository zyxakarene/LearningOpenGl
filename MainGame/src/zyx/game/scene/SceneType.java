package zyx.game.scene;

import zyx.engine.scene.Scene;
import zyx.game.scene.dev.TestScene;
import zyx.game.scene.dev.ParticleScene;
import zyx.game.scene.dev.showcase.*;
import zyx.game.scene.game.DinerScene;
import zyx.game.scene.menu.MenuScene;

public enum SceneType
{
	
	MENU,
	GAME,
	
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
	
	public Scene createScene()
	{
		switch (this)
		{
			case MENU: return new MenuScene();
			case GAME: return new DinerScene();
			
			case TEST: return new TestScene();
			case PARTICLE: return new ParticleScene();
			case EMPTY: return new EmptyScene();
		}
		
		throw new RuntimeException("No scene mapped for " + this);
	}
	
}
