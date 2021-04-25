package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin {
	
	private Connection connect()
	{
	Connection con = null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	//Provide the correct details: DBServer/DBName, username, password
	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Admin", "root", "");
	}
	catch (Exception e)
	{e.printStackTrace();}
	return con;
	}
	public String insertItem(String password, String userName, String category, String describe)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for inserting."; }
	// create a prepared statement
	String query = " insert into admin(`userID`,`password`,`userName`,`category`,`describe`)"+ " values (?, ?, ?, ?, ?)";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, 0);
	preparedStmt.setString(2, password);
	preparedStmt.setString(3, userName);
	preparedStmt.setDouble(4, Double.parseDouble(category));
	preparedStmt.setString(5, describe);
	// execute the statement
	
	preparedStmt.execute();
	con.close();
	output = "Inserted successfully";
	}
	catch (Exception e)
	{
	output = "Error while inserting the item.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	public String readItems()
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for reading."; }
	// Prepare the html table to be displayed
	output = "<table border='1'><tr><th>Password</th><th>User Name</th>" +"<th>Category</th>" +"<th>Description</th>" +"<th>Update</th><th>Remove</th></tr>";
	
	String query = "select * from admin";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	
	// iterate through the rows in the result set
	while (rs.next())
	{
	String userID = Integer.toString(rs.getInt("userID"));
	String password = rs.getString("password");
	String userName = rs.getString("userName");
	String category = rs.getString("category");
	String describe = rs.getString("describe");
	
	// Add into the html table
	output += "<tr><td>" + password + "</td>";
	output += "<td>" + userName + "</td>";
	output += "<td>" + category + "</td>";
	output += "<td>" + describe + "</td>";
	
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"+ "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"+ "<input name='userID' type='hidden' value='" + userID+ "'>" + "</form></td></tr>";
	}
	con.close();
	// Complete the html table
	output += "</table>";
	}
	catch (Exception e)
	{
	output = "Error while reading the items.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	public String updateItem(String ID,String password, String userName, String category, String describe)
	
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
		// create a prepared statement
		String query = "UPDATE admin SET password=?,userName=?,category=?,describe=?WHERE userID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, ID);
		preparedStmt.setString(2, password);
		preparedStmt.setString(3, userName);
		preparedStmt.setString(4, category);
		preparedStmt.setInt(5, Integer.parseInt(ID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
		}
		catch (Exception e)
		{
		output = "Error while updating the item.";
		System.err.println(e.getMessage());
		}
		return output;
		}
		public String deleteItem(String userID)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for deleting."; }
		// create a prepared statement
		String query = "delete from admin where userID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(userID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Deleted successfully";
		}
		catch (Exception e)
		{
		output = "Error while deleting the item.";
		System.err.println(e.getMessage());
		}
		return output;
		}

}
