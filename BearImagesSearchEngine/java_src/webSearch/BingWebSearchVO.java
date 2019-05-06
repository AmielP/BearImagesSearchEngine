package webSearch;

//import java.net.URL;
import java.util.Date;


public class BingWebSearchVO 
{
	private int bearImageID;
	private String webSearchURL;
	private String name;
	private String thumbnailURL;
	private Date datePublished;
	private boolean isFamilyFriendly;
	private String contentURL;
	private String hostPageURL;
	private String contentSize;
	private String encodingFormat;
	private String hostPageDisplayURL;
	private int width;
	private int height;
	private String thumbnail;
	
	public int getBearImageID()
	{
		return bearImageID;
	}
	public void setBearImageID(int bearImageID)
	{
		this.bearImageID = bearImageID;
	}
	public String getWebSearchURL()
	{
		return webSearchURL;
	}
	public void setWebSearchURL(String webSearchURL)
	{
		this.webSearchURL = webSearchURL;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getThumbnailURL()
	{
		return thumbnailURL;
	}
	public void setThumbnailURL(String thumbnailURL)
	{
		this.thumbnailURL = thumbnailURL;
	}
	public Date getDatePublished()
	{
		return datePublished;
	}
	public void setDatePublished(Date datePublished)
	{
		this.datePublished = datePublished;
	}
	public boolean isFamilyFriendly()
	{
		return isFamilyFriendly;
	}
	public void setFamilyFriendly(boolean isFamilyFriendly)
	{
		this.isFamilyFriendly = isFamilyFriendly;
	}
	public String getContentURL()
	{
		return contentURL;
	}
	public void setContentURL(String contentURL)
	{
		this.contentURL = contentURL;
	}
	public String getHostPageURL()
	{
		return hostPageURL;
	}
	public void setHostPageURL(String hostPageURL)
	{
		this.hostPageURL = hostPageURL;
	}
	public String getContentSize()
	{
		return contentSize;
	}
	public void setContentSize(String contentSize)
	{
		this.contentSize = contentSize;
	}
	public String getEncodingFormat()
	{
		return encodingFormat;
	}
	public void setEncodingFormat(String encodingFormat)
	{
		this.encodingFormat = encodingFormat;
	}
	public String getHostPageDisplayURL()
	{
		return hostPageDisplayURL;
	}
	public void setHostPageDisplayURL(String hostPageDisplayURL)
	{
		this.hostPageDisplayURL = hostPageDisplayURL;
	}
	public int getWidth()
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public int getHeight()
	{
		return height;
	}
	public void setHeight(int height)
	{
		this.height = height;
	}
	public String getThumbnail()
	{
		return thumbnail;
	}
	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}
}
