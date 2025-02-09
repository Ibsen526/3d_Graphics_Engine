package Main3D;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import SwingSetup.Panel;
import Util.Mat4;
import Util.Math3D;
import Util.Vec3;

public class Camera {

	public Mat4 view;
	public Mat4 proj;
	
	private float moveSpeed;
	private float sensitivity;
	
	private float yaw;
	private float pitch;
	
	private Vec3 camPos;
	public Vec3 getCamPos() { return camPos; }
	private Vec3 camFront;
	public Vec3 getCamFront() { return camFront; }
	private Vec3 worldUp;
		
	private KeyListener key;
	private MouseMotionListener mouseMotion;
	
	public Camera(float xDisp, float yDisp, float zDisp, Panel panel, int P_W, int P_H) {

		view = new Mat4();
		proj = Math3D.Perspective(90.0f, 16.0f / 9.0f, 0.01f, 10.0f);
		
		moveSpeed = 0.1f;
		sensitivity = 60.0f;
		yaw = -180.0f; //-90
		pitch = 0.0f;
		
		camPos = new Vec3(-xDisp, yDisp, zDisp);
		camFront = new Vec3(0.0f, 0.0f, 1.0f); //-1.0f
		worldUp = new Vec3(0.0f, 1.0f, 0.0f);

		key = new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == 'w') {
					camPos = Vec3.Sub(camPos, Vec3.Mul(moveSpeed, camFront));
					panel.repaint();
				}
				if(e.getKeyChar() == 'a') {
					camPos = Vec3.Add(camPos, Vec3.Mul(moveSpeed, Math3D.Normalize(Math3D.Cross(camFront, worldUp))));
					panel.repaint();
				}
				if(e.getKeyChar() == 's') {
					camPos = Vec3.Add(camPos, Vec3.Mul(moveSpeed, camFront));
					panel.repaint();
				}
				if(e.getKeyChar() == 'd') {
					camPos = Vec3.Sub(camPos, Vec3.Mul(moveSpeed, Math3D.Normalize(Math3D.Cross(camFront, worldUp))));
					panel.repaint();
				}
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					camPos = Vec3.Add(camPos, Vec3.Mul(moveSpeed, worldUp));
				}
				if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
					camPos = Vec3.Sub(camPos, Vec3.Mul(moveSpeed, worldUp));
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					panel.removeMouseMotionListener(mouseMotion);
				}				
			}

			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
		};

		mouseMotion = new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {}

			public void mouseMoved(MouseEvent e) {
				int mx = e.getX();
				int my = e.getY();
				
				float normX = ((float)mx - (float)P_W / 2.0f) / (float)P_W * 2.0f;
				float normY = (((float)my - (float)P_H / 2.0f) / (float)P_H * 2.0f);
						
				yaw += normX * sensitivity;
				pitch += normY * sensitivity;
				
				if(pitch > 89.0f)
					pitch = 89.0f;
				else if(pitch < -89.0f)
					pitch = -89.0f;
				
				float radYaw = (float) Math.toRadians(yaw);
				float radPitch = (float) Math.toRadians(pitch);
				
				Vec3 camDirection = new Vec3();
				camDirection.x =  (float) Math.cos(radYaw) * (float) Math.cos(radPitch);
				camDirection.y = (float) Math.sin((radPitch));
				camDirection.z = (float) Math.sin((radYaw)) * (float) Math.cos(radPitch);

				camFront = Math3D.Normalize(camDirection);
				
				//Center the mouse cursor (java is stupid)
				Robot r;
				try {
					r = new Robot();
					r.mouseMove((P_W / 2) + panel.getLocationOnScreen().x, (P_H / 2) + panel.getLocationOnScreen().y);					
				} 
				catch (AWTException e1) {
					e1.printStackTrace();
				}
				
				panel.repaint();
			}
		};
		
		panel.setFocusable(true);
		panel.requestFocus();
		panel.addKeyListener(key);
		panel.addMouseMotionListener(mouseMotion);
	}
	
	public void ViewMatrix() {
		view = Math3D.LookAt(camPos, Vec3.Add(camPos, camFront), worldUp);		
	}

}
