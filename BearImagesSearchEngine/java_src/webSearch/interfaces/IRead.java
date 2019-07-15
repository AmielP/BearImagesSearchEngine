package webSearch.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

import webSearch.vo.BingWebSearchResultVO;

public interface IRead
{
	ArrayList<BingWebSearchResultVO> readWebResultsData() throws SQLException;
}
