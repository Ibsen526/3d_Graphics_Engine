package Util;

public class Mat4 {
	public Mat4() { //identity matrix
		c1 = new Vec4(1, 0, 0, 0);
		c2 = new Vec4(0, 1, 0, 0);
		c3 = new Vec4(0, 0, 1, 0);
		c4 = new Vec4(0, 0, 0, 1);
	}

	public Mat4(Vec4 c1p, Vec4 c2p, Vec4 c3p, Vec4 c4p) { 
		c1 = c1p;
		c2 = c2p;
		c3 = c3p;
		c4 = c4p;
	}	


	public static Mat4 Mul(Mat4 a, Mat4 b) {
		
		return new Mat4(
			new Vec4(MatRowMul(new Vec4(a.c1.x, a.c2.x, a.c3.x, a.c4.x), b.c1), 
					MatRowMul(new Vec4(a.c1.y, a.c2.y, a.c3.y, a.c4.y), b.c1), 
					MatRowMul(new Vec4(a.c1.z, a.c2.z, a.c3.z, a.c4.z), b.c1), 
					MatRowMul(new Vec4(a.c1.w, a.c2.w, a.c3.w, a.c4.w), b.c1)),
			new Vec4(MatRowMul(new Vec4(a.c1.x, a.c2.x, a.c3.x, a.c4.x), b.c2), 
					MatRowMul(new Vec4(a.c1.y, a.c2.y, a.c3.y, a.c4.y), b.c2), 
					MatRowMul(new Vec4(a.c1.z, a.c2.z, a.c3.z, a.c4.z), b.c2), 
					MatRowMul(new Vec4(a.c1.w, a.c2.w, a.c3.w, a.c4.w), b.c2)),
			new Vec4(MatRowMul(new Vec4(a.c1.x, a.c2.x, a.c3.x, a.c4.x), b.c3), 
					MatRowMul(new Vec4(a.c1.y, a.c2.y, a.c3.y, a.c4.y), b.c3), 
					MatRowMul(new Vec4(a.c1.z, a.c2.z, a.c3.z, a.c4.z), b.c3), 
					MatRowMul(new Vec4(a.c1.w, a.c2.w, a.c3.w, a.c4.w), b.c3)),
			new Vec4(MatRowMul(new Vec4(a.c1.x, a.c2.x, a.c3.x, a.c4.x), b.c4), 
					MatRowMul(new Vec4(a.c1.y, a.c2.y, a.c3.y, a.c4.y), b.c4), 
					MatRowMul(new Vec4(a.c1.z, a.c2.z, a.c3.z, a.c4.z), b.c4), 
					MatRowMul(new Vec4(a.c1.w, a.c2.w, a.c3.w, a.c4.w), b.c4)) 
			);
	}
	
	private static float MatRowMul(Vec4 r, Vec4 c) {
		return r.x * c.x + r.y * c.y + r.z * c.z + r.w * c.w;
	}
	
	public Vec4 c1, c2, c3, c4; //column 1 - 4
}
