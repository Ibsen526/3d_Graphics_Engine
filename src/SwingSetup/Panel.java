package SwingSetup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import Util.Math3D;
import Util.Vec3;
import Util.Mat4;
import Util.Vec4;
import Main3D.Mesh;
import Main3D.Transformation;
import Main3D.Triangle;
import Main3D.Camera;
import Main3D.Light;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int P_W, P_H;
	
	private long elapsedTime;
	private float xD;
	private float yD;
	private float zD;
	
	private Timer gameLoop;
	
	private Camera cam;
	private Mesh cube;
	private Mesh ape;
	
	Light light;
	
	Panel (int PW, int PH) {
		//Panel = new JPanel(new GridLayout(1,7));
		this.setPreferredSize(new Dimension(PW, PH));
		P_W = PW; P_H = PH;
		this.setDoubleBuffered(true);
		//this.setBounds(0, 0, PW, PH);

		cam = new Camera(3.0f, 0.0f, 4.0f, this, P_W, P_H);
		cube = new Mesh("C:\\Users\\Marti\\Desktop\\Ranzig\\Programme_nichtSchule\\Java_Projekte\\3D_Graphics_API\\assets\\cube3.obj");
		ape = new Mesh("C:\\Users\\Marti\\Desktop\\Ranzig\\Programme_nichtSchule\\Java_Projekte\\3D_Graphics_API\\assets\\ape.obj");
		light = new Light(new Vec3(-1.0f, 0.0f, 1.0f), new Vec3(0.0f, 0.0f, -1.0f), 1.1f);

		
		gameLoop = new Timer();
		gameLoop.schedule(new TimerTask() {
			@Override
            public void run() {
                elapsedTime++;

                xD = (float) Math.sin((double) elapsedTime / 20.0);
                yD = (float) Math.cos((double) elapsedTime / 20.0);
                zD = (float) Math.cos((double) elapsedTime / 20.0);
                //light.MoveLightPos(new Vec3(0, yD, 0));
                repaint();
                
                //light.pos = cam.getCamPos();
                //light.direction = cam.getCamFront();
            }
		}, 1, 10);	
		
	}
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(new Color(0, 0, 0));
		g2D.fillRect(0, 0, P_W, P_H);
		
		cam.ViewMatrix();		
		
		//Create model matrices
		Mat4 cubeMat = new Mat4();
		cubeMat = Transformation.Scale(cubeMat, new Vec3(0.5f, 0.5f, 0.5f));
		cubeMat = Transformation.RotateX(cubeMat, 45.0f * xD);
		cubeMat = Transformation.RotateY(cubeMat, 30.0f);
		cubeMat = Transformation.RotateZ(cubeMat, 65.0f);
		cubeMat = Transformation.Translate(cubeMat, new Vec3(3.0f, 0.0f, 0.0f));
		Mat4 apeMat = new Mat4();
		apeMat = Transformation.Translate(apeMat, new Vec3(1.0f, 0.0f, 0.0f));
		
		
		ArrayList<Triangle> trianglesToRaster = new ArrayList<Triangle>();
		
		trianglesToRaster.addAll(cube.DrawMesh(g2D, cubeMat, P_W, P_H, cam, true, light, new Color(255, 100, 0)));
		trianglesToRaster.addAll(ape.DrawMesh(g2D, apeMat, P_W, P_H, cam, true, light, new Color(0, 255, 100)));

		//System.out.println(cam.getCamPos().x+" "+cam.getCamPos().y+" "+cam.getCamPos().z);

		//Now sort the triangles (only a hack, because depth buffer would be better)
		ArrayList<Triangle> sortedTriangles = SortTriangles(trianglesToRaster);
		
		//Draw them to the screen 
		DrawTriangle(g2D, sortedTriangles, false);
	}	
	
	private void DrawTriangle(Graphics2D g2D, ArrayList<Triangle> t, Boolean lines) {
		for (Triangle tri : t) {
			Polygon p = new Polygon();
			p.addPoint((int) tri.p1.x, (int) tri.p1.y);
			p.addPoint((int) tri.p2.x, (int) tri.p2.y);
			p.addPoint((int) tri.p3.x, (int) tri.p3.y);
			
			g2D.setColor(tri.col);
			g2D.fillPolygon(p);
			if(lines) {
				g2D.setColor(new Color(0, 0, 0));
				g2D.drawPolygon(p);
			}
		}
	}
	
	private ArrayList<Triangle> SortTriangles(ArrayList<Triangle> t) {
		
		for (int i = 0; i < t.size(); i++) { 		
			float triAvg = (t.get(i).p1.z + t.get(i).p2.z + t.get(i).p3.z) / 3.0f;
			
			for (int j = 0; j < t.size(); j++) {
				float triSwapAvg = (t.get(j).p1.z + t.get(j).p2.z + t.get(j).p3.z) / 3.0f;
				
				if(triAvg > triSwapAvg) {
					
					//Swap the triangles
					Triangle temp = t.get(i);
					t.set(i, t.get(j));
					t.set(j, temp);
					triAvg = triSwapAvg;
				}
			}
		}
		
		return t;		
	}
}
