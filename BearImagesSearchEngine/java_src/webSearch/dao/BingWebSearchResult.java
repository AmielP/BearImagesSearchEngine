package webSearch.dao;

//Courtesy of https://github.com/Azure-Samples/cognitive-services-REST-api-samples/blob/master/java/Search/BingImageSearchv7Quickstart.java

import java.net.*;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.awt.image.BufferedImage;
//import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
//import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gateway.ConnectionHelper;
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

	private static final int NUMBER_OF_MAX_SEARCHES = 50;

	private JsonObject json = null;
	private JsonArray jsonArray;

	private JsonObject jsonObjectResult;

	private SecureRandom secureRandom;


	// Can continue later for testing and fixing subscriptionKey issue
	private void testRandomSecurity()
	{
		secureRandom = new SecureRandom();
		byte bytes[] = new byte[20];
		secureRandom.nextBytes(bytes);
	}

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

	private ArrayList<BingWebSearchResultVO> readBingWebSearchData(JsonObject jO) /*throws IOException*/
	{
		ArrayList<BingWebSearchResultVO> headerList = new ArrayList<BingWebSearchResultVO>();

		jsonArray = jO.getAsJsonArray("value");

		for(int i = 0; i < jsonArray.size(); i++)
		{
			try
			{
				BingWebSearchResultVO bingWebSearchData = new BingWebSearchResultVO();

				jsonObjectResult = jsonArray.get(i).getAsJsonObject();

				// Print each of the URL results
				System.out.println("Result " + (i + 1) + ": " + jsonObjectResult.get("contentUrl").getAsString());
				URL imageData = new URL(jsonObjectResult.get("contentUrl").getAsString());
				ImageIO.read(imageData);
				//			bingWebSearchData.setContentID((i + 1));
				bingWebSearchData.setContentUrl(jsonObjectResult.get("contentUrl").getAsString());
				bingWebSearchData.setName(jsonObjectResult.get("name").getAsString());

				//			String s
				//			
				//			bingWebSearchData.setImageData(jsonObjectResult.get("contentUrl").getAsString());

				//			try
				//			{
				//				BufferedImage bufferedImage = ImageIO.read(new URL(jsonObjectResult.get("contentUrl").getAsString()));
				//				
				//				ImageIO.write(bufferedImage, "jpg", new File("D://BearImages/" + i + ".jpg"));
				//			} 
				//			catch (MalformedURLException e)
				//			{
				//				// TODO Auto-generated catch block
				//				e.printStackTrace();
				//				throw e;
				//			} 
				//			catch (IOException e)
				//			{
				//				// TODO Auto-generated catch block
				//				e.printStackTrace();
				//				throw e;
				//			}

				headerList.add(bingWebSearchData);
			}
			catch (Exception e)
			{
				System.out.println("test");
				i++;
				continue;
			}
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

	// Started working because kept faithful to the SQLite tutorial of creating a table
	public void createWebResultsTable() throws SQLException
	{
		Connection connection = null;
		Statement createTempTablePreparedStatement = null;
		Statement createWebSearchResultTablePreparedStatement = null;

		String createTempTableSql = "CREATE TABLE IF NOT EXISTS TEMP_TABLE (CONTENT_URL TEXT , NAME TEXT, CONSTRAINT CONTENT_URL_UNIQUE UNIQUE (CONTENT_URL) ON CONFLICT REPLACE)";
		String createWebSearchResultSql = "CREATE TABLE IF NOT EXISTS WEB_SEARCH_RESULT ( "
				+ " WEB_SEARCH_RESULT_ID INTEGER PRIMARY KEY, "
				+ " CONTENT_URL TEXT, "
				+ " NAME TEXT, "
				+ " CONSTRAINT CONTENT_URL_UNIQUE UNIQUE (CONTENT_URL) ON CONFLICT REPLACE"
				+ ");";

		//		String sql = "CREATE TABLE IF NOT EXISTS WEB_SEARCH_RESULT ( "
		//				+ " WEB_SEARCH_RESULT_ID INTEGER PRIMARY KEY, "
		//				+ " CONTENT_URL TEXT, "
		//				+ " NAME TEXT, "
		//				+ " IMAGE_DATA BLOB"
		//				+ ")";
		try
		{
			connection = ConnectionHelper.getConnection();
			createTempTablePreparedStatement = connection.createStatement();
			createWebSearchResultTablePreparedStatement = connection.createStatement();
			//			preparedStatement = connection.prepareStatement(sql);
			createTempTablePreparedStatement.execute(createTempTableSql);
			createWebSearchResultTablePreparedStatement.execute(createWebSearchResultSql);
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: createWebResultsTable or createTempResultsTable");
			System.out.println(e.getMessage() + "\n");
		}
		finally
		{
			if (createWebSearchResultTablePreparedStatement != null || createTempTablePreparedStatement != null)
			{
				try
				{
					createTempTablePreparedStatement.close();
					createWebSearchResultTablePreparedStatement.close();
				}
				catch (SQLException ignore) {}
			}
			// Connection is open because my local database and no server is using up the database resources
			//			ConnectionHelper.close(connection);
		}
	}

	public ArrayList<BingWebSearchResultVO> selectWebResultsData() throws SQLException
	{
		ArrayList<BingWebSearchResultVO> list = new ArrayList<BingWebSearchResultVO>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		String sql = "SELECT WEB_SEARCH_RESULT_ID, CONTENT_URL, NAME\r\n" + 
				"FROM WEB_SEARCH_RESULT\r\n" + 
				"WHERE CONTENT_URL NOT IN (SELECT ILLEGAL_CONTENT_URL FROM ILLEGAL_WEBSITE)";
		//		String sql = "SELECT WEB_SEARCH_RESULT_ID, CONTENT_URL, NAME, IMAGE_DATA FROM WEB_SEARCH_RESULT";
		//		String sql = "SELECT WEB_SEARCH_RESULT_ID, IMAGE_DATA, NAME FROM WEB_SEARCH_RESULT";
		//		String sql = "SELECT WEB_SEARCH_RESULT_ID, CONTENT_URL, NAME FROM WEB_SEARCH_RESULT";

		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				BingWebSearchResultVO data = new BingWebSearchResultVO();

				// Can be in any order of setting I want so long as the indexing starts from index 1,
				// the data types match the variable type you are setting,
				// and the index positions in the VO match the datagrid
				data.setContentID(resultSet.getInt(1));
				data.setContentUrl(resultSet.getString(2));
				data.setName(resultSet.getString(3));

				System.out.println("Selecting from " + data.getContentUrl());
				//				data.setImageData(resultSet.getBytes(4));

				//				data.setContentID(resultSet.getInt(1));
				//				data.setImageData(resultSet.getBytes(2));
				//				data.setName(resultSet.getString(3));

				list.add(data);
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: selectWebResultsData");
			System.out.println(e.getMessage() + "\n");
		}
		finally
		{
			if (preparedStatement != null)
			{
				try
				{
					preparedStatement.close();
				}
				catch (SQLException ignore) {}
			}
		}
		return list;
	}

	// Add measures to skip forbidden url addresses in the workplace.
	public void insertWebResultsData(ArrayList<BingWebSearchResultVO> insertList) throws SQLException
	{
		Connection connection = null;
		PreparedStatement tempPreparedStatement = null;
		PreparedStatement legalPreparedStatement = null;
		PreparedStatement illegalPreparedStatement = null;

		ResultSet resultSet = null;
		PreparedStatement selectTempPreparedStatement = null;

		int total = 0;


		//		String legalSql = "INSERT INTO WEB_SEARCH_RESULT(CONTENT_URL, NAME) "
		//				+ "VALUES(?,?) "
		//				+ "UNION SELECT CONTENT_URL, NAME FROM WEB_SEARCH_RESULT "
		//				+ "WHERE CONTENT_URL NOT IN (SELECT ILLEGAL_CONTENT_URL FROM ILLEGAL_WEBSITE)";

		String tempSql = "INSERT INTO TEMP_TABLE (CONTENT_URL, NAME) VALUES (?,?)";

		String legalSql = "INSERT INTO WEB_SEARCH_RESULT (CONTENT_URL, NAME) SELECT CONTENT_URL, NAME FROM TEMP_TABLE as t WHERE NOT EXISTS (SELECT * FROM ILLEGAL_WEBSITE as iw WHERE t.CONTENT_URL = iw.ILLEGAL_CONTENT_URL)";
		//		String legalSql = "INSERT INTO WEB_SEARCH_RESULT (CONTENT_URL, NAME) VALUES (?,?) EXCEPT SELECT ILLEGAL_CONTENT_URL, NAME FROM ILLEGAL_WEBSITE";

		//		String selectTempSql = "SELECT CONTENT_URL, NAME FROM TEMP_TABLE";

		String illegalSql = "INSERT INTO ILLEGAL_WEBSITE (ILLEGAL_CONTENT_URL, NAME) "
				+ "VALUES (?,?)";
		//		int legalCount = 0;
		//		String sql = "INSERT INTO WEB_SEARCH_RESULT (CONTENT_URL, NAME, IMAGE_DATA) "
		//				+ "VALUES (?,?,?)";
		try
		{
			connection = ConnectionHelper.getConnection();
			tempPreparedStatement = connection.prepareStatement(tempSql);
			legalPreparedStatement = connection.prepareStatement(legalSql);
			illegalPreparedStatement = connection.prepareStatement(illegalSql);


			for(int i = 0; i < insertList.size() /*NUMBER_OF_MAX_SEARCHES*/; i++)
			{
				try
				{
					if (total == (NUMBER_OF_MAX_SEARCHES))
					{
						break;
					}
					System.out.println(i + ". new URL(): " + insertList.get(i).getContentUrl());
					// BLOB for later.
					URL imageData = new URL(insertList.get(i).getContentUrl());

					//					BufferedImage bufferedImage = ImageIO.read(imageData);
					//					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					//					ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
					ImageIO.read(imageData);
					//					byte[] imageDataToByteArray = byteArrayOutputStream.toByteArray();

					//					preparedStatement.setInt(1, insertList.get(i).getContentID());
					tempPreparedStatement.setString(1, insertList.get(i).getContentUrl());
					tempPreparedStatement.setString(2, insertList.get(i).getName());
					//					preparedStatement.setBytes(3, imageDataToByteArray);
					//					preparedStatement.setBytes(4, insertList.get(i).getImageData());
					tempPreparedStatement.addBatch();

					total++;
					//					legalCount++;
					System.out.println("TOTAL: " + total);
				}
				//				catch (IOException ioe)
				//				{
				//					// Add link to ILLEGAL_LINK table
				//					// Skip to next link
				////					System.out.println("OOPSIE: image reading problem");
				////					System.out.println(ioe.getMessage());
				////					ioe.printStackTrace(System.out);
				//					i++;
				//					continue;
				//				}
				//				catch (NullPointerException npe)
				//				{
				//					// Add link to ILLEGAL_LINK table
				//					// Skip to next link
				////					System.out.println("OOPSIE: image reading problem");
				////					System.out.println(ioe.getMessage());
				////					ioe.printStackTrace(System.out);
				//					i++;
				//					continue;
				//				}
				catch (Exception e)
				{
					illegalPreparedStatement.setString(1, insertList.get(i).getContentUrl());
					illegalPreparedStatement.setString(2, insertList.get(i).getName());
					System.out.println("SKIPPED (" + i + ".) new URL(): " + insertList.get(i).getContentUrl() + " NAME: " + insertList.get(i).getName());
					illegalPreparedStatement.addBatch();
					i++;
					continue;
				}
			}
			tempPreparedStatement.executeBatch();
			illegalPreparedStatement.executeBatch();

			legalPreparedStatement.executeUpdate();
		}
		catch (ArrayIndexOutOfBoundsException aoe)
		{
			System.out.print("ERROR: Array out of bounds exception");
			aoe.printStackTrace(System.out);
			System.out.println(aoe.getMessage());
		}
		//		catch (IOException ioe)
		//		{
		//			System.out.println("ERROR: image reading problem");
		//			System.out.println(ioe.getMessage());
		//		}
		catch (SQLException sqle)
		{
			System.out.println("ERROR: insertWebResultsData");
			System.out.println(sqle.getMessage() + "\n");
		}
		finally
		{
			if (tempPreparedStatement != null || illegalPreparedStatement != null || legalPreparedStatement != null)
			{
				try
				{
					tempPreparedStatement.close();
					legalPreparedStatement.close();
					illegalPreparedStatement.close();
				}
				catch (SQLException ignore) {}
			}
		}
	}

	public void updateWebResultsData(ArrayList<BingWebSearchResultVO> updateList) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE WEB_SEARCH_RESULT SET CONTENT_URL = ?, "
				+ "NAME = ?, "
				+ "IMAGE_DATA = ? "
				+ "WHERE WEB_SEARCH_RESULT_ID = ?";
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			for(int i = 0; i < /*updateList.size()*/NUMBER_OF_MAX_SEARCHES; i++)
			{
				preparedStatement.setString(1, updateList.get(i).getContentUrl());
				preparedStatement.setString(2, updateList.get(i).getName());
				preparedStatement.setBytes(3, updateList.get(i).getImageData());
				preparedStatement.setInt(4, updateList.get(i).getContentID());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: updateWebResultsData");
			System.out.println(e.getMessage() + "\n");
		}
		finally
		{
			if (preparedStatement != null)
			{
				try
				{
					preparedStatement.close();
				}
				catch (SQLException ignore) {}
			}
		}
	}

	// Basic insert and update using condition statement
	public void insertAndUpdateWebResultsData(ArrayList<BingWebSearchResultVO> insertAndUpdateList, boolean isNewDataInTable) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO WEB_SEARCH_RESULT (CONTENT_URL, NAME, IMAGE_DATA, WEB_SEARCH_RESULT_ID) "
				+ "VALUES (?,?,?,?)";
		String updateSQL = "UPDATE WEB_SEARCH_RESULT SET CONTENT_URL = ?, "
				+ "NAME = ?, "
				+ "IMAGE_DATA = ? "
				+ "WHERE WEB_SEARCH_RESULT_ID = ?";

		try
		{
			connection = ConnectionHelper.getConnection();

			if (isNewDataInTable)
			{
				preparedStatement = connection.prepareStatement(insertSQL);
			}
			else
			{
				preparedStatement = connection.prepareStatement(updateSQL);
			}

			for (int i = 0; i < NUMBER_OF_MAX_SEARCHES; i++)
			{
				preparedStatement.setString(1, insertAndUpdateList.get(i).getContentUrl());
				preparedStatement.setString(2, insertAndUpdateList.get(i).getName());
				preparedStatement.setBytes(3, insertAndUpdateList.get(i).getImageData());
				preparedStatement.setInt(4, insertAndUpdateList.get(i).getContentID());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: insertAndUpdateWebResultsData");
			System.out.println(e.getMessage());
		}
		finally
		{
			if (preparedStatement != null)
			{
				try
				{
					preparedStatement.close();
				}
				catch (SQLException ignore) {}
			}
		}
	}

	// Advanced insert and update using upsert statement
	public void upsertWebResultsData(ArrayList<BingWebSearchResultVO> upsertList) throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO WEB_SEARCH_RESULT (WEB_SEARCH_RESULT_ID, CONTENT_URL, NAME, IMAGE_DATA) "
				+ "VALUES (?,?,?,?) ON CONFLICT(WEB_SEARCH_RESULT_ID) DO UPDATE SET CONTENT_URL=excluded.CONTENT_URL, NAME=excluded.NAME, IMAGE_DATA=excluded.IMAGE_DATA";
		try
		{
			connection = ConnectionHelper.getConnection();
			//						connection.setAutoCommit(false);
			//						connection.commit();

			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0; i < /*upsertList.size()*/NUMBER_OF_MAX_SEARCHES; i++)
			{
				preparedStatement.setInt(1, upsertList.get(i).getContentID());
				preparedStatement.setString(2, upsertList.get(i).getContentUrl());
				preparedStatement.setString(3, upsertList.get(i).getName());
				preparedStatement.setBytes(4, upsertList.get(i).getImageData());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();

			// executeUpdate() is for one record in the database.
			//			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: upsertWebResultsData");
			System.out.println(e.getMessage() + "\n");
		}
		finally
		{
			if (preparedStatement != null)
			{
				try
				{
					preparedStatement.close();
				}
				catch (SQLException ignore) {}
			}
			// Connection is open because my local database and no server is using up the database resources
			//			ConnectionHelper.close(connection);
		}
	}

	public void deleteWebResultsData() throws SQLException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM WEB_SEARCH_RESULT";
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.print("ERROR: deleteWebResultsData");
			System.out.println(e.getMessage() + "\n");
		}
		finally
		{
			if (preparedStatement != null)
			{
				try
				{
					preparedStatement.close();
				}
				catch(SQLException ignore) {}
			}
		}
	}

	public void dropTable() throws SQLException
	{
		Connection connection = null;
		PreparedStatement dropWebSearchResultPreparedStatement = null;
		PreparedStatement dropTempTablePreparedStatement = null;
		String dropWebSearchResultSql = "DROP TABLE IF EXISTS WEB_SEARCH_RESULT";
		String dropTempTableSql = "DROP TABLE IF EXISTS TEMP_TABLE";
		try
		{
			connection = ConnectionHelper.getConnection();
			dropWebSearchResultPreparedStatement = connection.prepareStatement(dropWebSearchResultSql);
			dropTempTablePreparedStatement = connection.prepareStatement(dropTempTableSql);

			dropWebSearchResultPreparedStatement.executeUpdate();

			dropTempTablePreparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.print("ERROR: dropTable");
			System.out.println(e.getMessage() + "\n");
		}
		finally
		{
			if (dropWebSearchResultPreparedStatement != null || dropTempTablePreparedStatement != null)
			{
				try
				{
					dropWebSearchResultPreparedStatement.close();
					dropTempTablePreparedStatement.close();
				}
				catch(SQLException ignore) {}
			}
		}
	}
}

