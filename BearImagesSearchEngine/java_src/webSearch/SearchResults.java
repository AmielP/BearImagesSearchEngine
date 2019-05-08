package webSearch;

import java.util.HashMap;

//Credits to https://github.com/Azure-Samples/cognitive-services-REST-api-samples/blob/master/java/Search/BingImageSearchv7Quickstart.java

public class SearchResults
{
	private HashMap<String, String> relevantHeaders;
	private String jsonResponse;
	public SearchResults(HashMap<String, String> headers, String json)
	{
		relevantHeaders = headers;
		jsonResponse = json;
	}
	
	public void setRelevantHeaders(HashMap<String, String> relevantHeaders)
	{
		this.relevantHeaders = relevantHeaders;
	}
	
	public HashMap<String, String> getRelevantHeaders()
	{
		return relevantHeaders;
	}
	
	public void setJSONResponse(String jsonResponse)
	{
		this.jsonResponse = jsonResponse;
	}
	
	public String getJSONResponse()
	{
		return jsonResponse;
	}
}
