package webSearch.dao;

//Courtesy of https://github.com/Azure-Samples/cognitive-services-REST-api-samples/blob/master/java/Search/BingImageSearchv7Quickstart.java

import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import webSearch.vo.BingWebSearchResultVO;

public class BingWebSearchResult
{
	private static int numberOfTimesUserSearched = 0;

	private String subscriptionKey = "7d6d60fa0fad4096a6f131cd79a75d6c";
	// Endpoint location is westus to gain authorization to connect to the stream and retrieve results from westus.
	// Results cannot be retrieved beyond westus locations.
	private String host = "https://westus.api.cognitive.microsoft.com";
	private String path = "/bing/v7.0/images/search";
	private String searchTerm = "";
	private String bearSearchTerm = "\"wildlife\" AND \"ursidae\" AND \"bear\" AND ";

	private JsonObject json = null;
	private JsonArray jsonArray;

	private JsonObject jsonObjectResult;

	private JsonObject convertJsonTextToJsonObject(String json_text)
	{
		JsonParser jsonParser = new JsonParser();
		json = jsonParser.parse(json_text).getAsJsonObject();

		return json;
	}

	public String prettifyThis(JsonObject json)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

	public SearchResults searchImages(String searchQuery) throws Exception
	{
		InputStream stream = null;
		SearchResults results = null;
		try
		{
			URL url = new URL(host + path + "?q=" + URLEncoder.encode(searchQuery, "UTF-8") + "&count=150&modules=Collections%2CRecognizedEntities%2CSimilarImages");

			System.out.println("URL : " + url.toString());

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
				if(header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-") || header.startsWith("X-Search-"))
				{
					results.getRelevantHeaders().put(header, headers.get(header).get(0));
				}
			}
		}
		finally
		{
			stream.close();
		}

		return results;
	}

	private ArrayList<BingWebSearchResultVO> readBingWebSearchData(JsonObject json)
	{
		ArrayList<BingWebSearchResultVO> headerList = new ArrayList<BingWebSearchResultVO>();

		jsonArray = json.getAsJsonArray("value");

		for(int i = 0; i < jsonArray.size(); i++)
		{
			BingWebSearchResultVO bingWebSearchData = new BingWebSearchResultVO();

			jsonObjectResult = jsonArray.get(i).getAsJsonObject();

			// Print each of the URL results
			//			System.out.println("Result " + (i + 1) + ": " + jsonObjectResult.get("contentUrl").getAsString());

			bingWebSearchData.setContentID((i + 1));
			bingWebSearchData.setContentUrl(jsonObjectResult.get("contentUrl").getAsString());
			bingWebSearchData.setName(jsonObjectResult.get("name").getAsString());

			headerList.add(bingWebSearchData);
		}
		return headerList;
	}

	public ArrayList<BingWebSearchResultVO> initiateWebSearch(String searchTerm) throws Exception
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
			System.out.println("---------------------------------------------------------------");
			System.out.println("Number of searches the user made: " + (numberOfTimesUserSearched + 1));
			System.out.println("Searching the web for: " + bearSearchTerm + "\"" + searchTerm + "\"");

			result = searchImages(bearSearchTerm + "\"" + searchTerm + "\"");

			System.out.println("\nRelevant HTTP Headers:\n");
			for(String header : result.getRelevantHeaders().keySet())
			{
				System.out.println(header + ": " + result.getRelevantHeaders().get(header));
			}

			json = convertJsonTextToJsonObject(result.getJSONResponse());

			// Resultant json text that shows verbose details of each image in a json response body
			//			System.out.println("\nJSON Response\n");

			//			System.out.println(prettify(result.getJSONResponse()));


			JsonArray results = json.getAsJsonArray("value");
			Random random = new Random();
			// Commented code requests for a random search result in the JSON object and then prints it in the console.
			int randomNumber = 0 + random.nextInt(results.size());
			jsonObjectResult = (JsonObject)results.get(randomNumber);
			String resultURL = jsonObjectResult.get("contentUrl").getAsString();
			String description = jsonObjectResult.get("name").getAsString();

			String total = json.get("totalEstimatedMatches").getAsString();
			if(!total.isEmpty())
			{
				System.out.println("\nThe total number of images found: " + total + "\n");
			}
			System.out.println("\nThe content URL to a random image, " + (randomNumber + 1) + ", search URL: " + resultURL + "\n" + "Description: " + description);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			throw e;
		}
		finally
		{
		}
		numberOfTimesUserSearched++;
		return readBingWebSearchData(json);
	}
}

