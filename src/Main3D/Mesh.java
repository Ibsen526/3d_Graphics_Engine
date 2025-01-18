package Main3D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.awt.BufferCapabilities;

import LoadFiles.LoadObj;
import LoadFiles.LoadObjReturn;
import LoadFiles.Vec2I;
import Util.Vec3;
import Util.Vec4;
import Util.Mat4;
import Util.Math3D;
import Util.Vec2;

public class Mesh {
	//public ArrayList<Vec3> vertices = new ArrayList<Vec3>();
	public ArrayList<Vec3> vertices = new ArrayList<Vec3>();
	public ArrayList<Integer> lineIndices = new ArrayList<Integer>();
	public ArrayList<Integer> triIndices = new ArrayList<Integer>();
	
	public Mesh(String path)
	{
		LoadObjReturn r1 = LoadObj.File(path);
		vertices = r1.vertices;
		triIndices = r1.indices;
		
		System.out.println("t");
	}
	
	public static int CoordsToScreen(float xy, int screen) {		
		return (int)(((-xy + 1.0f) / 2.0f) * (float)screen);
	}
	
	private void PerPixelTriangle(Graphics2D g2D, Vec2I p1, Vec2I p2, Vec2I p3) {
		//draw with pixels
		//g2D.drawOval(5, 5, 100, 100);
		//g2D.drawLine(0, 0, 0, 0);
		
		//g2D.drawRect(p1.x, p1.y, 1, 1);
		//g2D.drawRect(p2.x, p2.y, 1, 1);
		//g2D.drawRect(p3.x, p3.y, 1, 1);
		/*g2D.setRenderingHint(
		        RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);*/
		
		int l = p1.x;
		if (p2.x < l) l = p2.x;
		if (p3.x < l) l = p3.x;
		int r = p1.x;
		if (p2.x > r) r = p2.x;
		if (p3.x > r) r = p3.x;
		int t = p1.y;
		if (p2.y < t) t = p2.y;
		if (p3.y < t) t = p3.y;
		int b = p1.y;
		if (p2.y > b) b = p2.y;
		if (p3.y > b) b = p3.y;
		
		for(int x = l; x < r; x++) {
			for(int y = t; y < b; y++) {
				
				float xf = (float)x + 0.5f, yf = (float)y + 0.5f;
				if(x >= 0 && x <= 1280 && y >= 0 && y <= 720) {
					
					if((xf - p1.x) * (p2.y - p1.y) - (yf - p1.y) * (p2.x - p1.x) > 0
						&& (xf - p2.x) * (p3.y - p2.y) - (yf - p2.y) * (p3.x - p2.x) > 0
						&& (xf - p3.x) * (p1.y - p3.y) - (yf - p3.y) * (p1.x - p3.x) > 0) {
							g2D.setColor(new Color(255,100,0));
							g2D.fillRect(x, y, 1, 1);
							//g2D.drawLine(x, y, x, y);
							//bufferImage.setRGB(x, y, 2);
					}
					else if((xf - p1.x) * (p2.y - p1.y) - (yf - p1.y) * (p2.x - p1.x) > -10
						&& (xf - p2.x) * (p3.y - p2.y) - (yf - p2.y) * (p3.x - p2.x) > 0
						&& (xf - p3.x) * (p1.y - p3.y) - (yf - p3.y) * (p1.x - p3.x) > 0) {
							g2D.setColor(new Color(0,0,0));
							g2D.fillRect(x, y, 1, 1);
					}
					else if((xf - p1.x) * (p2.y - p1.y) - (yf - p1.y) * (p2.x - p1.x) > 0
						&& (xf - p2.x) * (p3.y - p2.y) - (yf - p2.y) * (p3.x - p2.x) > -10
						&& (xf - p3.x) * (p1.y - p3.y) - (yf - p3.y) * (p1.x - p3.x) > 0) {
							g2D.setColor(new Color(0,0,0));
							g2D.fillRect(x, y, 1, 1);
					}
					else if((xf - p1.x) * (p2.y - p1.y) - (yf - p1.y) * (p2.x - p1.x) > 0
						&& (xf - p2.x) * (p3.y - p2.y) - (yf - p2.y) * (p3.x - p2.x) > 0
						&& (xf - p3.x) * (p1.y - p3.y) - (yf - p3.y) * (p1.x - p3.x) > -10) {
							g2D.setColor(new Color(0,0,0));
							g2D.fillRect(x, y, 1, 1);
					}
					
				}
			}
		}
	}
	
	private Boolean BackfaceCulling(Vec3 normal, Vec3 cam) {
		if(Math3D.Dot(normal, cam) > 0.0f)
			return false;
		
		return true;
	}

