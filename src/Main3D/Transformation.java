package Main3D;

import Util.Mat4;
import Util.Vec3;
import Util.Vec4;

public class Transformation {

	public static Mat4 Translate(Mat4 lm, Vec3 t) {
		Mat4 nm = new Mat4(
			new Vec4(1.0f, 0.0f, 0.0f, 0.0f), 
			new Vec4(0.0f, 1.0f, 0.0f, 0.0f),
			new Vec4(0.0f, 0.0f, 1.0f, 0.0f),
			new Vec4(-t.x, t.y, t.z, 1.0f)
		);	
		
		return Mat4.Mul(nm, lm);
	}

	public static Mat4 Scale(Mat4 lm, Vec3 s) {
		Mat4 nm =  new Mat4(
			new Vec4(1.0f * s.x, 0.0f, 0.0f, 0.0f), 
			new Vec4(0.0f, 1.0f * s.y, 0.0f, 0.0f), 
			new Vec4(0.0f, 0.0f, 1.0f * s.z, 0.0f),
			new Vec4(0.0f, 0.0f, 0.0f, 1.0f)
		);			
		
		return Mat4.Mul(nm, lm);	
	}

	public static Mat4 RotateX(Mat4 lm, float a) { //Rotate at the x axis
		Mat4 nm =  new Mat4(
				new Vec4(1.0f, 0.0f, 0.0f, 0.0f), 
				new Vec4(0.0f, (float) Math.cos(Math.toRadians(a)), (float) Math.sin(Math.toRadians(a)), 0.0f), 
				new Vec4(0.0f, (float) -Math.sin(Math.toRadians(a)), (float) Math.cos(Math.toRadians(a)), 0.0f),
				new Vec4(0.0f, 0.0f, 0.0f, 1.0f)
		);
		
		return Mat4.Mul(nm, lm);
	}

	public static Mat4 RotateY(Mat4 lm, float a) { //Rotate at the y axis
		Mat4 nm =  new Mat4(
				new Vec4((float) Math.cos(Math.toRadians(a)), 0.0f, (float) Math.sin(Math.toRadians(a)), 0.0f), 
				new Vec4(0.0f, 1.0f, 0.0f, 0.0f), 
				new Vec4((float) -Math.sin(Math.toRadians(a)), 0.0f, (float) Math.cos(Math.toRadians(a)), 0.0f),
				new Vec4(0.0f, 0.0f, 0.0f, 1.0f)
		);
		
		return Mat4.Mul(nm, lm);
	}

	public static Mat4 RotateZ(Mat4 lm, float a) { //Rotate at the z axis
		Mat4 nm =  new Mat4(
				new Vec4((float) Math.cos(Math.toRadians(a)), (float) Math.sin(Math.toRadians(a)), 0.0f, 0.0f), 
				new Vec4((float) -Math.sin(Math.toRadians(a)), (float) Math.cos(Math.toRadians(a)), 0.0f, 0.0f),
				new Vec4(0.0f, 0.0f, 1.0f, 0.0f),
				new Vec4(0.0f, 0.0f, 0.0f, 1.0f)
		);
		
		return Mat4.Mul(nm, lm);
	}
}
