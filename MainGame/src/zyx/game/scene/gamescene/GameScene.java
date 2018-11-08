package zyx.game.scene.gamescene;

import java.util.ArrayList;
import java.util.Arrays;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.OnTeaPotClicked;
import zyx.engine.scene.Scene;
import zyx.game.components.AnimatedMesh;
import zyx.game.components.SimpleMesh;
import zyx.game.controls.input.KeyboardData;
import zyx.opengl.camera.ViewFrustum;
import zyx.opengl.models.SharedWorldModelTransformation;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.FloatMath;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;

public class GameScene extends Scene
{

	private ArrayList<SimpleMesh> children;

	public GameScene()
	{
		children = new ArrayList<>();
	}

	@Override
	protected void onInitialize()
	{
		AnimatedMesh knight = new AnimatedMesh();
		knight.load("mesh.worm.worm");
		knight.setAnimation("wiggle");
		world.addChild(knight);

		addPickedObject(knight, new OnTeaPotClicked());

		children.add(knight);

		AnimatedMesh parent = knight;
		for (int i = 0; i < 0; i++)
		{

			AnimatedMesh child = new AnimatedMesh();
			child.load("mesh.worm.worm");
			child.setAnimation("wiggle");
			parent.addChildAsAttachment(child, "Bone002");

			parent = child;

			children.add(child);

			addPickedObject(child, new OnTeaPotClicked());
		}
		
		DebugPoint.addToScene(0,0,0, 0);
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		super.onUpdate(timestamp, elapsedTime);

		if (KeyboardData.data.isDown(Keyboard.KEY_SPACE))
		{
			Matrix4f out = new Matrix4f();
			
			Matrix4f proj = SharedShaderObjects.SHARED_PROJECTION_TRANSFORM;
			Matrix4f view = SharedShaderObjects.SHARED_VIEW_TRANSFORM;
			
			Matrix4f transposedView = Matrix4f.transpose(view, null);
			
			Matrix4f.mul(proj, view, out);
			
			ViewFrustum fr = new ViewFrustum();
			ViewFrustum.extractPlanesOLD(out, fr.left, fr.right, fr.top, fr.bottom, fr.near, fr.far);

			Print.out(fr);
			
//			AddDebugAt(fr.left);
//			AddDebugAt(fr.right);
//			AddDebugAt(fr.top);
//			AddDebugAt(fr.bottom);
//			AddDebugAt(fr.near);
//			AddDebugAt(fr.far);
			
			boolean isoutside = outside(fr.left) || outside(fr.right) || outside(fr.top) || outside(fr.bottom) || outside(fr.near) || outside(fr.far);
			if (isoutside)
			{
				Print.out("Thing outside!!!");
			}
		}
		

	}
	
	private boolean outside(float[] plane)
	{
		Vector3f pos = new Vector3f();
		pos.x = 0;
		pos.y = 0;
		pos.z = 0;
		
		Vector3f planePos = new Vector3f();
		planePos.x = plane[0];
		planePos.y = plane[1];
		planePos.z = plane[2];
		planePos.normalise();
		Print.out(planePos);
		
		float dist = Vector3f.dot(pos, planePos) + plane[3] + 0;
		if(dist < 0)
		{
			// sphere culled
			return true;
		} 

		return false;
	}

	@Override
	protected void onDispose()
	{
		for (SimpleMesh simpleMesh : children)
		{
			simpleMesh.dispose();
		}

		children.clear();
		children = null;
	}

	private void AddDebugAt(float[] plane)
	{
//		NormalizePlane(plane);
		float d = plane[3] * 5;
		
		Vector3f pos = new Vector3f();
		pos.x = plane[0] *  d;
		pos.y = plane[1] *  d;
		pos.z = plane[2] *  d;
		
		Vector3f cameraPos = camera.getPosition(false, null);

		Vector4f p = new Vector4f();
		p.x = pos.x;
		p.y = pos.y;
		p.z = pos.z;
		
		Matrix4f transposedView = Matrix4f.transpose(SharedShaderObjects.SHARED_VIEW_TRANSFORM, null);

		Matrix4f.transform(transposedView, p, p);
		
		p.x += cameraPos.x;
		p.y += cameraPos.y;
		p.z += cameraPos.z;
		
		Print.out(Arrays.toString(plane));
		plane[0] = p.x;
		plane[1] = p.y;
		plane[2] = p.z;
		Print.out(Arrays.toString(plane));
		
		DebugPoint.addToScene(p, 20);
	}
	
	private void NormalizePlane(float[] plane)
	{
		float a = plane[0];
		float b = plane[1];
		float c = plane[2];
		float d = plane[3];
		
		float mag = FloatMath.sqrt(a * a + b * b + c * c);
		plane[0] = a / mag;
		plane[1] = b / mag;
		plane[2] = c / mag;
		plane[3] = d / mag;
	}
}
