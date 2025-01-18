package Util;

import java.util.ArrayList;

public class Math3D {

	
	public static Vec3 Normalize(Vec3 a) {
		float mag = (float) Math.sqrt(Math.pow(a.x, 2) + Math.pow(a.y, 2) + Math.pow(a.z, 2));
		return new Vec3(a.x / mag, a.y / mag, a.z / mag);
	}

	public static Vec3 Cross(Vec3 a, Vec3 b) {
		return new Vec3(
			a.y * b.z - a.z * b.y,
			a.z * b.x - a.x * b.z,
			a.x * b.y - a.y * b.x
		);
	}

	public static float Dot(Vec3 a, Vec3 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public static Mat4 LookAt(Vec3 camPos, Vec3 camFront, Vec3 worldUp) {
		//We create our own camera coordinate system with an up, right and front vector

		Vec3 zAxis = Normalize(Vec3.Sub(camPos, camFront));
		Vec3 xAxis = Normalize(Cross(worldUp, zAxis));
		Vec3 yAxis = Normalize(Cross(zAxis, xAxis));
		
		//Takes and identity matrix by default and then adds the position in one and the 
		//rotation in the other. Those two are then multiplied and returned

		Mat4 translation = new Mat4( //Aligns the points movement with the new camera coordinate system
			new Vec4(xAxis.x, yAxis.x, zAxis.x, 0.0f), //rechts
			new Vec4(xAxis.y, yAxis.y, zAxis.y, 0.0f), //oben
			new Vec4(xAxis.z, yAxis.z, zAxis.z, 0.0f), //vorn
			new Vec4(0.0f, 0.0f, 0.0f, 1.0f));

		Mat4 rotation = new Mat4( //Aligns the points rotation with the new camera coordinate system
			new Vec4(1.0f, 0.0f, 0.0f, 0.0f),
			new Vec4(0.0f, 1.0f, 0.0f, 0.0f),
			new Vec4(0.0f, 0.0f, 1.0f, 0.0f),
			new Vec4(-Dot(xAxis, camPos), -Dot(yAxis, camPos), -Dot(zAxis, camPos), 1.0f)); //CamPos gets projected onto the normalized new axis
				
			//(-camPos.x, -camPos.y, -camPos.z, 1.0f) not right

		return Mat4.Mul(rotation, translation);
		
	}
	
	public static Mat4 Perspective(float fov, float aspect, float near, float far) {
		Mat4 m = new Mat4();
		
		float radFov = (float) Math.toRadians(fov);
		float tanHalfFov = (float)Math.tan(radFov / 2);

		m.c1.x = 1 / (aspect * tanHalfFov);
		m.c2.y = 1 / (tanHalfFov);
		m.c3.z = -(far + near) / (far - near);
		m.c4.z = -(2 * far * near) / (far - near);
		//m.c3.z = far / (far - near);
		//m.c4.z = (-far * near) / (far - near);
		m.c3.w = 1;
		m.c4.w = 0;
	    return m;
	}
	
	public static Vec3 LinePlaneIntersection(Vec3 planePoint, Vec3 planeNormal, Vec3 lineStart, Vec3 lineEnd) {
				
		planeNormal = Normalize(planeNormal);
		float planeDist = Dot(planeNormal, planePoint);
		float startDist = Dot(lineStart, planeNormal);
		float endDist = Dot(lineEnd, planeNormal);
		float t = (planeDist - startDist) / (endDist - startDist);
		Vec3 lineStartToEnd = Vec3.Sub(lineEnd, lineStart);
		Vec3 lineToIntersect = Vec3.Mul(t, lineStartToEnd);
		return Vec3.Add(lineStart, lineToIntersect);		
	}
	
	public static ArrayList<Vec3> ClipTriangle(Vec3 planePoint, Vec3 planeNormal, Vec3 triP1, Vec3 triP2, Vec3 triP3, Vec3 camPos) {
		
		planeNormal = Normalize(planeNormal);
		
		ArrayList<Vec3> inside = new ArrayList<Vec3>();
		ArrayList<Vec3> outside = new ArrayList<Vec3>();
		ArrayList<Vec3> returnPoints = new ArrayList<Vec3>();
		
		if((planeNormal.x * triP1.x + planeNormal.y * triP1.y + planeNormal.z * triP1.z - Dot(planeNormal, planePoint)) >= 0) 
			{ inside.add(triP1); }
		else { outside.add(triP1); }
		
		if((planeNormal.x * triP2.x + planeNormal.y * triP2.y + planeNormal.z * triP2.z - Dot(planeNormal, planePoint)) >= 0) 
			{ inside.add(triP2); }
		else { outside.add(triP2); }
		
		if((planeNormal.x * triP3.x + planeNormal.y * triP3.y + planeNormal.z * triP3.z - Dot(planeNormal, planePoint)) >= 0) 
			{ inside.add(triP3); }
		else { outside.add(triP3); }
		
		
		//Check how many points are on the inside or outside of the plane and proceed accordingly
		if(inside.size() == 0) { //All points outside of the plane
			return returnPoints;
		}
		else if(inside.size() == 3) { //All points inside of the plane
			returnPoints.add(triP1);
			returnPoints.add(triP2);
			returnPoints.add(triP3);
			
		}
		else if(inside.size() == 1 && outside.size() == 2) { //One point is inside, so one new, smaller triangle is formed

			returnPoints.add(inside.get(0));
			returnPoints.add(LinePlaneIntersection(planePoint, planeNormal, 
					inside.get(0), outside.get(0)));
			returnPoints.add(LinePlaneIntersection(planePoint, planeNormal, 
					inside.get(0), outside.get(1)));
			
		}
		else if(inside.size() == 2 && outside.size() == 1) { //Two points inside, so two new triangles are formed

			returnPoints.add(inside.get(0));
			returnPoints.add(inside.get(1));
			returnPoints.add(LinePlaneIntersection(planePoint, planeNormal, 
					inside.get(0), outside.get(0)));

			returnPoints.add(inside.get(1));
			returnPoints.add(returnPoints.get(2));
			returnPoints.add(LinePlaneIntersection(planePoint, planeNormal, 
					inside.get(1), outside.get(0)));	
						
		}
		
		return returnPoints;
	}
}
