package webSearch.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import webSearch.vo.BingWebSearchResultVO;

public interface ICRUD
{
	void createWebResultsTable() throws SQLException;
	ArrayList<BingWebSearchResultVO> readWebResultsData() throws SQLException;
	void deleteWebResultsData() throws SQLException;
}
