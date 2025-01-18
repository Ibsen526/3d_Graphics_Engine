package Util;

public class Vec3 {
	public Vec3() {
		x = 0.0f; y = 0.0f; z = 0.0f;
	}
	
	public Vec3(float xp, float yp, float zp) {
		x = xp; y = yp; z = zp;
	}
	

	public static Vec3 Add(Vec3 a, Vec3 b) {
		return new Vec3(
			a.x + b.x,
			a.y + b.y, 
			a.z + b.z );
	}

	public static Vec3 Sub(Vec3 a, Vec3 b) {
		return new Vec3(
			a.x - b.x,
			a.y - b.y, 
			a.z - b.z );
	}

	public static Vec3 Mul(Vec3 a, Vec3 b) {
		return new Vec3(
			a.x * b.x,
			a.y * b.y, 
			a.z * b.z );
	}
	
	public static Vec3 Mul(float a, Vec3 b) {
		return new Vec3(
			a * b.x,
			a * b.y, 
			a * b.z );
	}

	public static Vec3 Div(Vec3 a, Vec3 b) {
		return new Vec3(
			a.x / b.x,
			a.y / b.y, 
			a.z / b.z );
	}
	
	public float x, y, z;
}
