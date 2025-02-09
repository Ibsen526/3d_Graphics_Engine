package LoadFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import Main3D.Mesh;
import Util.Vec3;

public class LoadObj {
	public static void File(String path, Mesh mesh) {
		
		File file;
		Scanner scanner;
		ArrayList<Vec3> v = new ArrayList<Vec3>();
		ArrayList<Vec2I> i = new ArrayList<Vec2I>();		
				
		try {
			file = new File(path);
			scanner = new Scanner(file);
		}
		catch(Exception ex) {
			System.out.println(ex);
			return;
		}
		
		while (scanner.hasNextLine()) {
	        String line = scanner.nextLine();
	        
	        String[] lineParts = line.split(" ");
	        
	        if (lineParts[0].equals("v")) {
		        try {
		        	v.add(new Vec3(Float.parseFloat(lineParts[1]), Float.parseFloat(lineParts[2]), Float.parseFloat(lineParts[3])));
		        }
		        catch (Exception e) {
					System.out.println("File corrupted! Can't convert vertex string to float!");
		        }
	        } 
	        else if (lineParts[0].equals("f")) {
		        try {
		        	i.add(StoreIndices(lineParts[1]));
		        	i.add(StoreIndices(lineParts[2]));
		        	i.add(StoreIndices(lineParts[3]));
		        }
		        catch (Exception e) {
					System.out.println("File corrupted! Can't convert index string to int!");
		        }
	        } 
			
	    }		
		
		scanner.close();

		//Now store the vertices and normals accordingly
		Integer ti = 0; //counts only the original faces (the size of vertices)
		ArrayList<Vec2I> tF = new ArrayList<Vec2I>(); //stores all the faces only once
		for (int j = 0; j < i.size(); j++)
		{			
			Boolean isIn = false;
			for (Integer k = 0; k < tF.size(); k++)
			{
				if (i.get(j).x == tF.get(k).x && i.get(j).y == tF.get(k).y) //checks if the current face is already stored
				{
					isIn = true;
					mesh.Indices.add(k);
					break;
				}
			}

			if (!isIn)
			{
				mesh.Vertices.add(v.get(i.get(j).x));
				tF.add(i.get(j));
				mesh.Indices.add(ti);
				ti++;
			}
		}
	}
	
	private static Vec2I StoreIndices(String x)
	{
		String[] face = x.split("/");

		Integer iv = Integer.parseInt(face[0]); 
		iv -= 1;
		Integer ivn = Integer.parseInt(face[2]); 
		ivn -= 1;
				
		return new Vec2I(iv, ivn);
	}
}
