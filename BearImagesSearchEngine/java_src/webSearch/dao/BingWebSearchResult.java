package webSearch.dao;

//Credits to https://github.com/Azure-Samples/cognitive-services-REST-api-samples/blob/master/java/Search/BingImageSearchv7Quickstart.java

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import webSearch.vo.BingWebSearchResultVO;

public class BingWebSearchResult
{
	private String subscriptionKey = "7d6d60fa0fad4096a6f131cd79a75d6c";
	// endpoint location is westus to gain authorization to connect to the stream and retrieve reults
	private String host = "https://westus.api.cognitive.microsoft.com";
	private String path = "/bing/v7.0/images/search";
	private String searchTerm = "";

	public String prettify(String jsonText)
	{
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonText).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

	public SearchResults searchImages(String searchQuery) throws Exception
	{
		InputStream stream = null;
		SearchResults results = null;
		try
		{
			URL url = new URL(host + path + "?q=" + URLEncoder.encode(searchQuery, "UTF-8") + "&count=150");
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
			connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

			// Get rid of connection.getErrorStream() if it's causing you problems
			//		connection.getErrorStream();
			stream = connection.getInputStream();
			String response = new Scanner(stream).useDelimiter("\\A").next();

			results = new SearchResults(new HashMap<String, String>(), response);

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
		}
		finally
		{
			stream.close();
		}

		return results;
	}		

	public ArrayList<BingWebSearchResultVO> initiateWebSearch(String searchTerm) throws Exception
	{
		ArrayList<BingWebSearchResultVO> list = new ArrayList<BingWebSearchResultVO>();
		this.searchTerm = searchTerm;

		if(subscriptionKey.length() != 32)
		{
			System.out.println("Invalid Bing Search API subscription key!");
			System.out.println("Please paste yours into the source code.");
			System.exit(1);
		}
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
			// Resultant json text that shows the details of each image in a json response body
			//			System.out.println(prettify(result.getJSONResponse()));


			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(result.getJSONResponse()).getAsJsonObject();

			JsonArray results = json.getAsJsonArray("value");
			Random random = new Random();
			// Commented code requests for a random search result in the JSON object and then prints it in the console.
			int randomNumber = 0 + random.nextInt(results.size());
			JsonObject firstResult = (JsonObject)results.get(randomNumber);
			JsonObject webSearchResult;
			String resultURL = firstResult.get("contentUrl").getAsString();

			for (int i = 0; i < results.size(); i++)
			{
				BingWebSearchResultVO data = new BingWebSearchResultVO();
				webSearchResult = (JsonObject)results.get(i);
				System.out.println("Result " + (i + 1) + ": " + webSearchResult.get("contentUrl").getAsString());
				data.setContentID(i + 1);
				data.setContentUrl(webSearchResult.get("contentUrl").getAsString());
				data.setName(webSearchResult.get("name").getAsString());
				list.add(data);
			}
			String total = json.get("totalEstimatedMatches").getAsString();
			if(!total.isEmpty())
			{
				System.out.println("\nThe total number of images found: " + total + "\n");
			}
			System.out.println("\nThe content URL to a random image, " + (randomNumber + 1) + ", search URL: " + resultURL + "\n");
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace(System.out);
			throw e;
		}
		finally
		{
			System.out.println("finally block reached: Check out the issue.");
		}
		return list;
	}
}

