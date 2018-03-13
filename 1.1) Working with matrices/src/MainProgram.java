import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainProgram {
	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Input m:");
			int m = Integer.parseInt(br.readLine());
			System.out.println("Input n:");
			int n = Integer.parseInt(br.readLine());
			double[][] matrix = new double[m][n];
			System.out.println("Input matrix(with spaces):");
			for(int i=0; i<m; ++i)
			{
				String line = br.readLine();
				String[] elems = line.split(" ");
				for(int j=0; j<n; ++i)
				{
					matrix[i][j] = Double.parseDouble(elems[j]);
				}
			}
			System.out.println("Matrix::::");
			for(int i=0; i<m; ++i)
			{
				for(int j=0; j<n; ++j)
				{
					System.out.print(matrix[i][j]+" ");
				}
				System.out.print('\n');
			}
		}
		catch(IOException exc)
		{
			System.err.println("Invalid parametr");
		}
	}
}