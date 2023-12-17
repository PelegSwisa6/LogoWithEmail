package com.logo.logoWithMail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
public class MultithreadThing extends Thread{
	final String serverAddress = "localhost"; // Server address
	final int serverPort = 1313; // Server port
	final int secPort = 1312;
	Random random = new Random();
	int numOfLogs = 10; // Choose how many messages each client will send
	
	//simulate logsMessages
	String logsMessages [] = { 
			"CRITICAL System crash detected. Initiating emergency shutdown.",
			"CRITICAL Security breach detected. Locking down the system.",
			"CRITICAL Database corruption detected. Halting all operations.",
			"CRITICAL Server overload. Immediate action required.",
			"CRITICAL Network failure. Isolating affected components.",
			"ERROR Failed to load configuration file.",
			"ERROR Database connection failed.",
			"ERROR Application encountered a critical error and will terminate.",
			"ERROR File not found: 'report.pdf'.",
			"ERROR Invalid input data received.",
			"INFO Application started successfully." ,
			"INFO Application started successfully." ,
			"INFO Database connection established.",
			"INFO Sent a request to the server.", 
			"INFO CPU usage is at 30%.",
			"WARNING Invalid input received from user. Ignoring the request.",
			"WARNING Disk space is running low (10% remaining)." , 
			"WARNING High network latency detected.",
			"WARNING Database connection pool is almost exhausted.", 
			"WARNING Data backup failed. Ensure data integrity."
	};


	public MultithreadThing (int numOfLogs) {
		this.numOfLogs = numOfLogs;
	}

	@Override
	public void run() {
		for( int i = 1; i <= numOfLogs; i++ ) {
			int randomNumber = random.nextInt(logsMessages.length);
			try {
				//Creating a socket to connect with the server
				Socket socket = new Socket(serverAddress, serverPort);
				String info = logsMessages[randomNumber];
				
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				
				// Sending a login request to the Server
				out.println("Hello, Server i want To connect");

				// Receiving a response from the server to continue activity
				String response = in.readLine();
				System.out.println("Server response: " + response);

				// If the server confirms sending the information
				out.println(info);



				Thread.sleep(1000);
				out.close();
				in.close();
				socket.close();
			} catch (IOException | InterruptedException e) {
			
				try {
					//Creating a socket to connect with the server
					Socket socket = new Socket(serverAddress, secPort);
					String info = logsMessages[randomNumber];
					
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					
					// Sending a login request to the client
					out.println("Hello, SecServer i want To connect");

					// Receiving a response from the server to continue activity
					String response = in.readLine();
					System.out.println("SecServer response: " + response);

					
					out.println(info);

					Thread.sleep(1000);
					out.close();
					in.close();
					socket.close();
				} catch (IOException | InterruptedException s) {
					s.printStackTrace();
					
				}
			}
		}
	}
}