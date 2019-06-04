
public class GoogleFolderNode
{
	private String name, ID, parentID;
	private GoogleFolderNode node;
	
	public GoogleFolderNode(String name, String ID, String parentID)
	{
		this.name = name;
		this.ID = ID;
		this.parentID = parentID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getID()
	{
		return ID;
	}
	
	public void setNextNode(GoogleFolderNode node)
	{
		this.node = node;
	}
}
