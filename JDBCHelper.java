import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCHelper {

		public static void close(Connection x)
		{
			try
			{
				if(x!=null)
				x.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
			
		public static void close(Statement x)
		{
			try
			{
				if(x!=null)
					x.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}	
			
		public static void close(ResultSet x)
		{
			try
			{
				if(x!=null)
				x.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
			
		public static Connection getConnection()
		{		
			/************************************/
			// Enter values
			/************************************/
			String url = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
			String uid = "npai";
			String pwd = "200060280";
			
			Connection con = null;
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url,uid,pwd);
				return con;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				return null;
			} 
			catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}

				
		}
			
	}	

