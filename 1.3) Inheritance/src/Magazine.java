public final class Magazine extends Edition
{
	private int number;
	private String genre;
	public Magazine(String _name, int _year, String _publication, int _number, String _genre)
	{
		super(_name, _year, _publication);
		number = _number;
		genre = _genre;
	}
	
	public String getGenre()
	{
		return genre;
	}
	
	public final String toString()
	{
		return name + " " + year + " " + publication + " " + number + " " + genre;
	}
}
