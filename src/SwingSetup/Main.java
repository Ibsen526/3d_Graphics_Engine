package SwingSetup;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;

import Util.Mat4;
import Util.Math3D;
import Util.Vec3;
import Util.Vec4;

import javax.swing.JFrame;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;

	private final int WINDOW_W = 1280;
	private final int WINDOW_H = 720;
	
	Panel pWindow;
	
	Main() {
		pWindow = new Panel(WINDOW_W, WINDOW_H);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setPreferredSize(new Dimension(WINDOW_W, WINDOW_H));
		this.add(pWindow);
		this.pack();
		this.setLocationRelativeTo(this);
		this.setResizable(false);
		this.setVisible(true);
		
	
		
		/*Vec3 v1 = new Vec3(1, -5, 6);
		Vec3 v2 = new Vec3(9, 4, 2);
		Vec3 v3 = new Vec3();

		//v3 = Math3D.AddVec3(v1, v2);
		//v3 = Math3D.Cross(v1, v2);
		v3 = Vec3.Mul(v1, v2);
		Vec3 camPos = new Vec3(-3,1,5);
		Vec3 camFront = new Vec3(0,0,-1);
		Mat4 view = Math3D.LookAt(camPos, Vec3.Add(camPos, camFront), new Vec3(0,1,0));
		
		System.out.println("DotProdukt "+Math3D.Dot(v1, v2));
		System.out.println("Result: " + v3.x + " " + v3.y + " " + v3.z);*/
		//Mat4 p = Math3D.Perspective(90, 16/9, 0.01f, 1000.0f);

		
		Mat4 view = new Mat4();
		Mat4 proj = new Mat4();
		Vec3 camPos = new Vec3(0.4f, -0.1f, -2.0f);
		Vec3 camFront = new Vec3(-0.1f, 0.22f, -0.9f);
		view = Math3D.LookAt(camPos, Vec3.Add(camFront, camPos), new Vec3(0.0f, 1.0f, 0.0f));
		proj = Math3D.Perspective(90.0f, 16.0f / 9.0f, 0.01f, 1000.0f);

		Vec4 p = new Vec4(0.5f, 0.5f, 0.5f, 1.0f);
		
		Vec4 p2 = Vec4.Mul(view, p);
		Vec4 p3 = Vec4.Mul(proj, p2);
		
		Mat4 mvp = Mat4.Mul(view, proj);
		//Both view and proj matrix have diffrent values!
		
		
		Vec3 tri1 = new Vec3(0,0,1);
		Vec3 tri2 = new Vec3(1,0,1);
		Vec3 tri3 = new Vec3(1,0,0);
		Vec3 v1 = Vec3.Sub(tri2, tri1);
		Vec3 v2 = Vec3.Sub(tri3, tri1);
		Vec3 normal = Math3D.Cross(v1, v2);
		
		
		
		Vec3 i = Math3D.LinePlaneIntersection(new Vec3(1,0,0), new Vec3(1,0,0), new Vec3(-2,1,0), new Vec3(2,0,0));

		System.out.println("Ende");
	}

	public static void main(String[] args) {
		new Main();
	}
}
