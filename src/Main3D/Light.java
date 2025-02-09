package Main3D;

import java.awt.Color;

import Util.Vec3;

public class Light {
	
	public float intensity;
	public Vec3 pos;
	public Vec3 direction;
	
	public Light(Vec3 p, Vec3 d, float i) {
		pos = p;
		direction = d;
		intensity = i;
	}
	
	public void MoveLightPos(Vec3 p) {
		pos = Vec3.Add(pos, p);
	}
	
	public Color LightDistanceToColor(Color baseColor, float cosTheta, float distLV) {
		
		float r = ((float) baseColor.getRed() * intensity * cosTheta / (distLV * distLV));
		if (r > 255) r = 255; else if (r < 0) r = 0;
		float g = ((float) baseColor.getGreen() * intensity * cosTheta / (distLV * distLV));
		if (g > 255) g = 255; else if (g < 0) g = 0;
		float b = ((float) baseColor.getBlue() * intensity * cosTheta / (distLV * distLV));		
		if (b > 255) b = 255; else if (b < 0) b = 0;
		
		
		return new Color((int) r, (int) g, (int) b);		
	}
}
