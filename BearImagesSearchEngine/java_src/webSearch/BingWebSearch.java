package webSearch;

import java.net.*;
import java.sql.ResultSet;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import flex.messaging.services.messaging.ThrottleManager.ThrottleResult.Result;
import searchResults.SearchResults;

//taken from https://docs.microsoft.com/en-us/azure/cognitive-services/bing-web-search/quickstarts/java

public class BingWebSearch 
{
	private String subscriptionKey = "bab9222bd5744ee6ace4a9dee973ff10";

	private String host = "https://api.cognitive.microsoft.com";
	private String path = "/bing/v7.0/images/search";
	private String defaultSearchTerm = "\"wildlife\" AND \"ursidae\" AND \"bear\" AND ";
	private String searchTerm = "";
	
	private JsonObject json = null;
//	private static Map<String, List<String>> headers;

	public String getSubscriptionKey()
	{
		return subscriptionKey;
	}

	public String getDefaultSearchTerm() 
	{
		return defaultSearchTerm;
	}

	public SearchResults searchWeb(String searchQuery) throws Exception
	{
		URL url = new URL(host + path + "?q=" + URLEncoder.encode(searchQuery, "UTF-8") + "&modules=Collections%2CRecognizedEntities%2CSimilarImages");

		System.out.println("URL : " + url.toString());

		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

		InputStream stream = connection.getInputStream();
		String response = new Scanner(stream).useDelimiter("\\A").next();

		SearchResults results = new SearchResults(new HashMap<String, String>(), response);

		Map<String, List<String>> headers = connection.getHeaderFields();
//		headers = connection.getHeaderFields();

		for(String header : headers.keySet())
		{
			if(header == null)
			{
				continue;
			}
			if(header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-") || header.startsWith("X-Search-"))
			{
				results.getRelevantHeaders().put(header, headers.get(header).get(0));
			}
		}

		stream.close();
		return results;
	}
	
	public JsonObject convertJsonTextToJsonObject(String json_text)
	{
		JsonParser parser = new JsonParser();
		json = parser.parse(json_text).getAsJsonObject();
		
		return json;
	}

	public String prettifyThis(JsonObject json)
	{
//		this.json = json;
//		JsonParser parser = new JsonParser();
//		JsonObject json = parser.parse(json_text).getAsJsonObject();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}
	
	private ArrayList<BingWebSearchVO> readBingWebSearchData(JsonObject json)
	{
//		this.json = json;
		ArrayList<BingWebSearchVO> headerList = new ArrayList<BingWebSearchVO>();
		
		JsonArray jsonArray = json.getAsJsonArray("value");
//		List<String> valueList = new ArrayList<String>();
		for(int i = 0; i < jsonArray.size(); i++)
		{
			BingWebSearchVO bingWebSearchData = new BingWebSearchVO();
			
			//can delete the following lines of code for brevity
//			String contentURL = jsonArray.get(i).getAsJsonObject().get("contentUrl").getAsString();
			bingWebSearchData.setBearImageID((i+ 1));
			bingWebSearchData.setContentURL(jsonArray.get(i).getAsJsonObject().get("contentUrl").getAsString());
			bingWebSearchData.setName(jsonArray.get(i).getAsJsonObject().get("name").getAsString());
			
			headerList.add(bingWebSearchData);
//			valueList.add(jsonArray.get(i).getAsJsonObject().get("webSearchUrl").getAsString());
		}
		
		System.out.println("Hayp!");
//		JsonObject webSearchURL = (JsonObject) json.get("webSearchUrl");
//		System.out.println(webSearchURL.toString());
		return headerList;
	}
	
	public ArrayList<BingWebSearchVO> searchWebAPI(String searchTerm)
	{
		this.searchTerm = searchTerm;
		SearchResults result = null;

		if(subscriptionKey.length() != 32)
		{
			System.out.println("Invalid Bing Search API subscription key!");
			System.out.println("Please paste yours into the source code.");
			System.exit(1);
		}

		try
		{
			System.out.println("Searching the Web for: " + defaultSearchTerm + "\"" + searchTerm + "\"");
			result = searchWeb(defaultSearchTerm + "\"" + searchTerm + "\"");
			System.out.println("\nRelevant HTTP Headers:\n");
			for(String header : result.getRelevantHeaders().keySet())
				System.out.println(header + ": " + result.getRelevantHeaders().get(header));
			
			json = convertJsonTextToJsonObject(result.getJSONResponse());
			
			
			System.out.println("\nJSON Response:\n");
			System.out.println(prettifyThis(json));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			System.exit(1);
		}
		return readBingWebSearchData(json);
	}

//	public void initiateSearchWeb(String searchTerm)
//	{
////		ArrayList<BingWebSearchVO> headerList = new ArrayList<BingWebSearchVO>();
//		ResultSet rs = null;
//
//		this.searchTerm = searchTerm;
//		BingWebSearch bwsObj = new BingWebSearch();
//		SearchResults result = null;
//
//		if(bwsObj.getSubscriptionKey().length() != 32)
//		{
//			System.out.println("Invalid Bing Search API subscription key!");
//			System.out.println("Please paste yours into the source code.");
//			System.exit(1);
//		}
//
//		try
//		{
//			System.out.println("Searching the Web for: " + defaultSearchTerm + "\"" + searchTerm + "\"");
//			result = bwsObj.searchWeb(defaultSearchTerm + "\"" + searchTerm + "\"");
//			System.out.println("\nRelevant HTTP Headers:\n");
//			for(String header : result.getRelevantHeaders().keySet())
//				System.out.println(header + ": " + result.getRelevantHeaders().get(header));
//			
////			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
////			
////			JsonObject jsonObj = (JsonObject) result;
////			
////			Iterator<Map.Entry> iter = headers.entrySet().iterator();
////			while(iter.hasNext())
////			{
////				Entry<String, List<String>> pair = iter.next();
////				System.out.println("TEST " + pair.getKey() + " : " + pair.getValue());
////			}
//			
////			for(int i = 0; i < result.getJSONResponse().length(); i++)
////			{
////				BingWebSearchVO bingWebSearchData = new BingWebSearchVO();
////
////				String key = 
////
////				switch(key)
////				{
////				case "contentURL":
////					bingWebSearchData.setWebSearchURL(result.getRelevantHeaders().get("contentURL"));
////				}
////s
////				headerList.add(bingWebSearchData);
////			}
//			json = convertJsonTextToJsonObject(result.getJSONResponse());
//			
//			readBingWebSearchData(json);
//			System.out.println("\nJSON Response:\n");
//			System.out.println(prettifyThis(json));
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace(System.out);
//			System.exit(1);
//		}
//
////		return headerList;
//	}
}
