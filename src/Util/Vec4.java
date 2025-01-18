package Util;

public class Vec4 {
	public Vec4() {
		x = 0.0f; y = 0.0f; z = 0.0f; w = 0.0f;
	}
	
	public Vec4(float xp, float yp, float zp, float wp) {
		x = xp; y = yp; z = zp; w = wp;
	}


	public static Vec4 Add(Vec4 a, Vec4 b) {
		return new Vec4(
			a.x + b.x,
			a.y + b.y, 
			a.z + b.z,
			a.w + b.w );
	}

	public static Vec4 Sub(Vec4 a, Vec4 b) {
		return new Vec4(
			a.x - b.x,
			a.y - b.y, 
			a.z - b.z,
			a.w - b.w );
	}

	public static Vec4 Mul(Vec4 a, Vec4 b) {
		return new Vec4(
			a.x * b.x,
			a.y * b.y, 
			a.z * b.z,
			a.w * b.w );
	}

	public static Vec4 Div(Vec4 a, Vec4 b) {
		return new Vec4(
			a.x / b.x,
			a.y / b.y, 
			a.z / b.z,
			a.w / b.w );
	}

	
	public static Vec4 Mul(Mat4 m, Vec4 v) {
		/*return new Vec4(
			v.x * m.c1.x + v.y * m.c1.y + v.z * m.c1.z + v.w * m.c1.w,
			v.x * m.c2.x + v.y * m.c2.y + v.z * m.c2.z + v.w * m.c2.w,
			v.x * m.c3.x + v.y * m.c3.y + v.z * m.c3.z + v.w * m.c3.w,
			v.x * m.c4.x + v.y * m.c4.y + v.z * m.c4.z + v.w * m.c4.w );*/

		return new Vec4(
			v.x * m.c1.x + v.y * m.c2.x + v.z * m.c3.x + v.w * m.c4.x,
			v.x * m.c1.y + v.y * m.c2.y + v.z * m.c3.y + v.w * m.c4.y,
			v.x * m.c1.z + v.y * m.c2.z + v.z * m.c3.z + v.w * m.c4.z,
			v.x * m.c1.w + v.y * m.c2.w + v.z * m.c3.w + v.w * m.c4.w );
	}
	
	public float x, y, z, w;
}
