package dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Example;
import utility.log.LoggerManager;
import dao.utility.DBConnectionManager;

public class ExampleDBDAO 
{
	private Connection conn;
	
	public void getConnection()
	{
		conn = DBConnectionManager.getInstance().getConnection();
	}
	
	public void insertCapital(model.Example example)
	{
		PreparedStatement prestmt = null;
		
		try {
			getConnection();
            prestmt = conn.prepareStatement("INSERT INTO `stock_2_capital` ("
            		+ "stock_id, "
            		+ "capital_date, "
            		+ "stock_total_volume, "
            		+ "stock_aissue_volume"
            		+ ") VALUES (?, ?, ?, ?)");
            
            prestmt.setString(1,example.getExampleId());
            prestmt.setString(2,example.getAbc());
        	
        	prestmt.executeUpdate();
            
        } catch (Exception ex) {
        	LoggerManager.getInstance().getLogger().error("Insert Example into MySQL Exception");
        	LoggerManager.getInstance().getLogger().error(ex);
        	ex.printStackTrace();
        } finally {
            if (prestmt != null) {
                try {
                    prestmt.close();
                } catch (SQLException sqlEx)
                {
                	LoggerManager.getInstance().getLogger().error("Insert Example into MySQL Exception");
                	LoggerManager.getInstance().getLogger().error(sqlEx);
                	sqlEx.printStackTrace();
                }
                prestmt = null;
            }
        }
	}
	
	public Example getCapitalAtDate(String exampleId)
	{
		Example result = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			getConnection();
			stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from stock_2_capital where "
            		+ " example_id='"+exampleId+"'"
            		+ " order by example_id desc");
            
            rs.next();
            
            result = new Example();
            result.setDbId(rs.getInt("id"));
            result.setExampleId(rs.getString("example_id"));
            result.setAbc(rs.getString("example_abc"));
            
        } catch (Exception ex) {
        	LoggerManager.getInstance().getLogger().error("Get Example from MySQL Exception");
        	LoggerManager.getInstance().getLogger().error(ex);
        	ex.printStackTrace();
        } finally {
        	if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx)
                {
                	LoggerManager.getInstance().getLogger().error("Get Example from MySQL Exception");
                	LoggerManager.getInstance().getLogger().error(sqlEx);
                	sqlEx.printStackTrace();
                }
                rs = null;
            }
        	
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx)
                {
                	LoggerManager.getInstance().getLogger().error("Get Latest Capital from MySQL Exception");
                	LoggerManager.getInstance().getLogger().error(sqlEx);
                	sqlEx.printStackTrace();
                }
                stmt = null;
            }
        }
		
		return result;
	}
}
