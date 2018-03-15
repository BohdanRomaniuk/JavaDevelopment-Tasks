import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainProgram 
{
	public static void printArray(Edition[] array)
	{
		for(int i=0; i<array.length; ++i)
		{
			System.out.println(array[i].toString());
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			System.out.println("Readed from file:");
			List<String> lines = Files.readAllLines(Paths.get("D:\\Хмара\\ЛНУ\\3 курс\\Програмування мовою JAVA (Бернакевич)\\JavaDevelopment-Tasks\\1.3) Inheritance\\src\\InputEditions.txt"), StandardCharsets.UTF_8);
			Edition[] editions = new Edition[lines.size()];
			int k = 0;
			for(String elem : lines)
			{
				String[] splited = elem.split(" ");
				if(splited[0].equals("1"))
				{
					editions[k++] = new Book(splited[1],Integer.parseInt(splited[2]), splited[3], splited[4], Integer.parseInt(splited[5]));
				}
				else
				{
					editions[k++] = new Magazine(splited[1],Integer.parseInt(splited[2]), splited[3], Integer.parseInt(splited[4]), splited[5]);
				}
			}
			printArray(editions);
			
			for(int i=0; i<editions.length; ++i)
			{
				Edition min = editions[i];
				int minIdx = i;
				for(int j=i+1; j<editions.length; ++j)
				{
					if(editions[j].getName().compareTo(min.getName())<0)
					{
						min = editions[j];
						minIdx = j;
					}
				}
				Edition temp = editions[i];
				editions[i] = min;
				editions[minIdx] = temp;
			}
			System.out.println("\nAfter sorting by name:");
			printArray(editions);
			
			System.out.println("\nAuthor who pruduced the greatest amount of books:");
			HashMap<String, Integer> authors = new HashMap<String, Integer>();
			for(int i=0; i<editions.length; ++i)
			{
				if(editions[i] instanceof Book)
				{
					if(!authors.containsKey(((Book)editions[i]).getAuthor()))
					{
						authors.put(((Book)editions[i]).getAuthor(), 1);
					}
					else
					{
						authors.put(((Book)editions[i]).getAuthor(), authors.get(((Book)editions[i]).getAuthor()) + 1);
					}
				}
			}
			String popularAuthor = "";
			int repeatCount = 0;
			for(Map.Entry<String, Integer> entry : authors.entrySet())
			{
			    if(entry.getValue()>repeatCount)
			    {
			    	popularAuthor = entry.getKey();
			    }
			}
			System.out.println(popularAuthor);
			
			System.out.println("\nInput genre:");
			@SuppressWarnings("resource")
			Scanner reader = new Scanner(System.in);
			String searchGenre = reader.next();
			System.out.println("Magazines with "+ searchGenre+ " genre:");
			boolean searchSuccesfull = false;
			for(int i=0; i<editions.length; ++i)
			{
				if(editions[i] instanceof Magazine && ((Magazine)editions[i]).getGenre().equals(searchGenre))
				{
					System.out.println(editions[i].toString());
					searchSuccesfull = true;
				}
			}
			System.out.println(searchSuccesfull?"":"Nothing found!");
		}
		catch(Exception exc)
		{
			System.out.println("Error occured!! "+exc.getClass().getName()+"!! "+exc.getMessage());
		}
	}
}
