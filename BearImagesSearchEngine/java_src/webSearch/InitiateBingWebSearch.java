package webSearch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import searchResults.SearchResults;

public class InitiateBingWebSearch 
{
	public static String prettify(String json_text)
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}
	
	public static void main(String[] args)
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
			System.out.println("Searching the Web for: " + bwsObj.getDefaultSearchTerm());
			SearchResults result = bwsObj.searchWeb(bwsObj.getDefaultSearchTerm());
			System.out.println("\nRelevant HTTP Headers:\n");
			for(String header : result.getRelevantHeaders().keySet())
			System.out.println(header + ": " + result.getRelevantHeaders().get(header));
			System.out.println("\nJSON Response:\n");
			System.out.println(prettify(result.getJSONResponse()));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			System.exit(1);
		}
	}
}
