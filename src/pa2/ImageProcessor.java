package pa2;

import java.awt.Color;
import java.util.ArrayList;

public class ImageProcessor {
	/**
	 * Reduces the given image width by x pixels by removing 1 column of width pixels at a time. Uses a minWidthCut to determine the least consequential pixels to remove
	 * @author Alex Dague
	 * @author Dan Savage
	 * @param x
	 * @param inputImage
	 * @return
	 */
	public static Picture reduceWidth(int x, String inputImage)
	{
		//create picture by filename
		Picture picture = new Picture(inputImage);		
		for(int k = 0; k < x; k++)
		{
			int W = picture.width();
			int H = picture.height();
			int[][][] M = new int[H][W][3];
			int[][] I = new int[H][W];
			//create 3D array of colors
			for(int i = 0; i < H; i++)
			{
				for(int j = 0; j < W; j++)
				{
					Color pixelcolor = picture.get(j, i);
					int Red = pixelcolor.getRed();
					int Green = pixelcolor.getGreen();
					int Blue = pixelcolor.getBlue();
					M[i][j][0] = Red;
					M[i][j][1] = Green;
					M[i][j][2] = Blue;
				}
			}
			
			//create 3D array of importance
			for(int i = 0; i < H; i++)
			{
				for(int j = 0; j < W; j++)
				{
					if(0 < j && j < W - 1)
					{
						int dist = (int) Math.pow((M[i][j-1][0] - M[i][j+1][0]), 2) + (int) Math.pow((M[i][j-1][1] - M[i][j+1][1]), 2) + (int) Math.pow((M[i][j-1][2] - M[i][j+1][2]), 2);
						I[i][j] = dist;
					}
					if(j == 0)
					{
						int dist = (int) Math.pow((M[i][j][0] - M[i][j+1][0]), 2) + (int) Math.pow((M[i][j][1] - M[i][j+1][1]), 2) + (int) Math.pow((M[i][j][2] - M[i][j+1][2]), 2);
						I[i][j] = dist;
	
					}
					if(j == W - 1)
					{
						int dist = (int) Math.pow((M[i][j][0] - M[i][j-1][0]), 2) + (int) Math.pow((M[i][j][1] - M[i][j-1][1]), 2) + (int) Math.pow((M[i][j][2] - M[i][j-1][2]), 2);
						I[i][j] = dist;
					}	
				}
			}
			
			ArrayList<Tuple> IWC = MatrixCuts.widthCut(I);
			
			for(int i = 1; i < IWC.size(); i++)
			{
				for(int j = IWC.get(i).getY() - 1; j >= 0; j--)
				{
					int Red = M[IWC.get(i).getX()][j][0];
					int Green = M[IWC.get(i).getX()][j][1];
					int Blue = M[IWC.get(i).getX()][j][2];
					Color color = new Color(Red, Green, Blue);
					picture.set(j + 1, IWC.get(i).getX(), color);
				}
			}
			picture = ImageStitch.crop(picture, 1);
		}
		
		return picture;
	}
}