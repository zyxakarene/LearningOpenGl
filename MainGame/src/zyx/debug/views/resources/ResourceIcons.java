package zyx.debug.views.resources;

import zyx.engine.resources.impl.sheet.SpriteSheetResource;
import zyx.engine.resources.impl.sheet.SpriteSheetJsonResource;
import zyx.engine.resources.impl.meshes.MeshResource;
import zyx.engine.resources.impl.textures.SpecularTextureResource;
import zyx.engine.resources.impl.textures.NormalTextureResource;
import zyx.engine.resources.impl.textures.TextureResource;
import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import zyx.engine.resources.impl.*;

class ResourceIcons
{
	private static HashMap<Class, ImageIcon> ICON_MAP = new HashMap<>();

	static Icon createIcon(Resource value)
	{
		Class clazz = value.getClass();
		ImageIcon icon = ICON_MAP.get(clazz);
		
		if (icon == null)
		{
			String path = getIconPath(clazz);
			File file = new File(path);
			
			icon = new ImageIcon(file.getAbsolutePath());
			
			ICON_MAP.put(clazz, icon);
		}

		return icon;
	}

	private static String getIconPath(Class clazz)
	{
		if (clazz == MeshResource.class)
		{
			return "assets/debug/resource/mesh.png";
		}
		else if (clazz == SoundResource.class)
		{
			return "assets/debug/resource/sound.png";
		}
		else if (clazz == TextureResource.class)
		{
			return "assets/debug/resource/texture.png";
		}
		else if (clazz == NormalTextureResource.class)
		{
			return "assets/debug/resource/normal.png";
		}
		else if (clazz == SpecularTextureResource.class)
		{
			return "assets/debug/resource/specular.png";
		}
		else if (clazz == ParticleResource.class)
		{
			return "assets/debug/resource/particle.png";
		}
		else if (clazz == FontResource.class)
		{
			return "assets/debug/resource/font.png";
		}
		else if (clazz == JsonResource.class)
		{
			return "assets/debug/resource/json.png";
		}
		else if (clazz == SpriteSheetJsonResource.class)
		{
			return "assets/debug/resource/json.png";
		}
		else if (clazz == SpriteSheetResource.class)
		{
			return "assets/debug/resource/spritesheet.png";
		}
		else if (clazz == CubemapResource.class)
		{
			return "assets/debug/resource/cubemap.png";
		}
		else if (clazz == SkyboxResource.class)
		{
			return "assets/debug/resource/skybox.png";
		}
		
		return "assets/debug/resource/default.png";
	}

}
