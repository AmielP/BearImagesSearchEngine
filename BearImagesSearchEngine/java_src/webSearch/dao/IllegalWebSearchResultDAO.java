package webSearch.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import gateway.ConnectionHelper;
import webSearch.vo.BingWebSearchResultVO;

public class IllegalWebSearchResultDAO extends WebSearchResultDAO
{
	@Override
	protected ArrayList<BingWebSearchResultVO> selectWebResultsData() throws SQLException
	{
		ArrayList<BingWebSearchResultVO> list = new ArrayList<BingWebSearchResultVO>();

		connection = null;
		preparedStatement = null;
		resultSet = null;
		String sql = "SELECT DOMAIN_URL "
				+ "FROM ILLEGAL_WEB_SEARCH_RESULT";

		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				BingWebSearchResultVO data = new BingWebSearchResultVO();

				data.setDomainUrl(resultSet.getString(1));

				list.add(data);
			}
		}
		catch (SQLException e)
		{
			printError(IllegalWebSearchResultDAO.class, "selectWebResultsData", e);
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

	}
	
	private void createWebResultsTable() throws SQLException
	{
		connection = null;
		statement = null;
		String sql = "CREATE TABLE IF NOT EXISTS "
				+ "ILLEGAL_WEB_SEARCH_RESULT "
				+ "(DOMAIN_URL TEXT CONSTRAINT DOMAIN_URL_UNIQUE UNIQUE ON CONFLICT REPLACE)";

		try
		{
			connection = ConnectionHelper.getConnection();
			statement = connection.createStatement();
		}
		catch (SQLException e)
		{
			printError(IllegalWebSearchResultDAO.class, "createWebResultsTable", e);
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
		String sql = "UPDATE ILLEGAL_WEB_SEARCH_RESULT SET DOMAIN_URL = ?";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			for (int i = 0; i < webResultsList.size(); i++)
			{
				preparedStatement.setString(1, webResultsList.get(i).getDomainUrl());
			
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (SQLException e)
		{
			printError(IllegalWebSearchResultDAO.class, "updateWebResultsData", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
	
	// If using this upsert, verify sql works
	private void upsertWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException
	{
		connection = null;
		preparedStatement = null;
		String sql = "INSERT INTO ILLEGAL_WEB_SEARCH_RESULT "
				+ "(DOMAIN_URL) "
				+ "VALUES (?) ON CONFLICT(DOMAIN_URL) "
				+ "DO UPDATE SET DOMAIN_URL=excluded.DOMAIN_URL";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < webResultsList.size(); i++)
			{
				preparedStatement.setString(1, webResultsList.get(i).getDomainUrl());
				
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
		catch (SQLException e)
		{
			printError(IllegalWebSearchResultDAO.class, "upsertWebResultsData", e);
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
		String sql = "DELETE FROM ILLEGAL_WEB_SEARCH_RESULT";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.executeLargeUpdate();
		}
		catch (SQLException e)
		{
			printError(IllegalWebSearchResultDAO.class, "deleteWebResultsData", e);
		}
		finally
		{
			statementList.add(preparedStatement);
			resolveSQLStatement(statementList);
		}
	}
}
