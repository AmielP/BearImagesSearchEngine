package searchResults;

import java.util.HashMap;

//taken from https://docs.microsoft.com/en-us/azure/cognitive-services/bing-web-search/quickstarts/java
public class SearchResults 
{
	private HashMap<String, String> relevantHeaders;
	private String jsonResponse;
	
	public HashMap<String, String> getRelevantHeaders()
	{
		return relevantHeaders;
	}
	
	public String getJSONResponse()
	{
		return jsonResponse;
	}
	
	public SearchResults(HashMap<String, String> headers, String json)
	{
		relevantHeaders = headers;
		jsonResponse = json;
	}
}
