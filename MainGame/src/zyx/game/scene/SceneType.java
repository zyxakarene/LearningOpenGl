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
	TEST,
	PARTICLE,
	EMPTY,
	
	SHOWCASE_NESTED_OBJECTS,
	SHOWCASE_GUI_INTERACTION,
	SHOWCASE_MESH_CLICK,
	SHOWCASE_PARTICLES,
	SHOWCASE_SKELETON,
	SHOWCASE_LIGHT,
	SHOWCASE_SHADOWS,
	SHOWCASE_CUBEMAPS,
	SHOWCASE_DEFERRED,
	SHOWCASE_DRAWING,
	SHOWCASE_LOADING;
	
	
	public Scene createScene()
	{
		switch (this)
		{
			case MENU: return new MenuScene();
			case GAME: return new DinerScene();
			
			case TEST: return new TestScene();
			case PARTICLE: return new ParticleScene();
			case EMPTY: return new EmptyScene();
			
			case SHOWCASE_NESTED_OBJECTS: return new NestedObjectsScene();
			case SHOWCASE_GUI_INTERACTION: return new GuiInteractionScene();
			case SHOWCASE_MESH_CLICK: return new MeshClickScene();
			case SHOWCASE_PARTICLES: return new ParticleShowScene();
			case SHOWCASE_SKELETON: return new SkeletonScene();
			case SHOWCASE_LIGHT: return new LightScene();
			case SHOWCASE_SHADOWS: return new ShadowScene();
			case SHOWCASE_CUBEMAPS: return new CubemapScene();
			case SHOWCASE_DEFERRED: return new DeferredScene();
			case SHOWCASE_DRAWING: return new DrawScene();
			case SHOWCASE_LOADING: return new LoadingScene();
		}
		
		throw new RuntimeException("No scene mapped for " + this);
	}
	
}
