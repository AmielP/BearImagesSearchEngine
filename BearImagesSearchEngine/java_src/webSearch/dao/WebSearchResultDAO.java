package webSearch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import webSearch.vo.BingWebSearchResultVO;

public abstract class WebSearchResultDAO
{
	protected Connection connection;
	protected Statement statement;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;
	protected ArrayList<Object> statementList;
	
	// Delete commented stuff after implementing these in at least one of the
	// inherited tables.
	
	// When using inserts, updates, or upsert; change the list size to 150.
	
	// done protected abstract void createWebResultsTable() throws SQLException;
	
	protected abstract ArrayList<BingWebSearchResultVO> selectWebResultsData() throws SQLException;
	
	// Must add a count iterator in order that the web results are inserted in the corresponding row
	// within the length of the jsonArray.
	protected abstract void insertWebResultsData(int jsonArrayIndex, ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException;

	// done protected abstract void updateWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException;
	
	// done protected abstract void insertAndUpdateWebResultsData(ArrayList<BingWebSearchResultVO webResultsList> throws SQLException;
	
	// done protected abstract void upsertWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException;

	// done protected abstract void deleteWebResultsData() throws SQLException;
	
	// done protected abstract void dropWebResultsTable() throws SQLException;
	
	protected void printError(Class<?> className, String reason, SQLException e)
	{
		System.out.println("ERROR: " + className.getSimpleName() + "." + reason);
		System.out.println(e.getMessage() + "\n");
	}
	
	protected void resolveSQLStatement(ArrayList<Object> statementList)
	{
//		statementList = new ArrayList<>();
		for (int i = 0; i < statementList.size(); i++)
		{
			if (statementList.get(i) != null)
			{
				try
				{
					// If using Statement, then need to accommodate for Statement class object
					((PreparedStatement) statementList.get(i)).close();
				}
				catch (SQLException ignore) {}
				}
		}
	}
}
