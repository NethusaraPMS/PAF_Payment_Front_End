package com;

import java.sql.*;

public class Item {

	public Connection connect() 
	{ 
	 Connection conn = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 conn= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ceb", "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return conn; 
	}
    
    
    //method to insert data
    public String insertItem(String cusID, String pType, String pAmount) {
    	
    	
    	String Output = "";
    	
    	try {
    		Connection conn = connect();
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "INSERT INTO pay (cusID,pType,pAmount) VALUES (?,?,?)";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	//preparedStatement.setInt(1, 0); 
        	preparedStatement.setString(1, cusID);
        	preparedStatement.setString(2, pType);
        	preparedStatement.setDouble(3, Double.parseDouble(pAmount));
        	
        	
        	//execute the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	String newItems = readItems(); 
        	 Output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}"; 
        	
        	
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to update data
    public String updateItem(String pID, String cusID, String pType, String pAmount) {
    	
    	
    	String Output = "";
    	
    	try {
    		Connection conn = connect();
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "UPDATE pay SET cusID = ?,pType = ?,pAmount = ? WHERE pID = ?";
        	
        	//binding data to SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setString(1, cusID);
        	preparedStatement.setString(2, pType);
        	preparedStatement.setDouble(3, Double.parseDouble(pAmount));
        	
        	//preparedStatement.setString(5, itemID);
        	preparedStatement.setInt(4, Integer.parseInt(pID)); 
        	
        	//execute the SQL statement
        	preparedStatement.executeUpdate();
        	conn.close();
        	String newItems = readItems(); 
        	
        	 Output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 

        	
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    
    //method to read data
    public String readItems() {
    	Connection conn = connect();
    	
    	String Output = "";
    	
    	try {
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "SELECT * FROM pay";
        	
        	//executing the SQL query
        	Statement statement = conn.createStatement();
        	ResultSet resultSet = statement.executeQuery(query);
        	
        	// Prepare the HTML table to be displayed
    		Output = "<table border=\"1\" class=\"table\"><tr><th>Customer Id</th>"
    		 		+ "<th>Payment Type</th><th>Payment Amount</th>"
    		 		
    		 		+ "<th>Update</th>"
    		 		+ "<th>Remove</th></tr>"; 
        	
        	while(resultSet.next()) {
        		String pID = Integer.toString(resultSet.getInt("pID"));
        		String cusID = resultSet.getString("cusID");
        		String pType = resultSet.getString("pType");
        		String pAmount = Double.toString(resultSet.getDouble("pAmount"));
        		//String itemDesc = resultSet.getString("itemDesc");
        		
        		// Add a row into the HTML table
        		 
        		Output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='"+pID+"'>"+cusID+"</td>";  
        		Output += "<td>" + pType + "</td>"; 
        		Output += "<td>" + pAmount + "</td>"; 
        	
        		
        		// buttons
        		Output += "<td><input name='btnUpdate' type='button' value='Update' "
       				 + "class='btnUpdate btn btn-secondary' data-pID='" + pID + "'></td>"
       				 + "<td><input name='btnRemove' type='button' value='Remove' "
       				 + "class='btnRemove btn btn-danger' data-pID='" + pID + "'></td></tr>";
        				
        	}

        	conn.close();
        	
        	// Complete the HTML table
        	Output += "</table>";
        	
    	} catch(Exception e) {
    		Output = "Failed to read the items";
    		System.err.println(e.getMessage());
    	}
    	
    	return Output;
    }
    
    //method to delete data
    public String deleteItem(String pID) {
    	String Output = "";
    	
    	
    	try {
    		Connection conn = connect();
        	if (conn == null) {
        		return "Database connection error";
        	}
        	
        	//SQL query
        	String query = "DELETE FROM pay WHERE pID = ?";
        	
        	//binding data to the SQL query
        	PreparedStatement preparedStatement = conn.prepareStatement(query);
        	preparedStatement.setInt(1, Integer.parseInt(pID));
        	
        	//executing the SQL statement
        	preparedStatement.execute();
        	conn.close();
        	
        	String newItems = readItems(); 
        	 Output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}"; 
        	
        	
        	
    	} catch(Exception e) {
    		Output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 

    		System.err.println(e.getMessage());
    	}
    	return Output;
    }
}
