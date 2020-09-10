package pa2;

import java.io.File;
import java.sql.Time;

public class Main {

	public static void main(String[] args) 
	{
//		int[][] M = new int [5][5];
//		M[0][0] = 5;
//		M[0][1] = 7;
//		M[0][2] = 9;
//		M[0][3] = 4;
//		M[0][4] = 5;
//		M[1][0] = 2;
//		M[1][1] = 3;
//		M[1][2] = 1;
//		M[1][3] = 1;
//		M[1][4] = 2;
//		M[2][0] = 2;
//		M[2][1] = 0;
//		M[2][2] = 49;
//		M[2][3] = 46;
//		M[2][4] = 8;
//		M[3][0] = 3;
//		M[3][1] = 1;
//		M[3][2] = 1;
//		M[3][3] = 1;
//		M[3][4] = 1;
//		M[4][0] = 50;
//		M[4][1] = 51;
//		M[4][2] = 25;
//		M[4][3] = 26;
//		M[4][4] = 1;
//		ArrayList<Tuple> t = MatrixCuts.stitchCut(M);
//		System.out.println(t.get(0).getX());
		System.out.println("strating: ");
		long sTime = System.currentTimeMillis();
		File file = new File("C:\\Users\\Alex\\Pictures\\smalltest3.jpg");
		System.out.println("running ");
		ImageProcessor.reduceWidth(420, "C:\\Users\\Alex\\Pictures\\me.jpg").save(file);
		System.out.println("Time elapsed (seconds): " + (System.currentTimeMillis() - sTime)/1000);
		System.out.println("done");
	}

}
