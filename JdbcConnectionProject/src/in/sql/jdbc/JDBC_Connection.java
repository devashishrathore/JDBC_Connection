package in.sql.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBC_Connection {
	public static void main(String[] args) throws Exception {

		String name = "Devraj";
		String email = "dev@321gmail.com";
		String password = "Dev@321";
		String gender = "male";
		String city = "Mumbai"; // User values from client this values we are storing in Database

		Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver class by external jar file path -> Step (1)
		System.out.println("Driver Class loaded successfully -> Step 1 Done");

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_db", "root", "root"); // Create-Connection
//         By urlPath of installed database and its username and password, If the password or syntax is wrong it will show the exception and its return Connection -> Step (2) 																					
		System.out.println("Successfully database connected -> Step 2 Done");

		// Insert data in the table

//		PreparedStatement ps = con
//				.prepareStatement("insert into register values('dev','dev@gmail.com', 'dev123', 'male', 'Indore')"); // Create-Statement and directly set/put the inserting value
		PreparedStatement psInsert = con.prepareStatement("insert into register values(?,?,?,?,?)"); // Create-Statement
//         By prepareStatement() method or we can also use some different methods like createStatement(), and some similar methods to create a statement and pass SQL query in parameter/argument it will return prepareSatetment -> Step (3)	
		psInsert.setString(1, name);
		psInsert.setString(2, email);
		psInsert.setString(3, password);
		psInsert.setString(4, gender);
		psInsert.setString(5, city); // By using positional Parameter(i.e. questionMark ?,?,?,?,?) and set their
										// position by setString() method

		int i = psInsert.executeUpdate(); // Execute prepared Statement Sql Query
// 		   By using the executeUpdate() method if the statement is like an update in the database it will return an int value and 
//         By using the executeQuery() method if the statement is like to get or retrieve any data from the database it will return ResultSet -> Step (4)

		if (i > 0) { // process the result i.e. check the result output and verify it --> Step (5)
			System.out.println("Successfully insert the register table entry -> Step 5 Done");
		} else {
			System.out.println("Fail to insert the register table entry -> Step 5 Done");
		}

		psInsert.close(); // Close the connection by using close() method from preparedStatement but
//		con.close(); // we can Close the connection by using close() method from Connection also -> Step (6)

		// Retrieve data from the table
		PreparedStatement psResult = con.prepareStatement("select * from register");

		ResultSet rs = psResult.executeQuery(); // get table data by executeQuery() method it will return ResultSet and
												// This ResultSet Interface has methods to retrieve one-by-one data
												// from table
		while (rs.next()) {
			String nameRes = rs.getString("name");
			String emailRes = rs.getString("email");
			String genderRes = rs.getString("gender");
			String cityRes = rs.getString("city");

			System.out.println(
					"Name:- " + nameRes + ", Email:- " + emailRes + ", Gender:- " + genderRes + ", City:- " + cityRes);
		}

		psResult.close();

		// Update data in the table

		String name1 = "Bholenath";
		String email1 = "dev@gmail.com";
		String city1 = "KailashParvat";

		PreparedStatement psUpdate = con.prepareStatement("update register set name=?, city=? where email=?");
//           Update name and city with respected email by using the update query in PreparedStatement 
		psUpdate.setString(1, name1);
		psUpdate.setString(2, city1);
		psUpdate.setString(3, email1);

		int count = psUpdate.executeUpdate();

		if (count > 0) {
			System.out.println("Name and city updated successfully");
		} else {
			System.out.println("Update failed");
		}

		psUpdate.close();

		// Delete data from the table

		String email2 = "dev@321gmail.com";
		PreparedStatement psDelete = con.prepareStatement("delete from register where email=?");
//            Delete the row with the respected email by using the delete query in PreparedStatement 
		psDelete.setString(1, email2);

		int deletedEntry = psDelete.executeUpdate();

		if (deletedEntry > 0) {
			System.out.println("Entry deleted successfully");
		} else {
			System.out.println("delete failed");
		}
	}
}
