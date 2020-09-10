package pa2;

import java.util.ArrayList;

public class MatrixCuts 
{
	/**
	 * Takes a 2 dimensional Matrix M, and returns an ArrayList of Tuples representing the minimum cost width cut
	 * @param M
	 * @author Alex Dague
	 * @author Dan Savage
	 * @return
	 */
	public static ArrayList<Tuple> widthCut(int[][] M)
	{
		int[] shortestPath = new int[M[0].length];
		//an array list that tracks the shortest path tuple for building the tuple arraylist at the end
		//the y value in this arraylist is the final value of the path to compare at the end
		Tuple[][] tupleTrack = new Tuple[M.length-1][M[0].length];
		//this for loop sets up the initial shortest path for each m in the second to last row of n
		for(int j = 0; j < M[0].length; j++)
		{
			int dist1 = -1;
			int dist2 = -1;
			int dist3 = -1;
			if(j > 0)
			{
				dist1 = M[M.length-2][j] + M[M.length-1][j-1];
			}
			dist2 = M[M.length-2][j] + M[M.length-1][j];
			if(j < M[0].length-1)
			{
				dist3 = M[M.length-2][j] + M[M.length-1][j+1];
			}
			if(dist1 < dist2 && dist1 != -1)
			{
				if(dist1 < dist3 || dist3 == -1)
				{
					shortestPath[j] = dist1;
					tupleTrack[tupleTrack.length-1][j] = new Tuple(j-1, j-1);
				}
				else
				{
					shortestPath[j] = dist3;
					tupleTrack[tupleTrack.length-1][j] = new Tuple(j+1, j+1);
				}
			}
			else if(dist2 < dist3 || dist3 == -1)
			{
				shortestPath[j] = dist2;
				tupleTrack[tupleTrack.length-1][j] = new Tuple(j, j);			
			}
			else
			{
				shortestPath[j] = dist3;
				tupleTrack[tupleTrack.length-1][j] = new Tuple(j+1, j+1);
			}
		}
		//this loop dynamically goes through and finds the shortest path of each m value
		for(int i = M.length - 3; i >= 0; i--)
		{
			int[] tempShortestPath = new int[M[0].length];
			for(int j = 0; j < M[0].length; j++)
			{
				int dist1 = -1;
				int dist2 = -1;
				int dist3 = -1;
				if(j > 0)
				{
					dist1 = M[i][j] + shortestPath[j-1];
				}
				dist2 = M[i][j] + shortestPath[j];
				if(j < M[0].length-1)
				{
					dist3 = M[i][j] + shortestPath[j+1];
				}
				if(dist1 < dist2 && dist1 != -1)
				{
					if(dist1 < dist3 || dist3 == -1)
					{
						tempShortestPath[j] = dist1;
						tupleTrack[i][j] = new Tuple(j-1, tupleTrack[i+1][j-1].getY());				
					}
					else
					{
						tempShortestPath[j] = dist3;
						tupleTrack[i][j] = new Tuple(j+1, tupleTrack[i+1][j+1].getY());
					}
				}
				else if(dist2 < dist3 || dist3 == -1)
				{
					tempShortestPath[j] = dist2;
					tupleTrack[i][j] = new Tuple(j, tupleTrack[i+1][j].getY());
				}
				else
				{
					tempShortestPath[j] = dist3;
					tupleTrack[i][j] = new Tuple(j+1, tupleTrack[i+1][j+1].getY());
				}
			}
			shortestPath = tempShortestPath;
		}
		int shortest = shortestPath[0]; 
		int index = 0;
		for(int i = 1; i < M[0].length; i++)
		{
			if(shortestPath[i] < shortest)
			{
				shortest = shortestPath[i];
				index = i;
			}
			if(shortestPath[i] == shortest)
			{
				if(tupleTrack[0][i].getY() < tupleTrack[0][index].getY())
				{
					shortest = shortestPath[i];
					index = i;
				}
			}
		}
		ArrayList<Tuple> tuples = new ArrayList<Tuple>();
		tuples.add(new Tuple(shortest, -1));
		tuples.add(new Tuple(0, index));
		index = tupleTrack[0][index].getX();
		for(int i = 1; i <= tupleTrack.length; i++)
		{
			tuples.add(new Tuple(i, tupleTrack[i-1][index].getX()));
			if(i < tupleTrack.length)
			{
				index = tupleTrack[i][index].getX();
			}
		}
		return tuples;
	}
	
