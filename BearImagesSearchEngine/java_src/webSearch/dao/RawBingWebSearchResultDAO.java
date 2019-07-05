package webSearch.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import gateway.ConnectionHelper;
import webSearch.vo.BingWebSearchResultVO;

public class RawBingWebSearchResultDAO extends BingWebSearchResultDAO
{	
	@Override
	protected ArrayList<BingWebSearchResultVO> selectWebResultsData() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
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
		String sql = "CREATE TABLE IF NOT EXISTS RAW_BING_WEB_SEARCH_RESULT (CONTENT_URL TEXT CONSTRAINT CONTENT_URL_UNIQUE UNIQUE ON CONFLICT REPLACE, NAME TEXT, DOMAIN_URL TEXT)";
		
		try
		{
			connection = ConnectionHelper.getConnection();
			statement = connection.createStatement();
		}
		catch (SQLException e)
		{
			printError("createWebResultsTable()", e);
		}
		finally
		{
			statementList = new ArrayList<>();
			statementList.add(statement);
			resolveSQLStatement(statementList);
		}
	}
}
