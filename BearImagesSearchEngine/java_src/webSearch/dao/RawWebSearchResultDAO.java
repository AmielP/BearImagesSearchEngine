package webSearch.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gateway.ConnectionHelper;
import webSearch.vo.BingWebSearchResultVO;

public class RawWebSearchResultDAO extends WebSearchResultDAO
{	
	@Override
	protected ArrayList<BingWebSearchResultVO> selectWebResultsData() throws SQLException
	{
		ArrayList<BingWebSearchResultVO> list = new ArrayList<BingWebSearchResultVO>();
		
		connection = null;
		preparedStatement = null;
		resultSet = null;
		String sql = "SELECT CONTENT_URL, "
				+ "NAME, "
				+ "DOMAIN_URL "
				+ "FROM RAW_WEB_SEARCH_RESULT";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				BingWebSearchResultVO data = new BingWebSearchResultVO();
				
				data.setContentUrl(resultSet.getString(1));
				data.setName(resultSet.getString(2));
				data.setDomainUrl(resultSet.getString(3));
				
				list.add(data);
			}
		}
		catch (SQLException e)
		{
			printError(RawWebSearchResultDAO.class, "selectWebResultsData", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
		return list;
	}

	@Override
	protected void insertWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}
	
	private void createWebResultsTable() throws SQLException
	{
		connection = null;
		statement = null;
		String sql = "CREATE TABLE IF NOT EXISTS "
				+ "RAW_WEB_SEARCH_RESULT "
				+ "(CONTENT_URL TEXT CONSTRAINT CONTENT_URL_UNIQUE UNIQUE ON CONFLICT REPLACE, "
				+ "NAME TEXT, "
				+ "DOMAIN_URL TEXT)";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			statement = connection.createStatement();
		}
		catch (SQLException e)
		{
			printError(RawWebSearchResultDAO.class, "createWebResultsTable", e);
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
	
	private void insertAndUpdateWebResultsData(boolean isNewDataInTable, ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String insertSql = "INSERT INTO RAW_WEB_SEARCH_RESULT "
				+ "(CONTENT_URL, "
				+ "NAME, "
				+ "DOMAIN_URL) "
				+ "VALUES(?,?,?)";
		String updateSql = "UPDATE RAW_WEB_SEARCH_RESULT "
				+ "SET CONTENT_URL = ?, "
				+ "NAME = ?, "
				+ "DOMAIN_URL = ?";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			
			if (isNewDataInTable)
			{
				preparedStatement = connection.prepareStatement(insertSql);
			}
			else
			{
				preparedStatement = connection.prepareStatement(updateSql);
			}
			
			for (int i = 0; i < webResultsList.size(); i++)
			{
				preparedStatement.setString(1, webResultsList.get(i).getContentUrl());
				preparedStatement.setString(2, webResultsList.get(i).getName());
				preparedStatement.setString(3, webResultsList.get(i).getDomainUrl());
				
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (SQLException e)
		{
			printError(RawWebSearchResultDAO.class, "insertAndUpdateWebResultsData", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
	
	private void updateWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String sql = "UPDATE RAW_WEB_SEARCH_RESULT "
				+ "SET CONTENT_URL = ?, "
				+ "NAME = ?, "
				+ "DOMAIN_URL = ?";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			for (int i = 0; i < webResultsList.size(); i++)
			{
				preparedStatement.setString(1, webResultsList.get(i).getContentUrl());
				preparedStatement.setString(2, webResultsList.get(i).getName());
				preparedStatement.setString(3, webResultsList.get(i).getDomainUrl());
				
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (SQLException e)
		{
			printError(RawWebSearchResultDAO.class, "updateWebResultsData", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
	
	private void deleteWebResultsData() throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String sql = "DELETE FROM RAW_WEB_SEARCH_RESULT";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			printError(RawWebSearchResultDAO.class, "deleteWebResultsData", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
}
