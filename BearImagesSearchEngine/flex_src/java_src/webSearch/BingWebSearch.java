import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java_src.searchResults.SearchResults;

//taken from https://docs.microsoft.com/en-us/azure/cognitive-services/bing-web-search/quickstarts/java

public class BingWebSearch 
{
	private String subscriptionKey = "enter key here";
	
	private String host = "https://api.cognitive.microsoft.com";
	private String path = "/bing/v7.0/search";
	private String searchTerm = "Microsoft Cognitive Services";
	
	public String getSubscriptionKey()
	{
		return subscriptionKey;
	}
	
	public String getSearchTerm() 
	{
		return searchTerm;
	}
	
	public SearchResults searchWeb(String searchQuery) throws Exception
	{
		URL url = new URL(host + path + "?q=" + URLEncoder.encode(searchQuery, "UTF-8"));
		
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
		
		InputStream stream = connection.getInputStream();
		String response = new Scanner(stream).useDelimiter("\\A").next();
		
		SearchResults results = new SearchResults(new HashMap<String, String>(), response);
		
		Map<String, List<String>> headers = connection.getHeaderFields();
		
		for(String header : headers.keySet())
		{
			if(header == null)
			{
				continue;
			}
			if(header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-"))
			{
				results.getRelevantHeaders().put(header, headers.get(header).get(0));
			}
		}
		
		stream.close();
		return results;
	}
	
	public String prettifyThis(String json_text)
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}
	
	public void initiateSearchWeb()
	{
		BingWebSearch bwsObj = new BingWebSearch();
		
		if(bwsObj.getSubscriptionKey().length() != 32)
		{
			System.out.println("Invalid Bing Search API subscription key!");
			System.out.println("Please paste yours into the source code.");
			System.exit(1);
		}
		
		try
		{
			System.out.println("Searching the Web for: " + bwsObj.getSearchTerm());
			SearchResults result = bwsObj.searchWeb(bwsObj.getSearchTerm());
			System.out.println("\nRelevant HTTP Headers:\n");
			for(String header : result.getRelevantHeaders().keySet())
			System.out.println(header + ": " + result.getRelevantHeaders().get(header));
			System.out.println("\nJSON Response:\n");
			System.out.println(prettifyThis(result.getJSONResponse()));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			System.exit(1);
		}
	}
}
