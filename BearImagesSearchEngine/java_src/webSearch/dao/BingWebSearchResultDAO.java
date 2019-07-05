package webSearch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import webSearch.vo.BingWebSearchResultVO;

public abstract class BingWebSearchResultDAO
{
	protected Connection connection;
	protected Statement statement;
	protected PreparedStatement preparedStatement;
	protected ArrayList<Object> statementList;
	// Delete commented stuff after implementing these in at least one of the
	// inherited tables.
	//protected abstract void createWebResultsTable() throws SQLException;
	
	protected abstract ArrayList<BingWebSearchResultVO> selectWebResultsData() throws SQLException;
	
	protected abstract void insertWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException;

	//protected abstract void updateWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException;
	
	//protected abstract void insertAndUpdateWebResultsData(ArrayList<BingWebSearchResultVO webResultsList> throws SQLException;
	
	//protected abstract void upsertWebResultsData(ArrayList<BingWebSearchResultVO> webResultsList) throws SQLException;

	//protected abstract void deleteWebResultsData() throws SQLException;
	
	//protected abstract void dropWebResultsTable() throws SQLException;
	
	protected void printError(String reason, SQLException e)
	{
		System.out.println("ERROR: " + reason);
		System.out.println(e.getMessage() + "\n");
	}
	
	protected void resolveSQLStatement(ArrayList<Object> statementList)
	{
		for (int i = 0; i < statementList.size(); i++)
		{
			if (statementList.get(i) != null)
			{
				try
				{
					((Connection) statementList.get(i)).close();
				}
				catch (SQLException ignore) {}
				}
		}
	}
}
