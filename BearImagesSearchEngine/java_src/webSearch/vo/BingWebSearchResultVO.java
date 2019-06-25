package webSearch.vo;

//import java.sql.Blob;
import java.util.Date;

public class BingWebSearchResultVO
{
	private int contentID;
	private String webSearchUrl;
	private String name;
	private String thumbnailUrl;
	private Date datePublished;
	private String contentUrl;
	private String hostPageUrl;
	private String contentSize;
	private String encodingFormat;
	private String hostPageDisplayUrl;
	private int width;
	private int height;
	private String thumbnail; //has two parameters: height and width
	private byte[] imageData;
	
	public int getContentID()
	{
		return contentID;
	}
	public void setContentID(int contentID)
	{
		this.contentID = contentID;
	}
	public String getWebSearchUrl()
	{
		return webSearchUrl;
	}
	public void setWebSearchUrl(String webSearchUrl)
	{
		this.webSearchUrl = webSearchUrl;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getThumbnailUrl()
	{
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl)
	{
		this.thumbnailUrl = thumbnailUrl;
	}
	public Date getDatePublished()
	{
		return datePublished;
	}
	public void setDatePublished(Date datePublished)
	{
		this.datePublished = datePublished;
	}
	public String getContentUrl()
	{
		return contentUrl;
	}
	public void setContentUrl(String contentUrl)
	{
		this.contentUrl = contentUrl;
	}
	public String getHostPageUrl()
	{
		return hostPageUrl;
	}
	public void setHostPageUrl(String hostPageUrl)
	{
		this.hostPageUrl = hostPageUrl;
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
	public String getHostPageDisplayUrl()
	{
		return hostPageDisplayUrl;
	}
	public void setHostPageDisplayUrl(String hostPageDisplayUrl)
	{
		this.hostPageDisplayUrl = hostPageDisplayUrl;
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
	public byte[] getImageData()
	{
		return imageData;
	}
	public void setImageData(byte[] imageData)
	{
		this.imageData = imageData;
	}
}
