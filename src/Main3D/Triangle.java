package Main3D;

import java.awt.Color;

import Util.Vec3;

public class Triangle {
	public Vec3 p1;
	public Vec3 p2;
	public Vec3 p3;
	public Color col;
	
	public Triangle(Vec3 p1p, Vec3 p2p, Vec3 p3p, Color cp) {
		p1 = p1p;
		p2 = p2p;
		p3 = p3p;
		col = cp;
	}
}