	/**
	 * Takes a 2 dimensional Matrix M, and returns an ArrayList of Tuples representing the minimum cost stitch cut
	 * @author Alex Dague
	 * @author Dan Savage
	 * @param M
	 * @return
	 */
	public static ArrayList<Tuple> stitchCut(int[][] M)
	{
		int[][][] tupleTracker = new int[M.length-1][M[0].length][2];
		int[] shortestPath = new int[M[0].length];
		for(int j = M[0].length-1; j >= 0; j--)
		{
			int dist1 = -1;
			int dist2 = -1;
			int dist3 = -1;
			dist1 = M[M.length-2][j] + M[M.length-1][j];
			if(j < M[0].length-1)
			{
				dist2 = M[M.length-2][j] + M[M.length-1][j+1];
				dist3 = M[M.length-2][j] + shortestPath[j+1];
			}
			if(dist1 < dist2 || dist2 == -1)
			{
				if(dist1 < dist3 || dist3 == -1)
				{
					shortestPath[j] = dist1;
					tupleTracker[M.length-2][j][0] = M.length-1;
					tupleTracker[M.length-2][j][1] = j;
//					tupleTracker[M.length-2][j][2] = j;
				}
				else
				{
					shortestPath[j] = dist3;
					tupleTracker[M.length-2][j][0] = M.length-2;
					tupleTracker[M.length-2][j][1] = j+1;
//					tupleTracker[M.length-2][j][2] = tupleTracker[M.length-2][j+1][2];
				}
				
			}
			else
			{
				shortestPath[j] = dist2;
				tupleTracker[M.length-2][j][0] = M.length-1;
				tupleTracker[M.length-2][j][1] = j+1;
//				tupleTracker[M.length-2][j][2] = j+1;
			}
		}
		//this loop dynamically goes through and finds the shortest path of each m value
		for(int i = M.length - 3; i >= 0; i--)
		{
			int[] tempShortestPath = new int[M[0].length];
			for(int j = M[0].length-1; j >= 0; j--)
			{
				int dist1 = -1;
				int dist2 = -1;
				int dist3 = -1;
				dist1 = M[i][j] + shortestPath[j];
				if(j < M[0].length-1)
				{
					dist2 = M[i][j] + shortestPath[j+1];
					dist3 = M[i][j] + tempShortestPath[j+1];
				}
				if(dist1 < dist2 || dist2 == -1)
				{
					if(dist1 < dist3 || dist3 == -1)
					{
						tempShortestPath[j] = dist1;
						tupleTracker[i][j][0] = i+1;
						tupleTracker[i][j][1] = j;
//						tupleTracker[i][j][2] = j;
						
					}
					else
					{
						tempShortestPath[j] = dist3;
						tupleTracker[i][j][0] = i;
						tupleTracker[i][j][1] = j+1;
//						tupleTracker[i][j][2] = tupleTracker[i][j+1][2];
					}
					
				}
				else
				{
					tempShortestPath[j] = dist2;
					tupleTracker[i][j][0] = i+1;
					tupleTracker[i][j][1] = j+1;
				//	tupleTracker[i][j][2] = j+1;
				}
			}
			shortestPath = tempShortestPath;
		}
		int shortest = shortestPath[0];
		int index = 0;
		for(int i = 1; i < shortestPath.length; i++)
		{
			if(shortest > shortestPath[i])
			{
				shortest = shortestPath[i];
				index = i;
			}
		}
		ArrayList<Tuple> tuples = new ArrayList<Tuple>();
		tuples.add(new Tuple(shortest, -1));
		tuples.add(new Tuple(0, index));
		int previousi = 0;
		while((tuples.get(tuples.size()-1).getX() !=M.length-1))// || (tuples.get(tuples.size()-1).getY() != tupleTracker[0][index][2]))
		{
			tuples.add(new Tuple(tupleTracker[previousi][index][0], tupleTracker[previousi][index][1]));
			int temp = index;
			index = tupleTracker[previousi][temp][1];
			previousi = tupleTracker[previousi][temp][0];
		}
		return tuples;
	}
}
