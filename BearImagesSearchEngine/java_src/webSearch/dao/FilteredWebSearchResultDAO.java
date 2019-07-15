package webSearch.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import gateway.ConnectionHelper;
import webSearch.interfaces.IDelete;
import webSearch.vo.BingWebSearchResultVO;

public class FilteredWebSearchResultDAO extends WebSearchResultDAO implements IDelete
{

	@Override
	protected ArrayList<BingWebSearchResultVO> selectWebResultsData() throws SQLException
	{
		ArrayList<BingWebSearchResultVO> list = new ArrayList<BingWebSearchResultVO>();
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		String sql = "SELECT NAME, "
				+ "CONTENT_URL "
				+ "FROM FILTERED_WEB_SEARCH_RESULT";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				BingWebSearchResultVO data = new BingWebSearchResultVO();
				
				data.setName(resultSet.getString(1));
				data.setContentUrl(resultSet.getString(2));
//				data.setContentUrl(resultSet.getString(2));
				
				list.add(data);
			}
		}
		catch (SQLException e)
		{
			printError(FilteredWebSearchResultDAO.class, "selectWebResultsData", e);
		}
		finally
		{
			statementList = new ArrayList<>();
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
		return list;
	}

	@Override
	protected void insertWebResultsData(int i, ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String sql = "INSERT INTO FILTERED_WEB_SEARCH_RESULT "
				+ "(NAME, CONTENT_URL) "
				+ "SELECT NAME, "
				+ "CONTENT_URL "
				+ "FROM RAW_WEB_SEARCH_RESULT "
				+ "as rwsr "
				+ "WHERE NOT EXISTS "
				+ "(SELECT DOMAIN_URL "
				+ "FROM ILLEGAL_WEB_SEARCH_RESULT "
				+ "as iwsr "
				+ "WHERE rwsr.DOMAIN_URL = iwsr.DOMAIN_URL)";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			printError(FilteredWebSearchResultDAO.class, "insertWebResultsData", e);
		}
		finally
		{
			statementList = new ArrayList<>();
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
	
	private void createWebResultsTable() throws SQLException
	{
		connection = null;
		statement = null;
		String sql = "CREATE TABLE IF NOT EXISTS "
				+ "FILTERED_WEB_SEARCH_RESULT "
				+ "(CONTENT_URL TEXT CONSTRAINT CONTENT_URL_UNIQUE UNIQUE ON CONFLICT REPLACE, "
				+ "NAME TEXT)";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			statement = connection.createStatement();
		}
		catch (SQLException e)
		{
			printError(FilteredWebSearchResultDAO.class, "createWebResultsTable", e);
		}
		finally
		{
			statementList.add(statement);
			resolveSQLStatement(statementList);
		}
	}
	
	private ArrayList<BingWebSearchResultVO> readWebResultsData() throws SQLException
	{
		return selectWebResultsData();
	}
	
	private void updateWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String sql = "UPDATE FILTERED_WEB_SEARCH_RESULT "
				+ "SET CONTENT_URL = ?, "
				+ "NAME = ?";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			for (int i = 0; i < webResultsList.size(); i++)
			{
				preparedStatement.setString(1, webResultsList.get(i).getContentUrl());
				preparedStatement.setString(2, webResultsList.get(i).getName());
				
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (SQLException e)
		{
			printError(FilteredWebSearchResultDAO.class, "updateWebResultsData", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
	
	@Override
	public void deleteWebResultsData() throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String sql = "DELETE FROM FILTERED_WEB_SEARCH_RESULT";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			printError(FilteredWebSearchResultDAO.class, "deleteWebResultsData", e);
		}
		finally
		{
			statementList = new ArrayList<>();
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
	
	private void dropWebResultsTable() throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String sql = "DROP TABLE IF EXISTS FILTERED_WEB_SEARCH_RESULT";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			printError(FilteredWebSearchResultDAO.class, "dropWebResultsTable()", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
}
