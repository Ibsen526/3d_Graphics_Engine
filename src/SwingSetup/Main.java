package SwingSetup;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
		this.add(pWindow);
		this.pack();
		this.setLocationRelativeTo(this);
		this.setResizable(false);
		this.setVisible(true);
	
		//Hides the cursor
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		this.getContentPane().setCursor(blankCursor);
				
		// Math class tests:
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
		
		
		Vec3 tri1 = new Vec3(0,0,1);
		Vec3 tri2 = new Vec3(1,0,1);
		Vec3 tri3 = new Vec3(1,0,0);
		Vec3 v1 = Vec3.Sub(tri2, tri1);
		Vec3 v2 = Vec3.Sub(tri3, tri1);
		Vec3 normal = Math3D.Cross(v1, v2);	
		
		
		Vec3 i = Math3D.LinePlaneIntersection(new Vec3(1,0,0), new Vec3(1,0,0), new Vec3(-2,1,0), new Vec3(2,0,0));
	}

	public static void main(String[] args) {
		new Main();
	}
}
