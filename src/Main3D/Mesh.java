package Main3D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import LoadFiles.LoadObj;
import LoadFiles.Vec2I;
import Util.Vec3;
import Util.Vec4;
import Util.Mat4;
import Util.Math3D;

public class Mesh {
	public ArrayList<Vec3> Vertices = new ArrayList<Vec3>();
	public ArrayList<Integer> Indices = new ArrayList<Integer>();
	
	public Mesh(String path)
	{
		LoadObj.File(path, this);
	}
	
	public static int CoordsToScreen(float xy, int screen) {		
		return (int)(((-xy + 1.0f) / 2.0f) * (float)screen);
	}
	
	private Boolean BackfaceCulling(Vec3 normal, Vec3 cam) {
		if(Math3D.Dot(normal, cam) > 0.0f)
			return false;
		
		return true;
	}

	public ArrayList<Triangle> DrawMesh(Mat4 model, int P_W, int P_H, Camera cam, Boolean culling, Light light, Color baseColor) {
		if (Indices.size() % 3 == 0) {
			ArrayList<Triangle> triangles = new ArrayList<Triangle>();
			
			for (int i = 0; i < Indices.size(); i+=3) {
			
				ArrayList<Vec3> viewPoints = new ArrayList<Vec3>();
				ArrayList<Vec3> cullingPoints = new ArrayList<Vec3>();
				
				//Transforms the points into view space for clipping
				for (int j = 0; j < 3; j++) {
					Vec4 origPoint = new Vec4(
						Vertices.get(Indices.get(i + j)).x, 
						Vertices.get(Indices.get(i + j)).y, 
						Vertices.get(Indices.get(i + j)).z,
						1.0f );	
					
					//Transform to world space
					Vec4 worldPoint = Vec4.Mul(model, origPoint);

					cullingPoints.add(new Vec3(worldPoint.x, worldPoint.y, worldPoint.z)); 

					Vec4 viewP = Vec4.Mul(cam.view, worldPoint);
					viewPoints.add(new Vec3(viewP.x, viewP.y, viewP.z));
				}
				

				//Culling: Normals that are similar to the camPos will display, the others are discarded
				Vec3 edge1 = Vec3.Sub(cullingPoints.get(1), cullingPoints.get(0));
				Vec3 edge2 = Vec3.Sub(cullingPoints.get(2), cullingPoints.get(0));
				Vec3 normal = Math3D.Normalize(Math3D.Cross(edge1, edge2));
				
				Vec3 reversedLight = Math3D.Normalize(Vec3.Sub(light.pos, light.direction));
				
				Vec3 lightToVertex = Math3D.Normalize(Vec3.Sub(light.pos, cullingPoints.get(0)));
				float distLV = (float) Math.sqrt(Math.pow(lightToVertex.x, 2) + Math.pow(lightToVertex.y, 2) + Math.pow(lightToVertex.z, 2));
				
				float distToLight = Math3D.Dot(reversedLight, normal);
				
				if (distToLight > 1.0f) distToLight = 1.0f;
				else if (distToLight < 0.05f) distToLight = 0.05f;
				
				
				if ((culling && BackfaceCulling(normal, Vec3.Sub(cullingPoints.get(0), cam.getCamPos())))
						|| !culling) {
				
					//Clips the view-points with the near plane
					ArrayList<Vec3> clipReturn = Math3D.ClipTriangle(new Vec3(0.0f,0.0f,0.01f), new Vec3(0.0f,0.0f,1.0f), 
							viewPoints.get(0), viewPoints.get(1), viewPoints.get(2), cam.getCamPos());
										
					//Draws the clipped Vertices onto the screen, now with projection 
					if (clipReturn.size() % 3 == 0) {
						for (int k = 0; k < clipReturn.size(); k+=3) {	
							
							ArrayList<Vec3> triPoints = new ArrayList<Vec3>();
							
							for (int j = 0; j < 3; j++) {					
			
								Vec4 clipViewP = new Vec4(
									clipReturn.get(k + j).x, 
									clipReturn.get(k + j).y, 
									clipReturn.get(k + j).z, 
									1.0f );			
								
								Vec4 pointProjection = Vec4.Mul(cam.proj, clipViewP);	
			
								triPoints.add(new Vec3(Mesh.CoordsToScreen(pointProjection.x / pointProjection.w, P_W),
										      Mesh.CoordsToScreen(pointProjection.y / pointProjection.w, P_H),
										      pointProjection.w));								
							}
							
							Color meshColor = light.LightDistanceToColor(baseColor, distToLight, distLV);
							
							triangles.add(new Triangle(triPoints.get(0), triPoints.get(1), triPoints.get(2), meshColor));
							
						}
					}			
				}
			}		
			
			return triangles;
		}	
		
		return null;
	}
}
