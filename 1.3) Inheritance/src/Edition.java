public abstract class Edition 
{
	protected String name;
	protected int year;
	protected String publication;
	public Edition(String _name, int _year, String _publication)
	{
		name = _name;
		year = _year;
		publication = _publication;
	}
	
	public String getName()
	{
		return name;
	}
	public abstract String toString();
}
