import java.util.Scanner;

public class MainProgram 
{
	public static boolean isSymetric(int digit)
	{
		String digitStr = Integer.toString(digit);
		for(int i=0; i<Math.ceil(digitStr.length()/2); ++i)
		{
			if(digitStr.charAt(i)!=digitStr.charAt(digitStr.length()-1-i))
			{
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Input num of rows(m):");
		int rows = reader.nextInt();
		System.out.println("Input num of cols(n):");
		int cols = reader.nextInt();
		
		int[][] matrix = new int[rows][cols]; 
		System.out.println("Input matrix[" + rows + "][" + cols +"] (with spaces):");
		for(int i=0; i<rows; ++i)
        { 
        	for(int j=0; j<cols; ++j)
        	{
        		matrix[i][j] = reader.nextInt();
        	}
        } 
        System.out.println("Readed matrix:");
        for(int i=0; i<rows; ++i)
        {
        	for(int j=0; j<cols; ++j)
        	{
        		System.out.print(matrix[i][j] + " ");
        	}
        	System.out.print("\n");
        }
        int[] vector = new int[rows];
        for(int i=0; i<rows; ++i)
        {
        	int minElement = 0;
        	for(int j=1; j<cols; ++j)
        	{
        		if(isSymetric(matrix[i][j]))
        		{
        			if(minElement==0)
        			{
        				minElement = matrix[i][j];
        			}
        			else if(matrix[i][j]<minElement)
        			{
        				minElement = matrix[i][j];
        			}
        		}
        	}
        	vector[i] = minElement;
        }
        System.out.println("Vector of min symetric elements of each row:");
        for(int i=0; i<vector.length; ++i)
        {
        	System.out.print(vector[i] + " ");
        }
	}
}