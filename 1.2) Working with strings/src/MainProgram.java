import java.util.Scanner;

public class MainProgram 
{
	public static void main(String[] args)
	{
		System.out.println("Input string separated by comas:");
		Scanner reader = new Scanner(System.in);
		String input = reader.next();
		reader.close();
		String[] words = input.split(",");
		System.out.println("\nResult after removing all last chars from each word:");
		for(int i=0; i<words.length; ++i)
		{
			String lastChracter = words[i].substring(words[i].length()-1);
			words[i] = words[i].replace(words[i].substring(words[i].length()-1), "");
			words[i] = words[i].concat(lastChracter);
			System.out.print(words[i]+((i!=words.length-1)?" ":""));
		}
	}
}