	public ArrayList<Triangle> DrawMesh(Graphics2D g2D, Mat4 model, int P_W, int P_H, Camera cam, Boolean culling, Light light, Color baseColor) {
		if (triIndices.size() % 3 == 0) {
			ArrayList<Triangle> triangles = new ArrayList<Triangle>();
			
			for (int i = 0; i < triIndices.size(); i+=3) {
			
				ArrayList<Vec3> viewPoints = new ArrayList<Vec3>();
				ArrayList<Vec3> cullingPoints = new ArrayList<Vec3>();
				
				//Transforms the points into view space for clipping
				for (int j = 0; j < 3; j++) {
					Vec4 origPoint = new Vec4(
						vertices.get(triIndices.get(i + j)).x, 
						vertices.get(triIndices.get(i + j)).y, 
						vertices.get(triIndices.get(i + j)).z,
						1.0f );	
					
					//Transform to world space
					Vec4 worldPoint = Vec4.Mul(model, origPoint);

					cullingPoints.add(new Vec3(worldPoint.x, worldPoint.y, worldPoint.z)); 

					Vec4 viewP = Vec4.Mul(cam.view, worldPoint);
					viewPoints.add(new Vec3(viewP.x, viewP.y, viewP.z));
					
					//Calculate the lighting for the triangle in world space
					/*Vec3 dTL = Vec3.Sub(new Vec3(worldPoint.x, worldPoint.y, worldPoint.z), cam.getCamPos());
					//float distToLight = 255.0f / (float) Math.sqrt(Math.pow(dTL.x, 2) + Math.pow(dTL.y, 2) + Math.pow(dTL.z, 2));
					float distToLight = 255.0f / (float) Math.sqrt(Math.pow(dTL.x, 2) + Math.pow(dTL.y, 2) + Math.pow(dTL.z, 2));
					
					if (distToLight > 255.0f) distToLight = 255.0f;
					else if (distToLight < 0.0f) distToLight = 0.0f;
					//System.out.println("dtl "+distToLight);
					
					distToLightAvg += distToLight;*/
				}
				//distToLightAvg = distToLightAvg / 3.0f;

				//Culling: Normals that are similar to the camPos will display, the others are discarded
				Vec3 edge1 = Vec3.Sub(cullingPoints.get(1), cullingPoints.get(0));
				Vec3 edge2 = Vec3.Sub(cullingPoints.get(2), cullingPoints.get(0));
				Vec3 normal = Math3D.Normalize(Math3D.Cross(edge1, edge2));
				
				
				/*Vec4 cullView = Vec4.Mul(cam.view, new Vec4(cullingPoints.get(0).x, cullingPoints.get(0).y, cullingPoints.get(0).z, 1.0f));
				Vec3 eyeDirection = Vec3.Sub(new Vec3(0.0f, 0.0f, 0.0f), new Vec3(cullView.x, cullView.y, cullView.z));

				Vec4 lightDir = Vec4.Mul(cam.view, new Vec4(light.pos.x, light.pos.y, light.pos.z, 1.0f));
				Vec3 lightDirection = new Vec3(lightDir.x + eyeDirection.x, lightDir.y + eyeDirection.y, lightDir.z + eyeDirection.z);

				Vec4 tempN = Vec4.Mul(Mat4.Mul(model, cam.view), new Vec4(normal.x, normal.y, normal.z, 0.0f));
				Vec3 lNormal = new Vec3(tempN.x, tempN.y, tempN.z);*/
				
				Vec3 reversedLight = Math3D.Normalize(Vec3.Sub(light.pos, light.direction));
				
				//Vec3 lightToVertex = Math3D.Normalize(Vec3.Sub(light.pos, cullingPoints.get(0)));
				Vec3 lightToVertex = Math3D.Normalize(Vec3.Sub(light.pos, cullingPoints.get(0)));
				float distLV = (float) Math.sqrt(Math.pow(lightToVertex.x, 2) + Math.pow(lightToVertex.y, 2) + Math.pow(lightToVertex.z, 2));
				
				float distToLight = Math3D.Dot(reversedLight, normal);
				
				//System.out.println("dtl "+distToLight);

				if (distToLight > 1.0f) distToLight = 1.0f;
				else if (distToLight < 0.05f) distToLight = 0.05f;
				
				
				if ((culling && BackfaceCulling(normal, Vec3.Sub(cullingPoints.get(0), cam.getCamPos())))
						|| !culling) {
				
					//Clips the view-points with the near plane
					ArrayList<Vec3> clipReturn = Math3D.ClipTriangle(new Vec3(0.0f,0.0f,0.01f), new Vec3(0.0f,0.0f,1.0f), 
							viewPoints.get(0), viewPoints.get(1), viewPoints.get(2), cam.getCamPos());
					
					
					//Draws the clipped vertices onto the screen, now with projection 
					if (clipReturn.size() % 3 == 0) {
						for (int k = 0; k < clipReturn.size(); k+=3) {
	
							//ArrayList<Vec2I> screenPoints = new ArrayList<Vec2I>();
							ArrayList<Vec3> triPoints = new ArrayList<Vec3>();
							
							for (int j = 0; j < 3; j++) {					
			
								Vec4 clipViewP = new Vec4(
									clipReturn.get(k + j).x, 
									clipReturn.get(k + j).y, 
									clipReturn.get(k + j).z, 
									1.0f );			
								
								//Mat4 vp = Mat4.Mul(proj, view);
								Vec4 pointProjection = Vec4.Mul(cam.proj, clipViewP);	
			
								triPoints.add(new Vec3(Mesh.CoordsToScreen(pointProjection.x / pointProjection.w, P_W),
										      Mesh.CoordsToScreen(pointProjection.y / pointProjection.w, P_H),
										      pointProjection.w));
								
								/*screenPoints.add(new Vec2I(Mesh.CoordsToScreen(pointProjection.x / pointProjection.w, P_W), 
										Mesh.CoordsToScreen(pointProjection.y / pointProjection.w, P_H)));*/

							}
							
							//int colorR = baseColor.getRed() * (int) distToLightAvg / 255;
							//int colorG = baseColor.getGreen() * (int) distToLightAvg / 255;
							//int colorB = baseColor.getBlue() * (int) distToLightAvg / 255;
							
							Color meshColor = light.LightDistanceToColor(baseColor, distToLight, distLV);
							
							triangles.add(new Triangle(triPoints.get(0), triPoints.get(1), triPoints.get(2), meshColor));
							
							//DrawTriangle(g2D, screenPoints.get(0), screenPoints.get(1), screenPoints.get(2));
							
						}
					}			
				}
			}		
			
			return triangles;
		}	
		
		return null;
	}
}
