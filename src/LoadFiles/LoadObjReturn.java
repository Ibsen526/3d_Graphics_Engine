package LoadFiles;

import java.util.ArrayList;

import Util.Vec3;

public class LoadObjReturn {
	public ArrayList<Vec3> vertices;
	public ArrayList<Integer> indices;

	public LoadObjReturn() {
		vertices = new ArrayList<Vec3>();
		indices = new ArrayList<Integer>();
	}
	
	public LoadObjReturn(ArrayList<Vec3> v, ArrayList<Integer> i) {
		vertices = v;
		indices = i;
	}
}
