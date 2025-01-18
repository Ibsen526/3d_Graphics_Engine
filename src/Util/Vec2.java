package Util;

public class Vec2 {
	public Vec2() {
		x = 0.0f; y = 0.0f; 
	}
	
	public Vec2(float xp, float yp) {
		x = xp; y = yp; 
	}
	

	public static Vec2 Add(Vec2 a, Vec2 b) {
		return new Vec2(
			a.x + b.x,
			a.y + b.y );
	}

	public static Vec2 Sub(Vec2 a, Vec2 b) {
		return new Vec2(
			a.x - b.x,
			a.y - b.y );
	}

	public static Vec2 Mul(Vec2 a, Vec2 b) {
		return new Vec2(
			a.x * b.x,
			a.y * b.y );
	}

	public static Vec2 Div(Vec2 a, Vec2 b) {
		return new Vec2(
			a.x / b.x,
			a.y / b.y );
	}
	
	public float x, y;
}
