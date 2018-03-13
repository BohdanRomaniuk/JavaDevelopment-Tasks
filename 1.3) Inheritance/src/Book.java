public class Book extends Edition
{
	private String author;
	private int pageCount;
	public Book(String _name, int _year, String _publication, String _author, int _pageCount)
	{
		super(_name, _year, _publication);
		author = _author;
		pageCount = _pageCount;
	}
}
