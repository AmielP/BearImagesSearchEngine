package webSearch.dao;

//Credits to https://github.com/Azure-Samples/cognitive-services-REST-api-samples/blob/master/java/Search/BingImageSearchv7Quickstart.java

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BingWebSearchResult
{
	private String subscriptionKey = "key";
	private String host = "https://api.cognitive.microsoft.com";
	private String path = "/bing.v7.0/images/search";
	private String searchTerm = "Digimon";
	
	public String prettify(String jsonText)
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonText).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

	public SearchResults searchImages(String searchQuery) throws Exception
	{
		URL url = new URL(host + path + "?q=" + URLEncoder.encode(searchQuery, "UTF-8"));
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
		
		InputStream stream = connection.getInputStream();
		String response = new Scanner(stream).useDelimiter("\\A").next();
		
		SearchResults results = new SearchResults(new HashMap<String, String>(), response);
		
		//Bing-specific headers that apply to required or optional request/response headers
		Map<String, List<String>> headers = connection.getHeaderFields();
		for(String header : headers.keySet())
		{
			if(header == null) continue;
			if(header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-"))
			{
				results.getRelevantHeaders().put(header,  headers.get(header).get(0));
			}
		}
		
		stream.close();
		return results;
	}		
	
	public void initiateWebSearch() throws Exception
	{
		if(subscriptionKey.length() != 32)
		{
			System.out.println("Invalid Bing Search API subscription key!");
			System.out.println("Please paste yours into the source code.");
			System.exit(1);
			
			try
			{
				System.out.println("Searching the web for: " + searchTerm);
				
				SearchResults result = searchImages(searchTerm);
				
				System.out.println("\nRelevant HTTP Headers:\n");
				for(String header : result.getRelevantHeaders().keySet())
				{
					System.out.println(header + ": " + result.getRelevantHeaders().get(header));
				}
				
				System.out.println("\nJSON Response\n");
				
				JsonParser parser = new JsonParser();
				JsonObject json = parser.parse(result.getJSONResponse()).getAsJsonObject();
				String total = json.get("totalEstimatedMatches").getAsString();
				JsonArray results = json.getAsJsonArray("value");
				JsonObject firstResult = (JsonObject)results.get(0);
				String resultURL = firstResult.get("thumbnailUrl").getAsString();
				
				System.out.println("\nThe total number of images found: " + total + "\n");
				System.out.println("\nThe thumbnail URL to the first image search URL: " + resultURL + "\n");
			}
			catch (Exception e)
			{
				// TODO: handle exception
				e.printStackTrace(System.out);
				System.exit(1);
			}
		}
	}
}
