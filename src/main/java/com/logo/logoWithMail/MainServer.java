package com.logo.logoWithMail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;




public class MainServer {
	public static  boolean mainServerState = true; //A variable that checks the state of the main server


	public static void main(String[] args) throws SQLException {

		mainServerState = true;
		Connection connection = initDB();
		startServer(connection);


	}

	public static void startServer (Connection connection) {
		final int port = 1313;

		//Creating a new server
		try  {
			//Send email to the team
			ConfigurableApplicationContext context = SpringApplication.run(EmailToTeam.class);
			context.close();
			
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				System.out.println("Server is listening on port " + port);
				//Running the server non-stop
				while(true) {
					//Receiving clients and handling them 
					Socket clientSocket = serverSocket.accept();
					String clientIP = clientSocket.getInetAddress().toString();
					System.out.println("Client connected from: " + clientIP);

					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

					// Receive a request from the client
					String message = in.readLine();

					System.out.println("Received from client: " + message);
					if(message != null) {

						// Permission to connect
						out.println("Hello, Client send me your Information!");

						// Receiving the information and saving it
						message =  in.readLine();
						System.out.println(message);
						int firstSpaceIndex = message.indexOf(" ");
						String info = message.substring(0 , firstSpaceIndex);


						String insertSQL = "INSERT INTO logService (log_ip, log_message, log_level) VALUES (?, ?, ?)";
						PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
						preparedStatement.setString(1, clientIP);
						preparedStatement.setString(2, message);
						preparedStatement.setString(3, info);
						int rowsAffected = preparedStatement.executeUpdate();
						preparedStatement.close();



					}
					// Close the connections
					in.close();
					out.close();
					clientSocket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static Connection initDB () {
		// JDBC URL, username, and password of MySQL server
		String url = "jdbc:mysql://localhost:3306/logdb";
		String user = "root";
		String password = "PelegSW2102";

		try  {
			Connection connection = DriverManager.getConnection(url, user, password);
			Statement stmt = connection.createStatement();

			//Create table if not exists 
			System.out.println("Connected to the database!");
			String sql = "CREATE TABLE IF NOT EXISTS logService ("
					+ "log_id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "log_ip TEXT, "
					+ "log_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
					+ "log_message TEXT,"
					+ "log_level TEXT"
					+ ")";

			stmt.executeUpdate(sql);
			return connection;    
		} catch (SQLException e) {
			System.err.println("Connection failed. Error: " + e.getMessage());

		}
		return null; 

	}

}
